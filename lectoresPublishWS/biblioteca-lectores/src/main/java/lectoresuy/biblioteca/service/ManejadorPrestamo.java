package lectoresuy.biblioteca.service;

import lectoresuy.biblioteca.dao.BibliotecarioDAO;
import lectoresuy.biblioteca.dao.LectorDAO;
import lectoresuy.biblioteca.dao.MaterialDAO;
import lectoresuy.biblioteca.dao.PrestamoDAO;
import lectoresuy.biblioteca.entidades.Bibliotecario;
import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Material;
import lectoresuy.biblioteca.entidades.Prestamo;
import lectoresuy.biblioteca.entidades.Prestamo.EstadoPrestamo;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.datatypes.DtBibliotecario;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.datatypes.DtMaterial;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class ManejadorPrestamo {

    private PrestamoDAO prestamoDAO;
    private LectorDAO lectorDAO;
    private BibliotecarioDAO bibliotecarioDAO;
    private MaterialDAO materialDAO;
    private static ManejadorPrestamo instancia;
    
    private ManejadorPrestamo() {
        this.prestamoDAO = new PrestamoDAO();
        this.lectorDAO = new LectorDAO();
        this.bibliotecarioDAO = new BibliotecarioDAO();
        this.materialDAO = new MaterialDAO();
    }
    
    public static ManejadorPrestamo getInstancia() {
        if (instancia == null)
            instancia = new ManejadorPrestamo();
        return instancia;
    }

    /*public void crearNuevoPrestamo(Long lectorId, Long bibliotecarioId, Long materialId) {
        Lector lector = lectorDAO.encontrarPorId(lectorId);
        Bibliotecario bibliotecario = bibliotecarioDAO.encontrarPorId(bibliotecarioId);
        Material material = materialDAO.encontrarPorId(materialId);

        if (lector != null && bibliotecario != null && material != null) {
            Prestamo nuevoPrestamo = new Prestamo();
            nuevoPrestamo.setLector(lector);
            nuevoPrestamo.setBibliotecario(bibliotecario);
            nuevoPrestamo.setMaterial(material);
            nuevoPrestamo.setFechaSolicitud(new Date());
            nuevoPrestamo.setEstado(EstadoPrestamo.PENDIENTE);
            
            prestamoDAO.guardar(nuevoPrestamo);
        } else {
            System.err.println("Error: No se puede crear el préstamo. Lector, Bibliotecario o Material no encontrados.");
        }
    }*/
    
    public void guardarPrestamo(Prestamo prestamo) {
        prestamoDAO.guardar(prestamo);
    }

    public boolean verificarDisponibilidadMaterial(Long materialId) {
        return prestamoDAO.materialEstaDisponible(materialId);
    }

    public void actualizarEstadoPrestamo(Long prestamoId, EstadoPrestamo nuevoEstado) {
        System.out.println("=== ManejadorPrestamo.actualizarEstadoPrestamo ===");
        System.out.println("ID del préstamo: " + prestamoId);
        System.out.println("Nuevo estado: " + nuevoEstado);
        
        try {
            Prestamo prestamo = prestamoDAO.encontrarPorId(prestamoId);
            System.out.println("Préstamo encontrado: " + (prestamo != null));
            
            if (prestamo != null) {
                System.out.println("Estado anterior: " + prestamo.getEstado());
                prestamo.setEstado(nuevoEstado);
                System.out.println("Estado actualizado a: " + prestamo.getEstado());
                
                prestamoDAO.actualizar(prestamo);
                System.out.println("Préstamo actualizado en la base de datos exitosamente");
            } else {
                System.out.println("ERROR: No se encontró el préstamo con ID: " + prestamoId);
            }
        } catch (Exception e) {
            System.out.println("ERROR en actualizarEstadoPrestamo: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        System.out.println("=== FIN ManejadorPrestamo.actualizarEstadoPrestamo ===");
    }

    public void actualizarPrestamo(Long prestamoId, EstadoPrestamo estado, Date fechaDevolucionEstimada) {
        Prestamo prestamo = prestamoDAO.encontrarPorId(prestamoId);
        if (prestamo != null) {
            prestamo.setEstado(estado);
            prestamo.setFechaDevolucionEstimada(fechaDevolucionEstimada);
            prestamoDAO.actualizar(prestamo);
        }
    }

    public void actualizarInformacionPrestamo(DtPrestamo dtPrestamo) {
        Prestamo prestamo = prestamoDAO.encontrarPorId(dtPrestamo.getId());
        if (prestamo != null) {
            // Actualizar fechas
            prestamo.setFechaSolicitud(dtPrestamo.getFechaSolicitud());
            prestamo.setFechaDevolucionEstimada(dtPrestamo.getFechaDevolucionEstimada());

            // Actualizar relaciones
            Lector lector = lectorDAO.encontrarPorId(dtPrestamo.getLector().getId());
            if (lector != null) {
                prestamo.setLector(lector);
            }

            Bibliotecario bibliotecario = bibliotecarioDAO.encontrarPorId(dtPrestamo.getBibliotecario().getId());
            if (bibliotecario != null) {
                prestamo.setBibliotecario(bibliotecario);
            }

            Material material = materialDAO.encontrarPorId(dtPrestamo.getMaterial().getId());
            if (material != null) {
                prestamo.setMaterial(material);
            }

            prestamoDAO.actualizar(prestamo);
        }
    }

    public List<DtPrestamo> listarPrestamos() {
        List<Prestamo> prestamos = prestamoDAO.encontrarTodos();
        List<DtPrestamo> dtPrestamos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            dtPrestamos.add(p.getDtPrestamo());
        }
        return dtPrestamos;
    }

    public List<DtPrestamo> prestamosLector(DtLector lector) {
        List<Prestamo> prestamos = prestamoDAO.encontrarTodos();
        List<DtPrestamo> dtPrestamos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getLector() != null && p.getLector().getEmail().equals(lector.getEmail())) {
                dtPrestamos.add(p.getDtPrestamo());
            }
        }
        return dtPrestamos;
    }

    public List<DtPrestamo> prestamosBibliotecario(DtBibliotecario bibliotecario) {
        List<Prestamo> prestamos = prestamoDAO.encontrarTodos();
        List<DtPrestamo> dtPrestamos = new ArrayList<>();
        for (Prestamo p : prestamos) {
            if (p.getLector() != null && p.getBibliotecario().getNumeroEmpleado().equals(bibliotecario.getNumeroEmpleado())) {
                dtPrestamos.add(p.getDtPrestamo());
            }
        }
        return dtPrestamos;
    }

    public List<DtPrestamo> prestamosZona(String zona) {
        List<Prestamo> prestamos = prestamoDAO.encontrarTodos();
        List<DtPrestamo> dtPrestamos = new ArrayList<>();
        for (Prestamo p : prestamos) {
        	Lector lector = p.getLector();
            if (lector != null && lector.getZona().equals(zona)) {
                dtPrestamos.add(p.getDtPrestamo());
            }
        }
        return dtPrestamos;
    }

    public List<DtMaterial> listarMaterialesConteoPrestamosPendientes() {
        List<Object[]> resultados = prestamoDAO.contarPrestamosPendientesPorMaterial();
        List<DtMaterial> dtMateriales = new ArrayList<>();
        for (Object[] resultado : resultados) {
            Long materialId = (Long) resultado[0];
            int conteo = ((Number) resultado[1]).intValue();
            Material material = materialDAO.encontrarPorId(materialId);
            if (material != null) {
                DtMaterial dtMaterial = material.getDtMaterial();
                dtMaterial.setCantidadPrestamosPendientes(conteo);
                dtMateriales.add(dtMaterial);
            }
        }
        return dtMateriales;
    }

    public Prestamo buscarPrestamo(Long idMaterial, String emailLector, String numeroBibliotecario) {
        List<Prestamo> prestamos = prestamoDAO.encontrarTodos();
        for (Prestamo p : prestamos) {
            if (p.getMaterial() != null && p.getMaterial().getId().equals(idMaterial)
                && p.getLector() != null && p.getLector().getEmail().equals(emailLector)
                && p.getBibliotecario() != null && p.getBibliotecario().getNumeroEmpleado().equals(numeroBibliotecario)) {
                return p;
            }
        }
        return null;
    }
}
