package lectoresuy.biblioteca.datatypes;

public class DtBibliotecario {

    private Long id;
    private String nombre;
    private String email;
    private String numeroEmpleado;

    // Constructors, getters, and setters

    public DtBibliotecario() {
    }

    public DtBibliotecario(Long id, String nombre, String email, String numeroEmpleado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.numeroEmpleado = numeroEmpleado;
    }

    public DtBibliotecario(String nombre, String email, String numeroEmpleado) {
        this.nombre = nombre;
        this.email = email;
        this.numeroEmpleado = numeroEmpleado;
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

    public String getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(String numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    @Override
    public String toString() {
        return numeroEmpleado + " - " + nombre + " (" + email + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DtBibliotecario that = (DtBibliotecario) obj;
        return numeroEmpleado != null && numeroEmpleado.equals(that.numeroEmpleado);
    }

    @Override
    public int hashCode() {
        return numeroEmpleado != null ? numeroEmpleado.hashCode() : 0;
    }
}
