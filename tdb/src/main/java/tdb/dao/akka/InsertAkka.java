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
		// 读取hibernate.cfg.xml中的配置
		cfg.configure();
		// 获取SessionFactory
		 sf = cfg.buildSessionFactory();
    }
    
	
	@Override
	public void onReceive(Object message) throws Exception {
		
		if(message instanceof String){
			Session session = sf.getCurrentSession(); //sf.openSession();
			//System.out.println(message+" "+Thread.currentThread().getId()+" "+sf.toString()+" "+session.hashCode() );
			
			// 开启事务
			session.beginTransaction();
			
			String sqlCreate = (String) message;
			Query queryCreate = session.createSQLQuery(sqlCreate);
			queryCreate.executeUpdate();
			
			
			// 提交事务
			session.getTransaction().commit();

			// 关闭连接
			//HibernateSessionFactory.closeSession();
			//session.close();
		}else
		      unhandled(message);
		
	}
}
