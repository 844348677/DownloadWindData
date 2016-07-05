package tdb.test.actor;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import akka.actor.UntypedActor;
import tdb.util.HibernateSessionFactory;

public class TestActor extends UntypedActor{
	

	static Configuration cfg;
    static SessionFactory sf;

    static {
		 cfg = new Configuration();
		// ��ȡhibernate.cfg.xml�е�����
		cfg.configure();
		// ��ȡSessionFactory
		 sf = cfg.buildSessionFactory();

    }
    
	
	@Override
	public void onReceive(Object message) throws Exception {
		
		if(message instanceof Integer){
			Session session = sf.getCurrentSession(); //sf.openSession();
			System.out.println(message+" "+Thread.currentThread().getId()+" "+sf.toString()+" "+session.hashCode() );
			
/*			Session session = sf.getCurrentSession(); //sf.openSession();
			// ��������
			session.beginTransaction();
			
			String sqlCreate = "INSERT INTO TESTSQL (ID,VALUE) VALUES ("+message+",'value')";
			Query queryCreate = session.createSQLQuery(sqlCreate);
			queryCreate.executeUpdate();
			
			
			// �ύ����
			session.getTransaction().commit();*/

			// �ر�����
			//HibernateSessionFactory.closeSession();
			//session.close();
		}
		
	}
}
