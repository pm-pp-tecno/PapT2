package lectoresuy.biblioteca.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lectoresuy.biblioteca.datatypes.DtLector;

import java.util.Date;

@Entity
@Table(name = "lectores")
@PrimaryKeyJoinColumn(name = "id_lector")
public class Lector extends Usuario {

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "fecha_registro")
    private Date fechaRegistro;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoLector estado;

    @Column(name = "zona")
    private String zona; // Opcional: Podría ser una entidad separada si las zonas tienen más propiedades

    // Enum para el estado del lector
    public enum EstadoLector {
        ACTIVO,
        SUSPENDIDO
    }

    // Constructores, getters y setters

    public Lector() {
        super();
    }

    public Lector(String nombre, String email, String direccion, Date fechaRegistro, EstadoLector estado, String zona) {
        super(nombre, email);
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.zona = zona;
    }

    public Lector(String nombre, String email, String password, String direccion, Date fechaRegistro, EstadoLector estado, String zona) {
        super(nombre, email, password);
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.zona = zona;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public EstadoLector getEstado() {
        return estado;
    }

    public void setEstado(EstadoLector estado) {
        this.estado = estado;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public DtLector getDtLector() {
        return new DtLector(this.getId(), this.getNombre(), this.getEmail(), this.getDireccion(), this.getFechaRegistro(), this.getEstado(), this.getZona());
    }
    
    @Override
    public String toString() {
        return this.getNombre() + " (" + this.getEmail() + ")";
    }
}
