package lectoresuy.biblioteca.dao;

import lectoresuy.biblioteca.entidades.Prestamo;
import lectoresuy.biblioteca.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Arrays;
import java.util.List;

public class PrestamoDAO {

    public void guardar(Prestamo prestamo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(prestamo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Prestamo encontrarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Prestamo.class, id);
        }
    }

    public void actualizar(Prestamo prestamo) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(prestamo);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    public List<Prestamo> encontrarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Prestamo> query = session.createQuery("from Prestamo order by id", Prestamo.class);
            return query.list();
        }
    }
    
    public boolean materialEstaDisponible(Long materialId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Buscar préstamos activos para este material
            Query<Prestamo> query = session.createQuery(
                "FROM Prestamo p WHERE p.material.id = :materialId AND p.estado IN (:estadosActivos)", 
                Prestamo.class);
            query.setParameter("materialId", materialId);
            query.setParameterList("estadosActivos", 
                Arrays.asList(Prestamo.EstadoPrestamo.PENDIENTE, Prestamo.EstadoPrestamo.EN_CURSO));
            
            // Si no encuentra ningún préstamo activo, el material está disponible
            Prestamo prestamoActivo = query.uniqueResult();
            return prestamoActivo == null;
        }
    }

    public List<Object[]> contarPrestamosPendientesPorMaterial() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Object[]> query = session.createQuery(
                "SELECT p.material.id, COUNT(p) FROM Prestamo p WHERE p.estado = :estado GROUP BY p.material.id ORDER BY COUNT(p) DESC", 
                Object[].class);
            query.setParameter("estado", Prestamo.EstadoPrestamo.PENDIENTE);
            return query.list();
        }
    }
}
