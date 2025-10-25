package lectoresuy.biblioteca.service;

import java.util.ArrayList;
import java.util.List;

import lectoresuy.biblioteca.dao.LectorDAO;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Lector.EstadoLector;

public class ManejadorLector {
	
    private static ManejadorLector instancia = null;
    private LectorDAO lectorDAO;

    private ManejadorLector() {
        this.lectorDAO = new LectorDAO();
    }

    public static ManejadorLector getInstancia() {
        if (instancia == null)
            instancia = new ManejadorLector();
        return instancia;
    }

    public void guardarLector(Lector lector) {
        lectorDAO.guardar(lector);
    }

    public void actualizarLector(Lector lector) {
        lectorDAO.actualizar(lector);
    }

    public List<DtLector> listarLectores() {
        List<Lector> lectores = lectorDAO.obtenerTodos();
        List<DtLector> dtLectores = new ArrayList<>();
        for (Lector l : lectores) {
            dtLectores.add(l.getDtLector());
        }
        return dtLectores;
    }
    
	public Lector buscarLector(String email) {
		return lectorDAO.buscarPorEmail(email);
	}
    
    public boolean lectorEstaSuspendido(String emailLector) {
        Lector lector = lectorDAO.buscarPorEmail(emailLector);
        if (lector != null) {
            return lector.getEstado() == EstadoLector.SUSPENDIDO;
        }
        return false; // Si no se encuentra el lector, asumir que no está suspendido
    }
    
}
