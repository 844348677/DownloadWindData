package tdb.test;

import java.io.IOException;

import tdb.util.PropertiesUtil;

public class TestWritePropAgain {
	public static void main(String[] args) {
		try {
			PropertiesUtil.WriteProperties("resources/ticksTable.properties", "SHFRU0309", 100000000+"");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
