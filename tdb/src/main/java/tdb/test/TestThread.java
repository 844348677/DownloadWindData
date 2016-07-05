package tdb.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class TestThread {
	public static void main(String[] args) {
/*		List<Integer> testList = new LinkedList<Integer>();
		testList.add(1);
		testList.add(2);
		testList.add(3);*/
		
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		
		System.out.println(time);
	}
}
