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
            
            // Obtener préstamos
            DtPrestamoArray prestamosArray = controlador.listarPrestamos();
            
            // Convertir a lista
            List<DtPrestamo> prestamos = new ArrayList<>();
            if (prestamosArray != null && prestamosArray.getItem() != null) {
                for (DtPrestamo prestamo : prestamosArray.getItem()) {
                    prestamos.add(prestamo);
                }
            }
            
            // Obtener datos para los selects
            DtLibroArray librosArray = controlador.listarLibros();
            DtArticuloArray articulosArray = controlador.listarArticulos();
            DtLectorArray lectoresArray = controlador.listarLectores();
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
