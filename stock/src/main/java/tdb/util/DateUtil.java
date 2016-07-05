package tdb.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DateUtil {
	
	private static final DateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");  
	/**
	 * 判断code代码  是不是  倒数  后面四个为数字组合
	 * @param code
	 * @return
	 */
	public static boolean isNormalCode(String code){
		boolean result = false;
		if(code.length()> 4){//code的字符创长度大于4
			String lastSub = code.substring(code.length()-4); //后四位字符串
			if(!(code.charAt(code.length()-5)+"").matches("\\d+")) //倒数第五个不为数字
				result = lastSub.matches("\\d+");  //后四位字符创是数字
		}
		return result;
	}

	
	/**
	 * 根据code代码的String，返回时间String的List  只有normalCode，而且 该规则是 1年前的16号，到该月15号
	 * @param code
	 */
	public static List<Integer> ensureNormalDate1(String code){
		String[] splitString = code.split("[.]"); //去除万得代买后面的 。市场
		String splitCode = splitString[0]; // 接下来截取code的后面 的四位数字时间
		String lastSub = splitCode.substring(splitCode.length()-4); //后四位字符串
		String stringYearShort = lastSub.substring(0, 2);
		String stringYear = "";
		if(Integer.parseInt(stringYearShort) >80) //合约中的年份有可能有  99 表示19年的
			stringYear = 19+stringYearShort;
		else
			stringYear = 20+stringYearShort;
		String stringMonth = lastSub.substring(2);
		//System.out.println(stringYearShort+"  "+stringMonth);  
		
		//获取当前日期

	    Calendar nowCal = Calendar.getInstance();
	    Calendar codeCal = Calendar.getInstance();
	    Calendar startCal = Calendar.getInstance();
	    Calendar endCal = Calendar.getInstance();
	    codeCal.set(Integer.parseInt(stringYear), Integer.parseInt(stringMonth)-1, 28); //默认15号  到期日期15号为周末节假日之后，往后顺延
	    startCal.set(Integer.parseInt(stringYear)-1, Integer.parseInt(stringMonth)-1, 16);
/*	    int currentYear = cal.get(Calendar.YEAR);
	    int currentMonth = cal.get(Calendar.MONTH)+1; //获取当前月份号
	    int currentMonthDay = cal.get(cal.DAY_OF_MONTH); //获取当前日期号
	    System.out.println("当前年份： "+currentYear+"  当前月份："+currentMonth +"  当前日期："+currentMonthDay);*/

	    long nowDate = nowCal.getTimeInMillis(); //现在距离某一时间点的long值
	    long codeDate = codeCal.getTimeInMillis(); //code时间距离某一点的long值    值越大，越往后 需要判断结束日期
	    if(codeDate > nowDate) //如果合约日期 大于 当前日期  结束日期就用当前日期  否则使用  code日期
	    	endCal = nowCal;
	    else
	    	endCal = codeCal;
	    
	    List<Integer> result = printDay(startCal,endCal);
	    
/*	    System.out.println(nowDate>codeDate);
	    
	    System.out.println("test: "+FORMATTER.format(codeCal.getTime()));
	    System.out.println("testNow: "+FORMATTER.format(nowCal.getTime()));
		
		System.out.println(lastSub);*/
	    return result;
	}
	
	public static void main(String[] args) throws Exception {
		List<Integer> intDateList = ensureNormalDate1("ag1605.SHF");
/*		System.out.println(getLastQuarterTime(false));
		
		Calendar startDay = Calendar.getInstance();  
		 Calendar endDay = Calendar.getInstance();  
		
		startDay.setTime(FORMATTER.parse("2016-05-01"));  
		endDay.setTime(FORMATTER.parse("2016-05-30"));   
		List<Integer> testResult = printDay(startDay, endDay);  
		for(int i=0;i<testResult.size();i++)
			System.out.println(testResult.get(i));*/
		for(int i=0;i<intDateList.size();i++){
			System.out.println(i+" "+intDateList.get(i));
		}
	}
	
	//获取两个日期之间的日期   不包括开始和结束   
	 public static List<Integer> printDay(Calendar startDay, Calendar endDay) {  
		 List<Integer> result = new LinkedList<Integer>();
		  // 给出的日期开始日比终了日大则不执行打印  
		  if (startDay.compareTo(endDay) >= 0) {  
		   return result;  
		  }  
		  
		  if(!isWeekend(startDay))
			  result.add(getIntDate(startDay));//加入开始日期
		  // 现在打印中的日期  
		  Calendar currentPrintDay = startDay;
		  
		  System.out.println("这步容易卡主");
		  //下面有bug！！！！！！！！！！！！！
/*		  while (true) {  
			   // 日期加一  
			   currentPrintDay.add(Calendar.DATE, 1);  
			   // 日期加一后判断是否达到终了日，达到则终止打印  
			   if (currentPrintDay.compareTo(endDay) == 0) {  
			   		break;  
			   }  
			   // 打印日期  
			   if(!isWeekend(currentPrintDay)){
				   //不是周日周六 就加入到结果List中
				   result.add(getIntDate(currentPrintDay));
			   }
			   //System.out.println(" printDay "+FORMATTER.format(currentPrintDay.getTime()));  
		  }  
		  */
		  
	        while(true){
	        	currentPrintDay.add(Calendar.DAY_OF_MONTH, 1);
	            if(currentPrintDay.getTimeInMillis() < endDay.getTimeInMillis()){//TODO 转数组或是集合，楼主看着写吧
	            	if(!isWeekend(currentPrintDay)){
	            		result.add(getIntDate(currentPrintDay));
	            	}
	        }else{
	            break;
	        }
        }
		  
		  
		  if(!isWeekend(endDay))
			  result.add(getIntDate(endDay)); //加入结束日期
		  
		  return result;
	 }  
	 
	 private static void dateBetweenTwoDay() throws ParseException{
	        Calendar startCalendar = Calendar.getInstance();
	        Calendar endCalendar = Calendar.getInstance();
	        SimpleDateFormat df = new SimpleDateFormat("yyyy/M/d");
	        Date startDate = df.parse("2012/3/1");
	        startCalendar.setTime(startDate);
	        Date endDate = df.parse("2012/3/5");
	        endCalendar.setTime(endDate);
	        
	        while(true){
		            startCalendar.add(Calendar.DAY_OF_MONTH, 1);
		            if(startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()){//TODO 转数组或是集合，楼主看着写吧
		            //System.out.println(df.format(startCalendar.getTime()));
		        }else{
		            break;
		        }
	        }
	 }
	 
	 /**
	  * 判断是否是周六周日
	  * @param cal
	  * @return
	  */
	 private static boolean isWeekend(Calendar cal){  
		    int week=cal.get(Calendar.DAY_OF_WEEK)-1;  
		    if(week ==6 || week==0){//0代表周日，6代表周六  
		        return true;  
		    }  
		    return false;  
	 }  
	 /**
	  * 将 cal 转成 int 好在下一步做输入
	  * @param cal
	  * @return
	  */
	 private static int getIntDate(Calendar cal){
		  int currentYear = cal.get(Calendar.YEAR);
		  int currentMonth = cal.get(Calendar.MONTH)+1; //获取当前月份号
		  int currentMonthDay = cal.get(Calendar.DAY_OF_MONTH); //获取当前日期号
		  //System.out.println("当前年份： "+currentYear+"  当前月份："+currentMonth +"  当前日期："+currentMonthDay);
		 
		 return currentYear*10000+currentMonth*100+currentMonthDay;
	 }
	
		/** 获取季度日期相关的代码  * @param flag true：开始日期；false：结束日期    * @return    */  
		public static String getLastQuarterTime(boolean flag){     
			SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");     
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");            
			String resultDate="";     Date now = null;     
			try {       
				Calendar calendar = Calendar.getInstance();       
				int currentMonth = calendar.get(Calendar.MONTH) + 1;      
				System.out.println(currentMonth);
				//true：开始日期；false：结束日期      
				if(flag){         
				if (currentMonth >= 1 && currentMonth <= 3)           
					calendar.set(Calendar.MONTH, 0);        
				else if (currentMonth >= 4 && currentMonth <= 6)           
					calendar.set(Calendar.MONTH, 3);         
				else if (currentMonth >= 7 && currentMonth <= 9)           
					calendar.set(Calendar.MONTH, 6);         
				else if (currentMonth >= 10 && currentMonth <= 12)           
					calendar.set(Calendar.MONTH, 9);         
				calendar.set(Calendar.DATE, 1);                    
				now = longSdf.parse(shortSdf.format(calendar.getTime()) + " 00:00:00");       
				}else{         
					if (currentMonth >= 1 && currentMonth <= 3) {           
						calendar.set(Calendar.MONTH, 2);          
						calendar.set(Calendar.DATE, 31);      
						} else if (currentMonth >= 4 && currentMonth <= 6) {    
							calendar.set(Calendar.MONTH, 5); 
							calendar.set(Calendar.DATE, 30);      
							} else if (currentMonth >= 7 && currentMonth <= 9) {    
								calendar.set(Calendar.MONTH, 8);      
								calendar.set(Calendar.DATE, 30);     
								} else if (currentMonth >= 10 && currentMonth <= 12) { 
									calendar.set(Calendar.MONTH, 11);     
									calendar.set(Calendar.DATE, 31);   
									}        
					now = longSdf.parse(shortSdf.format(calendar.getTime()) + " 23:59:59");   
					}       calendar.setTime(now);
					// 设置日期       
					calendar.add(Calendar.MONTH, -3);    
					resultDate = longSdf.format(calendar.getTime());     
					} catch (Exception e) { 
						; 
						}   
			return resultDate;  
		} 	
		
		
		/**
		 * 根据两个日期获取一个新的日期
		 * @param code
		 */
		public static List<Integer> betweenTwoDay(String startDate,String endDate){

			String startYear = startDate.substring(0, 4);
			String startMonth = startDate.substring(4,6);
			String startDay = startDate.substring(6,8);
			
			String endYear = endDate.substring(0,4);
			String endMonth = endDate.substring(4,6);
			String endDay = endDate.substring(6,8);

		    Calendar startCal = Calendar.getInstance();
		    Calendar endCal = Calendar.getInstance();
		    //codeCal.set(Integer.parseInt(stringYear), Integer.parseInt(stringMonth)-1, 28); //默认15号  到期日期15号为周末节假日之后，往后顺延
		    startCal.set(Integer.parseInt(startYear), Integer.parseInt(startMonth)-1, Integer.parseInt(startDay));
		    endCal.set(Integer.parseInt(endYear)-1, Integer.parseInt(endMonth)-1, Integer.parseInt(endDay));
	/*	    int currentYear = cal.get(Calendar.YEAR);
		    int currentMonth = cal.get(Calendar.MONTH)+1; //获取当前月份号
		    int currentMonthDay = cal.get(cal.DAY_OF_MONTH); //获取当前日期号
		    System.out.println("当前年份： "+currentYear+"  当前月份："+currentMonth +"  当前日期："+currentMonthDay);*/


		    
		    List<Integer> result = printDay(startCal,endCal);
		    
	/*	    System.out.println(nowDate>codeDate);
		    
		    System.out.println("test: "+FORMATTER.format(codeCal.getTime()));
		    System.out.println("testNow: "+FORMATTER.format(nowCal.getTime()));
			
			System.out.println(lastSub);*/
		    return result;
		}
		public static List<Integer> betweenTwoDayNew(String startDate,String endDate){

			String startYear = startDate.substring(0, 4);
			String startMonth = startDate.substring(4,6);
			String startDay = startDate.substring(6,8);
			
			String endYear = endDate.substring(0,4);
			String endMonth = endDate.substring(4,6);
			String endDay = endDate.substring(6,8);

		    Calendar startCal = Calendar.getInstance();
		    Calendar endCal = Calendar.getInstance();
		    //codeCal.set(Integer.parseInt(stringYear), Integer.parseInt(stringMonth)-1, 28); //默认15号  到期日期15号为周末节假日之后，往后顺延
		    startCal.set(Integer.parseInt(startYear), Integer.parseInt(startMonth)-1, Integer.parseInt(startDay));
		    endCal.set(Integer.parseInt(endYear), Integer.parseInt(endMonth)-1, Integer.parseInt(endDay));
	/*	    int currentYear = cal.get(Calendar.YEAR);
		    int currentMonth = cal.get(Calendar.MONTH)+1; //获取当前月份号
		    int currentMonthDay = cal.get(cal.DAY_OF_MONTH); //获取当前日期号
		    System.out.println("当前年份： "+currentYear+"  当前月份："+currentMonth +"  当前日期："+currentMonthDay);*/


		    
		    List<Integer> result = printDay(startCal,endCal);
		    
	/*	    System.out.println(nowDate>codeDate);
		    
		    System.out.println("test: "+FORMATTER.format(codeCal.getTime()));
		    System.out.println("testNow: "+FORMATTER.format(nowCal.getTime()));
			
			System.out.println(lastSub);*/
		    return result;
		}
		
}
