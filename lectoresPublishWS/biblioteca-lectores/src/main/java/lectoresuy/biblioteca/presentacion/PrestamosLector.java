package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.interfaces.IControladorLector;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;

public class PrestamosLector extends JInternalFrame {

    private IControladorLector iconL;
    private IControladorPrestamo iconP;
    private JComboBox<DtLector> lectoresComboBox;
    private JList<String> prestamosList;
    private DefaultListModel<String> listModel;

    public PrestamosLector(IControladorLector iconL, IControladorPrestamo iconP) {
        super("Listar Préstamos de un Lector", false, false, false, false);
        this.iconL = iconL;
        this.iconP = iconP;
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Panel superior con el ComboBox de lectores
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Seleccione un lector:"));
        lectoresComboBox = new JComboBox<>();
        topPanel.add(lectoresComboBox);
        add(topPanel, BorderLayout.NORTH);

        // Lista para mostrar los préstamos
        listModel = new DefaultListModel<>();
        prestamosList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(prestamosList);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar lectores al iniciar
        recargarLectores();

        // ActionListener para el ComboBox
        lectoresComboBox.addActionListener(e -> {
            DtLector lectorSeleccionado = (DtLector) lectoresComboBox.getSelectedItem();
            if (lectorSeleccionado != null) {
                cargarPrestamos(lectorSeleccionado);
            }
        });
    }

    public void recargarLectores() {
        lectoresComboBox.removeAllItems();
        List<DtLector> lectores = iconL.listarLectores();
        for (DtLector lector : lectores) {
            lectoresComboBox.addItem(lector);
        }
        if (lectoresComboBox.getItemCount() > 0) {
            DtLector primerLector = (DtLector) lectoresComboBox.getItemAt(0);
            if (primerLector != null) {
                cargarPrestamos(primerLector);
            }
        }
    }

    private void cargarPrestamos(DtLector lector) {
        listModel.clear();
        List<DtPrestamo> prestamos = iconP.listarPrestamosLector(lector);
        if (prestamos.isEmpty()) {
            listModel.addElement("El lector no tiene préstamos registrados.");
        } else {
            for (DtPrestamo prestamo : prestamos) {
                listModel.addElement("ID Préstamo: " + prestamo.getId() + ", Estado: " + prestamo.getEstado());
            }
        }
    }
}
