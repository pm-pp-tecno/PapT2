package lectoresuy.biblioteca.service;

import java.util.Date;

import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Bibliotecario;
import lectoresuy.biblioteca.entidades.Material;
import lectoresuy.biblioteca.entidades.Prestamo;
import lectoresuy.biblioteca.entidades.Prestamo.EstadoPrestamo;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;
import lectoresuy.biblioteca.excepciones.LectorNoEncontradoExcepcion;
import lectoresuy.biblioteca.excepciones.MaterialNoDisponibleExcepcion;
import java.util.List;

import lectoresuy.biblioteca.datatypes.DtBibliotecario;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.excepciones.BibliotecarioNoEncontradoExcepcion;
import lectoresuy.biblioteca.excepciones.BibliotecarioNoEncontradoExcepcion;
import lectoresuy.biblioteca.excepciones.MaterialNoDisponibleExcepcion;

public class ControladorPrestamo implements IControladorPrestamo {

    private ManejadorPrestamo manejadorPrestamo;
    private ManejadorLector manejadorLector;
    private ManejadorBibliotecario manejadorBibliotecario;
    private ManejadorMaterial manejadorMaterial;

    public ControladorPrestamo() {
        this.manejadorPrestamo = ManejadorPrestamo.getInstancia();
        this.manejadorLector = ManejadorLector.getInstancia();
        this.manejadorBibliotecario = ManejadorBibliotecario.getInstancia();
        this.manejadorMaterial = ManejadorMaterial.getInstancia();
    }

    @Override
    public void agregarPrestamo(Long idMaterial, String emailLector, String numeroBibliotecario, Date fechaDevolucion) throws Exception {
        
        if (!materialEstaDisponible(idMaterial)) {
            throw new MaterialNoDisponibleExcepcion("El material con ID " + idMaterial + " no está disponible para préstamo");
        }
        
        Lector lector = manejadorLector.buscarLector(emailLector);
        if (lector == null) {
            throw new LectorNoEncontradoExcepcion("No se encontró un lector con el email: " + emailLector);
        }
        
        Bibliotecario bibliotecario = manejadorBibliotecario.buscarBibliotecario(numeroBibliotecario);
        if (bibliotecario == null) {
            throw new BibliotecarioNoEncontradoExcepcion("No se encontró un bibliotecario con el número: " + numeroBibliotecario);
        }
        
        Material material = manejadorMaterial.buscarMaterial(idMaterial);
        if (material == null) {
            throw new Exception("No se encontró el material con ID: " + idMaterial);
        }
        
        Date fechaSolicitud = new Date();
        Prestamo nuevoPrestamo = new Prestamo(lector, bibliotecario, material, fechaSolicitud, fechaDevolucion, EstadoPrestamo.PENDIENTE);
        
        manejadorPrestamo.guardarPrestamo(nuevoPrestamo);
    }

    public boolean materialEstaDisponible(Long idMaterial) {
        return manejadorPrestamo.verificarDisponibilidadMaterial(idMaterial);
    }

    @Override
    public List<DtPrestamo> listarPrestamos() {
        return manejadorPrestamo.listarPrestamos();
    }

    @Override
    public void actualizarEstadoPrestamo(Long id, EstadoPrestamo nuevoEstado) {
        manejadorPrestamo.actualizarEstadoPrestamo(id, nuevoEstado);
    }

    @Override
    public void actualizarInformacionPrestamo(DtPrestamo prestamo) {
        manejadorPrestamo.actualizarInformacionPrestamo(prestamo);
    }

    @Override
    public List<DtPrestamo> listarPrestamosLector(DtLector lector) {
        return manejadorPrestamo.prestamosLector(lector);
    }

    @Override
    public List<DtPrestamo> listarPrestamosBibliotecario(DtBibliotecario bibliotecario) {
        return manejadorPrestamo.prestamosBibliotecario(bibliotecario);
    }

    @Override
    public List<DtPrestamo> listarPrestamosZona(String zona) {
        return manejadorPrestamo.prestamosZona(zona);
    }

    @Override
    public List<DtMaterial> listarMaterialesConteoPrestamosPendientes() {
        return manejadorPrestamo.listarMaterialesConteoPrestamosPendientes();
    }
    
}
