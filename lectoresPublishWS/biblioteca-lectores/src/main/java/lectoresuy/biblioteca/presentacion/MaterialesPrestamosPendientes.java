package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtArticulo;
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;

public class MaterialesPrestamosPendientes extends JInternalFrame {

    private IControladorPrestamo iconP;
    private IControladorMaterial iconM;
    private JList<String> donacionesList;
    private DefaultListModel<String> listModel;

    public MaterialesPrestamosPendientes(IControladorPrestamo iconP, IControladorMaterial iconM) {
        super("Control de materiales con muchos prestamos pendientes", false, false, false, false);
        this.iconP = iconP;
        this.iconM = iconM;
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Lista para mostrar los prestamos con muchos prestamos
        listModel = new DefaultListModel<>();
        donacionesList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(donacionesList);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar donaciones al iniciar
        recargarPrestamosMuchosPendientes();

    }

    public void recargarPrestamosMuchosPendientes() {
        listModel.clear();
        List<DtMaterial> materiales = iconP.listarMaterialesConteoPrestamosPendientes();
        if (materiales.isEmpty()) {
            listModel.addElement("No hay materiales con préstamos pendientes.");
        } else {
            for (DtMaterial material : materiales) {
                String texto;
                String tipoMaterial;
                String titulo;
                if (material instanceof DtLibro) {
                    DtLibro libro = (DtLibro) material;
                    titulo = libro.getTitulo();
                    tipoMaterial = "Libro";
                } else {
                    DtArticulo articulo = (DtArticulo) material;
                    titulo = articulo.getDescripcion();
                    tipoMaterial = "Articulo";
                }
                texto = "ID Material: " + material.getId() + " | " + tipoMaterial + ": " + titulo + " (PENDIENTES " + material.getCantidadPrestamosPendientes() + ")";
                listModel.addElement(texto);
            }
        }
    }
}
