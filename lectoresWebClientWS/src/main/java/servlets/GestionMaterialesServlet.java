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
import publicadores.DtLibro;
import publicadores.DtArticulo;
import publicadores.DtLibroArray;
import publicadores.DtArticuloArray;

public class GestionMaterialesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public GestionMaterialesServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        try {
            // Obtener datos del servicio web usando los nuevos métodos específicos
            ControladorPublishService service = new ControladorPublishService();
            ControladorPublish controlador = service.getControladorPublishPort();
            
            // Obtener libros y artículos por separado
            DtLibroArray librosArray = controlador.listarLibros();
            DtArticuloArray articulosArray = controlador.listarArticulos();
            
            // Combinar en una lista unificada
            List<Object> materiales = new ArrayList<>();
            if (librosArray != null && librosArray.getItem() != null) {
                for (DtLibro libro : librosArray.getItem()) {
                    materiales.add(libro);
                }
            }
            if (articulosArray != null && articulosArray.getItem() != null) {
                for (DtArticulo articulo : articulosArray.getItem()) {
                    materiales.add(articulo);
                }
            }
            
            // Configurar formato de fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            // Pasar datos a la vista
            request.setAttribute("materiales", materiales);
            request.setAttribute("dateFormat", dateFormat);
            
            // Redirigir a la vista
            request.getRequestDispatcher("/gestionMateriales.jsp").forward(request, response);
            
        } catch (Exception e) {
            // En caso de error, mostrar mensaje y lista vacía
            System.err.println("Error al cargar materiales: " + e.getMessage());
            e.printStackTrace();
            
            request.setAttribute("materiales", new ArrayList<Object>());
            request.setAttribute("errorMessage", "Error al cargar materiales: " + e.getMessage());
            request.setAttribute("dateFormat", new SimpleDateFormat("dd/MM/yyyy"));
            request.getRequestDispatcher("/gestionMateriales.jsp").forward(request, response);
        }
    }
}
