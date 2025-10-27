package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;
import publicadores.DtPrestamo;
import publicadores.DtPrestamoArray;

public class PrestamosZonaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PrestamosZonaServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtener datos del servicio web
            ControladorPublishService service = new ControladorPublishService();
            ControladorPublish controlador = service.getControladorPublishPort();
            
            // Obtener todos los préstamos
            DtPrestamoArray prestamosArray = controlador.listarPrestamos();
            
            // Agrupar préstamos por zona
            Map<String, List<DtPrestamo>> prestamosPorZona = new LinkedHashMap<>();
            
            if (prestamosArray != null && prestamosArray.getItem() != null) {
                for (DtPrestamo prestamo : prestamosArray.getItem()) {
                    if (prestamo.getLector() != null && prestamo.getLector().getZona() != null) {
                        String zona = prestamo.getLector().getZona();
                        
                        // Si la zona no existe en el mapa, crear una nueva lista
                        if (!prestamosPorZona.containsKey(zona)) {
                            prestamosPorZona.put(zona, new ArrayList<>());
                        }
                        
                        // Agregar el préstamo a la zona correspondiente
                        prestamosPorZona.get(zona).add(prestamo);
                    }
                }
            }
            
            System.out.println("Préstamos agrupados por zona:");
            for (Map.Entry<String, List<DtPrestamo>> entry : prestamosPorZona.entrySet()) {
                System.out.println("Zona: " + entry.getKey() + ", Préstamos: " + entry.getValue().size());
            }
            
            // Configurar formato de fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            // Pasar datos a la vista
            request.setAttribute("prestamosPorZona", prestamosPorZona);
            request.setAttribute("dateFormat", dateFormat);
            
            // Redirigir a la vista
            request.getRequestDispatcher("/prestamosZona.jsp").forward(request, response);
            
        } catch (Exception e) {
            // En caso de error, mostrar mensaje y estructura vacía
            System.err.println("Error al cargar préstamos por zona: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("prestamosPorZona", new LinkedHashMap<String, List<DtPrestamo>>());
            request.setAttribute("errorMessage", "Error al cargar préstamos por zona: " + e.getMessage());
            request.setAttribute("dateFormat", new SimpleDateFormat("dd/MM/yyyy"));
            request.getRequestDispatcher("/prestamosZona.jsp").forward(request, response);
        }
    }
}

