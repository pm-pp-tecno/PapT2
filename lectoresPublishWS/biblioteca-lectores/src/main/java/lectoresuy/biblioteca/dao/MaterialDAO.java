package lectoresuy.biblioteca.dao;

import lectoresuy.biblioteca.entidades.Material;
import lectoresuy.biblioteca.entidades.Prestamo;
import lectoresuy.biblioteca.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.Arrays;
import java.util.List;

public class MaterialDAO {

    public void guardar(Material material) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(material);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public void actualizar(Material material) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(material);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Material encontrarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Material.class, id);
        }
    }

    public List<Material> encontrarTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Material> query = session.createQuery("from Material", Material.class);
            return query.list();
        }
    }
    
    public List<Material> obtenerMaterialesDisponibles() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            /* Si filtramos solo por material DEVUELTO, no es posible continuar asignandole prestamos
             * por lo tanto el punto 4.3 no tiene sentido.
        	// Query para obtener materiales que NO están en préstamos activos
            Query<Material> query = session.createQuery(
                "SELECT m FROM Material m WHERE m.id NOT IN " +
                "(SELECT p.material.id FROM Prestamo p WHERE p.estado IN (:estadosActivos))", 
                Material.class);
            query.setParameterList("estadosActivos", 
                Arrays.asList(Prestamo.EstadoPrestamo.PENDIENTE, Prestamo.EstadoPrestamo.EN_CURSO));
            */
        	Query<Material> query = session.createQuery(
                    "SELECT m FROM Material m", 
                    Material.class);
            return query.list();
        }
    }
}