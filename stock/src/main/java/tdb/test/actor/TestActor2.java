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
		// 读取hibernate.cfg.xml中的配置
		cfg.configure();
		// 获取SessionFactory
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
			// 开启事务
			session.beginTransaction();
			
			String sqlCreate = "INSERT INTO TESTSQL (ID,VALUE) VALUES ("+message+",'value')";
			Query queryCreate = session.createSQLQuery(sqlCreate);
			queryCreate.executeUpdate();
			
			
			// 提交事务
			session.getTransaction().commit();*/

			// 关闭连接
			//HibernateSessionFactory.closeSession();
			//session.close();
		}else
		      unhandled(message);
		
	}
}
