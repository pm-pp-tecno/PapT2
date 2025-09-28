package lectoresuy.biblioteca.service;

import lectoresuy.biblioteca.dao.LectorDAO;
import lectoresuy.biblioteca.dao.MaterialDAO;
import lectoresuy.biblioteca.entidades.Articulo;
import lectoresuy.biblioteca.entidades.Libro;
import lectoresuy.biblioteca.entidades.Material;

import java.util.Date;
import java.util.List;

import lectoresuy.biblioteca.datatypes.DtMaterial;
import java.util.ArrayList;

public class ManejadorMaterial {

    private MaterialDAO materialDAO;
    private static ManejadorMaterial instancia = null;

    private ManejadorMaterial() {
        this.materialDAO = new MaterialDAO();
    }

    public static ManejadorMaterial getInstancia() {
        if (instancia == null)
            instancia = new ManejadorMaterial();
        return instancia;
    }
    
    public void registrarNuevoLibro(Libro nuevoLibro) {
        materialDAO.guardar(nuevoLibro);
    }

    public void registrarNuevoArticulo(Articulo nuevoArticulo) {
        materialDAO.guardar(nuevoArticulo);
    }

    public List<Material> consultarTodasLasDonaciones() {
        return materialDAO.encontrarTodos();
    }
	
	public List<Material> obtenerMaterialesDisponibles() {
        return materialDAO.obtenerMaterialesDisponibles();
    }
    
    public Material buscarMaterial(Long id) {
        return materialDAO.encontrarPorId(id);
    }

    public List<DtMaterial> listarMateriales() {
        List<Material> materiales = materialDAO.encontrarTodos(); // Asumiendo que tienes este método en tu DAO
        List<DtMaterial> dtMateriales = new ArrayList<>();
        for (Material m : materiales) {
            dtMateriales.add(m.getDtMaterial()); // Asumiendo que Material tiene el método getDtMaterial()
        }
        return dtMateriales;
    }

    public List<DtMaterial> listarDonacionesPorFecha(Date fechaInicio, Date fechaFin) {
        List<Material> materiales = materialDAO.encontrarTodos();
        List<DtMaterial> dtMateriales = new ArrayList<>();
        for (Material m : materiales) {
            Date fecha = m.getFechaIngreso();
            if (fecha != null && !fecha.before(fechaInicio) && !fecha.after(fechaFin)) {
                dtMateriales.add(m.getDtMaterial());
            }
        }
        return dtMateriales;
    }
}