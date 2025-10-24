package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;

public class AgregarMaterialServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AgregarMaterialServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String tipo = request.getParameter("tipo");
        String titulo = request.getParameter("titulo");
        String paginas = request.getParameter("paginas");
        String descripcion = request.getParameter("descripcion");
        String peso = request.getParameter("peso");
        String dimensiones = request.getParameter("dimensiones");
        String fecha = request.getParameter("fecha");

        boolean success = false;
        String message = "";

        // Validar parámetros requeridos
        if (tipo == null || tipo.trim().isEmpty()) {
            success = false;
            message = "Tipo de material es requerido.";
        } else if (fecha == null || fecha.trim().isEmpty()) {
            success = false;
            message = "La fecha de ingreso es requerida.";
        } else {
            try {
                ControladorPublishService service = new ControladorPublishService();
                ControladorPublish controlador = service.getControladorPublishPort();

                // La fecha ya está en formato String correcto (yyyy-MM-dd)
                String fechaString = fecha;

                if ("LIBRO".equals(tipo)) {
                    // Validar campos específicos de libro
                    if (titulo == null || titulo.trim().isEmpty()) {
                        success = false;
                        message = "El título del libro es requerido.";
                    } else if (paginas == null || paginas.trim().isEmpty()) {
                        success = false;
                        message = "La cantidad de páginas es requerida.";
                    } else {
                        try {
                            Integer cantidadPaginas = Integer.parseInt(paginas);
                            if (cantidadPaginas <= 0) {
                                success = false;
                                message = "La cantidad de páginas debe ser mayor a 0.";
                            } else {
                                controlador.agregarLibro(titulo.trim(), cantidadPaginas, fechaString);
                                message = "Libro agregado correctamente: " + titulo + " (" + cantidadPaginas + " páginas)";
                                success = true;
                            }
                        } catch (NumberFormatException e) {
                            success = false;
                            message = "La cantidad de páginas debe ser un número válido.";
                        }
                    }
                } else if ("ARTICULO".equals(tipo)) {
                    // Validar campos específicos de artículo
                    if (descripcion == null || descripcion.trim().isEmpty()) {
                        success = false;
                        message = "La descripción del artículo es requerida.";
                    } else if (peso == null || peso.trim().isEmpty()) {
                        success = false;
                        message = "El peso del artículo es requerido.";
                    } else {
                        try {
                            Double pesoValue = Double.parseDouble(peso);
                            if (pesoValue < 0) {
                                success = false;
                                message = "El peso debe ser mayor o igual a 0.";
                            } else {
                                controlador.agregarArticulo(descripcion.trim(), pesoValue, 
                                    dimensiones != null ? dimensiones.trim() : "", fechaString);
                                message = "Artículo agregado correctamente: " + descripcion + " (" + pesoValue + " kg)";
                                success = true;
                            }
                        } catch (NumberFormatException e) {
                            success = false;
                            message = "El peso debe ser un número válido.";
                        }
                    }
                } else {
                    success = false;
                    message = "Tipo de material no válido. Debe ser LIBRO o ARTICULO.";
                }

            } catch (Exception e) {
                success = false;
                String errorMsg = e.getMessage();
                if (errorMsg != null && errorMsg.contains("JdbcEnvironment")) {
                    message = "Error de conexión a la base de datos. Por favor, intente nuevamente en unos momentos.";
                } else if (errorMsg != null && errorMsg.contains("Connection")) {
                    message = "Error de conexión. El servidor puede estar sobrecargado. Intente nuevamente.";
                } else {
                    message = "Error al agregar material: " + errorMsg;
                }
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
