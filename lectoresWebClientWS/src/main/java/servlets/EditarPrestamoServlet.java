package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;

public class EditarPrestamoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditarPrestamoServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("=== EditarPrestamoServlet - Iniciando ===");
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        boolean success = false;
        String message = "Error desconocido";

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
        String idStr = null;
        String estado = null;
        String fechaDevolucionStr = null;
        
        if (jsonData.contains("\"id_prestamo\"")) {
            idStr = extraerValorJSON(jsonData, "id_prestamo");
        }
        if (jsonData.contains("\"estado\"")) {
            estado = extraerValorJSON(jsonData, "estado");
        }
        if (jsonData.contains("\"fechaDevolucion\"")) {
            fechaDevolucionStr = extraerValorJSON(jsonData, "fechaDevolucion");
        }
        
        System.out.println("Parámetros recibidos:");
        System.out.println("- ID: '" + idStr + "' (null? " + (idStr == null) + ")");
        System.out.println("- Estado: '" + estado + "' (null? " + (estado == null) + ")");
        System.out.println("- Fecha Devolución: '" + fechaDevolucionStr + "' (null? " + (fechaDevolucionStr == null) + ")");
        
        System.out.println("Iniciando validaciones...");
        
        // Log de todos los parámetros de la request
        System.out.println("Todos los parámetros de la request:");
        java.util.Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = request.getParameter(paramName);
            System.out.println("  " + paramName + " = '" + paramValue + "'");
        }

        // Validaciones básicas
        if (idStr == null || idStr.trim().isEmpty()) {
            success = false;
            message = "ID del préstamo es requerido. Recibido: '" + idStr + "'";
            System.out.println("ERROR: ID del préstamo es requerido. Recibido: '" + idStr + "'");
        } else if (estado == null || estado.trim().isEmpty()) {
            success = false;
            message = "Estado del préstamo es requerido.";
            System.out.println("ERROR: Estado del préstamo es requerido.");
        } else {
            System.out.println("Validaciones básicas pasadas. ID: " + idStr + ", Estado: " + estado);
            try {
                // Convertir ID del préstamo
                Long id = Long.parseLong(idStr);
                System.out.println("ID convertido: " + id);

                // Validar estado según el enum EstadoPrestamo
                System.out.println("Validando estado: '" + estado + "'");
                System.out.println("PENDIENTE.equals(estado): " + "PENDIENTE".equals(estado));
                System.out.println("EN_CURSO.equals(estado): " + "EN_CURSO".equals(estado));
                System.out.println("DEVUELTO.equals(estado): " + "DEVUELTO".equals(estado));
                
                if (!"PENDIENTE".equals(estado) && !"EN_CURSO".equals(estado) && 
                    !"DEVUELTO".equals(estado)) {
                    success = false;
                    message = "Estado del préstamo no válido: " + estado + ". Estados válidos: PENDIENTE, EN_CURSO, DEVUELTO";
                    System.out.println("ERROR: Estado no válido: " + estado);
                } else {
                    System.out.println("Validación de estado pasada. Estado válido: " + estado);
                    try {
                        // Conectar al servicio web
                        System.out.println("Conectando al servicio web...");
                        ControladorPublishService service = new ControladorPublishService();
                        ControladorPublish controlador = service.getControladorPublishPort();
                        System.out.println("Servicio web conectado exitosamente");
                        
                        // Convertir fecha de devolución si se proporciona
                        System.out.println("Procesando fecha de devolución: '" + fechaDevolucionStr + "'");
                        Date fechaDevolucion = null;
                        if (fechaDevolucionStr != null && !fechaDevolucionStr.trim().isEmpty()) {
                            System.out.println("Intentando parsear fecha: '" + fechaDevolucionStr + "'");
                            try {
                                // Parsear la fecha usando Calendar para evitar problemas de zona horaria
                                String[] partes = fechaDevolucionStr.split("-");
                                if (partes.length == 3) {
                                    int año = Integer.parseInt(partes[0]);
                                    int mes = Integer.parseInt(partes[1]) - 1; // Los meses en Java van de 0-11
                                    int dia = Integer.parseInt(partes[2]);
                                    
                                    java.util.Calendar cal = java.util.Calendar.getInstance();
                                    cal.set(java.util.Calendar.YEAR, año);
                                    cal.set(java.util.Calendar.MONTH, mes);
                                    cal.set(java.util.Calendar.DAY_OF_MONTH, dia);
                                    cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
                                    cal.set(java.util.Calendar.MINUTE, 0);
                                    cal.set(java.util.Calendar.SECOND, 0);
                                    cal.set(java.util.Calendar.MILLISECOND, 0);
                                    
                                    fechaDevolucion = cal.getTime();
                                } else {
                                    throw new ParseException("Formato de fecha inválido", 0);
                                }
                                System.out.println("Fecha de devolución parseada exitosamente: " + fechaDevolucion);
                            } catch (ParseException e) {
                                success = false;
                                message = "Formato de fecha inválido. Use YYYY-MM-DD: " + fechaDevolucionStr;
                                System.out.println("ERROR parseando fecha: " + e.getMessage());
                                System.out.println("Fecha recibida: '" + fechaDevolucionStr + "'");
                            }
                        } else {
                            System.out.println("No hay fecha de devolución para procesar");
                        }
                        
                        System.out.println("=== DIAGNÓSTICO DE SUCCESS ===");
                        System.out.println("Success antes del procesamiento de fecha: " + success);
                        System.out.println("Success después del procesamiento de fecha: " + success);
                        System.out.println("Mensaje actual: '" + message + "'");
                        System.out.println("=== FIN DIAGNÓSTICO ===");
                        
                        System.out.println("Estado de success después del procesamiento de fecha: " + success);
                        System.out.println("Mensaje actual: '" + message + "'");
                        
                        // Verificar si hubo error en el parseo de fecha
                        boolean errorEnFecha = false;
                        if (fechaDevolucionStr != null && !fechaDevolucionStr.trim().isEmpty() && fechaDevolucion == null) {
                            errorEnFecha = true;
                            System.out.println("ERROR: Hubo un problema parseando la fecha");
                        }
                        
                        if (!errorEnFecha) { // Solo si no hubo error en el parseo de fecha
                            // Primero, probar si el servicio web está disponible
                            try {
                                System.out.println("Probando conexión al servicio web...");
                                // Intentar listar préstamos como prueba de conexión
                                publicadores.DtPrestamoArray prestamosArray = controlador.listarPrestamos();
                                System.out.println("Conexión exitosa. Préstamos encontrados: " + prestamosArray.getItem().size());
                                
                                // Actualizar préstamo según si hay fecha o no
                                if (fechaDevolucion != null) {
                                    System.out.println("Llamando a actualizarPrestamo con ID: " + id + ", Estado: " + estado + ", Fecha: " + fechaDevolucionStr);
                                    controlador.actualizarPrestamo(id.longValue(), estado, fechaDevolucionStr);
                                    message = "Préstamo actualizado: estado=" + estado + ", fecha devolución=" + fechaDevolucionStr;
                                } else {
                                    System.out.println("Llamando a actualizarEstadoPrestamo con ID: " + id + ", Estado: " + estado);
                                    controlador.actualizarEstadoPrestamo(id.longValue(), estado);
                                    message = "Estado del préstamo actualizado a: " + estado;
                                }
                                System.out.println("Llamada al servicio web completada");
                                
                                success = true;
                                System.out.println("Préstamo actualizado exitosamente");
                                
                            } catch (Exception testException) {
                                System.out.println("Error en prueba de conexión: " + testException.getMessage());
                                throw testException; // Re-lanzar para que sea capturado por el catch principal
                            }
                        }
                        
                    } catch (Exception e) {
                        success = false;
                        System.out.println("=== ERROR DETALLADO ===");
                        System.out.println("Tipo de excepción: " + e.getClass().getName());
                        System.out.println("Mensaje: " + e.getMessage());
                        System.out.println("Causa: " + e.getCause());
                        e.printStackTrace();
                        System.out.println("=== FIN ERROR DETALLADO ===");
                        
                        if (e.getMessage() != null && e.getMessage().contains("JdbcEnvironment")) {
                            message = "Error de conexión con la base de datos. Por favor, intente nuevamente.";
                        } else if (e.getMessage() != null && e.getMessage().contains("Connection")) {
                            message = "Error de conexión con el servidor. Por favor, intente nuevamente.";
                        } else if (e.getMessage() != null && e.getMessage().contains("ConnectException")) {
                            message = "No se puede conectar al servicio web. Asegúrese de que el servicio esté ejecutándose.";
                        } else {
                            message = "Error al actualizar préstamo: " + e.getMessage();
                        }
                        System.out.println("Error actualizando préstamo: " + e.getMessage());
                    }
                }

            } catch (NumberFormatException e) {
                success = false;
                message = "El ID del préstamo debe ser un número válido: " + idStr;
                System.out.println("Error de conversión de ID: " + e.getMessage());
            } catch (Exception e) {
                success = false;
                message = "Error inesperado: " + e.getMessage();
                System.out.println("Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Construir respuesta JSON simple
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"success\":").append(success).append(',');
        sb.append("\"message\":\"").append(message.replace("\"", "\\\"")).append('"');
        sb.append('}');

        String jsonResponse = sb.toString();
        System.out.println("=== RESPUESTA FINAL ===");
        System.out.println("Success: " + success);
        System.out.println("Message: '" + message + "'");
        System.out.println("Respuesta JSON: " + jsonResponse);
        System.out.println("=== FIN RESPUESTA FINAL ===");
        
        response.getWriter().write(jsonResponse);
        System.out.println("=== EditarPrestamoServlet - Finalizando ===");
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
