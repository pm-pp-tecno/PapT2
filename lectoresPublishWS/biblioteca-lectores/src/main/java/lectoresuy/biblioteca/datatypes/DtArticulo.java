package lectoresuy.biblioteca.datatypes;

import java.util.Date;

public class DtArticulo extends DtMaterial {

    private Long id;
    private String descripcion;
    private double peso;
    private String dimensiones;

    // Constructors, getters, and setters

    public DtArticulo() {
        super();
    }

    public DtArticulo(Long id, String descripcion, double peso, String dimensiones, Date fechaIngreso) {
        super(id, fechaIngreso);
        this.id = id;
        this.descripcion = descripcion;
        this.peso = peso;
        this.dimensiones = dimensiones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getDimensiones() {
        return dimensiones;
    }

    public void setDimensiones(String dimensiones) {
        this.dimensiones = dimensiones;
    }

    @Override
    public String toString() {
        return id + ") " + descripcion;
    }
}
