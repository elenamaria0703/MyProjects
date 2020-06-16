package persistence;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ORMUtils {
    private static SessionFactory sessionFactory;
    static void initialize(){
        final StandardServiceRegistry registry=new StandardServiceRegistryBuilder().configure().build();
        try{
            sessionFactory=new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }catch (Exception e){
            System.out.println(e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    public static void close(){
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }

    }

}
