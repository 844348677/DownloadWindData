package tdb.stock.main;

import java.util.List;

import tdb.util.CodeUtil;
import tdb.util.DateUtil;

public class UpdateTable {
	public static void main(String[] args) {
		
		String startDate = "20150526";
		String endDate = "20160610";
		
		List dateList = DateUtil.betweenTwoDayNew(startDate, endDate);
		List<String> normalCodeList = CodeUtil.get50CodeList();
		
		System.out.println(normalCodeList.size());
		
		List testList = CodeUtil.getMoreCodeList(); //normalCodeList.subList(42, normalCodeList.size());
		
		for(int i=0;i<testList.size();i++){
			System.out.println(testList.get(i));
		}
	}
}
