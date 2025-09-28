package lectoresuy.biblioteca.interfaces;

import java.util.Date;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.datatypes.DtBibliotecario;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.entidades.Prestamo.EstadoPrestamo;

public interface IControladorPrestamo {

    void agregarPrestamo(Long idMaterial, String emailLector, String numeroBibliotecario, Date fechaDevolucion) throws Exception;

    List<DtPrestamo> listarPrestamos();

    void actualizarEstadoPrestamo(Long id, EstadoPrestamo nuevoEstado);

    void actualizarInformacionPrestamo(DtPrestamo prestamo);

    List<DtPrestamo> listarPrestamosLector(DtLector lector);

    List<DtPrestamo> listarPrestamosBibliotecario(DtBibliotecario bibliotecario);

    List<DtPrestamo> listarPrestamosZona(String zona);

    List<lectoresuy.biblioteca.datatypes.DtMaterial> listarMaterialesConteoPrestamosPendientes();
 
}
