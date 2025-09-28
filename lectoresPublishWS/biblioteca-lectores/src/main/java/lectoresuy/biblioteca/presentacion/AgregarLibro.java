package lectoresuy.biblioteca.presentacion;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.event.ActionEvent;
import lectoresuy.biblioteca.service.ManejadorMaterial;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;

public class AgregarLibro extends JInternalFrame {
	
	private static IControladorMaterial icon;

	private static final long serialVersionUID = 1L;
	private JTextField textFieldTitulo;
	private JTextField textFieldCPaginas;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public AgregarLibro(IControladorMaterial icon) {
		setTitle("Registrar Libro");
		setBounds(100, 100, 360, 324);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Título");
		lblNewLabel.setBounds(53, 48, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Cantidad de Páginas");
		lblNewLabel_1.setBounds(53, 90, 98, 14);
		getContentPane().add(lblNewLabel_1);
		
		textFieldTitulo = new JTextField();
		textFieldTitulo.setBounds(168, 45, 86, 20);
		getContentPane().add(textFieldTitulo);
		textFieldTitulo.setColumns(10);
		
		textFieldCPaginas = new JTextField();
		textFieldCPaginas.setBounds(168, 87, 86, 20);
		getContentPane().add(textFieldCPaginas);
		textFieldCPaginas.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String titulo = textFieldTitulo.getText().trim();
					String cPag = textFieldCPaginas.getText().trim();
				
					// Validar campos vacíos
					if (titulo.isEmpty() || cPag.isEmpty()) {
						JOptionPane.showMessageDialog(AgregarLibro.this, 
							"Todos los campos son obligatorios.", 
							"Validación", 
							JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					// Validar que cantidad de páginas sea un número válido
					try {
						int cantidadPaginas = Integer.parseInt(cPag);
						if (cantidadPaginas <= 0) {
							JOptionPane.showMessageDialog(AgregarLibro.this, 
								"La cantidad de páginas debe ser un número mayor a 0.", 
								"Validación", 
								JOptionPane.WARNING_MESSAGE);
							textFieldCPaginas.requestFocus();
							return;
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(AgregarLibro.this, 
							"La cantidad de páginas debe ser un número válido.", 
							"Validación", 
							JOptionPane.WARNING_MESSAGE);
						textFieldCPaginas.requestFocus();
						return;
					}
					
					Date fechaRegistro = new Date();
					icon.agregarLibro(titulo, cPag, fechaRegistro);
					
					// Mensaje de éxito
					JOptionPane.showMessageDialog(AgregarLibro.this, 
						"Libro registrado exitosamente.", 
						"Éxito", 
						JOptionPane.INFORMATION_MESSAGE);
					
					textFieldTitulo.setText("");
					textFieldCPaginas.setText("");
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(AgregarLibro.this, 
						"Error al registrar el libro: " + ex.getMessage(), 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(53, 152, 89, 23);
		getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textFieldTitulo.setText("");
				textFieldCPaginas.setText("");
				dispose(); // Cerrar la ventana
			}
		});
		btnCancelar.setBounds(165, 152, 89, 23);
		getContentPane().add(btnCancelar);

	}
}
