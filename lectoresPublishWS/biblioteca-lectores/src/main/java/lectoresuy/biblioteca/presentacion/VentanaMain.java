package lectoresuy.biblioteca.presentacion;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import java.awt.Rectangle;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JDesktopPane;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import lectoresuy.biblioteca.interfaces.Fabrica;
import lectoresuy.biblioteca.interfaces.IControladorLector;
import lectoresuy.biblioteca.interfaces.IControladorBibliotecario;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;
import lectoresuy.biblioteca.interfaces.IControladorPrestamo;

public class VentanaMain {

	private JFrame frmLectoresuy;
	
	/** REGISTROS **/
	private AgregarLector agregarLectorInternalFrame;
	private AgregarBibliotecario agregarBibliotecarioInternalFrame;
	private AgregarLibro agregarLibroInternalFrame;
	private AgregarArticulo agregarArticuloInternalFrame;
	private AgregarPrestamo agregarPrestamoInternalFrame;
	
	
	/** CONSULTAS **/
	private ListarDonaciones listarDonacionesInternalFrame;
	private ListarDonacionesFechas listarDonacionesFechasInternalFrame;
	private PrestamosLector prestamosLectorInternalFrame;
	
	
	/** ACTUALIZACIONES **/
	private SuspenderLector suspenderLectorInternalFrame;
	private CambiarZonaLector cambiarZonaLectorInternalFrame;
	private CambiarEstadoPrestamo cambiarEstadoPrestamoInternalFrame;
	private ActualizarInformacionPrestamo actualizarInformacionPrestamoInternalFrame;
		
	
	/** CONTROL **/
	private PrestamosBibliotecario prestamosBibliotecarioInternalFrame;
	private PrestamosZona prestamosZonaInternalFrame;
	private MaterialesPrestamosPendientes prestamosPendietesInternalFrame;
	
	
	
	// Método centralizado para mostrar solo el pane seleccionado
	private void mostrarSoloEstePane(JInternalFrame paneAMostrar) {
		for (JInternalFrame frame : desktopPane.getAllFrames()) {
			if (frame == paneAMostrar) {
				frame.setVisible(true);
				frame.toFront();
			} else {
				frame.setVisible(false);
			}
		}
	}
	
	private JDesktopPane desktopPane;
	private JInternalFrame internalFrameInicio;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMain window = new VentanaMain();
					window.frmLectoresuy.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creo the application.
	 */
	public VentanaMain() {
		initialize();
		
		Fabrica fabrica = Fabrica.getInstancia();
                IControladorLector iconL = fabrica.getIControladorLector();
                IControladorMaterial iconM = fabrica.getIControladorMaterial();
                IControladorBibliotecario iconB = fabrica.getIControladorBibliotecario();
                IControladorPrestamo iconP = fabrica.getIControladorPrestamo();
		
		Dimension desktopSize = frmLectoresuy.getSize();
		Dimension jInternalFrameSize;
		
		
		/** REGISTROS **/
		//Agrego el frame de AgregarLector
		agregarLectorInternalFrame = new AgregarLector(iconL);
		jInternalFrameSize = agregarLectorInternalFrame.getSize();
		agregarLectorInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		agregarLectorInternalFrame.setVisible(false);
		desktopPane.add(agregarLectorInternalFrame);
		
		//Agrego el frame de AgregarBibliotecario
		agregarBibliotecarioInternalFrame = new AgregarBibliotecario(iconB);
		jInternalFrameSize = agregarBibliotecarioInternalFrame.getSize();
		agregarBibliotecarioInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		agregarBibliotecarioInternalFrame.setVisible(false);
		desktopPane.add(agregarBibliotecarioInternalFrame);
		
		//Agrego el frame de AgregarLibro
		agregarLibroInternalFrame = new AgregarLibro(iconM);
		jInternalFrameSize = agregarLibroInternalFrame.getSize();
		agregarLibroInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		agregarLibroInternalFrame.setVisible(false);
		desktopPane.add(agregarLibroInternalFrame);
		
		//Agrego el frame de AgregarArticulo
		agregarArticuloInternalFrame = new AgregarArticulo(iconM);
		jInternalFrameSize = agregarArticuloInternalFrame.getSize();
		agregarArticuloInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		agregarArticuloInternalFrame.setVisible(false);
		desktopPane.add(agregarArticuloInternalFrame);
		
		//Agrego el frame de AgregarPrestamo
		agregarPrestamoInternalFrame = new AgregarPrestamo(iconP);
		jInternalFrameSize = agregarPrestamoInternalFrame.getSize();
		agregarPrestamoInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		agregarPrestamoInternalFrame.setVisible(false);
		desktopPane.add(agregarPrestamoInternalFrame);
		
		

		
		/** CONSULTAS **/
		//Agrego el frame de ListarDonaciones
		listarDonacionesInternalFrame = new ListarDonaciones(iconM);
		jInternalFrameSize = listarDonacionesInternalFrame.getSize();
		listarDonacionesInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		listarDonacionesInternalFrame.setVisible(false);
		desktopPane.add(listarDonacionesInternalFrame);

		//Agrego el frame de ListarDonacionesFecha
		listarDonacionesFechasInternalFrame = new ListarDonacionesFechas(iconM);
		jInternalFrameSize = listarDonacionesFechasInternalFrame.getSize();
		listarDonacionesFechasInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		listarDonacionesFechasInternalFrame.setVisible(false);
		desktopPane.add(listarDonacionesFechasInternalFrame);
		
		//Agrego el frame de PrestamosLector
		prestamosLectorInternalFrame = new PrestamosLector(iconL, iconP);
		jInternalFrameSize = prestamosLectorInternalFrame.getSize();
		prestamosLectorInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		prestamosLectorInternalFrame.setVisible(false);
		desktopPane.add(prestamosLectorInternalFrame);
		
		
		
		
		/** ACTUALIZACIONES **/
		//Agrego el frame de SuspenderLector
		suspenderLectorInternalFrame = new SuspenderLector(iconL);
		jInternalFrameSize = suspenderLectorInternalFrame.getSize();
		suspenderLectorInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		suspenderLectorInternalFrame.setVisible(false);
		desktopPane.add(suspenderLectorInternalFrame);
		
		//Agrego el frame de CambiarZonaLector
		cambiarZonaLectorInternalFrame = new CambiarZonaLector(iconL);
		jInternalFrameSize = cambiarZonaLectorInternalFrame.getSize();
		cambiarZonaLectorInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		cambiarZonaLectorInternalFrame.setVisible(false);
		desktopPane.add(cambiarZonaLectorInternalFrame);
		
		//Agrego el frame de CambiarEstadoPrestamo
		cambiarEstadoPrestamoInternalFrame = new CambiarEstadoPrestamo(iconP);
		jInternalFrameSize = cambiarEstadoPrestamoInternalFrame.getSize();
		cambiarEstadoPrestamoInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		cambiarEstadoPrestamoInternalFrame.setVisible(false);
		desktopPane.add(cambiarEstadoPrestamoInternalFrame);
		
		//Agrego el frame de ActualizarInformacionPrestamo
		actualizarInformacionPrestamoInternalFrame = new ActualizarInformacionPrestamo(iconP, iconL, iconB, iconM);
		jInternalFrameSize = actualizarInformacionPrestamoInternalFrame.getSize();
		actualizarInformacionPrestamoInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		actualizarInformacionPrestamoInternalFrame.setVisible(false);
		desktopPane.add(actualizarInformacionPrestamoInternalFrame);
		
		

		
		/** CONTROL **/
		//Agrego el frame de PrestamosBibliotecario
		prestamosBibliotecarioInternalFrame = new PrestamosBibliotecario(iconP, iconB);
		jInternalFrameSize = prestamosBibliotecarioInternalFrame.getSize();
		prestamosBibliotecarioInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		prestamosBibliotecarioInternalFrame.setVisible(false);
		desktopPane.add(prestamosBibliotecarioInternalFrame);

		//Agrego el frame de PrestamosZona
		prestamosZonaInternalFrame = new PrestamosZona(iconP, iconL);
		jInternalFrameSize = prestamosZonaInternalFrame.getSize();
		prestamosZonaInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		prestamosZonaInternalFrame.setVisible(false);
		desktopPane.add(prestamosZonaInternalFrame);
		
		//Agrego el frame de PrestamosPendientes
		prestamosPendietesInternalFrame = new MaterialesPrestamosPendientes(iconP, iconM);
		jInternalFrameSize = prestamosPendietesInternalFrame.getSize();
		prestamosPendietesInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			(desktopSize.height- jInternalFrameSize.height)/2);
		prestamosPendietesInternalFrame.setVisible(false);
		desktopPane.add(prestamosPendietesInternalFrame);
		
		
		
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLectoresuy = new JFrame();
		frmLectoresuy.setTitle("lectores.uy");
		frmLectoresuy.setBounds(100, 100, 900, 600);
		frmLectoresuy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLectoresuy.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JMenuBar menuBar = new JMenuBar();
		frmLectoresuy.setJMenuBar(menuBar);
		
		JMenu MenuInicioBase = new JMenu("Inicio");
		menuBar.add(MenuInicioBase);
		
		JMenuItem botonMenuInicio = new JMenuItem("Inicio");
		MenuInicioBase.add(botonMenuInicio);
		// Listener para mostrar solo el pane de inicio
		botonMenuInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarSoloEstePane(internalFrameInicio);
			}
		});
		
		
		JMenu mnNewMenu = new JMenu("Registros");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmRegistroLectorMenu = new JMenuItem("Registrar Lector");
		mntmRegistroLectorMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (agregarLectorInternalFrame == null || agregarLectorInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorLector iconL = fabrica.getIControladorLector();
					agregarLectorInternalFrame = new AgregarLector(iconL);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = agregarLectorInternalFrame.getSize();
					agregarLectorInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(agregarLectorInternalFrame);
				}
				mostrarSoloEstePane(agregarLectorInternalFrame);
			}
		});
		mnNewMenu.add(mntmRegistroLectorMenu);
		
		JMenuItem mntmRegistroBibliotecario = new JMenuItem("Registrar Bibliotecario");
		mntmRegistroBibliotecario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (agregarBibliotecarioInternalFrame == null || agregarBibliotecarioInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorBibliotecario iconB = fabrica.getIControladorBibliotecario();
					agregarBibliotecarioInternalFrame = new AgregarBibliotecario(iconB);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = agregarBibliotecarioInternalFrame.getSize();
					agregarBibliotecarioInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(agregarBibliotecarioInternalFrame);
				}
				mostrarSoloEstePane(agregarBibliotecarioInternalFrame);
			}
		});
		mnNewMenu.add(mntmRegistroBibliotecario);
		
		JMenuItem mntmRegistrarLibro = new JMenuItem("Registrar Libro");
		mntmRegistrarLibro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (agregarLibroInternalFrame == null || agregarLibroInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorMaterial iconM = fabrica.getIControladorMaterial();
					agregarLibroInternalFrame = new AgregarLibro(iconM);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = agregarLibroInternalFrame.getSize();
					agregarLibroInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(agregarLibroInternalFrame);
				}
				mostrarSoloEstePane(agregarLibroInternalFrame);
			}
		});
		mnNewMenu.add(mntmRegistrarLibro);
		
		JMenuItem mntmRegistrarArticulo = new JMenuItem("Registrar Artículo");
		mntmRegistrarArticulo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (agregarArticuloInternalFrame == null || agregarArticuloInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorMaterial iconM = fabrica.getIControladorMaterial();
					agregarArticuloInternalFrame = new AgregarArticulo(iconM);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = agregarArticuloInternalFrame.getSize();
					agregarArticuloInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(agregarArticuloInternalFrame);
				}
				mostrarSoloEstePane(agregarArticuloInternalFrame);
			}
		});
		mnNewMenu.add(mntmRegistrarArticulo);
		
		JMenuItem mntmRegistrarPrestamo = new JMenuItem("Registrar Prestamo");
		mntmRegistrarPrestamo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (agregarPrestamoInternalFrame == null || agregarPrestamoInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorPrestamo iconP = fabrica.getIControladorPrestamo();
					agregarPrestamoInternalFrame = new AgregarPrestamo(iconP);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = agregarPrestamoInternalFrame.getSize();
					agregarPrestamoInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(agregarPrestamoInternalFrame);
				}
				agregarPrestamoInternalFrame.cargarMaterialesDisponibles();
				mostrarSoloEstePane(agregarPrestamoInternalFrame);
			}
		});
		mnNewMenu.add(mntmRegistrarPrestamo);
		
		JMenu mnNewMenu_1 = new JMenu("Consultas");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmConsultaDonaciones = new JMenuItem("Consultar Donaciones");
		mntmConsultaDonaciones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listarDonacionesInternalFrame == null || listarDonacionesInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorMaterial iconM = fabrica.getIControladorMaterial();
					listarDonacionesInternalFrame = new ListarDonaciones(iconM);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = listarDonacionesInternalFrame.getSize();
					listarDonacionesInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(listarDonacionesInternalFrame);
				}
				listarDonacionesInternalFrame.recargarDonaciones();
				mostrarSoloEstePane(listarDonacionesInternalFrame);
			}
		});
		mnNewMenu_1.add(mntmConsultaDonaciones);
		
		
		JMenuItem mntmConsultaDonacionesFechas = new JMenuItem("Consultar Donaciones en un rango de fechas");
		mntmConsultaDonacionesFechas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (listarDonacionesFechasInternalFrame == null || listarDonacionesFechasInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorMaterial iconM = fabrica.getIControladorMaterial();
					listarDonacionesFechasInternalFrame = new ListarDonacionesFechas(iconM);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = listarDonacionesFechasInternalFrame.getSize();
					listarDonacionesFechasInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(listarDonacionesFechasInternalFrame);
				}
				listarDonacionesFechasInternalFrame.recargarDonaciones();
				mostrarSoloEstePane(listarDonacionesFechasInternalFrame);
			}
		});
		mnNewMenu_1.add(mntmConsultaDonacionesFechas);
		
		JMenuItem mntmConsultaPrestamosLector = new JMenuItem("Prestamos activos de un lector");
		mntmConsultaPrestamosLector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (prestamosLectorInternalFrame == null || prestamosLectorInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorPrestamo iconP = fabrica.getIControladorPrestamo();
					IControladorLector iconL = fabrica.getIControladorLector();
					prestamosLectorInternalFrame = new PrestamosLector(iconL, iconP);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = prestamosLectorInternalFrame.getSize();
					prestamosLectorInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(prestamosLectorInternalFrame);
				}
				prestamosLectorInternalFrame.recargarLectores();
				mostrarSoloEstePane(prestamosLectorInternalFrame);
			}
		});
		mnNewMenu_1.add(mntmConsultaPrestamosLector);

		
		JMenu mnActualizacion = new JMenu("Actualizaciones");
		menuBar.add(mnActualizacion);
		
		JMenuItem mntmSuspenderLector = new JMenuItem("Suspender Lector");
		mntmSuspenderLector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (suspenderLectorInternalFrame == null || suspenderLectorInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorLector iconL = fabrica.getIControladorLector();
					suspenderLectorInternalFrame = new SuspenderLector(iconL);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = suspenderLectorInternalFrame.getSize();
					suspenderLectorInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(suspenderLectorInternalFrame);
				}
				suspenderLectorInternalFrame.recargarLectores();
				mostrarSoloEstePane(suspenderLectorInternalFrame);
			}
		});
		mnActualizacion.add(mntmSuspenderLector);
		
		JMenuItem mntmCambiarZonaLector = new JMenuItem("Cambiar zona del Lector");
		mntmCambiarZonaLector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cambiarZonaLectorInternalFrame == null || cambiarZonaLectorInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorLector iconL = fabrica.getIControladorLector();
					cambiarZonaLectorInternalFrame = new CambiarZonaLector(iconL);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = cambiarZonaLectorInternalFrame.getSize();
					cambiarZonaLectorInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(cambiarZonaLectorInternalFrame);
				}
				cambiarZonaLectorInternalFrame.recargarLectores();
				mostrarSoloEstePane(cambiarZonaLectorInternalFrame);
			}
		});
		mnActualizacion.add(mntmCambiarZonaLector);
		
		JMenuItem mntmActualizarPrestamo = new JMenuItem("Cambiar estado de un prestamo");
		mntmActualizarPrestamo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cambiarEstadoPrestamoInternalFrame == null || cambiarEstadoPrestamoInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorPrestamo iconP = fabrica.getIControladorPrestamo();
					cambiarEstadoPrestamoInternalFrame = new CambiarEstadoPrestamo(iconP);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = cambiarEstadoPrestamoInternalFrame.getSize();
					cambiarEstadoPrestamoInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(cambiarEstadoPrestamoInternalFrame);
				}
				cambiarEstadoPrestamoInternalFrame.recargarPrestamos();
				mostrarSoloEstePane(cambiarEstadoPrestamoInternalFrame);
			}
		});
		mnActualizacion.add(mntmActualizarPrestamo);
		
		JMenuItem mntmActualizarInformacionPrestamo = new JMenuItem("Actualizar informacion del Prestamo");
		mntmActualizarInformacionPrestamo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (actualizarInformacionPrestamoInternalFrame == null || actualizarInformacionPrestamoInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorPrestamo iconP = fabrica.getIControladorPrestamo();
					IControladorLector iconL = fabrica.getIControladorLector();
					IControladorBibliotecario iconB = fabrica.getIControladorBibliotecario();
					IControladorMaterial iconM = fabrica.getIControladorMaterial();
					actualizarInformacionPrestamoInternalFrame = new ActualizarInformacionPrestamo(iconP, iconL, iconB, iconM);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = actualizarInformacionPrestamoInternalFrame.getSize();
					actualizarInformacionPrestamoInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(actualizarInformacionPrestamoInternalFrame);
				}
				actualizarInformacionPrestamoInternalFrame.recargarPrestamos();
				mostrarSoloEstePane(actualizarInformacionPrestamoInternalFrame);
			}
		});
		mnActualizacion.add(mntmActualizarInformacionPrestamo);
		

		JMenu mnControl = new JMenu("Control");
		menuBar.add(mnControl);
		
		JMenuItem mntmPrestamosBibliotecario = new JMenuItem("Historial de prestamos de bibliotecario");
		mntmPrestamosBibliotecario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (prestamosBibliotecarioInternalFrame == null || prestamosBibliotecarioInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorPrestamo iconP = fabrica.getIControladorPrestamo();
					IControladorBibliotecario iconB = fabrica.getIControladorBibliotecario();
					prestamosBibliotecarioInternalFrame = new PrestamosBibliotecario(iconP, iconB);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = prestamosBibliotecarioInternalFrame.getSize();
					prestamosBibliotecarioInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(prestamosBibliotecarioInternalFrame);
				}
				prestamosBibliotecarioInternalFrame.recargarBibliotecariosPrestamos();
				mostrarSoloEstePane(prestamosBibliotecarioInternalFrame);
			}
		});
		mnControl.add(mntmPrestamosBibliotecario);
		
		JMenuItem mntmPrestamosZona = new JMenuItem("Prestamos por zona");
		mntmPrestamosZona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (prestamosZonaInternalFrame == null || prestamosZonaInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorPrestamo iconP = fabrica.getIControladorPrestamo();
					IControladorLector iconL = fabrica.getIControladorLector();
					prestamosZonaInternalFrame = new PrestamosZona(iconP, iconL);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = prestamosZonaInternalFrame.getSize();
					prestamosZonaInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(prestamosZonaInternalFrame);
				}
				prestamosZonaInternalFrame.recargarPrestamos();
				mostrarSoloEstePane(prestamosZonaInternalFrame);
			}
		});
		mnControl.add(mntmPrestamosZona);
		
		JMenuItem mntmMaterialesPrestamosPendientes = new JMenuItem("Materiales con prestamos pendientes");
		mntmMaterialesPrestamosPendientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (prestamosPendietesInternalFrame == null || prestamosPendietesInternalFrame.isClosed()) {
					Fabrica fabrica = Fabrica.getInstancia();
					IControladorPrestamo iconP = fabrica.getIControladorPrestamo();
					IControladorMaterial iconM = fabrica.getIControladorMaterial();
					prestamosPendietesInternalFrame = new MaterialesPrestamosPendientes(iconP, iconM);
					Dimension desktopSize = frmLectoresuy.getSize();
					Dimension jInternalFrameSize = prestamosPendietesInternalFrame.getSize();
					prestamosPendietesInternalFrame.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
						(desktopSize.height- jInternalFrameSize.height)/2);
					desktopPane.add(prestamosPendietesInternalFrame);
				}
				prestamosPendietesInternalFrame.recargarPrestamosMuchosPendientes();
				mostrarSoloEstePane(prestamosPendietesInternalFrame);
			}
		});
		mnControl.add(mntmMaterialesPrestamosPendientes);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// 2. En el método initialize(), quita la declaración local y usa el atributo:
		desktopPane = new JDesktopPane();
		frmLectoresuy.getContentPane().add(desktopPane, BorderLayout.CENTER);
		
		internalFrameInicio = new JInternalFrame("Inicio");
		internalFrameInicio.setBounds(226, 31, 450, 450);
		desktopPane.add(internalFrameInicio);
		
		JDesktopPane desktopPane_1 = new JDesktopPane();
		desktopPane_1.setBackground(new Color(200, 200, 200));
		internalFrameInicio.getContentPane().add(desktopPane_1, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("lectores.uy - Gestión de Bibliotecas");
		lblNewLabel.setForeground(new Color(0, 64, 128));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 24));
		lblNewLabel.setBounds(10, 11, 413, 52);
		desktopPane_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Bienvenido al gestor de bibliotecas de lectores.uy");
		lblNewLabel_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(38, 74, 370, 15);
		desktopPane_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Panel de administración");
		lblNewLabel_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(38, 101, 370, 15);
		desktopPane_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Registro - Permite ingresar nuevos datos");
		lblNewLabel_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1_1_1.setBounds(38, 127, 370, 18);
		desktopPane_1.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Consultas - Permite consultar y verificar datos");
		lblNewLabel_1_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1_1_1_1.setBounds(38, 156, 370, 18);
		desktopPane_1.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Actualización - Permite modificar datos ya existentes");
		lblNewLabel_1_1_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1_1_1_1_1.setBounds(38, 185, 370, 18);
		desktopPane_1.add(lblNewLabel_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1 = new JLabel("Creado por");
		lblNewLabel_1_1_1_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1_1_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1_1_1_1_1_1.setBounds(38, 283, 370, 18);
		desktopPane_1.add(lblNewLabel_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_1 = new JLabel("Pablo Morales");
		lblNewLabel_1_1_1_1_1_1_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1_1_1_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1_1_1_1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1_1_1_1_1_1_1.setBounds(38, 310, 370, 18);
		desktopPane_1.add(lblNewLabel_1_1_1_1_1_1_1);
		
		JLabel lblNewLabel_1_1_1_1_1_1_2 = new JLabel("Nelson Henríquez");
		lblNewLabel_1_1_1_1_1_1_2.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1_1_1_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1_1_1_2.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1_1_1_1_1_1_2.setBounds(38, 332, 370, 18);
		desktopPane_1.add(lblNewLabel_1_1_1_1_1_1_2);
		
		JLabel lblNewLabel_1_1_1_1_1_1_3 = new JLabel("Ivan A. R. Pointin");
		lblNewLabel_1_1_1_1_1_1_3.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1_1_1_1_1_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1_1_1_1_1_3.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		lblNewLabel_1_1_1_1_1_1_3.setBounds(38, 355, 370, 18);
		desktopPane_1.add(lblNewLabel_1_1_1_1_1_1_3);
		internalFrameInicio.setVisible(true);

	}

}
