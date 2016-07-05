package tdb.main;

import java.util.Collections;
import java.util.List;

import org.bridj.Pointer;

import tdb.dao.CreateTable;
import tdb.dao.InsertData;
import tdb.domain.CodeDetail;
import tdb.domain.CodeInfo;
import tdb.domain.Ticks;
import tdb.main.thread.ThreadTicks;
import tdb.util.CodeUtil;
import tdb.util.DateUtil;
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

public class NewThreadDownload {

	public static void main(String[] args) throws InterruptedException {
		String ip = PropertiesUtil.GetValueByKey("resources/account.properties", "ip");
		String port = PropertiesUtil.GetValueByKey("resources/account.properties", "port");
		String user = PropertiesUtil.GetValueByKey("resources/account.properties", "user1");
		String password = PropertiesUtil.GetValueByKey("resources/account.properties", "password1");
		
		String testMarket = "SHF-1-0";
		
		
		Pointer<OPEN_SETTINGS> opsPointer = LoginInfo.getOpsPointer(ip,port,user,password); //���õ�¼��������Ϣ
		Pointer<TDBDefine_ResLogin> loginResPointer = Pointer.allocate(TDBDefine_ResLogin.class);
		Pointer<?> hTdb = null;
		hTdb = TDBAPILibrary.TDB_Open(opsPointer, loginResPointer); //��¼
		
		String szMarketName = testMarket;
		Pointer<Byte> szMarket = Pointer.pointerToBytes(szMarketName.getBytes()); //���� �г�(��ʽ:Market-Level-Source(SZ-2-0)),Ϊ������������Ȩ�г�
		Pointer<Pointer<TDBDefine_Code>> pCodeTable = Pointer.allocatePointer(TDBDefine_Code.class);
		Pointer<Integer> pCount = Pointer.pointerToInt(0);
		
		int getCodeRes = TDBAPILibrary.TDB_GetCodeTable(hTdb, szMarket, pCodeTable, pCount); //�����г���ȡ��Ӧ��code
		System.out.println(testMarket+" : "+pCount.getInt());
		//Util.getCodeInfo(pCodeTable,pCount.get());//��ȡcodetable��Ϣ
		List<CodeInfo> codeList = CodeUtil.getCodes(pCodeTable, pCount.getInt());  //��ȡCodeInfo��List
		System.out.println("���˵� ���� : "+codeList.size());
		
		CodeDetail codeDetail = CodeUtil.getCodeDetailByList(codeList, szMarketName);
		List<String> varietyList = codeDetail.getVarietyList();
		List<CodeInfo> normalCodeList = codeDetail.getNormalCodeList();
		List<CodeInfo> unNormalCodeList = codeDetail.getUnNormalCodeList();
		Collections.sort(varietyList);
		
		System.out.println("normalCodeList.size: "+normalCodeList.size()); 
		//������
		
        ThreadTicks tt1 = new ThreadTicks(hTdb, opsPointer, normalCodeList);
/*        ThreadTicks tt2 = new ThreadTicks(hTdb, opsPointer, normalCodeList);
        ThreadTicks tt3 = new ThreadTicks(hTdb, opsPointer, normalCodeList);
        ThreadTicks tt4 = new ThreadTicks(hTdb, opsPointer, normalCodeList);*/
        
        Thread t1 = new Thread(tt1);  
/*        Thread t2 = new Thread(tt2);  
        Thread t3 = new Thread(tt3);  
        Thread t4 = new Thread(tt4);  */
  
        t1.start();
/*        t2.start();
		t3.start();
		t4.start();*/
		
		
		
		TDBAPILibrary.TDB_Close(hTdb);
	}
	
}
