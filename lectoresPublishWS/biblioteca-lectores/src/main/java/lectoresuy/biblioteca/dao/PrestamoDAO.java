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
        System.out.println("=== PrestamoDAO.encontrarPorId ===");
        System.out.println("Buscando préstamo con ID: " + id);
        
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Prestamo prestamo = session.get(Prestamo.class, id);
            System.out.println("Préstamo encontrado: " + (prestamo != null));
            if (prestamo != null) {
                System.out.println("Estado actual del préstamo: " + prestamo.getEstado());
            }
            return prestamo;
        } catch (Exception e) {
            System.out.println("ERROR en PrestamoDAO.encontrarPorId: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void actualizar(Prestamo prestamo) {
        System.out.println("=== PrestamoDAO.actualizar ===");
        System.out.println("ID del préstamo: " + prestamo.getId());
        System.out.println("Estado del préstamo: " + prestamo.getEstado());
        
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("Sesión de Hibernate abierta");
            transaction = session.beginTransaction();
            System.out.println("Transacción iniciada");
            
            session.update(prestamo);
            System.out.println("Préstamo actualizado en la sesión");
            
            transaction.commit();
            System.out.println("Transacción confirmada - cambios guardados en BD");
        } catch (Exception e) {
            System.out.println("ERROR en PrestamoDAO.actualizar: " + e.getMessage());
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
                System.out.println("Transacción revertida debido al error");
            }
            throw e; // Re-lanzar la excepción para que sea capturada en el nivel superior
        }
        System.out.println("=== FIN PrestamoDAO.actualizar ===");
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
