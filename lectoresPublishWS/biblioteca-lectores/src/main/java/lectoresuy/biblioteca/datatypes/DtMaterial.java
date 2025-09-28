package lectoresuy.biblioteca.datatypes;

import java.util.Date;

public abstract class DtMaterial {

    private Long id;
    private Date fechaIngreso;
    private int cantidadPrestamosPendientes;

    // Constructores, getters y setters
    
    public DtMaterial() {
    }

    public DtMaterial(Long id, Date fechaIngreso) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public int getCantidadPrestamosPendientes() {
        return cantidadPrestamosPendientes;
    }

    public void setCantidadPrestamosPendientes(int cantidadPrestamosPendientes) {
        this.cantidadPrestamosPendientes = cantidadPrestamosPendientes;
    }
    
    @Override
    public String toString() {
        return "ID: " + id + ", Fecha ingreso: " + (fechaIngreso != null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaIngreso) : "-");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DtMaterial that = (DtMaterial) obj;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
