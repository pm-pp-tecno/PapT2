package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Lector.EstadoLector;
import lectoresuy.biblioteca.service.ManejadorLector;
import java.util.Date;

//import lectoresuy.biblioteca.excepciones.SocioRepetidoExcepcion;
import lectoresuy.biblioteca.interfaces.IControladorLector;

public class AgregarLector extends JInternalFrame {

	private IControladorLector icon;

    // private ManejadorLector manejadorLector;
    private JTextField nombreField;
    private JTextField emailField;
    private JTextField direccionField;
    private JComboBox<String> zonaComboBox;
    
    public AgregarLector(IControladorLector icon) {
        super("Registrar Lector", false, false, false, false);
        setSize(400, 400);
        
        this.icon = icon;

        //manejadorLector = new ManejadorLector();

        // Diseño del formulario con Swing
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        nombreField = new JTextField(20);
        panel.add(nombreField, gbc.clone());

        // Email
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Correo electrónico:"), gbc.clone());
        gbc.gridx = 1;
        gbc.gridy = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc.clone());
        
        // Direccion
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Dirección:"), gbc.clone());
        gbc.gridx = 1;
        gbc.gridy = 2;
        direccionField = new JTextField(20);
        panel.add(direccionField, gbc.clone());

        // Zona (usando ComboBox)
        String[] zonas = {"BIBLIOTECA CENTRAL", "SUCURSAL ESTE", "SUCURSAL OESTE", "BIBLIOTECA INFANTIL", "ARCHIVO GENERAL"};
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Zona:"), gbc.clone());
        gbc.gridx = 1;
        gbc.gridy = 3;
        zonaComboBox = new JComboBox<>(zonas);
        panel.add(zonaComboBox, gbc.clone());

        // Botones
        JButton aceptarButton = new JButton("Aceptar");
        JButton cancelarButton = new JButton("Cancelar");
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(aceptarButton);
        buttonPanel.add(cancelarButton);
        panel.add(buttonPanel, gbc.clone());

        // Action Listener para el botón "Aceptar"
        aceptarButton.addActionListener(e -> {
            try {
                String nombre = nombreField.getText().trim();
                String email = emailField.getText().trim();
                String direccion = direccionField.getText().trim();
                String zona = (String) zonaComboBox.getSelectedItem();

                if (nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || zona == null || zona.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Date fechaRegistro = new Date();
                EstadoLector estado = EstadoLector.ACTIVO;

                // Llama al controlador para guardar el lector (delega la persistencia a Hibernate)
                icon.agregarLector(nombre, email, direccion, fechaRegistro, estado, zona);

                JOptionPane.showMessageDialog(this, "Lector registrado con éxito.");
                nombreField.setText("");
                emailField.setText("");
                direccionField.setText("");
                zonaComboBox.setSelectedIndex(0);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar el lector: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelarButton.addActionListener(e -> this.dispose());

        add(panel);
    }
}
