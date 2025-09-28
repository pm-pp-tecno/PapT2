package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import lectoresuy.biblioteca.entidades.Lector.EstadoLector;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.interfaces.IControladorLector;

public class CambiarZonaLector extends JInternalFrame {

    private IControladorLector icon;
    private JComboBox<DtLector> lectoresComboBox;
    private JComboBox<String> zonaComboBox;

    public CambiarZonaLector(IControladorLector icon) {
        super("Cambiar Zona del Lector", false, false, false, false);
        setSize(400, 400);
        this.icon = icon;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ComboBox para Lectores
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Lector:"), gbc);

        lectoresComboBox = new JComboBox<>();
        lectoresComboBox.addActionListener(e -> {
            DtLector lectorSeleccionado = (DtLector) lectoresComboBox.getSelectedItem();
            if (lectorSeleccionado != null) {
                zonaComboBox.setSelectedItem(lectorSeleccionado.getZona());
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(lectoresComboBox, gbc);

        // ComboBox para Zonas
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nueva Zona:"), gbc);

        String[] zonas = {"BIBLIOTECA CENTRAL", "SUCURSAL ESTE", "SUCURSAL OESTE", "BIBLIOTECA INFANTIL", "ARCHIVO GENERAL"};
        zonaComboBox = new JComboBox<>(zonas);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(zonaComboBox, gbc);

        // Botones
        JButton aceptarButton = new JButton("Aceptar");
        JButton cancelarButton = new JButton("Cancelar");

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(aceptarButton);
        buttonPanel.add(cancelarButton);
        panel.add(buttonPanel, gbc);

        aceptarButton.addActionListener(e -> {
            try {
                DtLector lectorSeleccionado = (DtLector) lectoresComboBox.getSelectedItem();
                if (lectorSeleccionado == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar un lector.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String nuevaZona = (String) zonaComboBox.getSelectedItem();
                icon.cambiarZonaLector(lectorSeleccionado.getEmail(), nuevaZona);
                JOptionPane.showMessageDialog(this, "Zona del lector actualizada con éxito.");
                recargarLectores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar la zona del lector: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarButton.addActionListener(e -> this.dispose());

        add(panel);
    }

    public void recargarLectores() {
        lectoresComboBox.removeAllItems();
        List<DtLector> lectores = icon.listarLectores();
        for (DtLector dt : lectores) {
            lectoresComboBox.addItem(dt);
        }
        if (lectoresComboBox.getItemCount() > 0) {
            DtLector primerLector = (DtLector) lectoresComboBox.getItemAt(0);
            if (primerLector != null) {
                zonaComboBox.setSelectedItem(primerLector.getZona());
            }
        }
    }
}
