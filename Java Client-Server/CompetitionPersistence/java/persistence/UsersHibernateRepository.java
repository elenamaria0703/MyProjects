package persistence;

import jdk.internal.jline.console.UserInterruptException;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UsersHibernateRepository implements IRepository<String, User> {
    private SessionFactory sessionFactory;

    public UsersHibernateRepository() {
        ORMUtils.initialize();
        sessionFactory=ORMUtils.getSessionFactory();
    }

    @Override
    public int size() {
        int size=0;
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                size = (int) session.createQuery(
                        "select count(*) from User").uniqueResult();
                tx.commit();
            }catch (RuntimeException e){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return size;
    }

    @Override
    public void save(User entity) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx= session.beginTransaction();
                session.save(entity);
                tx.commit();
            }catch (RuntimeException e){
                if(tx!=null)
                    tx.rollback();
            }
        }
    }

    @Override
    public User findOne(String s) {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                String queryString = "from User user where user.username=:searchString";
                User result = (User) session.createQuery(queryString).setParameter("searchString", s).uniqueResult();
                return result;
            }catch (RuntimeException e){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    @Override
    public Iterable<User> finadAll() {
        try(Session session=sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx=session.beginTransaction();
                String queryString = "from User";
                Iterable<User> result = session.createQuery(queryString).list();
                return result;
            }catch (RuntimeException e){
                if(tx!=null)
                    tx.rollback();
            }
        }
        return null;
    }

    public void delete(User entity){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                User user = session.createQuery("from User where username=:searchString", User.class)
                        .setParameter("searchString",entity.getUsername())
                        .uniqueResult();
                System.err.println("Stergem userul " + user.getUsername());
                session.delete(user);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }
    public void update(User entity){
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                User user =
                        (User) session.load( User.class, entity.getId() );
                user.setUsername(entity.getUsername());
                user.setPassword(entity.getPassword());
                session.update(user);
                tx.commit();

            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
    }
}
