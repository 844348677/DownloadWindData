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
	 * �ж�code����  �ǲ���  ����  �����ĸ�Ϊ�������
	 * @param code
	 * @return
	 */
	public static boolean isNormalCode(String code){
		boolean result = false;
		if(code.length()> 4){//code���ַ������ȴ���4
			String lastSub = code.substring(code.length()-4); //����λ�ַ���
			if(!(code.charAt(code.length()-5)+"").matches("\\d+")) //�����������Ϊ����
				result = lastSub.matches("\\d+");  //����λ�ַ���������
		}
		return result;
	}

	
	/**
	 * ����code�����String������ʱ��String��List  ֻ��normalCode������ �ù����� 1��ǰ��16�ţ�������15��
	 * @param code
	 */
	public static List<Integer> ensureNormalDate1(String code){
		String[] splitString = code.split("[.]"); //ȥ����ô������� ���г�
		String splitCode = splitString[0]; // ��������ȡcode�ĺ��� ����λ����ʱ��
		String lastSub = splitCode.substring(splitCode.length()-4); //����λ�ַ���
		String stringYearShort = lastSub.substring(0, 2);
		String stringYear = "";
		if(Integer.parseInt(stringYearShort) >80) //��Լ�е�����п�����  99 ��ʾ19���
			stringYear = 19+stringYearShort;
		else
			stringYear = 20+stringYearShort;
		String stringMonth = lastSub.substring(2);
		//System.out.println(stringYearShort+"  "+stringMonth);  
		
		//��ȡ��ǰ����

	    Calendar nowCal = Calendar.getInstance();
	    Calendar codeCal = Calendar.getInstance();
	    Calendar startCal = Calendar.getInstance();
	    Calendar endCal = Calendar.getInstance();
	    codeCal.set(Integer.parseInt(stringYear), Integer.parseInt(stringMonth)-1, 28); //Ĭ��15��  ��������15��Ϊ��ĩ�ڼ���֮������˳��
	    startCal.set(Integer.parseInt(stringYear)-1, Integer.parseInt(stringMonth)-1, 16);
/*	    int currentYear = cal.get(Calendar.YEAR);
	    int currentMonth = cal.get(Calendar.MONTH)+1; //��ȡ��ǰ�·ݺ�
	    int currentMonthDay = cal.get(cal.DAY_OF_MONTH); //��ȡ��ǰ���ں�
	    System.out.println("��ǰ��ݣ� "+currentYear+"  ��ǰ�·ݣ�"+currentMonth +"  ��ǰ���ڣ�"+currentMonthDay);*/

	    long nowDate = nowCal.getTimeInMillis(); //���ھ���ĳһʱ����longֵ
	    long codeDate = codeCal.getTimeInMillis(); //codeʱ�����ĳһ���longֵ    ֵԽ��Խ���� ��Ҫ�жϽ�������
	    if(codeDate > nowDate) //�����Լ���� ���� ��ǰ����  �������ھ��õ�ǰ����  ����ʹ��  code����
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
	
	//��ȡ��������֮�������   ��������ʼ�ͽ���   
	 public static List<Integer> printDay(Calendar startDay, Calendar endDay) {  
		 List<Integer> result = new LinkedList<Integer>();
		  // ���������ڿ�ʼ�ձ������մ���ִ�д�ӡ  
		  if (startDay.compareTo(endDay) >= 0) {  
		   return result;  
		  }  
		  
		  if(!isWeekend(startDay))
			  result.add(getIntDate(startDay));//���뿪ʼ����
		  // ���ڴ�ӡ�е�����  
		  Calendar currentPrintDay = startDay;
		  
		  System.out.println("�ⲽ���׿���");
		  //������bug��������������������������
/*		  while (true) {  
			   // ���ڼ�һ  
			   currentPrintDay.add(Calendar.DATE, 1);  
			   // ���ڼ�һ���ж��Ƿ�ﵽ�����գ��ﵽ����ֹ��ӡ  
			   if (currentPrintDay.compareTo(endDay) == 0) {  
			   		break;  
			   }  
			   // ��ӡ����  
			   if(!isWeekend(currentPrintDay)){
				   //������������ �ͼ��뵽���List��
				   result.add(getIntDate(currentPrintDay));
			   }
			   //System.out.println(" printDay "+FORMATTER.format(currentPrintDay.getTime()));  
		  }  
		  */
		  
	        while(true){
	        	currentPrintDay.add(Calendar.DAY_OF_MONTH, 1);
	            if(currentPrintDay.getTimeInMillis() < endDay.getTimeInMillis()){//TODO ת������Ǽ��ϣ�¥������д��
	            	if(!isWeekend(currentPrintDay)){
	            		result.add(getIntDate(currentPrintDay));
	            	}
	        }else{
	            break;
	        }
        }
		  
		  
		  if(!isWeekend(endDay))
			  result.add(getIntDate(endDay)); //�����������
		  
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
		            if(startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()){//TODO ת������Ǽ��ϣ�¥������д��
		            //System.out.println(df.format(startCalendar.getTime()));
		        }else{
		            break;
		        }
	        }
	 }
	 
	 /**
	  * �ж��Ƿ�����������
	  * @param cal
	  * @return
	  */
	 private static boolean isWeekend(Calendar cal){  
		    int week=cal.get(Calendar.DAY_OF_WEEK)-1;  
		    if(week ==6 || week==0){//0�������գ�6��������  
		        return true;  
		    }  
		    return false;  
	 }  
	 /**
	  * �� cal ת�� int ������һ��������
	  * @param cal
	  * @return
	  */
	 private static int getIntDate(Calendar cal){
		  int currentYear = cal.get(Calendar.YEAR);
		  int currentMonth = cal.get(Calendar.MONTH)+1; //��ȡ��ǰ�·ݺ�
		  int currentMonthDay = cal.get(Calendar.DAY_OF_MONTH); //��ȡ��ǰ���ں�
		  //System.out.println("��ǰ��ݣ� "+currentYear+"  ��ǰ�·ݣ�"+currentMonth +"  ��ǰ���ڣ�"+currentMonthDay);
		 
		 return currentYear*10000+currentMonth*100+currentMonthDay;
	 }
	
		/** ��ȡ����������صĴ���  * @param flag true����ʼ���ڣ�false����������    * @return    */  
		public static String getLastQuarterTime(boolean flag){     
			SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");     
			SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");            
			String resultDate="";     Date now = null;     
			try {       
				Calendar calendar = Calendar.getInstance();       
				int currentMonth = calendar.get(Calendar.MONTH) + 1;      
				System.out.println(currentMonth);
				//true����ʼ���ڣ�false����������      
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
					// ��������       
					calendar.add(Calendar.MONTH, -3);    
					resultDate = longSdf.format(calendar.getTime());     
					} catch (Exception e) { 
						; 
						}   
			return resultDate;  
		} 	
		
		
		/**
		 * �����������ڻ�ȡһ���µ�����
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
		    //codeCal.set(Integer.parseInt(stringYear), Integer.parseInt(stringMonth)-1, 28); //Ĭ��15��  ��������15��Ϊ��ĩ�ڼ���֮������˳��
		    startCal.set(Integer.parseInt(startYear), Integer.parseInt(startMonth)-1, Integer.parseInt(startDay));
		    endCal.set(Integer.parseInt(endYear)-1, Integer.parseInt(endMonth)-1, Integer.parseInt(endDay));
	/*	    int currentYear = cal.get(Calendar.YEAR);
		    int currentMonth = cal.get(Calendar.MONTH)+1; //��ȡ��ǰ�·ݺ�
		    int currentMonthDay = cal.get(cal.DAY_OF_MONTH); //��ȡ��ǰ���ں�
		    System.out.println("��ǰ��ݣ� "+currentYear+"  ��ǰ�·ݣ�"+currentMonth +"  ��ǰ���ڣ�"+currentMonthDay);*/


		    
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
		    //codeCal.set(Integer.parseInt(stringYear), Integer.parseInt(stringMonth)-1, 28); //Ĭ��15��  ��������15��Ϊ��ĩ�ڼ���֮������˳��
		    startCal.set(Integer.parseInt(startYear), Integer.parseInt(startMonth)-1, Integer.parseInt(startDay));
		    endCal.set(Integer.parseInt(endYear), Integer.parseInt(endMonth)-1, Integer.parseInt(endDay));
	/*	    int currentYear = cal.get(Calendar.YEAR);
		    int currentMonth = cal.get(Calendar.MONTH)+1; //��ȡ��ǰ�·ݺ�
		    int currentMonthDay = cal.get(cal.DAY_OF_MONTH); //��ȡ��ǰ���ں�
		    System.out.println("��ǰ��ݣ� "+currentYear+"  ��ǰ�·ݣ�"+currentMonth +"  ��ǰ���ڣ�"+currentMonthDay);*/


		    
		    List<Integer> result = printDay(startCal,endCal);
		    
	/*	    System.out.println(nowDate>codeDate);
		    
		    System.out.println("test: "+FORMATTER.format(codeCal.getTime()));
		    System.out.println("testNow: "+FORMATTER.format(nowCal.getTime()));
			
			System.out.println(lastSub);*/
		    return result;
		}
		
}
