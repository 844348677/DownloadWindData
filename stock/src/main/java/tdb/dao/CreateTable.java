package tdb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.Query;
import org.hibernate.Session;

import tdb.util.HibernateSessionFactory;
import tdb.util.PropertiesUtil;

public class CreateTable {

	/**
	 * ���������õľ�̬�ַ�������
	 */
	public final static String createTableColumn = " (ID NUMBER(19) NOT NULL,CHWINDCODE VARCHAR2(20 CHAR),CHCODE VARCHAR2(20 CHAR),NDATE NUMBER(10),NTIME NUMBER(10),NPRICE NUMBER(10),"+
        "IVOLUME NUMBER(19),ITUROVER NUMBER(19),NMATCHITEMS NUMBER(10),NINTEREST NUMBER(10),CHTRADEFLAG VARCHAR2(10 CHAR),CHBSFLAG VARCHAR2(10 CHAR),IACCVOLUME NUMBER(19),IACCTUROVER NUMBER(19),"+
        "NHIGH NUMBER(10),NLOW NUMBER(10),NOPEN NUMBER(10),NPRECLOSE NUMBER(10),NSETTLE NUMBER(10),NPOSITION NUMBER(10),NCURDELTA NUMBER(10),NPRESETTLE NUMBER(10),NPREPOSITION NUMBER(10),"+
        "NASKPRICE0 NUMBER(10),NASKPRICE1 NUMBER(10),NASKPRICE2 NUMBER(10),NASKPRICE3 NUMBER(10),NASKPRICE4 NUMBER(10),NASKPRICE5 NUMBER(10),NASKPRICE6 NUMBER(10),NASKPRICE7 NUMBER(10),NASKPRICE8 NUMBER(10),"+
        "NASKPRICE9 NUMBER(10),NASKVOLUME0 NUMBER(10),NASKVOLUME1 NUMBER(10),NASKVOLUME2 NUMBER(10),NASKVOLUME3 NUMBER(10),NASKVOLUME4 NUMBER(10),NASKVOLUME5 NUMBER(10),NASKVOLUME6 NUMBER(10),NASKVOLUME7 NUMBER(10),"+
        "NASKVOLUME8 NUMBER(10),NASKVOLUME9 NUMBER(10),NBIDPRICE0 NUMBER(10),NBIDPRICE1 NUMBER(10),NBIDPRICE2 NUMBER(10),NBIDPRICE3 NUMBER(10),NBIDPRICE4 NUMBER(10),NBIDPRICE5 NUMBER(10),NBIDPRICE6 NUMBER(10),"+
        "NBIDPRICE7 NUMBER(10),NBIDPRICE8 NUMBER(10),NBIDPRICE9 NUMBER(10),NBIDVOLUME0 NUMBER(10),NBIDVOLUME1 NUMBER(10),NBIDVOLUME2 NUMBER(10),NBIDVOLUME3 NUMBER(10),NBIDVOLUME4 NUMBER(10),NBIDVOLUME5 NUMBER(10),"+
        "NBIDVOLUME6 NUMBER(10),NBIDVOLUME7 NUMBER(10),NBIDVOLUME8 NUMBER(10),NBIDVOLUME9 NUMBER(10),NASKAVPRICE NUMBER(10),NBIDAVPRICE NUMBER(10),ITOTALASKVOLUME NUMBER(19),ITOTALBIDVOLUME NUMBER(19),"+
        "NINDEX NUMBER(10),NSTOCKS NUMBER(10),NUPS NUMBER(10),NDOWNS NUMBER(10),NHOLDLINES NUMBER(10),NRESV1 NUMBER(10),NRESV2 NUMBER(10),NRESV3 NUMBER(10),PRIMARY KEY (ID))";
	
	
	/**
	 * �����жϱ��Ƿ���ڵķ���  ���ڷ���true�������ڷ���false
	 * @param table
	 * @return
	 * @throws SQLException
	 *  getTables(String catalog,String schemaPattern,String tableNamePattern,String[] types)
		����: catalog:Ŀ¼���ƣ�һ�㶼Ϊ��.		
		������schema:���ݿ���������sqlserver2005+��oracle��˵���û���		
		������tablename:������		
		������type :�������(TABLE | VIEW)		
		ע�⣺��ʹ�ù����У��������Ʊ���ʹ�ô�д�ġ�
	 */
	public synchronized static Boolean existTable(String code) {
		String[] splitString = code.split("[.]");
		//System.out.println(splitString[1].toUpperCase()+","+splitString[0].toUpperCase());
		String tableName = splitString[1].toUpperCase() + splitString[0].toUpperCase();
		
		Connection conn = HibernateSessionFactory.currentSession().connection();
		DatabaseMetaData metaData;
		ResultSet rs;
		try {
			metaData = conn.getMetaData();
			rs = metaData.getTables(null, "STOCK", tableName, new String[]{"TABLE"});  //���ݿ��û�����
			if(rs.next()){
				conn.close();
				rs.close();
				return true;    
			}else{
				conn.close();
				rs.close();
				return false;   
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
		
	}
	
	/**
	 * ������   ����codename�����ӣ�ag1604.SHF �����ı��� SHFAG1604
	 * @param code
	 */
	public synchronized static void createTableByCode(String code){
		
		
		code = code.replace("-", "");
		
		String[] splitString = code.split("[.]");
		//System.out.println(splitString[1].toUpperCase()+","+splitString[0].toUpperCase());
		String tableName = splitString[1].toUpperCase() + splitString[0].toUpperCase();
		
		// ��ȡSession
		Session session = HibernateSessionFactory.currentSession(); //sf.openSession();
		// ��������
		session.beginTransaction();
		
		String sqlCreate = "CREATE TABLE "+tableName+createTableColumn;
		Query queryCreate = session.createSQLQuery(sqlCreate);
		queryCreate.executeUpdate();
		
		// �ύ����
		session.getTransaction().commit();
		// �ر�����
		//session.close();
		
		try {
			PropertiesUtil.WriteProperties("resources/ticksTable.properties", tableName, 100000000+"");
		} catch (IOException e) {
			System.out.println("create table write into ticksTable.properties problem");
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		System.out.println(existTable("ag1604.SHF"));
/*		if(!existTable("ag1604.SHF"))
			createTableByCode("ag1604.SHF");*/
	}
	
	
}
