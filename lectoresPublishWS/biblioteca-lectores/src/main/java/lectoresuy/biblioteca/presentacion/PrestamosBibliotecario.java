package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtArticulo;
import lectoresuy.biblioteca.datatypes.DtBibliotecario;
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.interfaces.IControladorBibliotecario;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;

public class PrestamosBibliotecario extends JInternalFrame {

    private IControladorBibliotecario iconB;
    private IControladorPrestamo iconP;
    private JComboBox<DtBibliotecario> bibliotecariosComboBox;
    private JList<String> prestamosList;
    private DefaultListModel<String> listModel;

    public PrestamosBibliotecario(IControladorPrestamo iconP, IControladorBibliotecario iconB) {
        super("Listar Préstamos de un Bibliotecario", false, false, false, false);
        this.iconP = iconP;
        this.iconB = iconB;
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Panel superior con el ComboBox de bibliotecarios
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Seleccione un bibliotecario:"));
        bibliotecariosComboBox = new JComboBox<>();
        topPanel.add(bibliotecariosComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Lista para mostrar los préstamos
        listModel = new DefaultListModel<>();
        prestamosList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(prestamosList);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar bibliotecarios al iniciar
        recargarBibliotecariosPrestamos();

        // ActionListener para el ComboBox
        bibliotecariosComboBox.addActionListener(e -> {
        	DtBibliotecario bibliotecarioSeleccionado = (DtBibliotecario) bibliotecariosComboBox.getSelectedItem();
            if (bibliotecarioSeleccionado != null) {
                cargarPrestamos(bibliotecarioSeleccionado);
            }
        });
    }

    public void recargarBibliotecariosPrestamos() {
    	bibliotecariosComboBox.removeAllItems();
        List<DtBibliotecario> bibliotecarios = iconB.listarBibliotecarios();
        for (DtBibliotecario bibliotecario : bibliotecarios) {
        	bibliotecariosComboBox.addItem(bibliotecario);
        }
        if (bibliotecariosComboBox.getItemCount() > 0) {
        	DtBibliotecario primerBibliotecario = (DtBibliotecario) bibliotecariosComboBox.getItemAt(0);
            if (primerBibliotecario != null) {
                cargarPrestamos(primerBibliotecario);
            }
        }
    }

    private void cargarPrestamos(DtBibliotecario bibliotecario) {
        listModel.clear();
        List<DtPrestamo> prestamos = iconP.listarPrestamosBibliotecario(bibliotecario);
        if (prestamos.isEmpty()) {
            listModel.addElement("El bibliotecario no tiene préstamos registrados.");
        } else {
            for (DtPrestamo prestamo : prestamos) {
                String titulo;
                if (prestamo.getMaterial() instanceof DtLibro) {
                    titulo = ((DtLibro) prestamo.getMaterial()).getTitulo();
                } else {
                    titulo = ((DtArticulo) prestamo.getMaterial()).getDescripcion();
                }
                listModel.addElement("ID: " + prestamo.getId() + " | " + titulo + " | Lector: " + prestamo.getLector().getNombre() + " | Estado: " + prestamo.getEstado());
            }
        }
    }
}
