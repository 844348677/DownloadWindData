package tdb.util;

import org.bridj.Pointer;

import tdbapi.TDBDefine_ReqTick;

public class TickInfo {
	/*
	 * struct TDBDefine_ReqTick
		{
		    char chCode[32];    //֤ȯ��ô���(AG1312.SHF)
			char chMarketKey[24];		//�г�����,�磺SH-1-0;SZ-2-0
		    int  nDate;    //��ʼ���ڣ������գ�,Ϊ0��ӽ��죬��ʽ��YYMMDD������20130101��ʾ2013��1��1��
		    int  nBeginTime;    //��ʼʱ�䣺��<=0���ͷ����ʽ����HHMMSSmmm������94500000 ��ʾ 9��45��00��000����
		    int  nEndTime;      //����ʱ�䣺��<=0�������
		
			int nAutoComplete;  //�Զ������־:( 0�����Զ����룬1:�Զ����룩
		};
	 */
	/**
	 * ��ȡtick�� ��������ֵ  ��ȡTDBDefine_ReqTick��ָ��
	 * @param szCode
	 * @param szMarket
	 * @param y
	 * @param m
	 * @param d
	 * @return
	 */
	public static Pointer<TDBDefine_ReqTick> getReqTick(String szCode,String szMarket,String y,String m,String d){ //��ȡ������Ϣ
		Pointer<TDBDefine_ReqTick> resPointer = Pointer.allocate(TDBDefine_ReqTick.class);
		TDBDefine_ReqTick reqTick = resPointer.get();
		
		Pointer<Byte > codePointer = reqTick.chCode();
		codePointer.setArray(szCode.getBytes());
		
		Pointer<Byte > marketKeyPointer = reqTick.chMarketKey();
		marketKeyPointer.setArray(szMarket.getBytes());
		
		int intDate = Integer.parseInt(y)*10000 + Integer.parseInt(m)*100 + Integer.parseInt(d); //����ʱ��  ��ʼ���ڣ������գ�,Ϊ0��ӽ��죬��ʽ��YYMMDD������20130101��ʾ2013��1��1��
		reqTick.nDate(intDate);

		
		reqTick.nBeginTime(80000000);
		reqTick.nEndTime(160000000);
		
		return resPointer;
	}
	public static Pointer<TDBDefine_ReqTick> getReqTick(String szCode,String szMarket,int ymd){ //��ȡ������Ϣ
		Pointer<TDBDefine_ReqTick> resPointer = Pointer.allocate(TDBDefine_ReqTick.class);
		TDBDefine_ReqTick reqTick = resPointer.get();
		
		Pointer<Byte > codePointer = reqTick.chCode();
		codePointer.setArray(szCode.getBytes());
		
		Pointer<Byte > marketKeyPointer = reqTick.chMarketKey();
		marketKeyPointer.setArray(szMarket.getBytes());
		
		//int intDate = Integer.parseInt(y)*10000 + Integer.parseInt(m)*100 + Integer.parseInt(d); //����ʱ��  ��ʼ���ڣ������գ�,Ϊ0��ӽ��죬��ʽ��YYMMDD������20130101��ʾ2013��1��1��
		reqTick.nDate(ymd);

		
		reqTick.nBeginTime(80000000);
		reqTick.nEndTime(160000000);
		
		return resPointer;
	}
}
