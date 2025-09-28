package lectoresuy.biblioteca;

import lectoresuy.biblioteca.dao.LectorDAO;
import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Usuario;
import lectoresuy.biblioteca.util.HibernateUtil;

import java.util.Date;

public class MainApp {

    public static void main(String[] args) {
        System.out.println("Iniciando la aplicacion de la biblioteca...");

        // Probando la creacion de un lector con el DAO
        LectorDAO lectorDAO = new LectorDAO();

        Lector nuevoLector = new Lector();
        nuevoLector.setNombre("Ana Lopez");
        nuevoLector.setEmail("ana.lopez@email.com");
        nuevoLector.setDireccion("Calle Ficticia 123");
        nuevoLector.setFechaRegistro(new Date());
        nuevoLector.setEstado(Lector.EstadoLector.ACTIVO);
        nuevoLector.setZona("BIBLIOTECA CENTRAL");

        System.out.println("Guardando un nuevo lector...");
        lectorDAO.guardar(nuevoLector);
        System.out.println("Lector guardado con exito. ID: " + nuevoLector.getId());

        // Probando la busqueda de un lector
        System.out.println("Buscando el lector con ID: " + nuevoLector.getId());
        Lector lectorEncontrado = lectorDAO.encontrarPorId(nuevoLector.getId());
        if (lectorEncontrado != null) {
            System.out.println("Lector encontrado: " + lectorEncontrado.getNombre());
        } else {
            System.out.println("Lector no encontrado.");
        }

        // Cierra la SessionFactory al finalizar la aplicacion
        HibernateUtil.shutdown();
        System.out.println("Aplicacion finalizada.");
    }
}