package tdb.test;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import tdb.domain.CodeInfo;
import tdb.util.HibernateSessionFactory;


public class TestHibernate {
	public static void main(String[] args) {

		// 获取Session
		Session session = HibernateSessionFactory.currentSession(); //sf.openSession();
		// 开启事务
		session.beginTransaction();
		
		String sql = "select * from CODEINFO";
		Query query = session.createSQLQuery(sql).addEntity(CodeInfo.class);
		List<CodeInfo> result = query.list();
		System.out.println("list.size: "+result.size());
		System.out.println(result.get(0).toString());
		
		// 提交事务
		session.getTransaction().commit();

		// 关闭连接
		session.close();

	}
}
