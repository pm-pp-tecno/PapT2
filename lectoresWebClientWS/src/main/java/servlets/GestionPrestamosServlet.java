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
import publicadores.DtLibro;
import publicadores.DtArticulo;
import publicadores.DtLector;
import publicadores.DtBibliotecario;
import publicadores.DtLibroArray;
import publicadores.DtArticuloArray;
import publicadores.DtLectorArray;
import publicadores.DtBibliotecarioArray;

public class GestionPrestamosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GestionPrestamosServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtener datos del servicio web
            ControladorPublishService service = new ControladorPublishService();
            ControladorPublish controlador = service.getControladorPublishPort();
            
            // Obtener datos del usuario de la sesión
            String tipoUsuario = (String) request.getSession().getAttribute("tipoUsuario");
            String emailUsuario = (String) request.getSession().getAttribute("email");
            
            // Debug: Imprimir datos de sesión
            System.out.println("=== GESTION PRESTAMOS SERVLET DEBUG ===");
            System.out.println("Tipo de usuario: " + tipoUsuario);
            System.out.println("Email del usuario: " + emailUsuario);
            
            // Obtener préstamos
            DtPrestamoArray prestamosArray = controlador.listarPrestamos();
            
            // Debug: Imprimir total de préstamos
            int totalPrestamos = prestamosArray != null && prestamosArray.getItem() != null ? prestamosArray.getItem().size() : 0;
            System.out.println("Total de préstamos en el sistema: " + totalPrestamos);
            
            // Convertir a lista y filtrar según el tipo de usuario
            List<DtPrestamo> prestamos = new ArrayList<>();
            if (prestamosArray != null && prestamosArray.getItem() != null) {
                for (DtPrestamo prestamo : prestamosArray.getItem()) {
                    // Debug: Imprimir información de cada préstamo
                    if (prestamo.getLector() != null) {
                        System.out.println("Préstamo ID: " + prestamo.getId() + 
                                          ", Lector Email: " + prestamo.getLector().getEmail() + 
                                          ", Estado: " + prestamo.getEstado());
                    } else {
                        System.out.println("Préstamo ID: " + prestamo.getId() + 
                                          ", Lector: NULL" + 
                                          ", Estado: " + prestamo.getEstado());
                    }
                    
                    // Si es BIBLIOTECARIO, mostrar todos los préstamos
                    // Si es LECTOR, mostrar solo sus préstamos
                    if ("BIBLIOTECARIO".equals(tipoUsuario)) {
                        prestamos.add(prestamo);
                        System.out.println("Agregado para BIBLIOTECARIO");
                    } else if ("LECTOR".equals(tipoUsuario) && emailUsuario != null) {
                        // Filtrar por el email del lector actual
                        if (prestamo.getLector() != null && emailUsuario.equals(prestamo.getLector().getEmail())) {
                            prestamos.add(prestamo);
                            System.out.println("Agregado para LECTOR: " + emailUsuario);
                        } else {
                            System.out.println("NO agregado para LECTOR: " + emailUsuario + 
                                             " != " + (prestamo.getLector() != null ? prestamo.getLector().getEmail() : "NULL"));
                        }
                    }
                }
            }
            
            System.out.println("Préstamos filtrados finales: " + prestamos.size());
            System.out.println("=== FIN DEBUG ===");
            
            // Obtener datos para los selects (con información de disponibilidad y estado)
            DtLibroArray librosArray = controlador.listarLibrosParaSelect();
            DtArticuloArray articulosArray = controlador.listarArticulosParaSelect();
            DtLectorArray lectoresArray = controlador.listarLectoresParaSelect();
            DtBibliotecarioArray bibliotecariosArray = controlador.listarBibliotecarios();
            
            // Convertir a arrays
            DtLibro[] libros = librosArray != null ? librosArray.getItem().toArray(new DtLibro[0]) : new DtLibro[0];
            DtArticulo[] articulos = articulosArray != null ? articulosArray.getItem().toArray(new DtArticulo[0]) : new DtArticulo[0];
            DtLector[] lectores = lectoresArray != null ? lectoresArray.getItem().toArray(new DtLector[0]) : new DtLector[0];
            DtBibliotecario[] bibliotecarios = bibliotecariosArray != null ? bibliotecariosArray.getItem().toArray(new DtBibliotecario[0]) : new DtBibliotecario[0];
            
            
            // Configurar formato de fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            // Pasar datos a la vista
            request.setAttribute("prestamos", prestamos);
            request.setAttribute("dateFormat", dateFormat);
            request.setAttribute("libros", libros);
            request.setAttribute("articulos", articulos);
            request.setAttribute("lectores", lectores);
            request.setAttribute("bibliotecarios", bibliotecarios);
            
            // Redirigir a la vista
            request.getRequestDispatcher("/gestionPrestamos.jsp").forward(request, response);
            
        } catch (Exception e) {
            // En caso de error, mostrar mensaje y lista vacía
            System.err.println("Error al cargar préstamos: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("prestamos", new ArrayList<DtPrestamo>());
            request.setAttribute("errorMessage", "Error al cargar préstamos: " + e.getMessage());
            request.setAttribute("dateFormat", new SimpleDateFormat("dd/MM/yyyy"));
            request.setAttribute("libros", new DtLibro[0]);
            request.setAttribute("articulos", new DtArticulo[0]);
            request.setAttribute("lectores", new DtLector[0]);
            request.setAttribute("bibliotecarios", new DtBibliotecario[0]);
            request.getRequestDispatcher("/gestionPrestamos.jsp").forward(request, response);
        }
    }
}
