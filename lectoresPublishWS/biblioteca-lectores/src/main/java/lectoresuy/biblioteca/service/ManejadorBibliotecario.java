package lectoresuy.biblioteca.service;

import lectoresuy.biblioteca.dao.BibliotecarioDAO;
import lectoresuy.biblioteca.datatypes.DtBibliotecario;
import lectoresuy.biblioteca.entidades.Bibliotecario;
import java.util.ArrayList;
import java.util.List;

public class ManejadorBibliotecario {

	private static ManejadorBibliotecario instancia = null;
    private BibliotecarioDAO bibliotecarioDAO;

    public ManejadorBibliotecario() {
        this.bibliotecarioDAO = new BibliotecarioDAO();
    }

    public static ManejadorBibliotecario getInstancia() {
    	if (instancia == null)
    		instancia = new ManejadorBibliotecario();
    	return instancia;
    }
    
    public void registrarNuevoBibliotecario(String nombre, String email, String numeroEmpleado) {
        // Aquí podrías agregar lógica de negocio, como verificar la unicidad del número de empleado
        
        Bibliotecario nuevoBibliotecario = new Bibliotecario(nombre, email, numeroEmpleado);
        bibliotecarioDAO.guardar(nuevoBibliotecario);
    }
    

    public void guardarBibliotecario(Bibliotecario bibliotecario) {
    	bibliotecarioDAO.guardar(bibliotecario);
    }
    
    public Bibliotecario buscarBibliotecario(String numeroEmpleado) {
        return bibliotecarioDAO.encontrarPorNumeroEmpleado(numeroEmpleado);
    }
    
    public Bibliotecario buscarPorEmail(String email) {
        return bibliotecarioDAO.buscarPorEmail(email);
    }

    public List<DtBibliotecario> listarBibliotecarios() {
        List<Bibliotecario> bibliotecarios = bibliotecarioDAO.encontrarTodos();
        List<DtBibliotecario> dtBibliotecarios = new ArrayList<>();
        for (Bibliotecario b : bibliotecarios) {
            dtBibliotecarios.add(b.getDtBibliotecario());
        }
        return dtBibliotecarios;
    }
}
