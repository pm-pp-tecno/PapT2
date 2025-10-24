package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
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

        String email = request.getParameter("email");
        String estado = request.getParameter("estado");
        String zona = request.getParameter("zona");

        boolean success = false;
        String message = "";

        // Validar parámetros requeridos - usar email como identificador
        if (email == null || email.trim().isEmpty()) {
            success = false;
            message = "Email del lector es requerido.";
        } else {
            try {
                ControladorPublishService service = new ControladorPublishService();
                ControladorPublish controlador = service.getControladorPublishPort();

                // Cambiar zona si se proporcionó
                if (zona != null && !zona.trim().isEmpty()) {
                    controlador.cambiarZonaLector(email, zona);
                    message = "Zona actualizada correctamente.";
                }

                // Cambiar estado si se proporcionó
                if (estado != null && !estado.trim().isEmpty()) {
                    if ("SUSPENDIDO".equalsIgnoreCase(estado)) {
                        controlador.suspenderLector(email, estado);
                        message += (message.isEmpty() ? "" : " ") + "Estado actualizado a SUSPENDIDO.";
                    } else if ("ACTIVO".equalsIgnoreCase(estado)) {
                        // Para reactivar, también usamos suspenderLector pero con estado ACTIVO
                        controlador.suspenderLector(email, estado);
                        message += (message.isEmpty() ? "" : " ") + "Estado actualizado a ACTIVO.";
                    }
                }

                // Si no se proporcionó zona ni estado, informar
                if ((zona == null || zona.trim().isEmpty()) && (estado == null || estado.trim().isEmpty())) {
                    message = "No se proporcionaron datos para actualizar.";
                }

                success = true;
                if (message.trim().isEmpty()) {
                    message = "Datos actualizados correctamente.";
                }

            } catch (Exception e) {
                success = false;
                message = "Error al actualizar: " + e.getMessage();
                e.printStackTrace();
            }
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
