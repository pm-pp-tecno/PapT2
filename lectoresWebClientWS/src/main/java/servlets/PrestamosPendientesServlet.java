package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

public class PrestamosPendientesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PrestamosPendientesServlet() {
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
            
            // Filtrar solo los préstamos pendientes y agrupar por material
            Map<Long, Integer> cantidadPrestamosPorMaterial = new HashMap<>();
            Map<Long, DtPrestamo> materialRepresentativo = new HashMap<>();
            
            if (prestamosArray != null && prestamosArray.getItem() != null) {
                for (DtPrestamo prestamo : prestamosArray.getItem()) {
                    // Verificar si el préstamo está pendiente
                    if (prestamo.getEstado() != null && 
                        prestamo.getEstado().toString().equals("PENDIENTE")) {
                        
                        // Contar préstamos pendientes por material
                        if (prestamo.getMaterial() != null) {
                            Long idMaterial = prestamo.getMaterial().getId();
                            cantidadPrestamosPorMaterial.put(idMaterial, 
                                cantidadPrestamosPorMaterial.getOrDefault(idMaterial, 0) + 1);
                            
                            // Guardar un préstamo representativo del material (el primero que encuentre)
                            if (!materialRepresentativo.containsKey(idMaterial)) {
                                materialRepresentativo.put(idMaterial, prestamo);
                            }
                        }
                    }
                }
            }
            
            // Crear lista de materiales únicos ordenados por cantidad de préstamos
            List<DtPrestamo> materialesOrdenados = new ArrayList<>();
            for (Map.Entry<Long, DtPrestamo> entry : materialRepresentativo.entrySet()) {
                materialesOrdenados.add(entry.getValue());
            }
            
            // Ordenar por cantidad de préstamos del material (mayor a menor)
            materialesOrdenados.sort((p1, p2) -> {
                Long id1 = p1.getMaterial() != null ? p1.getMaterial().getId() : 0L;
                Long id2 = p2.getMaterial() != null ? p2.getMaterial().getId() : 0L;
                int count1 = cantidadPrestamosPorMaterial.getOrDefault(id1, 0);
                int count2 = cantidadPrestamosPorMaterial.getOrDefault(id2, 0);
                return Integer.compare(count2, count1); // Ordenar de mayor a menor
            });
            
            System.out.println("=== PRESTAMOS PENDIENTES SERVLET ===");
            System.out.println("Total de materiales con préstamos pendientes: " + materialesOrdenados.size());
            System.out.println("=== FIN DEBUG ===");
            
            // Configurar formato de fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            // Pasar datos a la vista
            request.setAttribute("materialesConPrestamosPendientes", materialesOrdenados);
            request.setAttribute("cantidadPrestamosPorMaterial", cantidadPrestamosPorMaterial);
            request.setAttribute("dateFormat", dateFormat);
            
            // Redirigir a la vista
            request.getRequestDispatcher("/prestamosPendientes.jsp").forward(request, response);
            
        } catch (Exception e) {
            // En caso de error, mostrar mensaje y lista vacía
            System.err.println("Error al cargar préstamos pendientes: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("materialesConPrestamosPendientes", new ArrayList<DtPrestamo>());
            request.setAttribute("cantidadPrestamosPorMaterial", new HashMap<Long, Integer>());
            request.setAttribute("errorMessage", "Error al cargar préstamos pendientes: " + e.getMessage());
            request.setAttribute("dateFormat", new SimpleDateFormat("dd/MM/yyyy"));
            request.getRequestDispatcher("/prestamosPendientes.jsp").forward(request, response);
        }
    }
}

