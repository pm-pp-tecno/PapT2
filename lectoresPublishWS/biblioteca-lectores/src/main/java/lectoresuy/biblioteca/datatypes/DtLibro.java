package lectoresuy.biblioteca.datatypes;

import java.util.Date;

public class DtLibro extends DtMaterial {

    private Long id;
    private String titulo;
    private int cantidadPaginas;

    // Constructores, getters y setters

    public DtLibro() {
        super();
    }

    public DtLibro(Long id, String titulo, int cantidadPaginas, Date fechaIngreso) {
        super(id, fechaIngreso);
        this.id = id;
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getCantidadPaginas() {
        return cantidadPaginas;
    }

    public void setCantidadPaginas(int cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }

    @Override
    public String toString() {
        return id + ") " +titulo;
    }
}
