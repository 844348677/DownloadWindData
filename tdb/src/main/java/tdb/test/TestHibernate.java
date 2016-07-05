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

		// ��ȡSession
		Session session = HibernateSessionFactory.currentSession(); //sf.openSession();
		// ��������
		session.beginTransaction();
		
		String sql = "select * from CODEINFO";
		Query query = session.createSQLQuery(sql).addEntity(CodeInfo.class);
		List<CodeInfo> result = query.list();
		System.out.println("list.size: "+result.size());
		System.out.println(result.get(0).toString());
		
		// �ύ����
		session.getTransaction().commit();

		// �ر�����
		session.close();

	}
}
