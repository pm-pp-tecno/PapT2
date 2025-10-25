package lectoresuy.biblioteca.service;

import lectoresuy.biblioteca.dao.LectorDAO;
import lectoresuy.biblioteca.dao.MaterialDAO;
import lectoresuy.biblioteca.entidades.Articulo;
import lectoresuy.biblioteca.entidades.Libro;
import lectoresuy.biblioteca.entidades.Material;

import java.util.Date;
import java.util.List;

import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtArticulo;
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

    public List<DtLibro> listarLibros() {
        List<Material> materiales = materialDAO.encontrarTodos();
        List<DtLibro> dtLibros = new ArrayList<>();
        for (Material m : materiales) {
            if (m instanceof Libro) {
                dtLibros.add(((Libro) m).getDtLibro());
            }
        }
        return dtLibros;
    }

    public List<DtArticulo> listarArticulos() {
        List<Material> materiales = materialDAO.encontrarTodos();
        List<DtArticulo> dtArticulos = new ArrayList<>();
        for (Material m : materiales) {
            if (m instanceof Articulo) {
                dtArticulos.add(((Articulo) m).getDtArticulo());
            }
        }
        return dtArticulos;
    }

    public void actualizarLibro(Long id, String titulo, Integer cantidadPaginas) {
        Material material = materialDAO.encontrarPorId(id);
        if (material instanceof Libro) {
            Libro libro = (Libro) material;
            libro.setTitulo(titulo);
            libro.setCantidadPaginas(cantidadPaginas.toString());
            materialDAO.actualizar(libro);
        } else {
            throw new RuntimeException("El material con ID " + id + " no es un libro");
        }
    }

    public void actualizarArticulo(Long id, String descripcion, Double peso, String dimensiones) {
        Material material = materialDAO.encontrarPorId(id);
        if (material instanceof Articulo) {
            Articulo articulo = (Articulo) material;
            articulo.setDescripcion(descripcion);
            articulo.setPeso(peso.floatValue());
            articulo.setDimensiones(dimensiones);
            materialDAO.actualizar(articulo);
        } else {
            throw new RuntimeException("El material con ID " + id + " no es un artículo");
        }
    }

    public void actualizarLibroConFecha(Long id, String titulo, Integer cantidadPaginas, Date fechaIngreso) {
        Material material = materialDAO.encontrarPorId(id);
        if (material instanceof Libro) {
            Libro libro = (Libro) material;
            libro.setTitulo(titulo);
            libro.setCantidadPaginas(cantidadPaginas.toString());
            libro.setFechaIngreso(fechaIngreso);
            materialDAO.actualizar(libro);
        } else {
            throw new RuntimeException("El material con ID " + id + " no es un libro");
        }
    }

    public void actualizarArticuloConFecha(Long id, String descripcion, Double peso, String dimensiones, Date fechaIngreso) {
        Material material = materialDAO.encontrarPorId(id);
        if (material instanceof Articulo) {
            Articulo articulo = (Articulo) material;
            articulo.setDescripcion(descripcion);
            articulo.setPeso(peso.floatValue());
            articulo.setDimensiones(dimensiones);
            articulo.setFechaIngreso(fechaIngreso);
            materialDAO.actualizar(articulo);
        } else {
            throw new RuntimeException("El material con ID " + id + " no es un artículo");
        }
    }

    public void actualizarFechaMaterial(Long id, Date fechaIngreso) {
        Material material = materialDAO.encontrarPorId(id);
        if (material != null) {
            material.setFechaIngreso(fechaIngreso);
            materialDAO.actualizar(material);
        } else {
            throw new RuntimeException("El material con ID " + id + " no existe");
        }
    }


    public void agregarLibro(String titulo, Integer cantidadPaginas, Date fechaIngreso) {
        Libro nuevoLibro = new Libro(fechaIngreso, titulo, cantidadPaginas.toString());
        materialDAO.guardar(nuevoLibro);
    }

    public void agregarArticulo(String descripcion, Double peso, String dimensiones, Date fechaIngreso) {
        Articulo nuevoArticulo = new Articulo(fechaIngreso, descripcion, peso.floatValue(), dimensiones);
        materialDAO.guardar(nuevoArticulo);
    }

    public List<DtMaterial> filtrarMaterialesPorFecha(Date fechaDesde, Date fechaHasta) {
        List<Material> materiales = materialDAO.encontrarTodos();
        List<DtMaterial> dtMateriales = new ArrayList<>();
        
        for (Material m : materiales) {
            Date fechaMaterial = m.getFechaIngreso();
            
            // Aplicar filtros de fecha
            boolean cumpleFiltro = true;
            if (fechaDesde != null && fechaMaterial.before(fechaDesde)) {
                cumpleFiltro = false;
            }
            if (fechaHasta != null && fechaMaterial.after(fechaHasta)) {
                cumpleFiltro = false;
            }
            
            if (cumpleFiltro) {
                dtMateriales.add(m.getDtMaterial());
            }
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