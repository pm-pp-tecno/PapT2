package lectoresuy.biblioteca.presentacion;

import java.awt.EventQueue;

import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SpinnerNumberModel;

import lectoresuy.biblioteca.entidades.Material;
import lectoresuy.biblioteca.excepciones.BibliotecarioNoEncontradoExcepcion;
import lectoresuy.biblioteca.excepciones.LectorNoEncontradoExcepcion;
import lectoresuy.biblioteca.excepciones.MaterialNoDisponibleExcepcion;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;
import lectoresuy.biblioteca.entidades.Libro;
import lectoresuy.biblioteca.entidades.Articulo;
import lectoresuy.biblioteca.service.ManejadorMaterial;

public class AgregarPrestamo extends JInternalFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtMail;
	private JTextField txtBibNum;
	private static IControladorPrestamo icon;
	private JComboBox<Material> comboBoxMat;
	
	/**
	 * Launch the application.
	 */

	/*public AgregarPrestamo() {
	}*/
	/**
	 * Create the frame.
	 */
	public AgregarPrestamo(IControladorPrestamo icon) {
		setTitle("Registrar Préstamo");
		setBounds(100, 100, 368, 411);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Seleccione el material");
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(114, 11, 126, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblAPedirPrestado = new JLabel("A pedir prestado");
		lblAPedirPrestado.setVerticalAlignment(SwingConstants.TOP);
		lblAPedirPrestado.setHorizontalAlignment(SwingConstants.LEFT);
		lblAPedirPrestado.setBounds(114, 25, 126, 14);
		getContentPane().add(lblAPedirPrestado);
		
		comboBoxMat = new JComboBox<>();
		comboBoxMat.setBounds(72, 50, 196, 22);
		getContentPane().add(comboBoxMat);
		
		cargarMaterialesDisponibles();
		
		JLabel lblNewLabel_1 = new JLabel("Inserte direccion de email");
		lblNewLabel_1.setBounds(32, 102, 152, 14);
		getContentPane().add(lblNewLabel_1);
		
		txtMail = new JTextField();
		txtMail.setBounds(194, 99, 135, 20);
		getContentPane().add(txtMail);
		txtMail.setColumns(10);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 15);
		
		JSpinner spDia = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1));
		spDia.setBounds(162, 164, 45, 20);
		getContentPane().add(spDia);
		
		JSpinner spMes = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.MONTH) + 1, 1, 12, 1));
		spMes.setBounds(217, 164, 39, 20);
		getContentPane().add(spMes);
		
		JSpinner spAnio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.YEAR), 2025, 2030, 1));
		spAnio.setBounds(266, 164, 55, 20);
		getContentPane().add(spAnio);
		
		JLabel lblNewLabel_2 = new JLabel("/");
		lblNewLabel_2.setBounds(257, 167, 11, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("/");
		lblNewLabel_3.setBounds(209, 167, 11, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Fecha de Devolución");
		lblNewLabel_4.setBounds(36, 167, 116, 14);
		getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Número del Bibliotecario");
		lblNewLabel_5.setBounds(36, 215, 121, 14);
		getContentPane().add(lblNewLabel_5);
		
		txtBibNum = new JTextField();
		txtBibNum.setBounds(177, 212, 86, 20);
		getContentPane().add(txtBibNum);
		txtBibNum.setColumns(10);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            Material materialSeleccionado = (Material) comboBoxMat.getSelectedItem();
		            
		            // Validar material seleccionado
		            if (materialSeleccionado == null || materialSeleccionado.getId() == null) {
		            	mostrarError("Error", "Debe seleccionar un material para el préstamo.");
		                return;
		            }
		            
		            String emailLector = txtMail.getText().trim();
		            String numeroBibliotecario = txtBibNum.getText().trim();
		            
		            // Validar campos vacíos
		            if (emailLector.isEmpty()) {
		                mostrarError("Error", "El email del lector es obligatorio.");
		                txtMail.requestFocus();
		                return;
		            }
		            
		            if (numeroBibliotecario.isEmpty()) {
		                mostrarError("Error", "El número de bibliotecario es obligatorio.");
		                txtBibNum.requestFocus();
		                return;
		            }
		            
		            // Validar fecha de devolución
		            try {
		                spDia.commitEdit();
		            } catch ( java.text.ParseException e12 ) {
		            	mostrarError("Error", e12.getMessage());
		                return;
		            }
		            int dia = (Integer) spDia.getValue();
		            
		            try {
		                spMes.commitEdit();
		            } catch ( java.text.ParseException e13 ) {
		            	mostrarError("Error", e13.getMessage());
		                return;
		            }
		            int mes = (Integer) spMes.getValue();
		            
		            try {
		                spAnio.commitEdit();
		            } catch ( java.text.ParseException e14 ) {
		            	mostrarError("Error", e14.getMessage());
		                return;
		            }
		            int anio = (Integer) spAnio.getValue();
		            
		            // Validar que la fecha sea válida
		            Calendar calendar = Calendar.getInstance();
		            calendar.set(Calendar.YEAR, anio);
		            calendar.set(Calendar.MONTH, mes-1);
		            calendar.set(Calendar.DATE, dia);
		            Date fechaDevolucion = calendar.getTime();
		            
		            // Validar que la fecha de devolución sea posterior a la fecha actual
		            Date fechaActual = new Date();
		            if (fechaDevolucion.before(fechaActual)) {
		                mostrarError("Error", "La fecha de devolución debe ser posterior a la fecha actual.");
		                return;
		            }
		            
		            icon.agregarPrestamo(materialSeleccionado.getId(), emailLector, numeroBibliotecario, fechaDevolucion);
		            
		            // Mensaje de éxito
		            JOptionPane.showMessageDialog(AgregarPrestamo.this, 
		                "Préstamo registrado exitosamente.", 
		                "Éxito", 
		                JOptionPane.INFORMATION_MESSAGE);
		            
		            txtMail.setText("");
		            txtBibNum.setText("");
		            cargarMaterialesDisponibles();
		            
		        }  catch (LectorNoEncontradoExcepcion e2) {
		            mostrarError("Error", e2.getMessage());
		            txtMail.requestFocus();
		        } catch (BibliotecarioNoEncontradoExcepcion e3) {
		            mostrarError("Error", e3.getMessage());
		            txtBibNum.requestFocus();
		        } catch (MaterialNoDisponibleExcepcion e4) {
		            mostrarError("Error", e4.getMessage());
		            comboBoxMat.requestFocus();
		        } catch (Exception e5) {
		            mostrarError("Error", "Ocurrió un error inesperado: " + e5.getMessage());
		            e5.printStackTrace();
		        }
			}
		});
		btnAceptar.setBounds(50, 269, 89, 23);
		getContentPane().add(btnAceptar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            txtMail.setText("");
                            txtBibNum.setText("");
                            comboBoxMat.setSelectedIndex(0);
                            dispose(); // Cerrar la ventana
			}
		});
		btnCancelar.setBounds(177, 269, 89, 23);
		getContentPane().add(btnCancelar);
	}
	
	public void cargarMaterialesDisponibles() {
        try {
            ManejadorMaterial manejadorMaterial = ManejadorMaterial.getInstancia();
            List<Material> materialesDisponibles = manejadorMaterial.obtenerMaterialesDisponibles();
            
            // Limpiar combobox
            comboBoxMat.removeAllItems();
            
            // Agregar materiales al combobox
            for (Material material : materialesDisponibles) {
                comboBoxMat.addItem(material);
            }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void mostrarError(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }
}
