package tdb.test;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import tdb.domain.CodeInfo;
import tdb.util.HibernateSessionFactory;

public class TestSQL {
	public static void main(String[] args) {
		insertData();
	}
	
	public static void createTable(){
		// 获取Session
		Session session = HibernateSessionFactory.currentSession(); //sf.openSession();
		// 开启事务
		session.beginTransaction();
		
/*		String sql = "select * from CODEINFO";
		Query query = session.createSQLQuery(sql).addEntity(CodeInfo.class);
		List<CodeInfo> result = query.list();
		System.out.println("list.size: "+result.size());
		System.out.println(result.get(0).toString());*/
		
		String sqlCreate = "CREATE TABLE TESTSQL(ID NUMBER(22) PRIMARY KEY,value VARCHAR2(40) )";
		Query queryCreate = session.createSQLQuery(sqlCreate);
		queryCreate.executeUpdate();
		
		
		// 提交事务
		session.getTransaction().commit();

		// 关闭连接
		session.close();
	}
	
	public static void insertData(){
		// 获取Session
		Session session = HibernateSessionFactory.currentSession(); //sf.openSession();
		// 开启事务
		session.beginTransaction();
		
/*		String sql = "select * from CODEINFO";
		Query query = session.createSQLQuery(sql).addEntity(CodeInfo.class);
		List<CodeInfo> result = query.list();
		System.out.println("list.size: "+result.size());
		System.out.println(result.get(0).toString());*/
		
		String sqlCreate = "INSERT INTO TESTSQL (ID,VALUE) VALUES (2,'test')";
		Query queryCreate = session.createSQLQuery(sqlCreate);
		queryCreate.executeUpdate();
		
		
		// 提交事务
		session.getTransaction().commit();

		// 关闭连接
		session.close();
	}
}
