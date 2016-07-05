package tdb.update;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.bridj.Pointer;

import tdb.dao.CreateInsertData;
import tdb.domain.Ticks;
import tdb.util.CodeUtil;
import tdb.util.DateUtil;
import tdb.util.LoginInfo;
import tdb.util.PointerUtil;
import tdb.util.PropertiesUtil;
import tdb.util.TickInfo;
import tdbapi.OPEN_SETTINGS;
import tdbapi.TDBAPILibrary;
import tdbapi.TDBDefine_ReqTick;
import tdbapi.TDBDefine_ResLogin;
import tdbapi.TDBDefine_Tick;

public class UpdateSH {
	public static void main(String[] args) {
		String ip = PropertiesUtil.GetValueByKey("resources/account.properties", "ip");
		String port = PropertiesUtil.GetValueByKey("resources/account.properties", "port");
		String user = PropertiesUtil.GetValueByKey("resources/account.properties", "user3");
		String password = PropertiesUtil.GetValueByKey("resources/account.properties", "password3");

		Pointer<OPEN_SETTINGS> opsPointer = LoginInfo.getOpsPointer(ip,port,user,password); //���õ�¼��������Ϣ
		Pointer<TDBDefine_ResLogin> loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
		Pointer<?> hTdb = null;
		hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer); //��¼
		
		String testMarket = "SH-2-0";
		
		
		
		String startDate = "20160610";
		String endDate = "20160701";
		
		
		
		
		List<Integer> dateList = DateUtil.betweenTwoDayNew(startDate, endDate);
		for(int i=0;i<dateList.size();i++){
			System.out.println(dateList.get(i));
		}
		
		Pointer<TDBDefine_ReqTick> pReq = Pointer.allocate(TDBDefine_ReqTick.class);
		Pointer<Integer> pTickCount = Pointer.allocate(Integer.class);		
		
		List<String> normalCodeList = new LinkedList<String>();
		normalCodeList = CodeUtil.getUpdateSHCodeList();
		
		for(int n=0;n<normalCodeList.size();n++){
			
			String windCode = normalCodeList.get(n);

				int totalSize = 0;
				int totalSize2 = 0;
				
				for(int i=0;i<dateList.size();i++){
					int intDate = dateList.get(i);

						//System.out.println(intDate);
						pReq = TickInfo.getReqTick(windCode, testMarket,intDate); //��ȡreqTick��ָ��
						Pointer<Pointer<TDBDefine_Tick>> pData =  Pointer.allocatePointer(TDBDefine_Tick.class);
	
						int getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);
						
						if(getTickRes == -2 || getTickRes == -1){  //�����������
							System.out.println("������� ���� -2�� -1 : "+getTickRes);
							do{
								Date date=new Date();
								DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String time=format.format(date);
								System.out.println("�Ͽ�����   time: "+time+" date: "+intDate);
								TDBAPILibrary.TDB_Free(pData.get());
								TDBAPILibrary.TDB_Close(hTdb);
								loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
								hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer);
								getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);
							}while(getTickRes != -2 && getTickRes!= -1);
						}
						
						//List<String> ticksList = PointerUtil.pointerManual2String(pData,pTickCount,intDate);
						List<Ticks> ticksList = PointerUtil.pointerManual(pData, pTickCount, intDate);
						System.out.println("windCode: "+windCode+" DATE: "+intDate+" tickSize: "+ticksList.size());
						CreateInsertData.insertData(windCode, ticksList);
						//CreateInsertData.insertDataByString(windCode, ticksList); //ʹ�ö��̴߳�����
						if(ticksList.size() != pTickCount.getInt())
							System.out.println(ticksList.size()+"  !!!!!!!!�����!!!!!!!!  "+pTickCount.getInt());
							
						//System.out.println(i+" : "+intDate +"  ticksListSize: "+ticksList.size());
						totalSize = totalSize + ticksList.size();
						totalSize2 = totalSize2 + pTickCount.getInt();
						
	
						//�ͷ���Դ  Ҫ���ڴ�ᵽ������  ��ס
						TDBAPILibrary.TDB_Free(pData.get());
					
				}

/*				System.out.println(n+" : "+windCode+" totalSize: "+totalSize);
				System.out.println(n+" : "+windCode+" totalSize2: "+totalSize2);
				if(dateList.size() != 0)
				System.out.println(dateList.get(0)+" -- "+dateList.get(dateList.size()-1));*/
			//}
				
		}	
		
		TDBAPILibrary.TDB_Close(hTdb);
		
	}
}
