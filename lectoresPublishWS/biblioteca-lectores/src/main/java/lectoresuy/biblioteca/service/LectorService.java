package lectoresuy.biblioteca.service;

import lectoresuy.biblioteca.dao.LectorDAO;
import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Lector.EstadoLector;

public class LectorService {

    private LectorDAO lectorDAO;

    public LectorService() {
        this.lectorDAO = new LectorDAO();
    }

    public void agregarLector(Lector lector) {
        // Aquí puedes agregar lógica de negocio, por ejemplo, validar los datos antes de guardarlos
        // Por ahora, simplemente guardamos el lector
        lectorDAO.guardar(lector);
    }

    public void suspenderLector(Long lectorId) {
        Lector lector = lectorDAO.encontrarPorId(lectorId);
        if (lector != null) {
            // Lógica de negocio: cambiar el estado del lector
            lector.setEstado(EstadoLector.SUSPENDIDO);
            lectorDAO.actualizar(lector);
        }
    }
}