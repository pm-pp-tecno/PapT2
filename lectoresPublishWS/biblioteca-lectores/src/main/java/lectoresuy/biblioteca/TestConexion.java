package lectoresuy.biblioteca;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import lectoresuy.biblioteca.entidades.Usuario; // Make sure to import your class
import lectoresuy.biblioteca.util.HibernateUtil;

public class TestConexion {
    public static void main(String[] args) {
        SessionFactory sessionFactory = null;
        Session session = null;

        try {
            System.out.println("Intentando conectar a la base de datos...");

            // Obtener la SessionFactory
            sessionFactory = HibernateUtil.getSessionFactory();
            System.out.println("Conexión establecida con éxito.");

            // Abrir una nueva sesión
            session = sessionFactory.openSession();

            // Iniciar una transacción
            Transaction transaction = session.beginTransaction();

            // Crear y guardar un nuevo usuario
            Usuario testUser = new Usuario("Juan", "juan@test.com");
            session.save(testUser);

            // Commit de la transacción
            transaction.commit();

            System.out.println("Tablas de la base de datos verificadas y/o creadas con éxito.");
            System.out.println("Se guardó un usuario de prueba.");

        } catch (Exception e) {
            System.err.println("Error durante la conexión, creación de tablas o guardado de datos.");
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                HibernateUtil.shutdown();
            }
        }
    }
}