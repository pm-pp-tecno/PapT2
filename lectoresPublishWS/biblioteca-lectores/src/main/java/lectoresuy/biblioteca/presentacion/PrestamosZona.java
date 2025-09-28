package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.interfaces.IControladorLector;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;

public class PrestamosZona extends JInternalFrame {

    private IControladorLector iconL;
    private IControladorPrestamo iconP;
    private JList<String> prestamosList;
    private DefaultListModel<String> listModel;
    private JComboBox<String> zonaComboBox;

    public PrestamosZona(IControladorPrestamo iconP, IControladorLector iconL) {
        super("Listar Préstamos de un Lector", false, false, false, false);
        this.iconP = iconP;
        this.iconL = iconL;
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Panel superior con el ComboBox de zonas
        String[] zonas = {"BIBLIOTECA CENTRAL", "SUCURSAL ESTE", "SUCURSAL OESTE", "BIBLIOTECA INFANTIL", "ARCHIVO GENERAL"};
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Seleccione una zona:"));
        zonaComboBox = new JComboBox<>(zonas);
        topPanel.add(zonaComboBox);
        add(topPanel, BorderLayout.NORTH);
        
        

        // Lista para mostrar los préstamos
        listModel = new DefaultListModel<>();
        prestamosList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(prestamosList);
        add(scrollPane, BorderLayout.CENTER);

        // Cargar lectores al iniciar
        recargarPrestamos();

        // ActionListener para el ComboBox
        zonaComboBox.addActionListener(e -> {
        	String zonaSeleccionada = (String) zonaComboBox.getSelectedItem();
            if (zonaSeleccionada != null) {
                cargarPrestamos(zonaSeleccionada);
            }
        });
    }

    public void recargarPrestamos() {
        if (zonaComboBox.getItemCount() > 0) {
        	String primerZona = (String) zonaComboBox.getItemAt(0);
            if (primerZona != null) {
                cargarPrestamos(primerZona);
            }
        }
    }

    private void cargarPrestamos(String zona) {
        listModel.clear();
        List<DtPrestamo> prestamos = iconP.listarPrestamosZona(zona);
        if (prestamos.isEmpty()) {
            listModel.addElement("La zona no tiene préstamos registrados.");
        } else {
            for (DtPrestamo prestamo : prestamos) {
                listModel.addElement("ID Préstamo: " + prestamo.getId() + ", Estado: " + prestamo.getEstado());
            }
        }
    }
}
