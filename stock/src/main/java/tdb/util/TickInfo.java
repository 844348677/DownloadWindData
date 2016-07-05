package tdb.util;

import org.bridj.Pointer;

import tdbapi.TDBDefine_ReqTick;

public class TickInfo {
	/*
	 * struct TDBDefine_ReqTick
		{
		    char chCode[32];    //证券万得代码(AG1312.SHF)
			char chMarketKey[24];		//市场设置,如：SH-1-0;SZ-2-0
		    int  nDate;    //开始日期（交易日）,为0则从今天，格式：YYMMDD，例如20130101表示2013年1月1日
		    int  nBeginTime;    //开始时间：若<=0则从头，格式：（HHMMSSmmm）例如94500000 表示 9点45分00秒000毫秒
		    int  nEndTime;      //结束时间：若<=0则至最后
		
			int nAutoComplete;  //自动补齐标志:( 0：不自动补齐，1:自动补齐）
		};
	 */
	/**
	 * 获取tick的 根据属性值  获取TDBDefine_ReqTick的指针
	 * @param szCode
	 * @param szMarket
	 * @param y
	 * @param m
	 * @param d
	 * @return
	 */
	public static Pointer<TDBDefine_ReqTick> getReqTick(String szCode,String szMarket,String y,String m,String d){ //获取各种信息
		Pointer<TDBDefine_ReqTick> resPointer = Pointer.allocate(TDBDefine_ReqTick.class);
		TDBDefine_ReqTick reqTick = resPointer.get();
		
		Pointer<Byte > codePointer = reqTick.chCode();
		codePointer.setArray(szCode.getBytes());
		
		Pointer<Byte > marketKeyPointer = reqTick.chMarketKey();
		marketKeyPointer.setArray(szMarket.getBytes());
		
		int intDate = Integer.parseInt(y)*10000 + Integer.parseInt(m)*100 + Integer.parseInt(d); //设置时间  开始日期（交易日）,为0则从今天，格式：YYMMDD，例如20130101表示2013年1月1日
		reqTick.nDate(intDate);

		
		reqTick.nBeginTime(80000000);
		reqTick.nEndTime(160000000);
		
		return resPointer;
	}
	public static Pointer<TDBDefine_ReqTick> getReqTick(String szCode,String szMarket,int ymd){ //获取各种信息
		Pointer<TDBDefine_ReqTick> resPointer = Pointer.allocate(TDBDefine_ReqTick.class);
		TDBDefine_ReqTick reqTick = resPointer.get();
		
		Pointer<Byte > codePointer = reqTick.chCode();
		codePointer.setArray(szCode.getBytes());
		
		Pointer<Byte > marketKeyPointer = reqTick.chMarketKey();
		marketKeyPointer.setArray(szMarket.getBytes());
		
		//int intDate = Integer.parseInt(y)*10000 + Integer.parseInt(m)*100 + Integer.parseInt(d); //设置时间  开始日期（交易日）,为0则从今天，格式：YYMMDD，例如20130101表示2013年1月1日
		reqTick.nDate(ymd);

		
		reqTick.nBeginTime(80000000);
		reqTick.nEndTime(160000000);
		
		return resPointer;
	}
}
