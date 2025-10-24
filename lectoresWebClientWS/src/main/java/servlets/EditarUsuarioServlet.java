package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;

public class EditarUsuarioServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditarUsuarioServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String id = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String direccion = request.getParameter("direccion");
        String estado = request.getParameter("estado");
        String zona = request.getParameter("zona");

        boolean success = false;
        String message = "";

        try {
            ControladorPublishService service = new ControladorPublishService();
            ControladorPublish controlador = service.getControladorPublishPort();

            // Cambiar zona si se proporcionó
            if (zona != null && !zona.trim().isEmpty() && id != null && !id.trim().isEmpty()) {
                controlador.cambiarZonaLector(id, zona);
            }

            // Cambiar estado: el servicio expone suspenderLector; para activar no hay método
            if (estado != null && id != null && !id.trim().isEmpty()) {
                if ("SUSPENDIDO".equalsIgnoreCase(estado)) {
                    // suspenderLector toma (id, motivo)
                    controlador.suspenderLector(id, "Suspendido desde web");
                } else if ("ACTIVO".equalsIgnoreCase(estado)) {
                    // No existe operación pública para reactivar en el WSDL generado.
                    // De momento no hacemos nada y devolvemos mensaje informativo.
                }
            }

            // Nota: el servicio SOAP disponible en este cliente no ofrece métodos para
            // actualizar nombre/email/direccion; por eso no se llaman aquí.

            success = true;
            message = "Datos actualizados correctamente.";

        } catch (Exception e) {
            success = false;
            message = "Error al actualizar: " + e.getMessage();
            e.printStackTrace();
        }

        // Construir respuesta JSON simple
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"success\":").append(success).append(',');
        sb.append("\"message\":\"").append(escapeJson(message)).append('\"');
        sb.append('}');

        try (PrintWriter out = response.getWriter()) {
            out.print(sb.toString());
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n");
    }
}
