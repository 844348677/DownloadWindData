package tdb.stock.main;

import java.util.List;

import org.bridj.Pointer;

import tdb.domain.CodeInfo;
import tdb.save.SaveCodeTable;
import tdb.util.CodeUtil;
import tdb.util.LoginInfo;
import tdb.util.PropertiesUtil;
import tdbapi.OPEN_SETTINGS;
import tdbapi.TDBAPILibrary;
import tdbapi.TDBDefine_Code;
import tdbapi.TDBDefine_ResLogin;

public class SaveCodeInfo {
	public static void main(String[] args) {
		String ip = PropertiesUtil.GetValueByKey("resources/account.properties", "ip");
		String port = PropertiesUtil.GetValueByKey("resources/account.properties", "port");
		String user = PropertiesUtil.GetValueByKey("resources/account.properties", "user3");
		String password = PropertiesUtil.GetValueByKey("resources/account.properties", "password3");
		
		Pointer<OPEN_SETTINGS> opsPointer = LoginInfo.getOpsPointer(ip,port,user,password); //设置登录服务器信息
		Pointer<TDBDefine_ResLogin> loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
		Pointer<?> hTdb = null;
		hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer); //登录
		
		String[] szMarkets = LoginInfo.getMarkets(loginResPointer); //获取账号对应的市场String[]
		
		for(int i=0;i<szMarkets.length;i++){
			String szMarketName = szMarkets[i];
			Pointer<Byte> szMarket = Pointer.pointerToBytes(szMarketName.getBytes()); //设置 市场(格式:Market-Level-Source(SZ-2-0)),为空请求所有授权市场
			Pointer<Pointer<TDBDefine_Code>> pCodeTable = Pointer.allocatePointer(TDBDefine_Code.class);
			Pointer<Integer> pCount = Pointer.pointerToInt(0);
			
			int getCodeRes = TDBAPILibrary.TDB_GetCodeTable(hTdb, szMarket, pCodeTable, pCount); //根据市场获取对应的code
			System.out.println(i+" : "+pCount.getInt());
			//String id0 = PropertiesUtil.GetValueByKey("resources/marketID.properties", szMarketName); //读配置文件  之后需要改成读数据库
			
			//SaveCodeTable.saveCodeTableByString(pCodeTable, pCount.getInt(),szMarketName);
/*			List<CodeInfo> codeList = CodeUtil.getCodes(pCodeTable, pCount.getInt());  //获取CodeInfo的List

			for(int j=0;j<codeList.size();j++){
				System.out.println(codeList.get(j));
			}*/
			
		}
		
	}
}
