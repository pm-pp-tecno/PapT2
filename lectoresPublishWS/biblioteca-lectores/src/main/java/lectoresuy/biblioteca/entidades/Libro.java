package lectoresuy.biblioteca.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.Date;
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtMaterial;

@Entity
@Table(name = "libros")
@PrimaryKeyJoinColumn(name = "id_libro")
public class Libro extends Material {

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "cantidad_paginas")
    private String cantidadPaginas;

    // Constructores, getters y setters
    
    public Libro() {
        super();
    }

    public Libro(Date fechaIngreso, String titulo, String cantidadPaginas) {
        super(fechaIngreso);
        this.titulo = titulo;
        this.cantidadPaginas = cantidadPaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCantidadPaginas() {
        return cantidadPaginas;
    }

    public void setCantidadPaginas(String cantidadPaginas) {
        this.cantidadPaginas = cantidadPaginas;
    }
    
    @Override
    public String toString() {
        return "Libro ID: " + getId() + " - " + titulo;
    }

    @Override
    public DtMaterial getDtMaterial() {
        return new DtLibro(this.getId(), this.getTitulo(), Integer.parseInt(this.getCantidadPaginas()), this.getFechaIngreso());
    }

    public DtLibro getDtLibro() {
        return new DtLibro(this.getId(), this.getTitulo(), Integer.parseInt(this.getCantidadPaginas()), this.getFechaIngreso());
    }
}
