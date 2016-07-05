package tdb.main;

import java.util.List;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.Pointer.StringType;

import tdbapi.OPEN_SETTINGS;
import tdbapi.TDBAPILibrary;
import tdbapi.TDBDefine_Code;
import tdbapi.TDBDefine_KLine;
import tdbapi.TDBDefine_ReqTick;
import tdbapi.TDBDefine_ResLogin;
import tdbapi.TDBDefine_Tick;
import tdbapi.TDBDefine_Transaction;
import tdb.domain.Ticks;
import tdb.util.LoginInfo;
import tdb.util.PointerUtil;
import tdb.util.TickInfo;
import tdb.util.Util;

public class NewMethod {
	public static void main(String[] args) {
		
		String kind = "tick";
		
		String ip = "114.80.154.34";
		String port = "6260";
		String user = "TD1066965004";
		String password = "60295306";
		
		String market = "SHF-1-0";
		String code = "ni00.SHF";
		
		String y = "2016";
		String m = "05";
		String d = "05";
		

		Pointer<OPEN_SETTINGS> opsPointer = LoginInfo.getOpsPointer(ip,port,user,password); //设置登录服务器信息
		Pointer<TDBDefine_ResLogin> loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
		Pointer<?> hTdb = null;
		hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer); //登录
		//System.out.println(hTdb.toString());
		
		String[] szMarkets = LoginInfo.getMarkets(loginResPointer); //获取账号对应的市场String[]

		Pointer<Byte> szMarket = Pointer.pointerToBytes(szMarkets[0].getBytes()); //设置 市场(格式:Market-Level-Source(SZ-2-0)),为空请求所有授权市场
		Pointer<Pointer<TDBDefine_Code>> pCodeTable = Pointer.allocatePointer(TDBDefine_Code.class);
		Pointer<Integer> pCount = Pointer.pointerToInt(0);
		
		int getCodeRes = TDBAPILibrary.TDB_GetCodeTable(hTdb, szMarket, pCodeTable, pCount); //根据市场获取对应的code
		System.out.println(getCodeRes+" : "+pCount.getInt());
		
		//Util.getCodeInfo(pCodeTable,pCount.get());//获取codetable信息
		
		
		Pointer<TDBDefine_ReqTick> pReq = TickInfo.getReqTick(code, market, y,m,d); //获取reqTick的指针
		Pointer<Pointer<TDBDefine_Tick>> pData =  Pointer.allocatePointer(TDBDefine_Tick.class);
		Pointer<Integer> pTickCount = Pointer.allocate(Integer.class);
		int getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);
		
/*		System.out.println("TickCount: "+pTickCount.get());
		System.out.println(BridJ.sizeOf(TDBDefine_Tick.class));
		System.out.println(BridJ.sizeOf(long.class));
		System.out.println(BridJ.sizeOf(TDBDefine_Code.class));
		System.out.println(BridJ.sizeOf(TDBDefine_KLine.class));
		System.out.println(BridJ.sizeOf(OPEN_SETTINGS.class));
		System.out.println(BridJ.sizeOf(TDBDefine_ReqTick.class));*/
		//Util.getTickInfo(pData, pTickCount);
		List<Ticks> ticksList = PointerUtil.pointerManual(pData,pTickCount,0);
		for(int i=0;i<ticksList.size();i++){
			System.out.println(ticksList.get(i));
		}
		
		
		//System.out.println(BridJ.sizeOf(TDBDefine_Transaction.class));
		
/*		byte testb= pData.get().get().chTradeFlag();
		System.out.println(testb);
		byte testb2 = pData.get().get().chBSFlag();
		System.out.println(testb2);*/
/*		int[] intArray = pData.get().get().nAskPrice().getInts();
		int[] intArray2 =  pData.get().get().nAskVolume().getInts();
		int[] intArray3 = pData.get().get().nBidPrice().getInts();
		int[] intArray4 = pData.get().get().nBidVolume().getInts();
		for(int i=0;i<intArray.length;i++){
			System.out.println(intArray[i]+","+intArray2[i]+","+intArray3[i]+","+intArray4[i]);
		}*/
		
		TDBAPILibrary.TDB_Close(hTdb);
	}
	

}
