package tdb.main.thread;

import java.util.List;

import org.bridj.Pointer;

import tdb.dao.CreateTable;
import tdb.dao.InsertData;
import tdb.domain.CodeInfo;
import tdb.util.DateUtil;
import tdb.util.PointerUtil;
import tdb.util.TickInfo;
import tdbapi.OPEN_SETTINGS;
import tdbapi.TDBAPILibrary;
import tdbapi.TDBDefine_ReqTick;
import tdbapi.TDBDefine_ResLogin;
import tdbapi.TDBDefine_Tick;

public class ThreadTicks implements Runnable {

	private Pointer<?> hTdb;
	private Pointer<OPEN_SETTINGS> opsPointer;
	private List<CodeInfo> normalCodeList;
	
	
	public ThreadTicks(Pointer<?> hTdb,Pointer<OPEN_SETTINGS> opsPointer,List<CodeInfo> normalCodeList) {  
		this.hTdb = hTdb;
		this.opsPointer = opsPointer;
        this.normalCodeList = normalCodeList;  
    }  
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String testMarket = "SHF-1-0";
		for(int n=0;n<normalCodeList.size();n++){
			
			Pointer<TDBDefine_ReqTick> pReq = Pointer.allocate(TDBDefine_ReqTick.class);
			Pointer<Integer> pTickCount = Pointer.allocate(Integer.class);		
			
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
								Pointer<TDBDefine_ResLogin> loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
								hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer);
								getTickRes = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pTickCount);
							}while(getTickRes != -2 && getTickRes!= -1);
						}
						
						List<String> ticksList = PointerUtil.pointerManual2String(pData,pTickCount,intDate);
/*						for(int i=0;i<ticksList.size();i++){
							System.out.println(i+" : "+ticksList.get(i));
						}*/
			
						InsertData.insertDataByString(windCode, ticksList);
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
	}

}
