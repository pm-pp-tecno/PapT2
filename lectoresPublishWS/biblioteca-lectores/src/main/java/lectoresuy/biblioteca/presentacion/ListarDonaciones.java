package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtArticulo;
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;

public class ListarDonaciones extends JInternalFrame {

    private IControladorMaterial iconM;
    private JList<String> donacionesList;
    private DefaultListModel<String> listModel;

    public ListarDonaciones(IControladorMaterial iconM) {
        super("Listar Donaciones", false, false, false, false);
        this.iconM = iconM;
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Lista para mostrar las donaciones
        listModel = new DefaultListModel<>();
        donacionesList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(donacionesList);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar donaciones al iniciar
        recargarDonaciones();

    }

    public void recargarDonaciones() {
        listModel.clear();
        List<DtMaterial> donaciones = iconM.listarMateriales();
        if (donaciones.isEmpty()) {
            listModel.addElement("No hay donaciones registradas.");
        } else {
            for (DtMaterial donacion : donaciones) {
                String texto;
                if (donacion instanceof DtLibro) {
                    DtLibro libro = (DtLibro) donacion;
                    texto = "ID: " + libro.getId() + " | Libro: " + libro.getTitulo();
                } else if (donacion instanceof DtArticulo) {
                    DtArticulo articulo = (DtArticulo) donacion;
                    texto = "ID: " + articulo.getId() + " | Artículo: " + articulo.getDescripcion();
                } else {
                    texto = "ID: " + donacion.getId() + " | Material no especificado";
                }
                listModel.addElement(texto);
            }
        }
    }
}
