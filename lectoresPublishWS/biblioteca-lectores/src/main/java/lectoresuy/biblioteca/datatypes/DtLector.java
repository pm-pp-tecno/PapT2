package lectoresuy.biblioteca.datatypes;

import lectoresuy.biblioteca.entidades.Lector.EstadoLector;

import java.util.Date;

public class DtLector {

    private Long id;
    private String nombre;
    private String email;
    private String direccion;
    private Date fechaRegistro;
    private EstadoLector estado;
    private String zona;

    // Constructores, getters y setters

    public DtLector() {
    }

    public DtLector(String nombre, String email, String direccion, Date fechaRegistro, EstadoLector estado, String zona) {
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.zona = zona;
    }

    public DtLector(Long id, String nombre, String email, String direccion, Date fechaRegistro, EstadoLector estado, String zona) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estado = estado;
        this.zona = zona;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return nombre + " (" + email + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DtLector dtLector = (DtLector) obj;
        return id != null && id.equals(dtLector.id);
    }
}
