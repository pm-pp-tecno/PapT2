package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtBibliotecario;
import lectoresuy.biblioteca.datatypes.DtLector;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.datatypes.DtPrestamo;
import lectoresuy.biblioteca.interfaces.IControladorBibliotecario;
import lectoresuy.biblioteca.interfaces.IControladorLector;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;

public class ActualizarInformacionPrestamo extends JInternalFrame {

    private IControladorPrestamo iconP;
    private IControladorLector iconL;
    private IControladorBibliotecario iconB;
    private IControladorMaterial iconM;

    private JComboBox<DtPrestamo> prestamosComboBox;
    private JComboBox<DtLector> lectoresComboBox;
    private JComboBox<DtBibliotecario> bibliotecariosComboBox;
    private JComboBox<DtMaterial> materialesComboBox;
    private JTextField fechaSolicitudField;
    private JTextField fechaDevolucionEstimadaField;

    public ActualizarInformacionPrestamo(IControladorPrestamo iconP, IControladorLector iconL, IControladorBibliotecario iconB, IControladorMaterial iconM) {
        super("Actualizar Información del Préstamo", false, false, false, false);
        setSize(500, 400);
        this.iconP = iconP;
        this.iconL = iconL;
        this.iconB = iconB;
        this.iconM = iconM;

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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                fechaSolicitudField.setText(sdf.format(prestamoSeleccionado.getFechaSolicitud()));
                fechaDevolucionEstimadaField.setText(sdf.format(prestamoSeleccionado.getFechaDevolucionEstimada()));
                lectoresComboBox.setSelectedItem(prestamoSeleccionado.getLector());
                bibliotecariosComboBox.setSelectedItem(prestamoSeleccionado.getBibliotecario());
                materialesComboBox.setSelectedItem(prestamoSeleccionado.getMaterial());
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(prestamosComboBox, gbc);

        // Campos de texto para fechas
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Fecha Solicitud:"), gbc);
        fechaSolicitudField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(fechaSolicitudField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Fecha Devolución Estimada:"), gbc);
        fechaDevolucionEstimadaField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(fechaDevolucionEstimadaField, gbc);

        // ComboBox para Lectores
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Lector:"), gbc);
        lectoresComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(lectoresComboBox, gbc);

        // ComboBox para Bibliotecarios
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Bibliotecario:"), gbc);
        bibliotecariosComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(bibliotecariosComboBox, gbc);

        // ComboBox para Materiales
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Material:"), gbc);
        materialesComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(materialesComboBox, gbc);

        // Botones
        JButton aceptarButton = new JButton("Aceptar");
        JButton cancelarButton = new JButton("Cancelar");

        gbc.gridx = 0;
        gbc.gridy = 6;
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
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date fechaSolicitud = sdf.parse(fechaSolicitudField.getText());
                Date fechaDevolucionEstimada = sdf.parse(fechaDevolucionEstimadaField.getText());

                prestamoSeleccionado.setFechaSolicitud(fechaSolicitud);
                prestamoSeleccionado.setFechaDevolucionEstimada(fechaDevolucionEstimada);
                prestamoSeleccionado.setLector((DtLector) lectoresComboBox.getSelectedItem());
                prestamoSeleccionado.setBibliotecario((DtBibliotecario) bibliotecariosComboBox.getSelectedItem());
                prestamoSeleccionado.setMaterial((DtMaterial) materialesComboBox.getSelectedItem());

                iconP.actualizarInformacionPrestamo(prestamoSeleccionado);
                JOptionPane.showMessageDialog(this, "Información del préstamo actualizada con éxito.");
                recargarPrestamos();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Use yyyy-MM-dd.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar la información del préstamo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarButton.addActionListener(e -> this.dispose());

	    add(panel);

	    // Poblar los combobox al abrir la ventana
	    recargarPrestamos();
    }

    public void recargarPrestamos() {
        prestamosComboBox.removeAllItems();
        List<DtPrestamo> prestamos = iconP.listarPrestamos();
        for (DtPrestamo dt : prestamos) {
            prestamosComboBox.addItem(dt);
        }

        lectoresComboBox.removeAllItems();
        List<DtLector> lectores = iconL.listarLectores();
        for (DtLector dt : lectores) {
            lectoresComboBox.addItem(dt);
        }

        bibliotecariosComboBox.removeAllItems();
        List<DtBibliotecario> bibliotecarios = iconB.listarBibliotecarios();
        for (DtBibliotecario dt : bibliotecarios) {
            bibliotecariosComboBox.addItem(dt);
        }

        materialesComboBox.removeAllItems();
        List<DtMaterial> materiales = iconM.listarMateriales();
        for (DtMaterial dt : materiales) {
            materialesComboBox.addItem(dt);
        }

        if (prestamosComboBox.getItemCount() > 0) {
            prestamosComboBox.setSelectedIndex(0);
        }
    }
}
