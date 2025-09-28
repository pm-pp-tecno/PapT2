package lectoresuy.biblioteca.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;
import lectoresuy.biblioteca.datatypes.DtArticulo;
import lectoresuy.biblioteca.datatypes.DtMaterial;

@Entity
@Table(name = "articulos")
@PrimaryKeyJoinColumn(name = "id_articulo")
public class Articulo extends Material {

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "peso")
    private float peso;

    @Column(name = "dimensiones")
    private String dimensiones;

    // Constructores, getters y setters
    
    public Articulo() {
        super();
    }

    public Articulo(Date fechaIngreso, String descripcion, float peso, String dimensiones) {
        super(fechaIngreso);
        this.descripcion = descripcion;
        this.peso = peso;
        this.dimensiones = dimensiones;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
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
        return "Artículo ID: " + getId() + " - " + descripcion;
    }

    @Override
    public DtMaterial getDtMaterial() {
        return new DtArticulo(this.getId(), this.getDescripcion(), this.getPeso(), this.getDimensiones(), this.getFechaIngreso());
    }
}
