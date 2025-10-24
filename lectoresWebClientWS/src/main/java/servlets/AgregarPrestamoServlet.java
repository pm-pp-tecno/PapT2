package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;

public class AgregarPrestamoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AgregarPrestamoServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        boolean success = false;
        String message = "";

        // Obtener parámetros del formulario
        String idMaterialStr = request.getParameter("idMaterial");
        String emailLector = request.getParameter("emailLector");
        String numeroBibliotecario = request.getParameter("numeroBibliotecario");
        String fechaDevolucionStr = request.getParameter("fechaDevolucion");

        // Validaciones
        if (idMaterialStr == null || idMaterialStr.trim().isEmpty()) {
            success = false;
            message = "ID del material es requerido.";
        } else if (emailLector == null || emailLector.trim().isEmpty()) {
            success = false;
            message = "Email del lector es requerido.";
        } else if (numeroBibliotecario == null || numeroBibliotecario.trim().isEmpty()) {
            success = false;
            message = "Número del bibliotecario es requerido.";
        } else if (fechaDevolucionStr == null || fechaDevolucionStr.trim().isEmpty()) {
            success = false;
            message = "Fecha de devolución es requerida.";
        } else {
            try {
                // Convertir ID del material
                Long idMaterial = Long.parseLong(idMaterialStr);

                // La fecha ya está en formato String correcto (yyyy-MM-dd)

                // Llamar al servicio web
                ControladorPublishService service = new ControladorPublishService();
                ControladorPublish controlador = service.getControladorPublishPort();

                controlador.registrarPrestamo(idMaterial, emailLector, numeroBibliotecario, fechaDevolucionStr);

                success = true;
                message = "Préstamo registrado correctamente.";

            } catch (NumberFormatException e) {
                success = false;
                message = "El ID del material debe ser un número válido.";
            } catch (Exception e) {
                success = false;
                String errorMsg = e.getMessage();
                if (errorMsg != null && errorMsg.contains("JdbcEnvironment")) {
                    message = "Error de conexión a la base de datos. Por favor, intente nuevamente en unos momentos.";
                } else if (errorMsg != null && errorMsg.contains("Connection")) {
                    message = "Error de conexión. El servidor puede estar sobrecargado. Intente nuevamente.";
                } else {
                    message = "Error al registrar préstamo: " + errorMsg;
                }
                e.printStackTrace();
            }
        }

        // Construir respuesta JSON simple
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"success\":").append(success).append(',');
        sb.append("\"message\":\"").append(message.replace("\"", "\\\"")).append('"');
        sb.append('}');

        response.getWriter().write(sb.toString());
    }

}
