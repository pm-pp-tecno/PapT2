package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;
import publicadores.DtPrestamoArray;
import publicadores.DtLibroArray;
import publicadores.DtArticuloArray;
import publicadores.DtLectorArray;

public class ReportesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ReportesServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Obtener datos del servicio web
            ControladorPublishService service = new ControladorPublishService();
            ControladorPublish controlador = service.getControladorPublishPort();
            
            // Obtener estadísticas
            DtPrestamoArray prestamosArray = controlador.listarPrestamos();
            DtLibroArray librosArray = controlador.listarLibros();
            DtArticuloArray articulosArray = controlador.listarArticulos();
            DtLectorArray lectoresArray = controlador.listarLectores();
            
            // Calcular cantidades
            int cantidadPrestamos = prestamosArray != null && prestamosArray.getItem() != null ? prestamosArray.getItem().size() : 0;
            int cantidadLibros = librosArray != null && librosArray.getItem() != null ? librosArray.getItem().size() : 0;
            int cantidadArticulos = articulosArray != null && articulosArray.getItem() != null ? articulosArray.getItem().size() : 0;
            int cantidadLectores = lectoresArray != null && lectoresArray.getItem() != null ? lectoresArray.getItem().size() : 0;
            
            // Pasar datos a la vista
            request.setAttribute("cantidadPrestamos", cantidadPrestamos);
            request.setAttribute("cantidadLibros", cantidadLibros);
            request.setAttribute("cantidadArticulos", cantidadArticulos);
            request.setAttribute("cantidadLectores", cantidadLectores);
            
            // Redirigir a la vista
            request.getRequestDispatcher("/reportes.jsp").forward(request, response);
            
        } catch (Exception e) {
            // En caso de error, mostrar valores por defecto
            System.err.println("Error al cargar estadísticas: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("cantidadPrestamos", 0);
            request.setAttribute("cantidadLibros", 0);
            request.setAttribute("cantidadArticulos", 0);
            request.setAttribute("cantidadLectores", 0);
            request.setAttribute("errorMessage", "Error al cargar estadísticas: " + e.getMessage());
            request.getRequestDispatcher("/reportes.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
