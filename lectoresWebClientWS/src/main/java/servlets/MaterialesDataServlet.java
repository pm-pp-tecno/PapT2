package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

public class MaterialesDataServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public MaterialesDataServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        try {
            // Obtener datos del servicio web
            ControladorPublishService service = new ControladorPublishService();
            ControladorPublish controlador = service.getControladorPublishPort();
            
            
            // Obtener libros y artículos por separado
            DtLibroArray librosArray = controlador.listarLibros();
            DtArticuloArray articulosArray = controlador.listarArticulos();
            
            // Combinar en una lista unificada, ordenando por ID
            List<Object> materiales = new ArrayList<>();
            
            // Agregar libros ordenados por ID
            if (librosArray != null && librosArray.getItem() != null) {
                List<DtLibro> libros = new ArrayList<>(librosArray.getItem());
                Collections.sort(libros, new Comparator<DtLibro>() {
                    @Override
                    public int compare(DtLibro a, DtLibro b) {
                        return Long.compare(a.getId(), b.getId());
                    }
                });
                materiales.addAll(libros);
            }
            
            // Agregar artículos ordenados por ID
            if (articulosArray != null && articulosArray.getItem() != null) {
                List<DtArticulo> articulos = new ArrayList<>(articulosArray.getItem());
                Collections.sort(articulos, new Comparator<DtArticulo>() {
                    @Override
                    public int compare(DtArticulo a, DtArticulo b) {
                        return Long.compare(a.getId(), b.getId());
                    }
                });
                materiales.addAll(articulos);
            }
            
            // Configurar formato de fecha
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            // Construir respuesta JSON
            StringBuilder json = new StringBuilder();
            json.append('[');
            
            boolean first = true;
            for (Object material : materiales) {
                if (!first) {
                    json.append(',');
                }
                first = false;
                
                if (material instanceof DtLibro) {
                    DtLibro libro = (DtLibro) material;
                    json.append('{');
                    json.append("\"id\":").append(libro.getId()).append(',');
                    json.append("\"tipo\":\"LIBRO\"").append(',');
                    json.append("\"titulo\":\"").append(escapeJson(libro.getTitulo())).append('"').append(',');
                    json.append("\"paginas\":").append(libro.getCantidadPaginas()).append(',');
                json.append("\"fechaIngreso\":\"").append(escapeJson(libro.getFechaIngreso().toString())).append('"');
                    json.append('}');
                } else if (material instanceof DtArticulo) {
                    DtArticulo articulo = (DtArticulo) material;
                    json.append('{');
                    json.append("\"id\":").append(articulo.getId()).append(',');
                    json.append("\"tipo\":\"ARTICULO\"").append(',');
                    json.append("\"descripcion\":\"").append(escapeJson(articulo.getDescripcion())).append('"').append(',');
                    json.append("\"peso\":").append(articulo.getPeso()).append(',');
                    json.append("\"dimensiones\":\"").append(escapeJson(articulo.getDimensiones())).append('"').append(',');
                json.append("\"fechaIngreso\":\"").append(escapeJson(articulo.getFechaIngreso().toString())).append('"');
                    json.append('}');
                }
            }
            
            json.append(']');
            
            try (PrintWriter out = response.getWriter()) {
                out.print(json.toString());
            }
            
        } catch (Exception e) {
            System.err.println("Error al cargar materiales: " + e.getMessage());
            e.printStackTrace();
            
            // Enviar array vacío en caso de error
            try (PrintWriter out = response.getWriter()) {
                out.print("[]");
            }
        }
    }

    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n").replace("\r","\\r").replace("\t","\\t");
    }
}
