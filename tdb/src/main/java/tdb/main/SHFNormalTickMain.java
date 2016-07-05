package tdb.main;

import java.util.Collections;
import java.util.List;

import org.bridj.Pointer;

import tdb.dao.CreateTable;
import tdb.dao.InsertData;
import tdb.domain.CodeDetail;
import tdb.domain.CodeInfo;
import tdb.domain.Ticks;
import tdb.util.CodeUtil;
import tdb.util.DateUtil;
import tdb.util.LoginInfo;
import tdb.util.PointerUtil;
import tdb.util.PropertiesUtil;
import tdb.util.TickInfo;
import tdb.util.Util;
import tdbapi.OPEN_SETTINGS;
import tdbapi.TDBAPILibrary;
import tdbapi.TDBDefine_Code;
import tdbapi.TDBDefine_ReqTick;
import tdbapi.TDBDefine_ResLogin;
import tdbapi.TDBDefine_Tick;

/**
 * 创建normalTick的表  并且插入数据
 * @author liuh
 *
 */
public class SHFNormalTickMain {
	public static void main(String[] args) {
		String ip = PropertiesUtil.GetValueByKey("resources/account.properties", "ip");
		String port = PropertiesUtil.GetValueByKey("resources/account.properties", "port");
		String user = PropertiesUtil.GetValueByKey("resources/account.properties", "user1");
		String password = PropertiesUtil.GetValueByKey("resources/account.properties", "password1");
		
		String testMarket = "SHF-1-0";
		
		
		Pointer<OPEN_SETTINGS> opsPointer = LoginInfo.getOpsPointer(ip,port,user,password); //设置登录服务器信息
		Pointer<TDBDefine_ResLogin> loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
		Pointer<?> hTdb = null;
		hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer); //登录
		
		String szMarketName = testMarket;
		Pointer<Byte> szMarket = Pointer.pointerToBytes(szMarketName.getBytes()); //设置 市场(格式:Market-Level-Source(SZ-2-0)),为空请求所有授权市场
		Pointer<Pointer<TDBDefine_Code>> pCodeTable = Pointer.allocatePointer(TDBDefine_Code.class);
		Pointer<Integer> pCount = Pointer.pointerToInt(0);
		
		int getCodeRes = TDBAPILibrary.TDB_GetCodeTable(hTdb, szMarket, pCodeTable, pCount); //根据市场获取对应的code
		System.out.println(testMarket+" : "+pCount.getInt());
		//Util.getCodeInfo(pCodeTable,pCount.get());//获取codetable信息
		List<CodeInfo> codeList = CodeUtil.getCodes(pCodeTable, pCount.getInt());  //获取CodeInfo的List
		System.out.println("过滤掉 仿真 : "+codeList.size());
		
		CodeDetail codeDetail = CodeUtil.getCodeDetailByList(codeList, szMarketName);
		List<String> varietyList = codeDetail.getVarietyList();
		List<CodeInfo> normalCodeList = codeDetail.getNormalCodeList();
		List<CodeInfo> unNormalCodeList = codeDetail.getUnNormalCodeList();
		Collections.sort(varietyList);
		
		System.out.println("normalCodeList.size: "+normalCodeList.size()); 
		//创建表
		Pointer<TDBDefine_ReqTick> pReq = Pointer.allocate(TDBDefine_ReqTick.class);
		Pointer<Integer> pTickCount = Pointer.allocate(Integer.class);		
		
		for(int n=0;n<normalCodeList.size();n++){
			
			CodeInfo codeInfo = normalCodeList.get(n);
			String windCode = codeInfo.getWindcode();
			//如果没有这张表，创建表，并且插入数据
			if(!CreateTable.existTable(windCode)){ 
				CreateTable.createTableByCode(windCode);
				
				List<Integer> intDateList = DateUtil.ensureNormalDate1(windCode);
				
				int totalSize = 0;
				int totalSize2 = 0;
				
				for(int i=0;i<intDateList.size();i++){
					int intDate = intDateList.get(i);
					if(intDate < 20160519){
						System.out.println(intDate);
						pReq = TickInfo.getReqTick(windCode, testMarket,intDate); //获取reqTick的指针
						Pointer<Pointer<TDBDefine_Tick>> pData =  Pointer.allocatePointer(TDBDefine_Tick.class);
	
						int getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);
						
						if(getTickRes == -2 || getTickRes == -1){
							System.out.println("网络错误 等于 -2或 -1 : "+getTickRes);
							do{
								System.out.println("断开重连");
								TDBAPILibrary.TDB_Free(pData.get());
								TDBAPILibrary.TDB_Close(hTdb);
								loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
								hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer);
								getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);
							}while(getTickRes != -2 && getTickRes!= -1);
						}
						
						List<Ticks> ticksList = PointerUtil.pointerManual(pData,pTickCount,intDate);
/*						for(int i=0;i<ticksList.size();i++){
							System.out.println(i+" : "+ticksList.get(i));
						}*/
			
						InsertData.insertData(windCode, ticksList);
						if(ticksList.size() != pTickCount.getInt())
							System.out.println(ticksList.size()+"  !!!!!!!!不相等!!!!!!!!  "+pTickCount.getInt());
							
						//System.out.println(i+" : "+intDate +"  ticksListSize: "+ticksList.size());
						totalSize = totalSize + ticksList.size();
						totalSize2 = totalSize2 + pTickCount.getInt();
						
	
						//释放资源  要不内存会到最上面  卡住
						TDBAPILibrary.TDB_Free(pData.get());
					}
				}

				System.out.println(n+" : "+windCode+" totalSize: "+totalSize);
				System.out.println(n+" : "+windCode+" totalSize2: "+totalSize2);
				if(intDateList.size() != 0)
				System.out.println(intDateList.get(0)+" -- "+intDateList.get(intDateList.size()-1));
			}
				
		}	
		
		TDBAPILibrary.TDB_Close(hTdb);
	}
	
	
}
