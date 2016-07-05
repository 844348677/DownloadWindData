package tdb.util;

import org.bridj.BridJ;
import org.bridj.Pointer;
import org.bridj.PointerIO;

import tdb.domain.Ticks;

import java.awt.Point;
import java.nio.Buffer;
import java.util.LinkedList;
import java.util.List;

import tdbapi.TDBDefine_Tick;

public class PointerUtil {
	/**
	 * 需要自己构造 指针 解析内存块
	 * @param pData
	 * @param pTickCount
	 */
	public static List<Ticks> pointerManual(Pointer<Pointer<TDBDefine_Tick>> pData,Pointer<Integer> pTickCount,int date){
		int tickSize = pTickCount.getInt();
		List<Ticks> ticksList = new LinkedList<Ticks>();
		if(tickSize==0) //判断时候有ticks数据
			System.out.println("setConf no data today "+date);
		else{
			Pointer<TDBDefine_Tick> pDataPointer = pData.get();
			
			
			long peer = pDataPointer.getPeer();
			//System.out.println(peer); 
			Pointer<Byte> headPointer = Pointer.pointerToAddress(peer, Byte.class); //头指针
	//		System.out.println(new String(new byte[]{headPointer.get()}));
	//		System.out.println(new String(new byte[]{headPointer.next().get()}));
			Pointer<Byte> tailPointer = Pointer.pointerToAddress(peer, Byte.class); //尾指针 
	
			for(int i=0;i<tickSize;i++){ //pTickCount指针的个数，交易数据有多少
				//System.out.println("第幾個: "+i);
				headPointer= tailPointer.as(Byte.class);  //将下一个的头指针   指向    前一个的尾指针  
				//System.out.println(new String(new byte[]{headPointer.get()}));
				//System.out.println(new String(new byte[]{headPointer.next().get()}));
	
				//万得代码(AG1312.SHF)
				
				String chWindCode = new String(headPointer.getBytes(32)).trim();
				//System.out.println(chWindCode);
	
				//交易所代码(ag1312)
				headPointer = headPointer.next(32);
				String chCode = new String(headPointer.getBytes(32)).trim();
				//System.out.println(chCode);
				
				//日期（自然日）
				//时间（HHMMSSmmm）例如94500000 表示 9点45分00秒000毫秒
				//成交价((a double number + 0.00005) *10000)
				headPointer = headPointer.next(32);
				int[] tmpints1 = headPointer.getInts(3);
				int nDate = tmpints1[0];
				int nTime = tmpints1[1];
				int nPrice = tmpints1[2];
				//System.out.println(nDate+" "+nTime+" "+nPrice);
				//成交量
				//成交额(元)
				headPointer = headPointer.next(4*3);
				long[] tmplongs1 = headPointer.getLongs(2);
				long iVolume = tmplongs1[0];
				long iTurover = tmplongs1[1];
				//System.out.println(iVolume+" "+iTurover);
				//成交笔数
				//IOPV(基金)、利息(债券)
				headPointer = headPointer.next(8*2);
				int[] tmpints2 = headPointer.getInts(2);
				int nMatchItems = tmpints2[0];
				int nInterest = tmpints2[1];
				//System.out.println(nMatchItems+" "+nInterest);
				//成交标志
				 //BS标志
				headPointer = headPointer.next(4*2);
				byte[] tmpchars1 = headPointer.getBytes(2);
				char chTradeFlag = (char) (tmpchars1[0]==0?'0':tmpchars1[0]);
				char chBSFlag = (char) (tmpchars1[1]==32?'0':tmpchars1[1]);
				//System.out.println(chTradeFlag+","+chBSFlag);
				
				//当日累计成交量
				//当日成交额(元)
				headPointer = headPointer.next(2);
				long[] tmplongs2 = headPointer.getLongs(2);
				long iAccVolume = tmplongs2[0];
				long iAccTurover = tmplongs2[1];
				//System.out.println(iAccVolume+" "+iAccTurover);
				
	            //最高((a double number + 0.00005) *10000)
			    //最低((a double number + 0.00005) *10000)
			    //开盘((a double number + 0.00005) *10000)
			    //前收盘((a double number + 0.00005) *10000)
				
				//期货字段
				//结算价((a double number + 0.00005) *10000)
				//持仓量
				//虚实度
				//昨结算((a double number + 0.00005) *10000)
				//昨持仓
	
				//买卖盘字段
	            //叫卖价((a double number + 0.00005) *10000)
				//叫卖量
				//叫买价((a double number + 0.00005) *10000)
				//叫买量
			    //加权平均叫卖价(上海L2)((a double number + 0.00005) *10000)
				//加权平均叫买价(上海L2)((a double number + 0.00005) *10000)
				
				headPointer = headPointer.next(8*2);
				int[] tmpints3 = headPointer.getInts(51);
				int nHigh = tmpints3[0]; 
				int nLow = tmpints3[1]; 
				int    nOpen =  tmpints3[2]; 
				int    nPreClose =  tmpints3[3];
				int nSettle = tmpints3[4];
				int nPosition = tmpints3[5];
				int nCurDelta = tmpints3[6];
				int nPreSettle = tmpints3[7];
				int nPrePosition = tmpints3[8];
				
				int nAskPrice0 = tmpints3[9];
				int nAskPrice1 = tmpints3[10];
				int nAskPrice2 = tmpints3[11];
				int nAskPrice3 = tmpints3[12];
				int nAskPrice4 = tmpints3[13];
				int nAskPrice5 = tmpints3[14];
				int nAskPrice6 = tmpints3[15];
				int nAskPrice7 = tmpints3[16];
				int nAskPrice8 = tmpints3[17];
				int nAskPrice9 = tmpints3[18];
				
				int nAskVolume0 = tmpints3[19];
				int nAskVolume1 = tmpints3[20];
				int nAskVolume2 = tmpints3[21];
				int nAskVolume3 = tmpints3[22];
				int nAskVolume4 = tmpints3[23];
				int nAskVolume5 = tmpints3[24];
				int nAskVolume6 = tmpints3[25];
				int nAskVolume7 = tmpints3[26];
				int nAskVolume8 = tmpints3[27];
				int nAskVolume9 = tmpints3[28];
				
				int nBidPrice0 = tmpints3[29];
				int nBidPrice1 = tmpints3[30];
				int nBidPrice2 = tmpints3[31];
				int nBidPrice3 = tmpints3[32];
				int nBidPrice4 = tmpints3[33];
				int nBidPrice5 = tmpints3[34];
				int nBidPrice6 = tmpints3[35];
				int nBidPrice7 = tmpints3[36];
				int nBidPrice8 = tmpints3[37];
				int nBidPrice9 = tmpints3[38];
				
				int nBidVolume0 = tmpints3[39];
				int nBidVolume1 = tmpints3[40];
				int nBidVolume2 = tmpints3[41];
				int nBidVolume3 = tmpints3[42];
				int nBidVolume4 = tmpints3[43];
				int nBidVolume5 = tmpints3[44];
				int nBidVolume6 = tmpints3[45];
				int nBidVolume7 = tmpints3[46];
				int nBidVolume8 = tmpints3[47];
				int nBidVolume9 = tmpints3[48];
				
				int nAskAvPrice = tmpints3[49];
				int nBidAvPrice = tmpints3[50];
				
	/*			System.out.println(nPreSettle);
				System.out.println(nPrePosition);
				System.out.println(nAskPrice0);
				System.out.println(nAskVolume0);
				System.out.println(nBidPrice0);
				System.out.println(nBidVolume0);*/
				
				//叫卖总量(上海L2)
				//叫买总量(上海L2)
				headPointer = headPointer.next(4*51);
				long[] tmplongs3 = headPointer.getLongs(2);
				long iTotalAskVolume = tmplongs3[0];
				long iTotalBidVolume = tmplongs3[1];
				//System.out.println(iTotalAskVolume+" "+iTotalBidVolume);
				
				//不加权指数
				//品种总数
				//上涨品种数
				//下跌品种数
				//持平品种数
				headPointer = headPointer.next(8*2);
				int[] tmpints4 = headPointer.getInts(8);
				int nIndex = tmpints4[0];
				int nStocks = tmpints4[1];
				int nUps = tmpints4[2];
				int nDowns = tmpints4[3];
				int nHoldLines = tmpints4[4];
				int nResv1 = tmpints4[5];
				int nResv2 = tmpints4[6];
				int nResv3 = tmpints4[7];
				//System.out.println(nIndex+" "+nStocks+" "+nUps+" "+nDowns+" "+nHoldLines+" "+nResv1+" "+nResv2+" "+nResv3);
				
				tailPointer = headPointer.next(4*8);
				
				Ticks ticks = new Ticks();  //构造tick对象
				ticks.setChWindCode(chWindCode);
				ticks.setChCode(chCode);
				ticks.setnDate(nDate);
				ticks.setnTime(nTime);
				ticks.setnPrice(nPrice);
				ticks.setiVolume(iVolume);
				ticks.setiTurover(iTurover);
				ticks.setnMatchItems(nMatchItems);
				ticks.setnInterest(nInterest);
				ticks.setChTradeFlag(chTradeFlag);
				ticks.setChBSFlag(chBSFlag);
				ticks.setiAccVolume(iAccVolume);
				ticks.setiAccTurover(iAccTurover);
				ticks.setnHigh(nHigh);
				ticks.setnLow(nLow);
				ticks.setnOpen(nOpen);
				ticks.setnPreClose(nPreClose);
				ticks.setnSettle(nSettle);
				ticks.setnPosition(nPosition);
				ticks.setnCurDelta(nCurDelta);
				ticks.setnPreSettle(nPreSettle);
				ticks.setnPrePosition(nPrePosition);
				
				ticks.setnAskPrice0(nAskPrice0);
				ticks.setnAskPrice1(nAskPrice1);
				ticks.setnAskPrice2(nAskPrice2);
				ticks.setnAskPrice3(nAskPrice3);
				ticks.setnAskPrice4(nAskPrice4);
				ticks.setnAskPrice5(nAskPrice5);
				ticks.setnAskPrice6(nAskPrice6);
				ticks.setnAskPrice7(nAskPrice7);
				ticks.setnAskPrice8(nAskPrice8);
				ticks.setnAskPrice9(nAskPrice9);
				
				ticks.setnAskVolume0(nAskVolume0);
				ticks.setnAskVolume1(nAskVolume1);
				ticks.setnAskVolume2(nAskVolume2);
				ticks.setnAskVolume3(nAskVolume3);
				ticks.setnAskVolume4(nAskVolume4);
				ticks.setnAskVolume5(nAskVolume5);
				ticks.setnAskVolume6(nAskVolume6);
				ticks.setnAskVolume7(nAskVolume7);
				ticks.setnAskVolume8(nAskVolume8);
				ticks.setnAskVolume9(nAskVolume9);
				
				ticks.setnBidPrice0(nBidPrice0);
				ticks.setnBidPrice1(nBidPrice1);
				ticks.setnBidPrice2(nBidPrice2);
				ticks.setnBidPrice3(nBidPrice3);
				ticks.setnBidPrice4(nBidPrice4);
				ticks.setnBidPrice5(nBidPrice5);
				ticks.setnBidPrice6(nBidPrice6);
				ticks.setnBidPrice7(nBidPrice7);
				ticks.setnBidPrice8(nBidPrice8);
				ticks.setnBidPrice9(nBidPrice9);
				
				ticks.setnBidVolume0(nBidVolume0);
				ticks.setnBidVolume1(nBidVolume1);
				ticks.setnBidVolume2(nBidVolume2);
				ticks.setnBidVolume3(nBidPrice3);
				ticks.setnBidVolume4(nBidVolume4);
				ticks.setnBidVolume5(nBidVolume5);
				ticks.setnBidVolume6(nBidVolume6);
				ticks.setnBidVolume7(nBidVolume7);
				ticks.setnBidVolume8(nBidVolume8);
				ticks.setnBidVolume9(nBidVolume9);
				
				ticks.setnAskAvPrice(nAskAvPrice);
				ticks.setnBidAvPrice(nBidAvPrice);
				ticks.setnIndex(nIndex);
				ticks.setnStocks(nStocks);
				ticks.setnUps(nUps);
				ticks.setnDowns(nDowns);
				ticks.setnHoldLines(nHoldLines);
				ticks.setnResv1(nResv1);
				ticks.setnResv2(nResv2);
				ticks.setnResv3(nResv3);
				
				//System.out.println(ticks.toString());
				ticksList.add(ticks);
				
			
				
			}
			
			pDataPointer.get().chCode().release();
			pDataPointer.get().chWindCode().release();
			pDataPointer.get().nAskPrice().release();
			pDataPointer.get().nAskVolume().release();
			pDataPointer.get().nBidPrice().release();
			pDataPointer.get().nBidVolume().release();
			
			pDataPointer.release();
			headPointer.release();
			tailPointer.release();
			Pointer.release(pDataPointer);
			Pointer.release(headPointer);
			Pointer.release(tailPointer);
			
		}
		
		
		return ticksList;
	}
	
	/**
	 * 需要自己构造 指针 解析内存块
	 * @param pData
	 * @param pTickCount
	 */
	public static List<String> pointerManual2String(Pointer<Pointer<TDBDefine_Tick>> pData,Pointer<Integer> pTickCount,int date){
		int tickSize = pTickCount.getInt();
		List<String> ticksList = new LinkedList<String>();
		if(tickSize==0) //判断时候有ticks数据
			System.out.println("setConf no data today "+date);
		else{
			Pointer<TDBDefine_Tick> pDataPointer = pData.get();
			
			
			long peer = pDataPointer.getPeer();
			//System.out.println(peer); 
			Pointer<Byte> headPointer = Pointer.pointerToAddress(peer, Byte.class); //头指针
	//		System.out.println(new String(new byte[]{headPointer.get()}));
	//		System.out.println(new String(new byte[]{headPointer.next().get()}));
			Pointer<Byte> tailPointer = Pointer.pointerToAddress(peer, Byte.class); //尾指针 
	
			for(int i=0;i<tickSize;i++){ //pTickCount指针的个数，交易数据有多少
				//System.out.println("第幾個: "+i);
				headPointer= tailPointer.as(Byte.class);  //将下一个的头指针   指向    前一个的尾指针  
				//System.out.println(new String(new byte[]{headPointer.get()}));
				//System.out.println(new String(new byte[]{headPointer.next().get()}));
	
				//万得代码(AG1312.SHF)
				
				String chWindCode = new String(headPointer.getBytes(32)).trim();
				//System.out.println(chWindCode);
	
				//交易所代码(ag1312)
				headPointer = headPointer.next(32);
				String chCode = new String(headPointer.getBytes(32)).trim();
				//System.out.println(chCode);
				
				//日期（自然日）
				//时间（HHMMSSmmm）例如94500000 表示 9点45分00秒000毫秒
				//成交价((a double number + 0.00005) *10000)
				headPointer = headPointer.next(32);
				int[] tmpints1 = headPointer.getInts(3);
				int nDate = tmpints1[0];
				int nTime = tmpints1[1];
				int nPrice = tmpints1[2];
				//System.out.println(nDate+" "+nTime+" "+nPrice);
				//成交量
				//成交额(元)
				headPointer = headPointer.next(4*3);
				long[] tmplongs1 = headPointer.getLongs(2);
				long iVolume = tmplongs1[0];
				long iTurover = tmplongs1[1];
				//System.out.println(iVolume+" "+iTurover);
				//成交笔数
				//IOPV(基金)、利息(债券)
				headPointer = headPointer.next(8*2);
				int[] tmpints2 = headPointer.getInts(2);
				int nMatchItems = tmpints2[0];
				int nInterest = tmpints2[1];
				//System.out.println(nMatchItems+" "+nInterest);
				//成交标志
				 //BS标志
				headPointer = headPointer.next(4*2);
				byte[] tmpchars1 = headPointer.getBytes(2);
				char chTradeFlag = (char) (tmpchars1[0]==0?'0':tmpchars1[0]);
				char chBSFlag = (char) (tmpchars1[1]==32?'0':tmpchars1[1]);
				if(tmpchars1[1]==32 || tmpchars1[1]==0)
					chBSFlag = '0';
				else
					chBSFlag = (char) tmpchars1[1];

				//System.out.println(chTradeFlag+","+chBSFlag);
				
				//当日累计成交量
				//当日成交额(元)
				headPointer = headPointer.next(2);
				long[] tmplongs2 = headPointer.getLongs(2);
				long iAccVolume = tmplongs2[0];
				long iAccTurover = tmplongs2[1];
				//System.out.println(iAccVolume+" "+iAccTurover);
				
	            //最高((a double number + 0.00005) *10000)
			    //最低((a double number + 0.00005) *10000)
			    //开盘((a double number + 0.00005) *10000)
			    //前收盘((a double number + 0.00005) *10000)
				
				//期货字段
				//结算价((a double number + 0.00005) *10000)
				//持仓量
				//虚实度
				//昨结算((a double number + 0.00005) *10000)
				//昨持仓
	
				//买卖盘字段
	            //叫卖价((a double number + 0.00005) *10000)
				//叫卖量
				//叫买价((a double number + 0.00005) *10000)
				//叫买量
			    //加权平均叫卖价(上海L2)((a double number + 0.00005) *10000)
				//加权平均叫买价(上海L2)((a double number + 0.00005) *10000)
				
				headPointer = headPointer.next(8*2);
				int[] tmpints3 = headPointer.getInts(51);
				int nHigh = tmpints3[0]; 
				int nLow = tmpints3[1]; 
				int    nOpen =  tmpints3[2]; 
				int    nPreClose =  tmpints3[3];
				int nSettle = tmpints3[4];
				int nPosition = tmpints3[5];
				int nCurDelta = tmpints3[6];
				int nPreSettle = tmpints3[7];
				int nPrePosition = tmpints3[8];
				
				int nAskPrice0 = tmpints3[9];
				int nAskPrice1 = tmpints3[10];
				int nAskPrice2 = tmpints3[11];
				int nAskPrice3 = tmpints3[12];
				int nAskPrice4 = tmpints3[13];
				int nAskPrice5 = tmpints3[14];
				int nAskPrice6 = tmpints3[15];
				int nAskPrice7 = tmpints3[16];
				int nAskPrice8 = tmpints3[17];
				int nAskPrice9 = tmpints3[18];
				
				int nAskVolume0 = tmpints3[19];
				int nAskVolume1 = tmpints3[20];
				int nAskVolume2 = tmpints3[21];
				int nAskVolume3 = tmpints3[22];
				int nAskVolume4 = tmpints3[23];
				int nAskVolume5 = tmpints3[24];
				int nAskVolume6 = tmpints3[25];
				int nAskVolume7 = tmpints3[26];
				int nAskVolume8 = tmpints3[27];
				int nAskVolume9 = tmpints3[28];
				
				int nBidPrice0 = tmpints3[29];
				int nBidPrice1 = tmpints3[30];
				int nBidPrice2 = tmpints3[31];
				int nBidPrice3 = tmpints3[32];
				int nBidPrice4 = tmpints3[33];
				int nBidPrice5 = tmpints3[34];
				int nBidPrice6 = tmpints3[35];
				int nBidPrice7 = tmpints3[36];
				int nBidPrice8 = tmpints3[37];
				int nBidPrice9 = tmpints3[38];
				
				int nBidVolume0 = tmpints3[39];
				int nBidVolume1 = tmpints3[40];
				int nBidVolume2 = tmpints3[41];
				int nBidVolume3 = tmpints3[42];
				int nBidVolume4 = tmpints3[43];
				int nBidVolume5 = tmpints3[44];
				int nBidVolume6 = tmpints3[45];
				int nBidVolume7 = tmpints3[46];
				int nBidVolume8 = tmpints3[47];
				int nBidVolume9 = tmpints3[48];
				
				int nAskAvPrice = tmpints3[49];
				int nBidAvPrice = tmpints3[50];
				
	/*			System.out.println(nPreSettle);
				System.out.println(nPrePosition);
				System.out.println(nAskPrice0);
				System.out.println(nAskVolume0);
				System.out.println(nBidPrice0);
				System.out.println(nBidVolume0);*/
				
				//叫卖总量(上海L2)
				//叫买总量(上海L2)
				headPointer = headPointer.next(4*51);
				long[] tmplongs3 = headPointer.getLongs(2);
				long iTotalAskVolume = tmplongs3[0];
				long iTotalBidVolume = tmplongs3[1];
				//System.out.println(iTotalAskVolume+" "+iTotalBidVolume);
				
				//不加权指数
				//品种总数
				//上涨品种数
				//下跌品种数
				//持平品种数
				headPointer = headPointer.next(8*2);
				int[] tmpints4 = headPointer.getInts(8);
				int nIndex = tmpints4[0];
				int nStocks = tmpints4[1];
				int nUps = tmpints4[2];
				int nDowns = tmpints4[3];
				int nHoldLines = tmpints4[4];
				int nResv1 = tmpints4[5];
				int nResv2 = tmpints4[6];
				int nResv3 = tmpints4[7];
				//System.out.println(nIndex+" "+nStocks+" "+nUps+" "+nDowns+" "+nHoldLines+" "+nResv1+" "+nResv2+" "+nResv3);
				
				tailPointer = headPointer.next(4*8);
				
/*				Ticks ticks = new Ticks();  //构造tick对象
				ticks.setChWindCode(chWindCode);
				ticks.setChCode(chCode);
				ticks.setnDate(nDate);
				ticks.setnTime(nTime);
				ticks.setnPrice(nPrice);
				ticks.setiVolume(iVolume);
				ticks.setiTurover(iTurover);
				ticks.setnMatchItems(nMatchItems);
				ticks.setnInterest(nInterest);
				ticks.setChTradeFlag(chTradeFlag);
				ticks.setChBSFlag(chBSFlag);
				ticks.setiAccVolume(iAccVolume);
				ticks.setiAccTurover(iAccTurover);
				ticks.setnHigh(nHigh);
				ticks.setnLow(nLow);
				ticks.setnOpen(nOpen);
				ticks.setnPreClose(nPreClose);
				ticks.setnSettle(nSettle);
				ticks.setnPosition(nPosition);
				ticks.setnCurDelta(nCurDelta);
				ticks.setnPreSettle(nPreSettle);
				ticks.setnPrePosition(nPrePosition);
				
				ticks.setnAskPrice0(nAskPrice0);
				ticks.setnAskPrice1(nAskPrice1);
				ticks.setnAskPrice2(nAskPrice2);
				ticks.setnAskPrice3(nAskPrice3);
				ticks.setnAskPrice4(nAskPrice4);
				ticks.setnAskPrice5(nAskPrice5);
				ticks.setnAskPrice6(nAskPrice6);
				ticks.setnAskPrice7(nAskPrice7);
				ticks.setnAskPrice8(nAskPrice8);
				ticks.setnAskPrice9(nAskPrice9);
				
				ticks.setnAskVolume0(nAskVolume0);
				ticks.setnAskVolume1(nAskVolume1);
				ticks.setnAskVolume2(nAskVolume2);
				ticks.setnAskVolume3(nAskVolume3);
				ticks.setnAskVolume4(nAskVolume4);
				ticks.setnAskVolume5(nAskVolume5);
				ticks.setnAskVolume6(nAskVolume6);
				ticks.setnAskVolume7(nAskVolume7);
				ticks.setnAskVolume8(nAskVolume8);
				ticks.setnAskVolume9(nAskVolume9);
				
				ticks.setnBidPrice0(nBidPrice0);
				ticks.setnBidPrice1(nBidPrice1);
				ticks.setnBidPrice2(nBidPrice2);
				ticks.setnBidPrice3(nBidPrice3);
				ticks.setnBidPrice4(nBidPrice4);
				ticks.setnBidPrice5(nBidPrice5);
				ticks.setnBidPrice6(nBidPrice6);
				ticks.setnBidPrice7(nBidPrice7);
				ticks.setnBidPrice8(nBidPrice8);
				ticks.setnBidPrice9(nBidPrice9);
				
				ticks.setnBidVolume0(nBidVolume0);
				ticks.setnBidVolume1(nBidVolume1);
				ticks.setnBidVolume2(nBidVolume2);
				ticks.setnBidVolume3(nBidPrice3);
				ticks.setnBidVolume4(nBidVolume4);
				ticks.setnBidVolume5(nBidVolume5);
				ticks.setnBidVolume6(nBidVolume6);
				ticks.setnBidVolume7(nBidVolume7);
				ticks.setnBidVolume8(nBidVolume8);
				ticks.setnBidVolume9(nBidVolume9);
				
				ticks.setnAskAvPrice(nAskAvPrice);
				ticks.setnBidAvPrice(nBidAvPrice);
				ticks.setnIndex(nIndex);
				ticks.setnStocks(nStocks);
				ticks.setnUps(nUps);
				ticks.setnDowns(nDowns);
				ticks.setnHoldLines(nHoldLines);
				ticks.setnResv1(nResv1);
				ticks.setnResv2(nResv2);
				ticks.setnResv3(nResv3);*/
				
				//System.out.println(ticks.toString());
				
				//"("+id+
				
				String chWindCode2 = "'"+chWindCode+"'";
				String chCode2 = "'"+chCode+"'";
				String chTradeFlag2 = "'"+chTradeFlag+"'";
				String chBSFlag2 = "'"+chBSFlag+"'";
				
				String value = ","+chWindCode2+","+chCode2+","+nDate+","+nTime+","+nPrice+","+iVolume+","+iTurover+","+nMatchItems+","+nInterest+","+chTradeFlag2+","+chBSFlag2+","+
						iAccVolume+","+iAccTurover+","+nHigh+","+nLow+","+nOpen+","+nPreClose+","+nSettle+","+nPosition+","+nCurDelta+","+nPreSettle+","+nPrePosition+","+nAskPrice0+","+
						nAskPrice1+","+nAskPrice2+","+nAskPrice3+","+nAskPrice4+","+nAskPrice5+","+nAskPrice6+","+nAskPrice7+","+nAskPrice8+","+nAskPrice9+","+nAskVolume0+","+nAskVolume1+","+
						nAskVolume2+","+nAskVolume3+","+nAskVolume4+","+nAskVolume5+","+nAskVolume6+","+nAskVolume7+","+nAskVolume8+","+nAskVolume9+","+nBidPrice0+","+nBidPrice1+","+
						nBidPrice2+","+nBidPrice3+","+nBidPrice4+","+nBidPrice5+","+nBidPrice6+","+nBidPrice7+","+nBidPrice8+","+nBidPrice9+","+nBidVolume0+","+nBidVolume1+","+
						nBidVolume2+","+nBidVolume3+","+nBidVolume4+","+nBidVolume5+","+nBidVolume6+","+nBidVolume7+","+nBidVolume8+","+nBidVolume9+","+nAskAvPrice+","+nBidAvPrice+","+
						iTotalAskVolume+","+iTotalBidVolume+","+nIndex+","+nStocks+","+nUps+","+nDowns+","+nHoldLines+","+nResv1+","+nResv2+","+nResv3+")";
				
				ticksList.add(value);
				
			
				
			}
			
			pDataPointer.get().chCode().release();
			pDataPointer.get().chWindCode().release();
			pDataPointer.get().nAskPrice().release();
			pDataPointer.get().nAskVolume().release();
			pDataPointer.get().nBidPrice().release();
			pDataPointer.get().nBidVolume().release();
			
			pDataPointer.release();
			headPointer.release();
			tailPointer.release();
			Pointer.release(pDataPointer);
			Pointer.release(headPointer);
			Pointer.release(tailPointer);
			
		}
		
		
		return ticksList;
	}
	
}
