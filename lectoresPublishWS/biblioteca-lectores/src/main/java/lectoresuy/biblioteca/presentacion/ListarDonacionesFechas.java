package lectoresuy.biblioteca.presentacion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import lectoresuy.biblioteca.datatypes.DtArticulo;
import lectoresuy.biblioteca.datatypes.DtLibro;
import lectoresuy.biblioteca.datatypes.DtMaterial;
import lectoresuy.biblioteca.interfaces.IControladorMaterial;

public class ListarDonacionesFechas extends JInternalFrame {

    private IControladorMaterial iconM;
    private JList<String> donacionesList;
    private DefaultListModel<String> listModel;
    private JSpinner spDiaInicio, spMesInicio, spAnioInicio;
    private JSpinner spDiaFin, spMesFin, spAnioFin;

    public ListarDonacionesFechas(IControladorMaterial iconM) {
        super("Listar Donaciones en un rango de fechas", false, false, false, false);
        this.iconM = iconM;
        setSize(500, 400);
        setLayout(new BorderLayout());

        // Panel for date selection
        JPanel panelFechas = new JPanel();
        panelFechas.setLayout(new GridLayout(3, 1));

        // Start Date Components
        JPanel panelInicio = new JPanel();
        panelInicio.add(new JLabel("Fecha Inicial:"));
        Calendar cal = Calendar.getInstance();
        spDiaInicio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1));
        spMesInicio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.MONTH) + 1, 1, 12, 1));
        spAnioInicio = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.YEAR), 2000, 2030, 1));
        panelInicio.add(spDiaInicio);
        panelInicio.add(new JLabel("/"));
        panelInicio.add(spMesInicio);
        panelInicio.add(new JLabel("/"));
        panelInicio.add(spAnioInicio);

        // End Date Components
        JPanel panelFin = new JPanel();
        panelFin.add(new JLabel("Fecha Final: "));
        spDiaFin = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.DAY_OF_MONTH), 1, 31, 1));
        spMesFin = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.MONTH) + 1, 1, 12, 1));
        spAnioFin = new JSpinner(new SpinnerNumberModel(cal.get(Calendar.YEAR), 2000, 2030, 1));
        panelFin.add(spDiaFin);
        panelFin.add(new JLabel("/"));
        panelFin.add(spMesFin);
        panelFin.add(new JLabel("/"));
        panelFin.add(spAnioFin);

        JButton btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                recargarDonaciones();
            }
        });

        panelFechas.add(panelInicio);
        panelFechas.add(panelFin);
        panelFechas.add(btnFiltrar);

        add(panelFechas, BorderLayout.NORTH);

        // List to display donations
        listModel = new DefaultListModel<>();
        donacionesList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(donacionesList);
        add(scrollPane, BorderLayout.CENTER);

        // Load donations on startup
        recargarDonaciones();
    }

    public void recargarDonaciones() {
        try {
            int diaInicio = (Integer) spDiaInicio.getValue();
            int mesInicio = (Integer) spMesInicio.getValue();
            int anioInicio = (Integer) spAnioInicio.getValue();
            Calendar calInicio = Calendar.getInstance();
            calInicio.set(anioInicio, mesInicio - 1, diaInicio, 0, 0, 0);
            Date fechaInicio = calInicio.getTime();

            int diaFin = (Integer) spDiaFin.getValue();
            int mesFin = (Integer) spMesFin.getValue();
            int anioFin = (Integer) spAnioFin.getValue();
            Calendar calFin = Calendar.getInstance();
            calFin.set(anioFin, mesFin - 1, diaFin, 23, 59, 59);
            Date fechaFin = calFin.getTime();

            if (fechaInicio.after(fechaFin)) {
                JOptionPane.showMessageDialog(this, "La fecha de inicio no puede ser posterior a la fecha final.", "Error de Fechas", JOptionPane.ERROR_MESSAGE);
                return;
            }

            listModel.clear();
            List<DtMaterial> donaciones = iconM.listarDonacionesPorFecha(fechaInicio, fechaFin);
            if (donaciones.isEmpty()) {
                listModel.addElement("No hay donaciones registradas en el rango de fechas seleccionado.");
            } else {
                for (DtMaterial donacion : donaciones) {
                    String fechaStr = (donacion.getFechaIngreso() != null) ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(donacion.getFechaIngreso()) : "-";
                    String texto;
                    if (donacion instanceof DtLibro) {
                        DtLibro libro = (DtLibro) donacion;
                        texto = "ID: " + libro.getId() + " | Libro: " + libro.getTitulo() + " | Fecha ingreso: " + fechaStr;
                    } else if (donacion instanceof DtArticulo) {
                        DtArticulo articulo = (DtArticulo) donacion;
                        texto = "ID: " + articulo.getId() + " | Artículo: " + articulo.getDescripcion() + " | Fecha ingreso: " + fechaStr;
                    } else {
                        texto = "ID: " + donacion.getId() + " | Material no especificado | Fecha ingreso: " + fechaStr;
                    }
                    listModel.addElement(texto);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar las donaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
