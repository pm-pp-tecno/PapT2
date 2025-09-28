package lectoresuy.biblioteca.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import lectoresuy.biblioteca.datatypes.DtPrestamo;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_prestamo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_lector")
    private Lector lector;

    @ManyToOne
    @JoinColumn(name = "id_bibliotecario")
    private Bibliotecario bibliotecario;

    @ManyToOne
    @JoinColumn(name = "id_material")
    private Material material;

    @Column(name = "fecha_solicitud")
    private Date fechaSolicitud;

    @Column(name = "fecha_devolucion_estimada")
    private Date fechaDevolucionEstimada;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoPrestamo estado;

    // Enum para el estado del préstamo
    public enum EstadoPrestamo {
        PENDIENTE("PENDIENTE"),
        EN_CURSO("EN CURSO"),
        DEVUELTO("DEVUELTO");

        private final String displayValue;

        private EstadoPrestamo(String displayValue) {
            this.displayValue = displayValue;
        }

        @Override
        public String toString() {
            return displayValue;
        }
    }

    // Constructores, getters y setters
    public Prestamo() {
        // Constructor por defecto
    }

    public Prestamo(Lector lector, Bibliotecario bibliotecario, Material material, Date fechaSolicitud, Date fechaDevolucionEstimada, EstadoPrestamo estado) {
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

    public Lector getLector() {
        return lector;
    }

    public void setLector(Lector lector) {
        this.lector = lector;
    }

    public Bibliotecario getBibliotecario() {
        return bibliotecario;
    }

    public void setBibliotecario(Bibliotecario bibliotecario) {
        this.bibliotecario = bibliotecario;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
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

    public DtPrestamo getDtPrestamo() {
        return new DtPrestamo(this.id, this.lector.getDtLector(), this.bibliotecario.getDtBibliotecario(), this.material.getDtMaterial(), this.fechaSolicitud, this.fechaDevolucionEstimada, this.estado);
    }
}
