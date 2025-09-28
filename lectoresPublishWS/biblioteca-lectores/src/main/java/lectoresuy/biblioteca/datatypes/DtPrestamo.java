package lectoresuy.biblioteca.datatypes;

import java.util.Date;
import lectoresuy.biblioteca.entidades.Prestamo.EstadoPrestamo;

public class DtPrestamo {

    private Long id;
    private DtLector lector;
    private DtBibliotecario bibliotecario;
    private DtMaterial material;
    private Date fechaSolicitud;
    private Date fechaDevolucionEstimada;
    private EstadoPrestamo estado;

    // Constructors, getters, and setters

    public DtPrestamo() {
    }

    public DtPrestamo(Long id, DtLector lector, DtBibliotecario bibliotecario, DtMaterial material, Date fechaSolicitud, Date fechaDevolucionEstimada, EstadoPrestamo estado) {
        this.id = id;
        this.lector = lector;
        this.bibliotecario = bibliotecario;
        this.material = material;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DtLector getLector() {
        return lector;
    }

    public void setLector(DtLector lector) {
        this.lector = lector;
    }

    public DtBibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(DtBibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public DtMaterial getMaterial() {
        return material;
    }

    public void setMaterial(DtMaterial material) {
        this.material = material;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaDevolucionEstimada() {
        return fechaDevolucionEstimada;
    }

    public void setFechaDevolucionEstimada(Date fechaDevolucionEstimada) {
        this.fechaDevolucionEstimada = fechaDevolucionEstimada;
    }

    public EstadoPrestamo getEstado() {
        return estado;
    }

    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ID: " + id + " - " + lector.getNombre() + " - " + material.toString();
    }
}
