package tdb.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tdb.util.PropertiesUtil;

public class TestWriteCode {
	public static void main(String[] args) throws Exception {
//		List<String> all = new LinkedList<String>();
//		try {
//			all = PropertiesUtil.returnAllProperties("resources/ticks.properties");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		for(int i=0;i<all.size();i++){
//			String[] line = all.get(i).split("=");
//			System.out.println("private "+line[1]+" "+line[0]+";");
//		}
		
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream("resources/ticks.properties"), "utf-8")); //传来的filename对应的csv文件
		String text = null;

		while ((text = br.readLine()) != null) {  //循环行
			String[] line = text.split("=");
			//System.out.println("private "+line[1]+" "+line[0]+";");
			System.out.println("<property name=\""+line[0]+"\" type=\""+line[1].toLowerCase()+"\" length=\"\"  column=\""+line[0].toUpperCase()+"\"/>");
		}
		br.close();
	}
}
