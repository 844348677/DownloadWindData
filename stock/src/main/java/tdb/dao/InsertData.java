package tdb.dao;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bridj.Pointer;
import org.hibernate.Query;
import org.hibernate.Session;

import tdb.domain.Ticks;
import tdb.util.DateUtil;
import tdb.util.HibernateSessionFactory;
import tdb.util.LoginInfo;
import tdb.util.PointerUtil;
import tdb.util.PropertiesUtil;
import tdb.util.TickInfo;
import tdbapi.OPEN_SETTINGS;
import tdbapi.TDBAPILibrary;
import tdbapi.TDBDefine_Code;
import tdbapi.TDBDefine_ReqTick;
import tdbapi.TDBDefine_ResLogin;
import tdbapi.TDBDefine_Tick;

public class InsertData {

	public final static String insertData = " (ID,CHWINDCODE,CHCODE,NDATE,NTIME,NPRICE,IVOLUME,ITUROVER,NMATCHITEMS,NINTEREST,CHTRADEFLAG,CHBSFLAG,IACCVOLUME,IACCTUROVER,"+
        "NHIGH,NLOW,NOPEN,NPRECLOSE,NSETTLE,NPOSITION,NCURDELTA,NPRESETTLE,NPREPOSITION,NASKPRICE0,NASKPRICE1,NASKPRICE2,NASKPRICE3,NASKPRICE4,NASKPRICE5,NASKPRICE6,NASKPRICE7,"+
        "NASKPRICE8,NASKPRICE9,NASKVOLUME0,NASKVOLUME1,NASKVOLUME2,NASKVOLUME3,NASKVOLUME4,NASKVOLUME5,NASKVOLUME6,NASKVOLUME7,NASKVOLUME8,NASKVOLUME9,NBIDPRICE0,NBIDPRICE1,NBIDPRICE2,"+
        "NBIDPRICE3,NBIDPRICE4,NBIDPRICE5,NBIDPRICE6,NBIDPRICE7,NBIDPRICE8,NBIDPRICE9,NBIDVOLUME0,NBIDVOLUME1,NBIDVOLUME2,NBIDVOLUME3,NBIDVOLUME4,NBIDVOLUME5,NBIDVOLUME6,NBIDVOLUME7,"+
        "NBIDVOLUME8,NBIDVOLUME9,NASKAVPRICE,NBIDAVPRICE,ITOTALASKVOLUME,ITOTALBIDVOLUME,NINDEX,NSTOCKS,NUPS,NDOWNS,NHOLDLINES,NRESV1,NRESV2,NRESV3) ";
	
	
	public static void insertData(String code ,List<Ticks> ticksList){
		
		
		String[] splitString = code.split("[.]");
		//System.out.println(splitString[1].toUpperCase()+","+splitString[0].toUpperCase());
		String tableName = splitString[1].toUpperCase() + splitString[0].toUpperCase();
		
		// 获取Session
		Session session = HibernateSessionFactory.currentSession(); //sf.openSession();
		// 开启事务
		session.beginTransaction();

		//第一次插入，添加条目数
		//long index = 100000001 ;
		long index = Long.parseLong(PropertiesUtil.GetValueByKey("resources/ticksTable.properties", tableName)) ;
		for(int i=0;i<ticksList.size();i++){
			index = index + 1;
			Ticks tick = ticksList.get(i);
			String id = index +"";
			String chWindCode = "'"+tick.getChWindCode()+"'";
			String chCode = "'"+tick.getChCode()+"'";
			String nDate = "'"+tick.getnDate()+"'";
			String nTime = tick.getnTime()+"";
			String nPrice = tick.getnPrice()+"";
			String iVolume = tick.getiVolume()+"";
			String iTurover = tick.getiTurover()+"";
			String nMatchItems = tick.getnMatchItems()+"";
			String nInterest = tick.getnInterest()+"";
			String chTradeFlag = "'"+tick.getChTradeFlag()+"'";
			String chBSFlag = "'"+tick.getChBSFlag()+"'";
			String iAccVolume = tick.getiAccVolume()+"";
			String iAccTurover = tick.getiAccTurover()+"";
			String nHigh = tick.getnHigh()+"";
			String nLow = tick.getnLow()+"";
			String nOpen = tick.getnOpen()+"";
			String nPreClose = tick.getnPreClose()+"";
			String nSettle = tick.getnSettle()+"";
			String nPosition = tick.getnPosition()+"";
			String nCurDelta = tick.getnCurDelta()+"";
			String nPreSettle = tick.getnPreSettle()+"";
			String nPrePosition = tick.getnPrePosition()+"";
			String nAskPrice0 = tick.getnAskPrice0()+"";
			String nAskPrice1 = tick.getnAskPrice1()+"";
			String nAskPrice2 = tick.getnAskPrice2()+"";
			String nAskPrice3 = tick.getnAskPrice3()+"";
			String nAskPrice4 = tick.getnAskPrice4()+"";
			String nAskPrice5 = tick.getnAskPrice5()+"";
			String nAskPrice6 = tick.getnAskPrice6()+"";
			String nAskPrice7 = tick.getnAskPrice7()+"";
			String nAskPrice8 = tick.getnAskPrice8()+"";
			String nAskPrice9 = tick.getnAskPrice9()+"";
			String nAskVolume0 = tick.getnAskVolume0()+"";
			String nAskVolume1 = tick.getnAskVolume1()+"";
			String nAskVolume2 = tick.getnAskVolume2()+"";
			String nAskVolume3 = tick.getnAskVolume3()+"";
			String nAskVolume4 = tick.getnAskVolume4()+"";
			String nAskVolume5 = tick.getnAskVolume5()+"";
			String nAskVolume6 = tick.getnAskVolume6()+"";
			String nAskVolume7 = tick.getnAskVolume7()+"";
			String nAskVolume8 = tick.getnAskVolume8()+"";
			String nAskVolume9 = tick.getnAskVolume9()+"";
			String nBidPrice0 = tick.getnBidPrice0()+"";
			String nBidPrice1 = tick.getnBidPrice1()+"";
			String nBidPrice2 = tick.getnBidPrice2()+"";
			String nBidPrice3 = tick.getnBidPrice3()+"";
			String nBidPrice4 = tick.getnBidPrice4()+"";
			String nBidPrice5 = tick.getnBidPrice5()+"";
			String nBidPrice6 = tick.getnBidPrice6()+"";
			String nBidPrice7 = tick.getnBidPrice7()+"";
			String nBidPrice8 = tick.getnBidPrice8()+"";
			String nBidPrice9 = tick.getnBidPrice9()+"";
			String nBidVolume0 = tick.getnBidVolume0()+"";
			String nBidVolume1 = tick.getnBidVolume1()+"";
			String nBidVolume2 = tick.getnBidVolume2()+"";
			String nBidVolume3 = tick.getnBidVolume3()+"";
			String nBidVolume4 = tick.getnBidVolume4()+"";
			String nBidVolume5 = tick.getnBidVolume5()+"";
			String nBidVolume6 = tick.getnBidVolume6()+"";
			String nBidVolume7 = tick.getnBidVolume7()+"";
			String nBidVolume8 = tick.getnBidVolume8()+"";
			String nBidVolume9 = tick.getnBidVolume9()+"";
			String nAskAvPrice = tick.getnAskAvPrice()+"";
			String nBidAvPrice = tick.getnBidAvPrice()+"";
			String iTotalAskVolume = tick.getiTotalAskVolume()+"";
			String iTotalBidVolume = tick.getiTotalBidVolume()+"";
			String nIndex = tick.getnIndex()+"";
			String nStocks = tick.getnStocks()+"";
			String nUps = tick.getnUps()+"";
			String nDowns = tick.getnDowns()+"";
			String nHoldLines = tick.getnHoldLines()+"";
			String nResv1 = tick.getnResv1()+"";
			String nResv2 = tick.getnResv2()+"";
			String nResv3 = tick.getnResv3()+"";
			String value = "("+id+","+chWindCode+","+chCode+","+nDate+","+nTime+","+nPrice+","+iVolume+","+iTurover+","+nMatchItems+","+nInterest+","+chTradeFlag+","+chBSFlag+","+
					iAccVolume+","+iAccTurover+","+nHigh+","+nLow+","+nOpen+","+nPreClose+","+nSettle+","+nPosition+","+nCurDelta+","+nPreSettle+","+nPrePosition+","+nAskPrice0+","+
					nAskPrice1+","+nAskPrice2+","+nAskPrice3+","+nAskPrice4+","+nAskPrice5+","+nAskPrice6+","+nAskPrice7+","+nAskPrice8+","+nAskPrice9+","+nAskVolume0+","+nAskVolume1+","+
					nAskVolume2+","+nAskVolume3+","+nAskVolume4+","+nAskVolume5+","+nAskVolume6+","+nAskVolume7+","+nAskVolume8+","+nAskVolume9+","+nBidPrice0+","+nBidPrice1+","+
					nBidPrice2+","+nBidPrice3+","+nBidPrice4+","+nBidPrice5+","+nBidPrice6+","+nBidPrice7+","+nBidPrice8+","+nBidPrice9+","+nBidVolume0+","+nBidVolume1+","+
					nBidVolume2+","+nBidVolume3+","+nBidVolume4+","+nBidVolume5+","+nBidVolume6+","+nBidVolume7+","+nBidVolume8+","+nBidVolume9+","+nAskAvPrice+","+nBidAvPrice+","+
					iTotalAskVolume+","+iTotalBidVolume+","+nIndex+","+nStocks+","+nUps+","+nDowns+","+nHoldLines+","+nResv1+","+nResv2+","+nResv3+")";
					
					
			String insertSQL = "INSERT INTO "+tableName+" "+insertData+" VALUES "+value;
			Query queryCreate = session.createSQLQuery(insertSQL);
			queryCreate.executeUpdate();
			
			 if(i%50==0){   //每一千条刷新并写入数据库  
	                session.flush();  
	                session.clear();  
	          }  
		}
		
		// 提交事务
		session.getTransaction().commit();
		// 关闭连接
		//session.close();
		try {
			PropertiesUtil.WriteProperties("resources/ticksTable.properties", tableName, index+"");
		} catch (IOException e) {
			System.out.println("create table write into ticksTable.properties problem");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//insertData("ag1604.SHF",new ArrayList<Ticks>());
		System.out.println(CreateTable.existTable("ag1604.SHF"));
		if(!CreateTable.existTable("ag1604.SHF"))
			CreateTable.createTableByCode("ag1604.SHF");
		
		String ip = PropertiesUtil.GetValueByKey("resources/account.properties", "ip");
		String port = PropertiesUtil.GetValueByKey("resources/account.properties", "port");
		String user = PropertiesUtil.GetValueByKey("resources/account.properties", "user1");
		String password = PropertiesUtil.GetValueByKey("resources/account.properties", "password1");
		
		Pointer<OPEN_SETTINGS> opsPointer = LoginInfo.getOpsPointer(ip,port,user,password); //设置登录服务器信息
		Pointer<TDBDefine_ResLogin> loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
		Pointer<?> hTdb = null;
		hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer); //登录
		
		Pointer<TDBDefine_ReqTick> pReq = Pointer.allocate(TDBDefine_ReqTick.class);
		Pointer<Integer> pTickCount = Pointer.allocate(Integer.class);


		pReq = TickInfo.getReqTick("ag1604.SHF", "SHF-1-0",20160408); //获取reqTick的指针
		Pointer<Pointer<TDBDefine_Tick>> pData =  Pointer.allocatePointer(TDBDefine_Tick.class);

		int getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);

		List<Ticks> ticksList = PointerUtil.pointerManual(pData,pTickCount,0);
		insertData("ag1604.SHF",ticksList);
		
		System.out.println(pTickCount.getInt());
		
	}
	
	public static void insertDataByString(String code ,List<String> ticksList){
		
		
		String[] splitString = code.split("[.]");
		//System.out.println(splitString[1].toUpperCase()+","+splitString[0].toUpperCase());
		String tableName = splitString[1].toUpperCase() + splitString[0].toUpperCase();
		
		// 获取Session
		Session session = HibernateSessionFactory.currentSession(); //sf.openSession();
		// 开启事务
		session.beginTransaction();

		//第一次插入，添加条目数
		//long index = 100000001 ;
		long index = Long.parseLong(PropertiesUtil.GetValueByKey("resources/ticksTable.properties", tableName)) ;
		for(int i=0;i<ticksList.size();i++){
			index = index + 1;
			String tickString = ticksList.get(i);
			String id = index +"";

			String value = "("+id+tickString;
					
			String insertSQL = "INSERT INTO "+tableName+" "+insertData+" VALUES "+value;
			Query queryCreate = session.createSQLQuery(insertSQL);
			queryCreate.executeUpdate();
			
			 if(i%50==0){   //每一千条刷新并写入数据库  
	                session.flush();  
	                session.clear();  
	          }  
		}
		
		// 提交事务
		session.getTransaction().commit();
		// 关闭连接
		//session.close();
		try {
			PropertiesUtil.WriteProperties("resources/ticksTable.properties", tableName, index+"");
		} catch (IOException e) {
			System.out.println("create table write into ticksTable.properties problem");
			e.printStackTrace();
		}
	}
	
}
