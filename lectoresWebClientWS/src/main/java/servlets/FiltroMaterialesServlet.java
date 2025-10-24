package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

public class FiltroMaterialesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FiltroMaterialesServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String fechaDesdeStr = request.getParameter("fechaDesde");
        String fechaHastaStr = request.getParameter("fechaHasta");

        boolean success = false;
        String message = "";
        List<Object> materialesFiltrados = new ArrayList<>();

        try {
            // Convertir fechas
            Date fechaDesde = null;
            Date fechaHasta = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (fechaDesdeStr != null && !fechaDesdeStr.trim().isEmpty()) {
                fechaDesde = dateFormat.parse(fechaDesdeStr);
            }
            if (fechaHastaStr != null && !fechaHastaStr.trim().isEmpty()) {
                fechaHasta = dateFormat.parse(fechaHastaStr);
            }

            // Obtener todos los materiales usando el servicio web
            ControladorPublishService service = new ControladorPublishService();
            ControladorPublish controlador = service.getControladorPublishPort();

            // Obtener libros
            DtLibroArray librosArray = controlador.listarLibros();
            if (librosArray != null && librosArray.getItem() != null) {
                for (DtLibro libro : librosArray.getItem()) {
                    if (cumpleFiltroFecha(libro.getFechaIngreso(), fechaDesde, fechaHasta)) {
                        materialesFiltrados.add(libro);
                    }
                }
            }

            // Obtener artículos
            DtArticuloArray articulosArray = controlador.listarArticulos();
            if (articulosArray != null && articulosArray.getItem() != null) {
                for (DtArticulo articulo : articulosArray.getItem()) {
                    if (cumpleFiltroFecha(articulo.getFechaIngreso(), fechaDesde, fechaHasta)) {
                        materialesFiltrados.add(articulo);
                    }
                }
            }

            success = true;
            message = "Filtro aplicado correctamente. Se encontraron " + materialesFiltrados.size() + " materiales.";

        } catch (ParseException e) {
            success = false;
            message = "Error en el formato de fecha: " + e.getMessage();
        } catch (Exception e) {
            success = false;
            message = "Error al aplicar el filtro: " + e.getMessage();
            e.printStackTrace();
        }

        // Construir respuesta JSON
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("\"success\":").append(success).append(',');
        sb.append("\"message\":\"").append(escapeJson(message)).append('\"');
        sb.append(',');
        sb.append("\"materiales\":[");
        
        // Agregar materiales filtrados al JSON
        for (int i = 0; i < materialesFiltrados.size(); i++) {
            if (i > 0) sb.append(',');
            Object material = materialesFiltrados.get(i);
            sb.append(convertirMaterialAJson(material));
        }
        
        sb.append(']');
        sb.append('}');

        try (PrintWriter out = response.getWriter()) {
            out.print(sb.toString());
        }
    }

    private boolean cumpleFiltroFecha(Object fechaObj, Date fechaDesde, Date fechaHasta) {
        if (fechaObj == null) return false;
        
        Date fechaMaterial = null;
        
        // Convertir fecha del material a Date
        if (fechaObj instanceof Date) {
            fechaMaterial = (Date) fechaObj;
        } else if (fechaObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
            fechaMaterial = ((javax.xml.datatype.XMLGregorianCalendar) fechaObj).toGregorianCalendar().getTime();
        } else {
            return false;
        }
        
        // Aplicar filtros
        if (fechaDesde != null && fechaMaterial.before(fechaDesde)) {
            return false;
        }
        if (fechaHasta != null && fechaMaterial.after(fechaHasta)) {
            return false;
        }
        
        return true;
    }

    private String convertirMaterialAJson(Object material) {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        
        if (material instanceof DtLibro) {
            DtLibro libro = (DtLibro) material;
            sb.append("\"id\":").append(libro.getId()).append(',');
            sb.append("\"tipo\":\"LIBRO\"").append(',');
            sb.append("\"titulo\":\"").append(escapeJson(libro.getTitulo())).append('\"').append(',');
            sb.append("\"paginas\":").append(libro.getCantidadPaginas()).append(',');
            sb.append("\"fechaIngreso\":\"").append(libro.getFechaIngreso().toString()).append('\"').append(',');
            sb.append("\"prestamosPendientes\":").append(libro.getCantidadPrestamosPendientes());
        } else if (material instanceof DtArticulo) {
            DtArticulo articulo = (DtArticulo) material;
            sb.append("\"id\":").append(articulo.getId()).append(',');
            sb.append("\"tipo\":\"ARTICULO\"").append(',');
            sb.append("\"descripcion\":\"").append(escapeJson(articulo.getDescripcion())).append('\"').append(',');
            sb.append("\"peso\":").append(articulo.getPeso()).append(',');
            sb.append("\"dimensiones\":\"").append(escapeJson(articulo.getDimensiones())).append('\"').append(',');
            sb.append("\"fechaIngreso\":\"").append(articulo.getFechaIngreso().toString()).append('\"').append(',');
            sb.append("\"prestamosPendientes\":").append(articulo.getCantidadPrestamosPendientes());
        }
        
        sb.append('}');
        return sb.toString();
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n");
    }
}
