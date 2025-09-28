package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Lector.EstadoLector;
import lectoresuy.biblioteca.service.ManejadorLector;
import java.util.Date;
import java.util.List;

import lectoresuy.biblioteca.datatypes.DtLector;

import lectoresuy.biblioteca.interfaces.IControladorLector;

public class SuspenderLector extends JInternalFrame {
	
	private static IControladorLector icon;

    // private ManejadorLector manejadorLector;
    private JComboBox<DtLector> lectoresComboBox;
    private JComboBox<EstadoLector> estadosComboBox;
	
	public SuspenderLector(IControladorLector icon) {
	
		super("Suspender Lector", false, false, false, false);
        setSize(400, 400);
        
        this.icon = icon;

        //manejadorLector = new ManejadorLector();

        // Diseño del formulario con Swing
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
                estadosComboBox.setSelectedItem(lectorSeleccionado.getEstado());
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(lectoresComboBox, gbc);

        // ComboBox para Estados
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Estado:"), gbc);
        
        estadosComboBox = new JComboBox<>(EstadoLector.values());
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

        // Action Listener para el botón "Aceptar"
        aceptarButton.addActionListener(e -> {
            try {
                DtLector lectorSeleccionado = (DtLector) lectoresComboBox.getSelectedItem();
                if (lectorSeleccionado == null) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar un lector.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                EstadoLector estadoSeleccionado = (EstadoLector) estadosComboBox.getSelectedItem();

                // Llama al controlador para cambiar el estado del lector
                icon.suspenderLector(lectorSeleccionado.getEmail(), estadoSeleccionado);

                JOptionPane.showMessageDialog(this, "Estado del lector actualizado con éxito.");
                recargarLectores();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar el estado del lector: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
                estadosComboBox.setSelectedItem(primerLector.getEstado());
            }
        }
    }

}
