package tdb.test.actor;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import akka.actor.UntypedActor;
import tdb.util.HibernateSessionFactory;

public class TestActor2 extends UntypedActor{
	

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
		
		if(message instanceof List){
			Session session = sf.getCurrentSession(); //sf.openSession();
			System.out.println(message+" "+Thread.currentThread().getId()+" "+sf.toString()+" "+session.hashCode() );
			
			List list = (List) message;
			for(int i=0;i<list.size();i++){
				System.out.println(list.get(i));
			}
			sender().tell(true);
			
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
		}else
		      unhandled(message);
		
	}
}
