package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.entidades.Prestamo.EstadoPrestamo;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;

public class CambiarEstadoPrestamo extends JInternalFrame {

    private IControladorPrestamo icon;
    private JComboBox<DtPrestamo> prestamosComboBox;
    private JComboBox<EstadoPrestamo> estadosComboBox;

    public CambiarEstadoPrestamo(IControladorPrestamo icon) {
        super("Cambiar estado de un prestamo", false, false, false, false);
        setSize(450, 200);
        this.icon = icon;

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ComboBox para Préstamos
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Préstamo:"), gbc);

        prestamosComboBox = new JComboBox<>();
        prestamosComboBox.addActionListener(e -> {
            DtPrestamo prestamoSeleccionado = (DtPrestamo) prestamosComboBox.getSelectedItem();
            if (prestamoSeleccionado != null) {
                estadosComboBox.setSelectedItem(prestamoSeleccionado.getEstado());
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(prestamosComboBox, gbc);

        // ComboBox para Estados
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nuevo Estado:"), gbc);

        estadosComboBox = new JComboBox<>(EstadoPrestamo.values());
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(estadosComboBox, gbc);

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
                DtPrestamo prestamoSeleccionado = (DtPrestamo) prestamosComboBox.getSelectedItem();
                if (prestamoSeleccionado == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar un préstamo.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                EstadoPrestamo nuevoEstado = (EstadoPrestamo) estadosComboBox.getSelectedItem();
                icon.actualizarEstadoPrestamo(prestamoSeleccionado.getId(), nuevoEstado);
                JOptionPane.showMessageDialog(this, "Estado del préstamo actualizado con éxito.");
                recargarPrestamos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el estado del préstamo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarButton.addActionListener(e -> this.dispose());

        add(panel);
    }

    public void recargarPrestamos() {
        prestamosComboBox.removeAllItems();
        List<DtPrestamo> prestamos = icon.listarPrestamos();
        for (DtPrestamo dt : prestamos) {
            prestamosComboBox.addItem(dt);
        }
        if (prestamosComboBox.getItemCount() > 0) {
            DtPrestamo primerPrestamo = (DtPrestamo) prestamosComboBox.getItemAt(0);
            if (primerPrestamo != null) {
                estadosComboBox.setSelectedItem(primerPrestamo.getEstado());
            }
        }
    }
}
