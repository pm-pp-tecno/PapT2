package lectoresuy.biblioteca.presentacion;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Date;
import lectoresuy.biblioteca.service.ManejadorMaterial;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;
import javax.swing.JOptionPane;

public class AgregarArticulo extends JInternalFrame {
	
	static IControladorMaterial icon;
	
	private static final long serialVersionUID = 1L;
	private JTextField txtfDescripcion;
	private JTextField txtfPeso;
	private JTextField txtfDimensiones;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public AgregarArticulo(IControladorMaterial icon) {
		setTitle("Registrar Articulo");
		setBounds(100, 100, 421, 430);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Descripción");
		lblNewLabel.setBounds(64, 48, 86, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Peso (en kg)");
		lblNewLabel_1.setBounds(64, 111, 86, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Dimensiones");
		lblNewLabel_2.setBounds(64, 149, 86, 14);
		getContentPane().add(lblNewLabel_2);
		
		txtfDescripcion = new JTextField();
		txtfDescripcion.setBounds(190, 45, 175, 51);
		getContentPane().add(txtfDescripcion);
		txtfDescripcion.setColumns(10);
		
		txtfPeso = new JTextField();
		txtfPeso.setBounds(190, 108, 86, 20);
		getContentPane().add(txtfPeso);
		txtfPeso.setColumns(10);
		
		txtfDimensiones = new JTextField();
		txtfDimensiones.setBounds(190, 146, 136, 20);
		getContentPane().add(txtfDimensiones);
		txtfDimensiones.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String descripcion = txtfDescripcion.getText().trim();
					String dimensiones = txtfDimensiones.getText().trim();
					String pesoTexto = txtfPeso.getText().trim();
					
					// Validar campos vacíos
					if (descripcion.isEmpty() || dimensiones.isEmpty() || pesoTexto.isEmpty()) {
						JOptionPane.showMessageDialog(AgregarArticulo.this, 
							"Todos los campos son obligatorios.", 
							"Validación", 
							JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					// Validar que peso sea un número válido
					float peso;
					try {
						peso = Float.parseFloat(pesoTexto);
						if (peso <= 0) {
							JOptionPane.showMessageDialog(AgregarArticulo.this, 
								"El peso debe ser un número mayor a 0.", 
								"Validación", 
								JOptionPane.WARNING_MESSAGE);
							txtfPeso.requestFocus();
							return;
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(AgregarArticulo.this, 
							"El peso debe ser un número válido.", 
							"Validación", 
							JOptionPane.WARNING_MESSAGE);
						txtfPeso.requestFocus();
						return;
					}
					
					Date fechaRegistro = new Date();
					icon.agregarArticulo(descripcion, peso, dimensiones, fechaRegistro);
					
					// Mensaje de éxito
					JOptionPane.showMessageDialog(AgregarArticulo.this, 
						"Artículo registrado exitosamente.", 
						"Éxito", 
						JOptionPane.INFORMATION_MESSAGE);
					
					txtfDescripcion.setText("");
					txtfPeso.setText("");
					txtfDimensiones.setText("");
					
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(AgregarArticulo.this, 
						"Error al registrar el artículo: " + ex.getMessage(), 
						"Error", 
						JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnAceptar.setBounds(61, 222, 89, 23);
		getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtfDescripcion.setText("");
				txtfPeso.setText("");
				txtfDimensiones.setText("");
				dispose(); // Cerrar la ventana
			}
		});
		btnCancelar.setBounds(190, 222, 89, 23);
		getContentPane().add(btnCancelar);

	}

}
