package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import lectoresuy.biblioteca.entidades.Lector;
import lectoresuy.biblioteca.entidades.Lector.EstadoLector;
import lectoresuy.biblioteca.service.ManejadorBibliotecario;
import java.util.Date;

//import lectoresuy.biblioteca.excepciones.SocioRepetidoExcepcion;
import lectoresuy.biblioteca.interfaces.IControladorBibliotecario;

public class AgregarBibliotecario extends JInternalFrame {

	private IControladorBibliotecario icon;

    // private ManejadorLector manejadorLector;
    private JTextField nombreField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField numeroEmpleadoField;
    
    public AgregarBibliotecario(IControladorBibliotecario icon) {
        super("Registrar Bibliotecario", false, false, false, false);
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
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Contraseña:"), gbc.clone());
        gbc.gridx = 1;
        gbc.gridy = 2;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc.clone());
        
        // Número empleado
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Número empleado:"), gbc.clone());
        gbc.gridx = 1;
        gbc.gridy = 3;
        numeroEmpleadoField = new JTextField(20);
        panel.add(numeroEmpleadoField, gbc.clone());

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
                String password = new String(passwordField.getPassword()).trim();
                String numeroEmpleado = numeroEmpleadoField.getText().trim();

                if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || numeroEmpleado.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Llama al controlador para guardar el bibliotecario (delega la persistencia a Hibernate)
                icon.agregarBibliotecario(nombre, email, password, numeroEmpleado);

                JOptionPane.showMessageDialog(this, "Bibliotecario registrado con éxito.");
                nombreField.setText("");
                emailField.setText("");
                passwordField.setText("");
                numeroEmpleadoField.setText("");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar el bibliotecario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelarButton.addActionListener(e -> this.dispose());

        add(panel);
    }
}
