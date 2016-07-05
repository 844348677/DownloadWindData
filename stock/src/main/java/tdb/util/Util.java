package tdb.util;

import org.bridj.Pointer;

import com.alibaba.fastjson.serializer.TimeZoneCodec;

import tdbapi.TDBDefine_Code;
import tdbapi.TDBDefine_Tick;
import org.bridj.BridJ;
public class Util {
	//byte[]数组转成String
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
	 * 根据 TDBDefine_Code的双指针，打印code具体信息
	 * @param pCodeTable
	 * @param pCount
	 */
	public static void getCodeInfo(Pointer<Pointer<TDBDefine_Code>> pCodeTable,int pCount){
		//TDBDefine_Code codeInfo = pCodeTable.get().get(0); //双重指针 仅获取第一个
		System.out.println("万得代码(AG1312.SHF),交易所代码(ag1312),市场代码(SHF),证券中文名称,证券英文名称");
		for(int i=0;i<pCount;i++){
			TDBDefine_Code codeInfo = pCodeTable.get().get(i);
			
			String chWindCode = new String(codeInfo.chWindCode().getBytes()).trim(); //万得代码(AG1312.SHF)
			String chCode = new String(codeInfo.chCode().getBytes()).trim(); //交易所代码(ag1312)
			String chMarket = new String(codeInfo.chMarket().getBytes()).trim(); //市场代码(SHF)
			String chCNName = new String(codeInfo.chCNName().getBytes()).trim();    //证券中文名称
			String chENName = new String(codeInfo.chENName().getBytes()).trim();     //证券英文名称
			int nType = codeInfo.nType();  //证券类型
			
			System.out.println(i+" : "+chWindCode+","+chCode+","+chMarket
					+","+chCNName+","+chENName+","+nType);
		}
	}
	
	
	/**
	 * 打印tick具体信息   java中的 sizeOf(TDBDefine_Tick)和c++中的大小不一致，所以需要自己写解析内存地址的代码
	 * @param pData 
	 * @param pTickCount
	 */
	public static void getTickInfo(Pointer<Pointer<TDBDefine_Tick>> pData,Pointer<Integer> pTickCount){
		//TDBDefine_Tick tickData = pData.get().get(200);  //仅获取第一个
		System.out.println("万得代码(AG1312.SHF),交易所代码(ag1312),日期（自然日）,时间（HHMMSSmmm）例如94500000 表示 9点45分00秒000毫秒,成交价((a double number + 0.00005) *10000),"
				+ "成交量,成交额(元),成交笔数,IOPV(基金)、利息(债券),成交标志,BS标志,当日累计成交量,当日成交额(元),最高((a double number + 0.00005) *10000),最低((a double number + 0.00005) *10000),"
				+ "开盘((a double number + 0.00005) *10000),前收盘((a double number + 0.00005) *10000),结算价((a double number + 0.00005) *10000),结算价((a double number + 0.00005) *10000),"
				+ "持仓量,虚实度,昨结算((a double number + 0.00005) *10000),昨持仓,叫卖价((a double number + 0.00005) *10000),叫卖量,叫买价((a double number + 0.00005) *10000),"
				+ "叫买量,加权平均叫卖价(上海L2)((a double number + 0.00005) *10000),加权平均叫买价(上海L2)((a double number + 0.00005) *10000),不加权指数,品种总数,上涨品种数,下跌品种数,持平品种数,保留字段1,保留字段2,保留字段3");

		BridJ.sizeOf(TDBDefine_Tick.class);
		
		int size = pTickCount.getInt();
		if(size == 0)
			System.out.println("该日无ticks");
		for(int i=0;i<10;i++){
			
			TDBDefine_Tick tickData = pData.get().get(i);
			
			System.out.println(BridJ.sizeOf(tickData.getClass()));
			
		    String chWindCode = new String(tickData.chWindCode().getBytes());                //万得代码(AG1312.SHF)
		    String chCode = new String(tickData.chCode().getBytes());                    //交易所代码(ag1312)
		    int nDate = tickData.nDate();                          //日期（自然日）
		    int nTime =  tickData.nTime();                          //时间（HHMMSSmmm）例如94500000 表示 9点45分00秒000毫秒
		    int nPrice =  tickData.nPrice();                         //成交价((a double number + 0.00005) *10000)
		    long iVolume = tickData.iVolume();                    //成交量
		    long    iTurover = tickData.iTurover();                //成交额(元)
		    int nMatchItems = tickData.nMatchItems();                    //成交笔数
		    int nInterest = tickData.nInterest();                      //IOPV(基金)、利息(债券)
		    byte chTradeFlag = tickData.chTradeFlag();                   //成交标志
		    byte chBSFlag = tickData.chBSFlag();                      //BS标志
		    long iAccVolume = tickData.iAccVolume();                 //当日累计成交量
		    long   iAccTurover = tickData.iAccTurover();             //当日成交额(元)
		    int nHigh = tickData.nHigh();                          //最高((a double number + 0.00005) *10000)
		    int nLow = tickData.nLow();                           //最低((a double number + 0.00005) *10000)
		    int    nOpen =  tickData.nOpen();                       //开盘((a double number + 0.00005) *10000)
		    int    nPreClose = tickData.nPreClose();                   //前收盘((a double number + 0.00005) *10000)
			
			//期货字段
			int nSettle = tickData.nSettle();                        //结算价((a double number + 0.00005) *10000)
			int nPosition = tickData.nPosition();                       //持仓量
			int nCurDelta =  tickData.nCurDelta();                      //虚实度
			int nPreSettle =  tickData.nPreSettle();                    //昨结算((a double number + 0.00005) *10000)
			int nPrePosition = tickData.nPrePosition();                  //昨持仓
	
			//买卖盘字段
			int[]    nAskPrice = tickData.nAskPrice().getInts() ;               //叫卖价((a double number + 0.00005) *10000)
			int[] nAskVolume = tickData.nAskVolume().getInts();            //叫卖量
			int[]    nBidPrice = tickData.nBidPrice().getInts();               //叫买价((a double number + 0.00005) *10000)
			int[] nBidVolume =  tickData.nBidVolume().getInts();            //叫买量
			int    nAskAvPrice = tickData.nAskAvPrice();                 //加权平均叫卖价(上海L2)((a double number + 0.00005) *10000)
			int    nBidAvPrice =  tickData.nBidAvPrice();                 //加权平均叫买价(上海L2)((a double number + 0.00005) *10000)
			long  iTotalAskVolume = tickData.iTotalAskVolume();           //叫卖总量(上海L2)
			long  iTotalBidVolume = tickData.iTotalBidVolume();           //叫买总量(上海L2)
		   
			//下面的字段指数使用
		    int        nIndex = tickData.nIndex();                  //不加权指数
		    int        nStocks = tickData.nStocks();                 //品种总数
		    int        nUps = tickData.nUps();                    //上涨品种数
		    int        nDowns = tickData.nDowns();                  //下跌品种数
		    int        nHoldLines =  tickData.nHoldLines();              //持平品种数
	
			//保留字段
			int nResv1 = tickData.nResv1();//保留字段1
			int nResv2 =  tickData.nResv2();//保留字段2
			int nResv3  = tickData.nResv3();//保留字段3
			System.out.println(i+" : "+chWindCode+","+chCode+","+nDate+","+nTime+","+nPrice+","+iVolume+","+iTurover+","+nMatchItems+","+nInterest+","+chTradeFlag+","+chBSFlag+","+iAccVolume+","
					+iAccTurover+","+nHigh+","+nLow+","+nOpen+","+nPreClose+","+nSettle+","+nPosition+","+nCurDelta+","+nPreSettle+","+nPrePosition+","+nAskAvPrice+","+nAskVolume+","+nBidPrice+","
					+nBidVolume+","+nAskAvPrice+","+nBidAvPrice+","+iTotalAskVolume+","+iTotalBidVolume+","+nIndex+","+nStocks+","+nUps+","+nDowns+","+nHoldLines+","+nResv1+","+nResv2+","+nResv3);
			System.out.println(tickData.toString());
		}
	}
	
	
}
