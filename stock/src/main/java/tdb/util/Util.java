package tdb.util;

import org.bridj.Pointer;

import com.alibaba.fastjson.serializer.TimeZoneCodec;

import tdbapi.TDBDefine_Code;
import tdbapi.TDBDefine_Tick;
import org.bridj.BridJ;
public class Util {
	//byte[]����ת��String
	public static String Bytes2HexString(byte[] b) { 
	    String ret = ""; 
	    for (int i = 0; i < b.length; i++) { 
	        String hex = Integer.toHexString(b[i] & 0xFF); 
	        if (hex.length() == 1) { 
	            hex = '0' + hex; 
	        } 
	        ret += hex.toUpperCase(); 
	    } 
	    return ret; 
	} 

	
	/**
	 * ���� TDBDefine_Code��˫ָ�룬��ӡcode������Ϣ
	 * @param pCodeTable
	 * @param pCount
	 */
	public static void getCodeInfo(Pointer<Pointer<TDBDefine_Code>> pCodeTable,int pCount){
		//TDBDefine_Code codeInfo = pCodeTable.get().get(0); //˫��ָ�� ����ȡ��һ��
		System.out.println("��ô���(AG1312.SHF),����������(ag1312),�г�����(SHF),֤ȯ��������,֤ȯӢ������");
		for(int i=0;i<pCount;i++){
			TDBDefine_Code codeInfo = pCodeTable.get().get(i);
			
			String chWindCode = new String(codeInfo.chWindCode().getBytes()).trim(); //��ô���(AG1312.SHF)
			String chCode = new String(codeInfo.chCode().getBytes()).trim(); //����������(ag1312)
			String chMarket = new String(codeInfo.chMarket().getBytes()).trim(); //�г�����(SHF)
			String chCNName = new String(codeInfo.chCNName().getBytes()).trim();    //֤ȯ��������
			String chENName = new String(codeInfo.chENName().getBytes()).trim();     //֤ȯӢ������
			int nType = codeInfo.nType();  //֤ȯ����
			
			System.out.println(i+" : "+chWindCode+","+chCode+","+chMarket
					+","+chCNName+","+chENName+","+nType);
		}
	}
	
	
	/**
	 * ��ӡtick������Ϣ   java�е� sizeOf(TDBDefine_Tick)��c++�еĴ�С��һ�£�������Ҫ�Լ�д�����ڴ��ַ�Ĵ���
	 * @param pData 
	 * @param pTickCount
	 */
	public static void getTickInfo(Pointer<Pointer<TDBDefine_Tick>> pData,Pointer<Integer> pTickCount){
		//TDBDefine_Tick tickData = pData.get().get(200);  //����ȡ��һ��
		System.out.println("��ô���(AG1312.SHF),����������(ag1312),���ڣ���Ȼ�գ�,ʱ�䣨HHMMSSmmm������94500000 ��ʾ 9��45��00��000����,�ɽ���((a double number + 0.00005) *10000),"
				+ "�ɽ���,�ɽ���(Ԫ),�ɽ�����,IOPV(����)����Ϣ(ծȯ),�ɽ���־,BS��־,�����ۼƳɽ���,���ճɽ���(Ԫ),���((a double number + 0.00005) *10000),���((a double number + 0.00005) *10000),"
				+ "����((a double number + 0.00005) *10000),ǰ����((a double number + 0.00005) *10000),�����((a double number + 0.00005) *10000),�����((a double number + 0.00005) *10000),"
				+ "�ֲ���,��ʵ��,�����((a double number + 0.00005) *10000),��ֲ�,������((a double number + 0.00005) *10000),������,�����((a double number + 0.00005) *10000),"
				+ "������,��Ȩƽ��������(�Ϻ�L2)((a double number + 0.00005) *10000),��Ȩƽ�������(�Ϻ�L2)((a double number + 0.00005) *10000),����Ȩָ��,Ʒ������,����Ʒ����,�µ�Ʒ����,��ƽƷ����,�����ֶ�1,�����ֶ�2,�����ֶ�3");

		BridJ.sizeOf(TDBDefine_Tick.class);
		
		int size = pTickCount.getInt();
		if(size == 0)
			System.out.println("������ticks");
		for(int i=0;i<10;i++){
			
			TDBDefine_Tick tickData = pData.get().get(i);
			
			System.out.println(BridJ.sizeOf(tickData.getClass()));
			
		    String chWindCode = new String(tickData.chWindCode().getBytes());                //��ô���(AG1312.SHF)
		    String chCode = new String(tickData.chCode().getBytes());                    //����������(ag1312)
		    int nDate = tickData.nDate();                          //���ڣ���Ȼ�գ�
		    int nTime =  tickData.nTime();                          //ʱ�䣨HHMMSSmmm������94500000 ��ʾ 9��45��00��000����
		    int nPrice =  tickData.nPrice();                         //�ɽ���((a double number + 0.00005) *10000)
		    long iVolume = tickData.iVolume();                    //�ɽ���
		    long    iTurover = tickData.iTurover();                //�ɽ���(Ԫ)
		    int nMatchItems = tickData.nMatchItems();                    //�ɽ�����
		    int nInterest = tickData.nInterest();                      //IOPV(����)����Ϣ(ծȯ)
		    byte chTradeFlag = tickData.chTradeFlag();                   //�ɽ���־
		    byte chBSFlag = tickData.chBSFlag();                      //BS��־
		    long iAccVolume = tickData.iAccVolume();                 //�����ۼƳɽ���
		    long   iAccTurover = tickData.iAccTurover();             //���ճɽ���(Ԫ)
		    int nHigh = tickData.nHigh();                          //���((a double number + 0.00005) *10000)
		    int nLow = tickData.nLow();                           //���((a double number + 0.00005) *10000)
		    int    nOpen =  tickData.nOpen();                       //����((a double number + 0.00005) *10000)
		    int    nPreClose = tickData.nPreClose();                   //ǰ����((a double number + 0.00005) *10000)
			
			//�ڻ��ֶ�
			int nSettle = tickData.nSettle();                        //�����((a double number + 0.00005) *10000)
			int nPosition = tickData.nPosition();                       //�ֲ���
			int nCurDelta =  tickData.nCurDelta();                      //��ʵ��
			int nPreSettle =  tickData.nPreSettle();                    //�����((a double number + 0.00005) *10000)
			int nPrePosition = tickData.nPrePosition();                  //��ֲ�
	
			//�������ֶ�
			int[]    nAskPrice = tickData.nAskPrice().getInts() ;               //������((a double number + 0.00005) *10000)
			int[] nAskVolume = tickData.nAskVolume().getInts();            //������
			int[]    nBidPrice = tickData.nBidPrice().getInts();               //�����((a double number + 0.00005) *10000)
			int[] nBidVolume =  tickData.nBidVolume().getInts();            //������
			int    nAskAvPrice = tickData.nAskAvPrice();                 //��Ȩƽ��������(�Ϻ�L2)((a double number + 0.00005) *10000)
			int    nBidAvPrice =  tickData.nBidAvPrice();                 //��Ȩƽ�������(�Ϻ�L2)((a double number + 0.00005) *10000)
			long  iTotalAskVolume = tickData.iTotalAskVolume();           //��������(�Ϻ�L2)
			long  iTotalBidVolume = tickData.iTotalBidVolume();           //��������(�Ϻ�L2)
		   
			//������ֶ�ָ��ʹ��
		    int        nIndex = tickData.nIndex();                  //����Ȩָ��
		    int        nStocks = tickData.nStocks();                 //Ʒ������
		    int        nUps = tickData.nUps();                    //����Ʒ����
		    int        nDowns = tickData.nDowns();                  //�µ�Ʒ����
		    int        nHoldLines =  tickData.nHoldLines();              //��ƽƷ����
	
			//�����ֶ�
			int nResv1 = tickData.nResv1();//�����ֶ�1
			int nResv2 =  tickData.nResv2();//�����ֶ�2
			int nResv3  = tickData.nResv3();//�����ֶ�3
			System.out.println(i+" : "+chWindCode+","+chCode+","+nDate+","+nTime+","+nPrice+","+iVolume+","+iTurover+","+nMatchItems+","+nInterest+","+chTradeFlag+","+chBSFlag+","+iAccVolume+","
					+iAccTurover+","+nHigh+","+nLow+","+nOpen+","+nPreClose+","+nSettle+","+nPosition+","+nCurDelta+","+nPreSettle+","+nPrePosition+","+nAskAvPrice+","+nAskVolume+","+nBidPrice+","
					+nBidVolume+","+nAskAvPrice+","+nBidAvPrice+","+iTotalAskVolume+","+iTotalBidVolume+","+nIndex+","+nStocks+","+nUps+","+nDowns+","+nHoldLines+","+nResv1+","+nResv2+","+nResv3);
			System.out.println(tickData.toString());
		}
	}
	
	
}
