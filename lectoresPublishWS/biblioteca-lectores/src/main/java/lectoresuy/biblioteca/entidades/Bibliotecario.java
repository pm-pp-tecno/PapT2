package lectoresuy.biblioteca.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import lectoresuy.biblioteca.datatypes.DtBibliotecario;

@Entity
@Table(name = "bibliotecarios")
@PrimaryKeyJoinColumn(name = "id_bibliotecario")
public class Bibliotecario extends Usuario {

    @Column(name = "numero_empleado")
    private String numeroEmpleado;

    // Constructores, getters y setters

    public Bibliotecario() {
        super();
    }

    public Bibliotecario(String nombre, String email, String numeroEmpleado) {
        super(nombre, email);
        this.numeroEmpleado = numeroEmpleado;
    }

    public Bibliotecario(String nombre, String email, String password, String numeroEmpleado) {
        super(nombre, email, password);
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public DtBibliotecario getDtBibliotecario() {
        return new DtBibliotecario(this.getId(), this.getNombre(), this.getEmail(), this.getNumeroEmpleado());
    }
    
    @Override
    public String toString() {
        return this.getNumeroEmpleado() + " - " + this.getNombre() + " (" + this.getEmail() + ")";
    }
}
