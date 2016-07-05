package tdb.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.bridj.Pointer;

import tdbapi.TDBAPILibrary;
import tdbapi.TDBDefine_ReqTick;
import tdbapi.TDBDefine_Tick;

public class Test {
	
	public static void main(String[] args) throws ParseException {
/*		Pointer<?> hTdb = null;
		Pointer<TDBDefine_ReqTick> pReq = null;
		Pointer<Pointer<TDBDefine_Tick>> pData = null;
		Pointer<Integer> pCount = null;
		int resultCode = TDBAPILibrary.TDB_GetTick(hTdb, pReq, pData, pCount);
		
		int num = pCount.get();
		Pointer<TDBDefine_Tick> _data = pData.get();
		
		TDBDefine_Tick data = _data.get(0);*/
		
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
            System.out.println(df.format(startCalendar.getTime()));
        }else{
            break;
        }
        }
	}

}
