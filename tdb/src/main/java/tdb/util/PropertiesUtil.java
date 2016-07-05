package tdb.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
/**
 * .properties w�ļ���Ҫ�õ���util��
 * @author liuh
 *
 */
public class PropertiesUtil {
	// ����Key��ȡValue
	public static String GetValueByKey(String filePath, String key) {
		Properties pps = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			pps.load(in);
			String value = pps.getProperty(key);
			System.out.println(key + " = " + value);
			return value;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ȡProperties��ȫ����Ϣ
	public static void GetAllProperties(String filePath) throws IOException {
		Properties pps = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		pps.load(in);
		Enumeration en = pps.propertyNames(); // �õ������ļ�������

		while (en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = pps.getProperty(strKey);
			System.out.println(strKey + "=" + strValue);
		}
	}
	// ��ȡProperties��ȫ����Ϣ
	public static List<String> returnAllProperties(String filePath) throws IOException {
		List<String> result = new LinkedList<String>();
		Properties pps = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream(filePath));
		pps.load(in);
		Enumeration en = pps.propertyNames(); // �õ������ļ�������

		while (en.hasMoreElements()) {
			String strKey = (String) en.nextElement();
			String strValue = pps.getProperty(strKey);
			System.out.println(strKey + "=" + strValue);
			result.add(strKey + "=" + strValue);
		}
		return result;
	}

	// д��Properties��Ϣ
	public static void WriteProperties(String filePath, String pKey, String pValue) throws IOException {
		Properties pps = new Properties();

		InputStream in = new FileInputStream(filePath);
		// ���������ж�ȡ�����б�����Ԫ�ضԣ�
		pps.load(in);
		// ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
		// ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
		OutputStream out = new FileOutputStream(filePath);
		pps.setProperty(pKey, pValue);
		// ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
		// ���� Properties ���е������б�����Ԫ�ضԣ�д�������
		pps.store(out, "Update " + pKey + " name");
	}
	
}
