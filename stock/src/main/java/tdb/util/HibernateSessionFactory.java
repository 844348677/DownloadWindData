package tdb.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * ������ �� hibernate3�е� sessionFactory
 * @author liuh
 *
 */
public class HibernateSessionFactory {       
	
    private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();        
    private static final Configuration cfg = new Configuration().configure();        
    private static SessionFactory sessionFactory;
    
/*    static {
        try {
            //����hibernate.cfg.xml����SessionFactory
            Configuration config=new Configuration().configure();//��ȡ�����ļ�
            sessionFactory=config.buildSessionFactory();      
        } catch (Throwable ex) {
            System.err.println("����SessionFactory����" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
/*    public static Session currentSession() throws HibernateException {          
        Session session=(Session)threadLocal.get();
        if(session==null||session.isOpen()){
         session=(sessionFactory!=null)?sessionFactory.openSession():null;
         threadLocal.set(session);
        }
        return session;     
    }*/
    public static Session currentSession() throws HibernateException {          
    	Session session = threadLocal.get();            
    	if (session == null || session.isOpen() == false) {                
    		if (sessionFactory == null) {                  
    			try {                     
    				sessionFactory = cfg.buildSessionFactory();                  
    			} catch (Exception e) {                      
    				e.printStackTrace();                  
    			}              
    		}                
    		session = sessionFactory.openSession();              
    		threadLocal.set(session);            
    	}            
    	return session;      
    }
  
    public static void closeSession(){
        Session session=(Session)threadLocal.get();
        threadLocal.set(null);
        if(session!=null){
         session.close();
        }
    }
    
}