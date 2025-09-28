package lectoresuy.biblioteca.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import java.util.Date;
import lectoresuy.biblioteca.datatypes.DtMaterial;

@Entity
@Table(name = "material")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_material")
    private Long id;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    // Constructores, getters y setters
    
    public Material() {
        // Constructor por defecto
    }

    public Material(Date fechaIngreso) {
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
    
    @Override
    public String toString() {
        return "Material ID: " + id;
    }

    public abstract DtMaterial getDtMaterial();
}
