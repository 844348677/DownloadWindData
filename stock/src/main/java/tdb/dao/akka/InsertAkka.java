package tdb.dao.akka;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import akka.actor.UntypedActor;

public class InsertAkka extends UntypedActor {
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
		
		if(message instanceof String){
			Session session = sf.getCurrentSession(); //sf.openSession();
			//System.out.println(message+" "+Thread.currentThread().getId()+" "+sf.toString()+" "+session.hashCode() );
			
			// ��������
			session.beginTransaction();
			
			String sqlCreate = (String) message;
			Query queryCreate = session.createSQLQuery(sqlCreate);
			queryCreate.executeUpdate();
			
			
			// �ύ����
			session.getTransaction().commit();

			// �ر�����
			//HibernateSessionFactory.closeSession();
			//session.close();
		}else
		      unhandled(message);
		
	}
}
