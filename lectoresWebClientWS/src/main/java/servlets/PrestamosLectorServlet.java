package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;
import publicadores.DtPrestamo;
import publicadores.DtPrestamoArray;
import publicadores.DtLibro;
import publicadores.DtArticulo;

public class PrestamosLectorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PrestamosLectorServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		try {
			String email = request.getParameter("email");
			
			if (email == null || email.trim().isEmpty()) {
				response.getWriter().write("{\"success\":false,\"message\":\"Email requerido\",\"prestamos\":[]}");
				return;
			}

			// Conectar al servicio web
			ControladorPublishService service = new ControladorPublishService();
			ControladorPublish controlador = service.getControladorPublishPort();

			// Obtener todos los préstamos
			DtPrestamoArray prestamosArray = controlador.listarPrestamos();

			// Filtrar préstamos del lector por email y solo activos (PENDIENTE o EN_CURSO)
			List<Object> prestamosLector = new ArrayList<>();
			if (prestamosArray != null && prestamosArray.getItem() != null) {
				for (DtPrestamo prestamo : prestamosArray.getItem()) {
					if (prestamo.getLector() != null && prestamo.getLector().getEmail() != null &&
						prestamo.getLector().getEmail().equals(email)) {
						
						String estado = prestamo.getEstado().toString();
						if ("PENDIENTE".equals(estado) || "EN_CURSO".equals(estado)) {
							// Crear objeto JSON simple
							String materialTitulo = "N/A";
							if (prestamo.getMaterial() instanceof DtLibro) {
								DtLibro libro = (DtLibro) prestamo.getMaterial();
								materialTitulo = libro.getTitulo() != null ? libro.getTitulo() : "Libro";
							} else if (prestamo.getMaterial() instanceof DtArticulo) {
								DtArticulo articulo = (DtArticulo) prestamo.getMaterial();
								materialTitulo = articulo.getDescripcion() != null ? articulo.getDescripcion() : "Artículo";
							}
							
							String fechaSolicitud = "N/A";
							Object fechaObj = prestamo.getFechaSolicitud();
							if (fechaObj != null) {
								try {
									if (fechaObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
										javax.xml.datatype.XMLGregorianCalendar cal = (javax.xml.datatype.XMLGregorianCalendar) fechaObj;
										if (cal != null) {
											fechaSolicitud = String.format("%02d/%02d/%04d", cal.getDay(), cal.getMonth(), cal.getYear());
										}
									} else {
										fechaSolicitud = fechaObj.toString();
									}
								} catch (Exception e) {
									fechaSolicitud = "N/A";
								}
							}
							
							// Escapar comillas en el título del material
							String materialTituloEscapado = materialTitulo.replace("\"", "\\\"").replace("\n", " ");
							
							String prestamoJson = String.format(
								"{\"id\":%d,\"materialTitulo\":\"%s\",\"fechaSolicitud\":\"%s\",\"estado\":\"%s\"}",
								prestamo.getId(),
								materialTituloEscapado,
								fechaSolicitud.replace("\"", "\\\""),
								estado
							);
							prestamosLector.add(prestamoJson);
						}
					}
				}
			}

			// Construir respuesta JSON
			StringBuilder jsonResponse = new StringBuilder();
			jsonResponse.append("{\"success\":true,\"prestamos\":[");
			for (int i = 0; i < prestamosLector.size(); i++) {
				if (i > 0) jsonResponse.append(",");
				jsonResponse.append(prestamosLector.get(i));
			}
			jsonResponse.append("]}");

			response.getWriter().write(jsonResponse.toString());
			
		} catch (Exception e) {
			System.err.println("Error en PrestamosLectorServlet: " + e.getMessage());
			e.printStackTrace();
			response.getWriter().write("{\"success\":false,\"message\":\"Error al cargar préstamos\",\"prestamos\":[]}");
		}
	}
}

