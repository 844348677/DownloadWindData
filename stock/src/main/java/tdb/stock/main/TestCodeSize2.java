package tdb.stock.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.bridj.Pointer;

import scala.collection.parallel.ParIterableLike.Count;
import tdb.dao.CreateInsertData;
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
public class TestCodeSize2 {
	public static void main(String[] args) {
		String ip = PropertiesUtil.GetValueByKey("resources/account.properties", "ip");
		String port = PropertiesUtil.GetValueByKey("resources/account.properties", "port");
		String user = PropertiesUtil.GetValueByKey("resources/account.properties", "user3");
		String password = PropertiesUtil.GetValueByKey("resources/account.properties", "password3");

		Pointer<OPEN_SETTINGS> opsPointer = LoginInfo.getOpsPointer(ip,port,user,password); //设置登录服务器信息
		Pointer<TDBDefine_ResLogin> loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
		Pointer<?> hTdb = null;
		hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer); //登录
		
/*		String windCode = "601988.SH";
		String testMarket = "SH-2-0";*/

/*		Pointer<TDBDefine_ReqTick> pReq = TickInfo.getReqTick(windCode, testMarket,intDate);
		Pointer<Integer> pTickCount = Pointer.allocate(Integer.class);		
		Pointer<Pointer<TDBDefine_Tick>> pData =  Pointer.allocatePointer(TDBDefine_Tick.class);
		
		int getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);
		List<String> ticksList = PointerUtil.pointerManual2String(pData,pTickCount,intDate);
		System.out.println("windCode: "+windCode+" DATE: "+intDate+" tickSize: "+ticksList.size());*/
		String testMarket = "SH-2-0";
		String startDate = "20110101";
		String endDate = "20160525";
		List<Integer> dateList = DateUtil.betweenTwoDay(startDate, endDate);
/*		for(int i=0;i<dateList.size();i++){
			System.out.println(dateList.get(i));
		}*/
		
		Pointer<TDBDefine_ReqTick> pReq = Pointer.allocate(TDBDefine_ReqTick.class);
		Pointer<Integer> pTickCount = Pointer.allocate(Integer.class);		
		
		String windCode="600111.SH";
		

				
				
				int totalSize = 0;
				int totalSize2 = 0;
				
				for(int i=0;i<dateList.size();i++){
					int intDate = dateList.get(i);
					if(intDate < 20160519){
						//System.out.println(intDate);
						pReq = TickInfo.getReqTick(windCode, testMarket,intDate); //获取reqTick的指针
						Pointer<Pointer<TDBDefine_Tick>> pData =  Pointer.allocatePointer(TDBDefine_Tick.class);
	
						int getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);
						
						if(getTickRes == -2 || getTickRes == -1){  //网络断线重连
							System.out.println("网络错误 等于 -2或 -1 : "+getTickRes);
							do{
								Date date=new Date();
								DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String time=format.format(date);
								System.out.println("断开重连   time: "+time+" date: "+intDate);
								TDBAPILibrary.TDB_Free(pData.get());
								TDBAPILibrary.TDB_Close(hTdb);
								loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
								hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer);
								getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);
							}while(getTickRes != -2 && getTickRes!= -1);
						}
						
						List<String> ticksList = PointerUtil.pointerManual2String(pData,pTickCount,intDate);
						System.out.println("windCode: "+windCode+" DATE: "+intDate+" tickSize: "+ticksList.size());
/*						for(int i=0;i<ticksList.size();i++){
							System.out.println(i+" : "+ticksList.get(i));
						}*/
						
						//CreateInsertData.insertDataByString(windCode, ticksList); //使用多线程存数据
						if(ticksList.size() != pTickCount.getInt())
							System.out.println(ticksList.size()+"  !!!!!!!!不相等!!!!!!!!  "+pTickCount.getInt());
							
						//System.out.println(i+" : "+intDate +"  ticksListSize: "+ticksList.size());
						totalSize = totalSize + ticksList.size();
						totalSize2 = totalSize2 + pTickCount.getInt();
						
	
						//释放资源  要不内存会到最上面  卡住
						TDBAPILibrary.TDB_Free(pData.get());
					}
				}

				System.out.println(" : "+windCode+" totalSize: "+totalSize);
				System.out.println(" : "+windCode+" totalSize2: "+totalSize2);
				if(dateList.size() != 0)
				System.out.println(dateList.get(0)+" -- "+dateList.get(dateList.size()-1));

				

		
		TDBAPILibrary.TDB_Close(hTdb);
		
	}
	
	
}
