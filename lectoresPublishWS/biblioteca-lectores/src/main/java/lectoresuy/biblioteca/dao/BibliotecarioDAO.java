package lectoresuy.biblioteca.dao;

import lectoresuy.biblioteca.entidades.Bibliotecario;
import lectoresuy.biblioteca.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class BibliotecarioDAO {

    public void guardar(Bibliotecario bibliotecario) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(bibliotecario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Bibliotecario encontrarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Bibliotecario.class, id);
        }
    }

    public void actualizar(Bibliotecario bibliotecario) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(bibliotecario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public Bibliotecario encontrarPorNumeroEmpleado(String numeroEmpleado) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bibliotecario> query = session.createQuery(
                "FROM Bibliotecario b WHERE b.numeroEmpleado = :numeroEmpleado", 
                Bibliotecario.class
            );
            query.setParameter("numeroEmpleado", numeroEmpleado);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Bibliotecario> encontrarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Bibliotecario> query = session.createQuery("from Bibliotecario", Bibliotecario.class);
            return query.list();
        }
    }
}
