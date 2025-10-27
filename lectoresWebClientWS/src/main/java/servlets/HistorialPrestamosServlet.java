package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;
import publicadores.DtPrestamo;
import publicadores.DtPrestamoArray;

public class HistorialPrestamosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HistorialPrestamosServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtener datos del servicio web
            ControladorPublishService service = new ControladorPublishService();
            ControladorPublish controlador = service.getControladorPublishPort();
            
            // Obtener datos del usuario de la sesión
            String numeroEmpleado = (String) request.getSession().getAttribute("id");
            
            // Debug: Imprimir datos de sesión
            System.out.println("=== HISTORIAL PRESTAMOS SERVLET DEBUG ===");
            System.out.println("Número de empleado: " + numeroEmpleado);
            
            // Obtener todos los préstamos
            DtPrestamoArray prestamosArray = controlador.listarPrestamos();
            
            // Convertir a lista y filtrar por bibliotecario actual
            List<DtPrestamo> prestamos = new ArrayList<>();
            if (prestamosArray != null && prestamosArray.getItem() != null) {
                for (DtPrestamo prestamo : prestamosArray.getItem()) {
                    // Filtrar por el número de empleado del bibliotecario que gestionó el préstamo
                    if (prestamo.getBibliotecario() != null && numeroEmpleado != null) {
                        String numeroBiblio = prestamo.getBibliotecario().getNumeroEmpleado();
                        if (numeroEmpleado.equals(numeroBiblio)) {
                            prestamos.add(prestamo);
                            System.out.println("Préstamo ID: " + prestamo.getId() + 
                                              " gestionado por: " + numeroBiblio);
                        }
                    }
                }
            }
            
            System.out.println("Préstamos filtrados finales: " + prestamos.size());
            System.out.println("=== FIN DEBUG ===");
            
            // Configurar formato de fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            // Pasar datos a la vista
            request.setAttribute("prestamos", prestamos);
            request.setAttribute("dateFormat", dateFormat);
            
            // Redirigir a la vista
            request.getRequestDispatcher("/historialPrestamos.jsp").forward(request, response);
            
        } catch (Exception e) {
            // En caso de error, mostrar mensaje y lista vacía
            System.err.println("Error al cargar historial de préstamos: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("prestamos", new ArrayList<DtPrestamo>());
            request.setAttribute("errorMessage", "Error al cargar historial de préstamos: " + e.getMessage());
            request.setAttribute("dateFormat", new SimpleDateFormat("dd/MM/yyyy"));
            request.getRequestDispatcher("/historialPrestamos.jsp").forward(request, response);
        }
    }
}

