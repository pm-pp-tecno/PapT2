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
import publicadores.DtLector;
import publicadores.DtLectorArray;

public class GestionUsuariosServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public GestionUsuariosServlet() {
        super();
        System.out.println("=== CONSTRUCTOR GestionUsuariosServlet EJECUTADO ===");
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        System.out.println("=== INICIANDO doGet DE GestionUsuariosServlet ===");
        
        List<DtLector> lectores = new ArrayList<DtLector>();
        String errorMessage = null;
        
        try {
            System.out.println("=== INICIANDO CONEXIÓN AL SERVICIO SOAP ===");
            
            // Crear instancia del servicio
            ControladorPublishService service = new ControladorPublishService();
            System.out.println("Servicio creado correctamente");
            
            ControladorPublish controlador = service.getControladorPublishPort();
            System.out.println("Puerto del servicio obtenido correctamente");
            
            // Llamar al servicio web para obtener la lista de lectores
            System.out.println("Llamando a listarLectores()...");
            DtLectorArray lectoresArray = controlador.listarLectores();
            System.out.println("Respuesta recibida del servicio");
            
            if (lectoresArray != null) {
                System.out.println("lectoresArray no es null");
                if (lectoresArray.getItem() != null) {
                    System.out.println("lectoresArray.getItem() no es null, tamaño: " + lectoresArray.getItem().size());
                    lectores.addAll(lectoresArray.getItem());
                    System.out.println("Lectores agregados a la lista, total: " + lectores.size());
                } else {
                    System.out.println("lectoresArray.getItem() es null");
                }
            } else {
                System.out.println("lectoresArray es null");
            }
            
        } catch (Exception e) {
            errorMessage = "Error al conectar con el servicio web: " + e.getMessage();
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        }
        
        // Configurar atributos para la JSP
        request.setAttribute("lectores", lectores);
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("dateFormat", new SimpleDateFormat("dd/MM/yyyy"));
        
        // Redirigir a la JSP
        request.getRequestDispatcher("/gestionUsuarios.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
