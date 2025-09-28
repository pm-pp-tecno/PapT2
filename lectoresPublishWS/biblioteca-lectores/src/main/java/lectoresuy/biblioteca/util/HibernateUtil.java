package lectoresuy.biblioteca.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Crea el objeto Configuration a partir de hibernate.cfg.xml
                Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
                
                configuration.addAnnotatedClass(lectoresuy.biblioteca.entidades.Material.class);
                configuration.addAnnotatedClass(lectoresuy.biblioteca.entidades.Usuario.class);
                configuration.addAnnotatedClass(lectoresuy.biblioteca.entidades.Articulo.class);
                configuration.addAnnotatedClass(lectoresuy.biblioteca.entidades.Bibliotecario.class);
                configuration.addAnnotatedClass(lectoresuy.biblioteca.entidades.Lector.class);
                configuration.addAnnotatedClass(lectoresuy.biblioteca.entidades.Libro.class);
                configuration.addAnnotatedClass(lectoresuy.biblioteca.entidades.Prestamo.class);
                
                // Crea el ServiceRegistry
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                // Crea el SessionFactory a partir del ServiceRegistry
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                System.err.println("Error al inicializar SessionFactory: " + e.getMessage());
                e.printStackTrace();
                throw new ExceptionInInitializerError("No se pudo inicializar Hibernate SessionFactory. Verifica la configuración.\n" + e.getMessage());
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}