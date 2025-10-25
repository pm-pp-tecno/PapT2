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

        System.out.println("*** AGREGAR PRESTAMO SERVLET EJECUTANDOSE ***");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        boolean success = false;
        String message = "";

        // Leer datos JSON del cuerpo de la request
        StringBuilder jsonBuffer = new StringBuilder();
        String line;
        try (java.io.BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }
        
        String jsonData = jsonBuffer.toString();
        System.out.println("JSON recibido: " + jsonData);
        
        // Parsear JSON simple (sin librerías externas)
        String idMaterialStr = null;
        String emailLector = null;
        String numeroBibliotecario = null;
        String fechaDevolucionStr = null;
        
        // Parsear JSON usando el método robusto
        if (jsonData != null && !jsonData.trim().isEmpty()) {
            System.out.println("Iniciando parseo del JSON...");
            
            if (jsonData.contains("\"idMaterial\"")) {
                idMaterialStr = extraerValorJSON(jsonData, "idMaterial");
                System.out.println("idMaterial parseado: " + idMaterialStr);
            }
            if (jsonData.contains("\"emailLector\"")) {
                emailLector = extraerValorJSON(jsonData, "emailLector");
                System.out.println("emailLector parseado: " + emailLector);
            }
            if (jsonData.contains("\"numeroBibliotecario\"")) {
                numeroBibliotecario = extraerValorJSON(jsonData, "numeroBibliotecario");
                System.out.println("numeroBibliotecario parseado: " + numeroBibliotecario);
            }
            if (jsonData.contains("\"fechaDevolucion\"")) {
                fechaDevolucionStr = extraerValorJSON(jsonData, "fechaDevolucion");
                System.out.println("fechaDevolucion parseada: " + fechaDevolucionStr);
            }
        }
        
        // Log de parámetros recibidos
        System.out.println("=== AGREGAR PRESTAMO - PARAMETROS RECIBIDOS ===");
        System.out.println("ID Material: " + idMaterialStr);
        System.out.println("Email Lector: " + emailLector);
        System.out.println("Numero Bibliotecario: " + numeroBibliotecario);
        System.out.println("Fecha Devolucion: " + fechaDevolucionStr);

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
                System.out.println("ID Material convertido: " + idMaterial);

                // La fecha ya está en formato String correcto (yyyy-MM-dd)
                System.out.println("Fecha de devolucion (String): " + fechaDevolucionStr);

                // Llamar al servicio web
                System.out.println("Conectando al servicio web...");
                ControladorPublishService service = new ControladorPublishService();
                ControladorPublish controlador = service.getControladorPublishPort();
                System.out.println("Servicio web conectado exitosamente.");

                System.out.println("Llamando a registrarPrestamo...");
                controlador.registrarPrestamo(idMaterial, emailLector, numeroBibliotecario, fechaDevolucionStr);
                System.out.println("Préstamo registrado exitosamente en el servicio web.");

                success = true;
                message = "Préstamo registrado correctamente.";
                System.out.println("Operación completada exitosamente.");

            } catch (NumberFormatException e) {
                success = false;
                message = "El ID del material debe ser un número válido.";
            } catch (Exception e) {
                success = false;
                String errorMsg = e.getMessage();
                System.out.println("ERROR en AgregarPrestamoServlet: " + errorMsg);
                System.out.println("Tipo de excepción: " + e.getClass().getSimpleName());
                e.printStackTrace();
                
                if (errorMsg != null && errorMsg.contains("JdbcEnvironment")) {
                    message = "Error de conexión a la base de datos. Por favor, intente nuevamente en unos momentos.";
                } else if (errorMsg != null && errorMsg.contains("Connection")) {
                    message = "Error de conexión. El servidor puede estar sobrecargado. Intente nuevamente.";
                } else {
                    message = "Error al registrar préstamo: " + errorMsg;
                }
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
    
    // Método auxiliar para extraer valores de JSON simple
    private String extraerValorJSON(String json, String key) {
        try {
            String pattern = "\"" + key + "\"\\s*:\\s*\"([^\"]+)\"";
            java.util.regex.Pattern p = java.util.regex.Pattern.compile(pattern);
            java.util.regex.Matcher m = p.matcher(json);
            if (m.find()) {
                return m.group(1);
            }
            
            // Intentar con número
            pattern = "\"" + key + "\"\\s*:\\s*(\\d+)";
            p = java.util.regex.Pattern.compile(pattern);
            m = p.matcher(json);
            if (m.find()) {
                return m.group(1);
            }
        } catch (Exception e) {
            System.out.println("Error extrayendo valor JSON para " + key + ": " + e.getMessage());
        }
        return null;
    }

}
