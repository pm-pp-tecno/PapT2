<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="publicadores.DtPrestamo" %>
<%@ page import="publicadores.DtLibro" %>
<%@ page import="publicadores.DtArticulo" %>
<%@ page import="publicadores.DtLector" %>
<%@ page import="publicadores.DtBibliotecario" %>

<%
	// Obtener datos del servlet
	List<DtPrestamo> prestamos = (List<DtPrestamo>) request.getAttribute("prestamos");
	String errorMessage = (String) request.getAttribute("errorMessage");
	SimpleDateFormat dateFormat = (SimpleDateFormat) request.getAttribute("dateFormat");
	
	// Obtener datos del usuario de la sesión
	String usuario = (String) session.getAttribute("usuario");
	String tipoUsuario = (String) session.getAttribute("tipoUsuario");
    
	// Si no hay datos, inicializar lista vacía
	if (prestamos == null) {
		prestamos = new java.util.ArrayList();
	}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">

<title>Gestion Prestamos</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="index">Lectores UY</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="gestionUsuarios">Gestion Usuarios</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="gestionMateriales">Gestion Materiales</a>
				</li>
				<li class="nav-item active">
					<a class="nav-link" href="gestionPrestamos">Gestion Prestamos</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="consultas">Consultas</a>
				</li>
		</ul>
		<ul class="navbar-nav ml-auto">
			<li class="nav-item dropdown">
				<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
					<%= usuario %> (<%= tipoUsuario %>)
				</a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<a class="dropdown-item" href="logout">Cerrar Sesión</a>
				</div>
			</li>
		</ul>
	</div>
	</nav>

	<div class="container mt-4">
		<div class="row">
			<div class="col-12">
				<% if ("LECTOR".equals(tipoUsuario)) { %>
					<h2 class="mb-4">Mis Préstamos</h2>
				<% } else { %>
					<h2 class="mb-4">Gestión de Préstamos</h2>
				<% } %>
				
				<% if (errorMessage != null) { %>
					<div class="alert alert-danger" role="alert">
						<%= errorMessage %>
					</div>
				<% } %>
				
				<div class="card">
					<div class="card-header d-flex justify-content-between align-items-center">
						<% if ("LECTOR".equals(tipoUsuario)) { %>
							<h5 class="mb-0">Mis Préstamos</h5>
						<% } else { %>
							<h5 class="mb-0">Lista de Préstamos</h5>
							<div>
								<button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#agregarPrestamoModal">
									<i class="fas fa-plus"></i> Nuevo Préstamo
								</button>
							</div>
						<% } %>
					</div>
					<div class="card-body">
						<% if ("LECTOR".equals(tipoUsuario)) { %>
							<!-- Vista para LECTOR: Préstamos agrupados por estado -->
							<% if (prestamos.isEmpty()) { %>
								<div class="alert alert-info" role="alert">
									No tienes préstamos registrados.
								</div>
							<% } else { %>
								<!-- Préstamos Pendientes -->
								<div class="card mb-3">
									<div class="card-header bg-warning text-dark">
										<h6 class="mb-0">
											<i class="fas fa-clock"></i> Préstamos Pendientes 
											<span class="badge badge-dark">
												<% 
													int contadorPendientes = 0;
													for (DtPrestamo p : prestamos) {
														if ("PENDIENTE".equals(p.getEstado().name())) {
															contadorPendientes++;
														}
													}
												%>
												<%= contadorPendientes %>
											</span>
										</h6>
									</div>
									<div class="card-body">
										<% 
											List<DtPrestamo> pendientes = new java.util.ArrayList<DtPrestamo>();
											for (DtPrestamo p : prestamos) {
												if ("PENDIENTE".equals(p.getEstado().name())) {
													pendientes.add(p);
												}
											}
										%>
										<% if (pendientes.isEmpty()) { %>
											<p class="text-muted mb-0">No tienes préstamos pendientes.</p>
										<% } else { %>
											<div class="table-responsive">
												<table class="table table-sm">
													<thead>
														<tr>
															<th>Material</th>
															<th>Fecha Solicitud</th>
															<th>Fecha Devolución</th>
														</tr>
													</thead>
													<tbody>
														<% for (DtPrestamo prestamo : pendientes) { %>
															<tr>
																<td>
																	<% if (prestamo.getMaterial() instanceof publicadores.DtLibro) { %>
																		<% publicadores.DtLibro libro = (publicadores.DtLibro) prestamo.getMaterial(); %>
																		<strong><%= libro.getTitulo() %></strong><br>
																		<small class="text-muted">Libro - <%= libro.getCantidadPaginas() %> páginas</small>
																	<% } else if (prestamo.getMaterial() instanceof publicadores.DtArticulo) { %>
																		<% publicadores.DtArticulo articulo = (publicadores.DtArticulo) prestamo.getMaterial(); %>
																		<strong><%= articulo.getDescripcion() %></strong><br>
																		<small class="text-muted">Artículo - <%= articulo.getPeso() %> kg</small>
																	<% } %>
																</td>
																<td>
																	<%
																		Object fechaSolicitudObj = prestamo.getFechaSolicitud();
																		String fechaSolicitudStr = "N/A";
																		if (fechaSolicitudObj != null && dateFormat != null) {
																			if (fechaSolicitudObj instanceof java.util.Date) {
																				fechaSolicitudStr = dateFormat.format((java.util.Date) fechaSolicitudObj);
																			} else if (fechaSolicitudObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
																				fechaSolicitudStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaSolicitudObj).toGregorianCalendar().getTime());
																			} else {
																				fechaSolicitudStr = fechaSolicitudObj.toString();
																			}
																		}
																	%>
																	<%= fechaSolicitudStr %>
																</td>
																<td>
																	<%
																		Object fechaDevolucionObj = prestamo.getFechaDevolucionEstimada();
																		String fechaDevolucionStr = "N/A";
																		if (fechaDevolucionObj != null && dateFormat != null) {
																			if (fechaDevolucionObj instanceof java.util.Date) {
																				fechaDevolucionStr = dateFormat.format((java.util.Date) fechaDevolucionObj);
																			} else if (fechaDevolucionObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
																				fechaDevolucionStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaDevolucionObj).toGregorianCalendar().getTime());
																			} else {
																				fechaDevolucionStr = fechaDevolucionObj.toString();
																			}
																		}
																	%>
																	<%= fechaDevolucionStr %>
																</td>
															</tr>
														<% } %>
													</tbody>
												</table>
											</div>
										<% } %>
									</div>
								</div>

								<!-- Préstamos En Curso -->
								<div class="card mb-3">
									<div class="card-header bg-success text-white">
										<h6 class="mb-0">
											<i class="fas fa-book-open"></i> Préstamos En Curso 
											<span class="badge badge-light">
												<% 
													int contadorEnCurso = 0;
													for (DtPrestamo p : prestamos) {
														if ("EN_CURSO".equals(p.getEstado().name())) {
															contadorEnCurso++;
														}
													}
												%>
												<%= contadorEnCurso %>
											</span>
										</h6>
									</div>
									<div class="card-body">
										<% 
											List<DtPrestamo> enCurso = new java.util.ArrayList<DtPrestamo>();
											for (DtPrestamo p : prestamos) {
												if ("EN_CURSO".equals(p.getEstado().name())) {
													enCurso.add(p);
												}
											}
										%>
										<% if (enCurso.isEmpty()) { %>
											<p class="text-muted mb-0">No tienes préstamos en curso.</p>
										<% } else { %>
											<div class="table-responsive">
												<table class="table table-sm">
													<thead>
														<tr>
															<th>Material</th>
															<th>Fecha Solicitud</th>
															<th>Fecha Devolución</th>
														</tr>
													</thead>
													<tbody>
														<% for (DtPrestamo prestamo : enCurso) { %>
															<tr>
																<td>
																	<% if (prestamo.getMaterial() instanceof publicadores.DtLibro) { %>
																		<% publicadores.DtLibro libro = (publicadores.DtLibro) prestamo.getMaterial(); %>
																		<strong><%= libro.getTitulo() %></strong><br>
																		<small class="text-muted">Libro - <%= libro.getCantidadPaginas() %> páginas</small>
																	<% } else if (prestamo.getMaterial() instanceof publicadores.DtArticulo) { %>
																		<% publicadores.DtArticulo articulo = (publicadores.DtArticulo) prestamo.getMaterial(); %>
																		<strong><%= articulo.getDescripcion() %></strong><br>
																		<small class="text-muted">Artículo - <%= articulo.getPeso() %> kg</small>
																	<% } %>
																</td>
																<td>
																	<%
																		Object fechaSolicitudObj = prestamo.getFechaSolicitud();
																		String fechaSolicitudStr = "N/A";
																		if (fechaSolicitudObj != null && dateFormat != null) {
																			if (fechaSolicitudObj instanceof java.util.Date) {
																				fechaSolicitudStr = dateFormat.format((java.util.Date) fechaSolicitudObj);
																			} else if (fechaSolicitudObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
																				fechaSolicitudStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaSolicitudObj).toGregorianCalendar().getTime());
																			} else {
																				fechaSolicitudStr = fechaSolicitudObj.toString();
																			}
																		}
																	%>
																	<%= fechaSolicitudStr %>
																</td>
																<td>
																	<%
																		Object fechaDevolucionObj = prestamo.getFechaDevolucionEstimada();
																		String fechaDevolucionStr = "N/A";
																		if (fechaDevolucionObj != null && dateFormat != null) {
																			if (fechaDevolucionObj instanceof java.util.Date) {
																				fechaDevolucionStr = dateFormat.format((java.util.Date) fechaDevolucionObj);
																			} else if (fechaDevolucionObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
																				fechaDevolucionStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaDevolucionObj).toGregorianCalendar().getTime());
																			} else {
																				fechaDevolucionStr = fechaDevolucionObj.toString();
																			}
																		}
																	%>
																	<%= fechaDevolucionStr %>
																</td>
															</tr>
														<% } %>
													</tbody>
												</table>
											</div>
										<% } %>
									</div>
								</div>

								<!-- Préstamos Devueltos -->
								<div class="card mb-3">
									<div class="card-header bg-secondary text-white">
										<h6 class="mb-0">
											<i class="fas fa-check-circle"></i> Préstamos Devueltos 
											<span class="badge badge-light">
												<% 
													int contadorDevueltos = 0;
													for (DtPrestamo p : prestamos) {
														if ("DEVUELTO".equals(p.getEstado().name())) {
															contadorDevueltos++;
														}
													}
												%>
												<%= contadorDevueltos %>
											</span>
										</h6>
									</div>
									<div class="card-body">
										<% 
											List<DtPrestamo> devueltos = new java.util.ArrayList<DtPrestamo>();
											for (DtPrestamo p : prestamos) {
												if ("DEVUELTO".equals(p.getEstado().name())) {
													devueltos.add(p);
												}
											}
										%>
										<% if (devueltos.isEmpty()) { %>
											<p class="text-muted mb-0">No tienes préstamos devueltos.</p>
										<% } else { %>
											<div class="table-responsive">
												<table class="table table-sm">
													<thead>
														<tr>
															<th>Material</th>
															<th>Fecha Solicitud</th>
															<th>Fecha Devolución</th>
														</tr>
													</thead>
													<tbody>
														<% for (DtPrestamo prestamo : devueltos) { %>
															<tr>
																<td>
																	<% if (prestamo.getMaterial() instanceof publicadores.DtLibro) { %>
																		<% publicadores.DtLibro libro = (publicadores.DtLibro) prestamo.getMaterial(); %>
																		<strong><%= libro.getTitulo() %></strong><br>
																		<small class="text-muted">Libro - <%= libro.getCantidadPaginas() %> páginas</small>
																	<% } else if (prestamo.getMaterial() instanceof publicadores.DtArticulo) { %>
																		<% publicadores.DtArticulo articulo = (publicadores.DtArticulo) prestamo.getMaterial(); %>
																		<strong><%= articulo.getDescripcion() %></strong><br>
																		<small class="text-muted">Artículo - <%= articulo.getPeso() %> kg</small>
																	<% } %>
																</td>
																<td>
																	<%
																		Object fechaSolicitudObj = prestamo.getFechaSolicitud();
																		String fechaSolicitudStr = "N/A";
																		if (fechaSolicitudObj != null && dateFormat != null) {
																			if (fechaSolicitudObj instanceof java.util.Date) {
																				fechaSolicitudStr = dateFormat.format((java.util.Date) fechaSolicitudObj);
																			} else if (fechaSolicitudObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
																				fechaSolicitudStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaSolicitudObj).toGregorianCalendar().getTime());
																			} else {
																				fechaSolicitudStr = fechaSolicitudObj.toString();
																			}
																		}
																	%>
																	<%= fechaSolicitudStr %>
																</td>
																<td>
																	<%
																		Object fechaDevolucionObj = prestamo.getFechaDevolucionEstimada();
																		String fechaDevolucionStr = "N/A";
																		if (fechaDevolucionObj != null && dateFormat != null) {
																			if (fechaDevolucionObj instanceof java.util.Date) {
																				fechaDevolucionStr = dateFormat.format((java.util.Date) fechaDevolucionObj);
																			} else if (fechaDevolucionObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
																				fechaDevolucionStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaDevolucionObj).toGregorianCalendar().getTime());
																			} else {
																				fechaDevolucionStr = fechaDevolucionObj.toString();
																			}
																		}
																	%>
																	<%= fechaDevolucionStr %>
																</td>
															</tr>
														<% } %>
													</tbody>
												</table>
											</div>
										<% } %>
									</div>
								</div>
							<% } %>
						<% } else { %>
							<!-- Vista para BIBLIOTECARIO: Tabla completa -->
							<% if (prestamos.isEmpty()) { %>
								<div class="alert alert-info" role="alert">
									No hay préstamos registrados en el sistema.
								</div>
							<% } else { %>
							<div class="table-responsive">
								<table class="table table-striped table-hover">
									<thead class="thead-dark">
										<tr>
											<th>ID</th>
											<th>Lector</th>
											<th>Material</th>
											<th>Bibliotecario</th>
											<th>Fecha Solicitud</th>
											<th>Fecha Devolución</th>
											<th>Estado</th>
											<th>Acciones</th>
										</tr>
									</thead>
									<tbody>
										<% for (DtPrestamo prestamo : prestamos) { %>
											<tr data-id="<%= prestamo.getId() %>">
												<td><%= prestamo.getId() %></td>
												<td><%= prestamo.getLector().getNombre() %> (<%= prestamo.getLector().getEmail() %>)</td>
												<td>
													<% if (prestamo.getMaterial() instanceof publicadores.DtLibro) { %>
														<% publicadores.DtLibro libro = (publicadores.DtLibro) prestamo.getMaterial(); %>
														<strong><%= libro.getTitulo() %></strong><br>
														<small class="text-muted">Libro - <%= libro.getCantidadPaginas() %> páginas</small>
													<% } else if (prestamo.getMaterial() instanceof publicadores.DtArticulo) { %>
														<% publicadores.DtArticulo articulo = (publicadores.DtArticulo) prestamo.getMaterial(); %>
														<strong><%= articulo.getDescripcion() %></strong><br>
														<small class="text-muted">Artículo - <%= articulo.getPeso() %> kg</small>
													<% } else { %>
														<%= prestamo.getMaterial().getId() %>
													<% } %>
												</td>
												<td><%= prestamo.getBibliotecario().getNombre() %> (<%= prestamo.getBibliotecario().getNumeroEmpleado() %>)</td>
												<% 
													Object fechaSolicitudObj = prestamo.getFechaSolicitud();
													String fechaSolicitudStr = "N/A";
													if (fechaSolicitudObj != null && dateFormat != null) {
														if (fechaSolicitudObj instanceof java.util.Date) {
															fechaSolicitudStr = dateFormat.format((java.util.Date) fechaSolicitudObj);
														} else if (fechaSolicitudObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
															fechaSolicitudStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaSolicitudObj).toGregorianCalendar().getTime());
														} else {
															fechaSolicitudStr = fechaSolicitudObj.toString();
														}
													}
												%>
												<td><%= fechaSolicitudStr %></td>
												<% 
													Object fechaDevolucionObj = prestamo.getFechaDevolucionEstimada();
													String fechaDevolucionStr = "N/A";
													if (fechaDevolucionObj != null && dateFormat != null) {
														if (fechaDevolucionObj instanceof java.util.Date) {
															fechaDevolucionStr = dateFormat.format((java.util.Date) fechaDevolucionObj);
														} else if (fechaDevolucionObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
															fechaDevolucionStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaDevolucionObj).toGregorianCalendar().getTime());
														} else {
															fechaDevolucionStr = fechaDevolucionObj.toString();
														}
													}
												%>
												<td><%= fechaDevolucionStr %></td>
												<td>
													<%
														String estado = prestamo.getEstado().toString();
														String badgeClass = "badge-secondary";
														String estadoTexto = "";
														
														if ("PENDIENTE".equals(estado)) {
															badgeClass = "badge-warning";
															estadoTexto = "Pendiente";
														} else if ("EN_CURSO".equals(estado)) {
															badgeClass = "badge-primary";
															estadoTexto = "En Curso";
														} else if ("DEVUELTO".equals(estado)) {
															badgeClass = "badge-success";
															estadoTexto = "Devuelto";
														} else {
															estadoTexto = estado;
														}
													%>
													<span class="badge <%= badgeClass %>"><%= estadoTexto %></span>
												</td>
												<td>
													<button type="button" class="btn btn-sm btn-primary" onclick="editarPrestamo('<%= prestamo.getId() %>')">
														Editar
													</button>
												</td>
											</tr>
										<% } %>
									</tbody>
								</table>
							</div>
							<div class="mt-3">
								<small class="text-muted">Total de préstamos: <%= prestamos.size() %></small>
							</div>
							<% } %>
						<% } %>
					</div>
				</div>
			</div>
		</div>
	</div>



	<!-- Modal para Agregar Préstamo -->
	<div class="modal fade" id="agregarPrestamoModal" tabindex="-1" role="dialog" aria-labelledby="agregarPrestamoModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="agregarPrestamoModalLabel">Nuevo Préstamo</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="agregarPrestamoForm">
					<div class="modal-body">
						<input type="hidden" id="agregar-idMaterial" name="idMaterial" value="">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="agregar-tipoMaterial">Tipo de Material *</label>
									<select class="form-control" id="agregar-tipoMaterial" name="tipoMaterial" required>
										<option value="">Seleccione un tipo</option>
										<option value="LIBRO">Libro</option>
										<option value="ARTICULO">Artículo</option>
									</select>
									<small class="form-text text-muted">Seleccione el tipo de material</small>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="agregar-material">Material *</label>
									<select class="form-control" id="agregar-material" name="idMaterial" required disabled>
										<option value="">Primero seleccione el tipo</option>
									</select>
									<small class="form-text text-muted">Seleccione el material a prestar</small>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="agregar-lector">Lector *</label>
									<select class="form-control" id="agregar-lector" name="emailLector" required>
										<option value="">Seleccione un lector</option>
										<%
											DtLector[] lectores = (DtLector[]) request.getAttribute("lectores");
											if (lectores != null) {
												for (DtLector lector : lectores) {
													String nombreCompleto = lector.getNombre() + " (" + lector.getEmail() + ")";
													boolean suspendido = lector.getNombre().contains("(SUSPENDIDO)");
													String optionClass = suspendido ? "disabled" : "";
													String optionStyle = suspendido ? "color: #999;" : "";
										%>
										<option value="<%= lector.getEmail() %>" class="<%= optionClass %>" style="<%= optionStyle %>" <%= suspendido ? "disabled" : "" %>><%= nombreCompleto %></option>
										<%
												}
											}
										%>
									</select>
									<small class="form-text text-muted">Seleccione el lector que solicita el préstamo</small>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="agregar-bibliotecario">Bibliotecario *</label>
									<select class="form-control" id="agregar-bibliotecario" name="numeroBibliotecario" required>
										<option value="">Seleccione un bibliotecario</option>
										<%
											DtBibliotecario[] bibliotecarios = (DtBibliotecario[]) request.getAttribute("bibliotecarios");
											if (bibliotecarios != null) {
												for (DtBibliotecario bibliotecario : bibliotecarios) {
													String nombreCompleto = bibliotecario.getNombre() + " (" + bibliotecario.getNumeroEmpleado() + ")";
										%>
										<option value="<%= bibliotecario.getNumeroEmpleado() %>"><%= nombreCompleto %></option>
										<%
												}
											}
										%>
									</select>
									<small class="form-text text-muted">Seleccione el bibliotecario que procesa el préstamo</small>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="form-group">
									<label for="agregar-fechaDevolucion">Fecha de Devolución *</label>
									<input type="date" class="form-control" id="agregar-fechaDevolucion" name="fechaDevolucion" required>
									<small class="form-text text-muted">Fecha estimada de devolución</small>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-success">Agregar Préstamo</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Modal para Editar Préstamo -->
	<div class="modal fade" id="editarPrestamoModal" tabindex="-1" role="dialog" aria-labelledby="editarPrestamoModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="editarPrestamoModalLabel">Editar Préstamo</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form id="editarPrestamoForm">
					<div class="modal-body">
						<input type="hidden" id="edit-id" name="id_prestamo" value="">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="edit-estado">Estado del Préstamo *</label>
									<select class="form-control" id="edit-estado" name="estado" required>
										<option value="PENDIENTE">Pendiente</option>
										<option value="EN_CURSO">En Curso</option>
										<option value="DEVUELTO">Devuelto</option>
									</select>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="edit-fechaDevolucion">Fecha de Devolución</label>
									<input type="date" class="form-control" id="edit-fechaDevolucion" name="fechaDevolucion">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-12">
								<div class="alert alert-info">
									<strong>Información del Préstamo:</strong><br>
									<span id="edit-info-prestamo"></span>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
						<button type="submit" class="btn btn-primary">Actualizar Préstamo</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
		integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
		integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
		crossorigin="anonymous"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
		integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
		crossorigin="anonymous"></script>

	<script>
		// Manejar formulario de agregar préstamo
		$('#agregarPrestamoForm').on('submit', function(e) {
			e.preventDefault();
			console.log("*** FORMULARIO AGREGAR PRESTAMO ENVIADO ***");
			
			// Asegurar que el ID del material esté en el campo oculto
			var idMaterial = $('#agregar-material').val();
			$('#agregar-idMaterial').val(idMaterial);
			console.log("ID Material final:", idMaterial);
			
			// Crear objeto JSON con los datos del formulario
			var idMaterial = $('#agregar-idMaterial').val();
			var emailLector = $('#agregar-lector').val();
			var numeroBibliotecario = $('#agregar-bibliotecario').val();
			var fechaDevolucion = $('#agregar-fechaDevolucion').val();
			
			console.log("Valores capturados:");
			console.log("idMaterial:", idMaterial);
			console.log("emailLector:", emailLector);
			console.log("numeroBibliotecario:", numeroBibliotecario);
			console.log("fechaDevolucion:", fechaDevolucion);
			
			var jsonData = {
				idMaterial: idMaterial,
				emailLector: emailLector,
				numeroBibliotecario: numeroBibliotecario,
				fechaDevolucion: fechaDevolucion
			};
			console.log("JSON creado:", jsonData);
			
			console.log("Enviando petición a: agregarPrestamo");
			fetch('agregarPrestamo', {
				method: 'POST',
				headers: { 
					'Content-Type': 'application/json',
					'Accept': 'application/json' 
				},
				body: JSON.stringify(jsonData)
			}).then(function(res) {
				console.log("Respuesta recibida:", res);
				return res.json();
			}).then(function(data) {
				console.log("Datos de respuesta:", data);
				if (data && data.success) {
					$('#agregarPrestamoModal').modal('hide');
					$('#agregarPrestamoForm')[0].reset();
					mostrarMensajeExito(data.message || 'Préstamo agregado correctamente');
					setTimeout(function() {
						location.reload();
					}, 1500);
				} else {
					var errorMsg = (data && data.message) ? data.message : 'Error al agregar el préstamo';
					if (errorMsg.includes('conexión') || errorMsg.includes('base de datos')) {
						mostrarErrorConexion(errorMsg);
					} else {
						mostrarErrorNegocio(errorMsg);
					}
				}
			}).catch(function(err) {
				console.error('*** ERROR EN AGREGAR PRESTAMO ***:', err);
				mostrarErrorConexion('Error de comunicación con el servidor');
			});
		});

		// Función para editar préstamo
		function editarPrestamo(id) {
			console.log('=== Abriendo modal para editar préstamo ID:', id);
			
			// Buscar el préstamo en la tabla para obtener sus datos
			var row = $('button[onclick="editarPrestamo(\'' + id + '\')"]').closest('tr');
			var estadoTexto = row.find('span.badge').text().trim();
			var fechaDevolucion = row.find('td:eq(5)').text().trim(); // Columna de fecha de devolución
			
			console.log('Estado encontrado:', estadoTexto);
			console.log('Fecha de devolución encontrada:', fechaDevolucion);
			
			// Mapear el texto del estado a los valores del enum
			var estado = '';
			switch(estadoTexto) {
				case 'Pendiente':
					estado = 'PENDIENTE';
					break;
				case 'En Curso':
					estado = 'EN_CURSO';
					break;
				case 'Devuelto':
					estado = 'DEVUELTO';
					break;
				default:
					estado = estadoTexto.toUpperCase();
			}
			
			console.log('Estado mapeado:', estado);
			
			// Llenar el modal con los datos del préstamo
			$('#edit-id').val(id);
			$('#edit-estado').val(estado);
			
			// Convertir la fecha al formato correcto para el input date
			if (fechaDevolucion && fechaDevolucion !== '') {
				var fechaFormateada = convertirFechaParaInput(fechaDevolucion);
				$('#edit-fechaDevolucion').val(fechaFormateada);
				console.log('Fecha convertida para input:', fechaFormateada);
			} else {
				$('#edit-fechaDevolucion').val('');
			}
			
			console.log('ID asignado al campo oculto:', $('#edit-id').val());
			console.log('Estado asignado al select:', $('#edit-estado').val());
			console.log('Fecha asignada al input:', $('#edit-fechaDevolucion').val());
			
			// Mostrar información del préstamo
			var lector = row.find('td:eq(1)').text();
			var material = row.find('td:eq(2)').text();
			var bibliotecario = row.find('td:eq(3)').text();
			
			$('#edit-info-prestamo').html(
				'<strong>Lector:</strong> ' + lector + '<br>' +
				'<strong>Material:</strong> ' + material + '<br>' +
				'<strong>Bibliotecario:</strong> ' + bibliotecario
			);
			
			// Mostrar el modal
			$('#editarPrestamoModal').modal('show');
		}

		// Manejar formulario de editar préstamo
		$('#editarPrestamoForm').on('submit', function(e) {
			e.preventDefault();
			
			console.log('=== Enviando formulario de editar préstamo ===');
			
			// Obtener datos directamente de los campos
			var idPrestamo = $('#edit-id').val();
			var estado = $('#edit-estado').val();
			var fechaDevolucion = $('#edit-fechaDevolucion').val();
			
			console.log('Datos obtenidos:');
			console.log('- ID Préstamo:', idPrestamo);
			console.log('- Estado:', estado);
			console.log('- Fecha Devolución:', fechaDevolucion);
			
			// Crear objeto JSON
			var datos = {
				id_prestamo: idPrestamo,
				estado: estado,
				fechaDevolucion: fechaDevolucion
			};
			
			console.log('Datos JSON:', JSON.stringify(datos));
			
			fetch('/lectores-web/editarPrestamo', {
				method: 'POST',
				headers: { 
					'Accept': 'application/json',
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(datos)
			}).then(function(res) {
				console.log('Respuesta del servidor:', res.status, res.statusText);
				if (!res.ok) {
					throw new Error('HTTP ' + res.status + ': ' + res.statusText);
				}
				return res.json();
			}).then(function(data) {
				console.log('Datos recibidos:', data);
				if (data && data.success) {
					$('#editarPrestamoModal').modal('hide');
					mostrarMensajeExito(data.message || 'Préstamo actualizado correctamente');
					// Actualizar la tabla dinámicamente sin recargar la página
					actualizarFilaEnTabla();
				} else {
					var errorMsg = (data && data.message) ? data.message : 'Error al actualizar el préstamo';
					console.log('Error en respuesta:', errorMsg);
					if (errorMsg.includes('conexión') || errorMsg.includes('base de datos')) {
						mostrarErrorConexion(errorMsg);
					} else {
						mostrarErrorNegocio(errorMsg);
					}
				}
			}).catch(function(err) {
				console.error('Error en fetch:', err);
				mostrarErrorConexion('Error de comunicación con el servidor: ' + err.message);
			});
		});

	// Función para mostrar mensaje de éxito
	function mostrarMensajeExito(mensaje) {
		var alertDiv = $('<div class="alert alert-success alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); z-index: 9999; max-width: 90%; min-width: 300px;">' +
			'<strong>Éxito!</strong> ' + mensaje +
			'<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
			'<span aria-hidden="true">&times;</span></button></div>');
		
		$('body').append(alertDiv);
		
		setTimeout(function() {
			alertDiv.alert('close');
		}, 3000);
	}

		// Función para mostrar error de conexión con opción de reintentar
		function mostrarErrorConexion(mensaje) {
			var errorDiv = $('<div class="alert alert-warning alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); z-index: 9999; max-width: 90%; min-width: 300px;">' +
				'<strong>Error de Conexión!</strong><br>' + mensaje + '<br><br>' +
				'<button type="button" class="btn btn-sm btn-warning mr-2" onclick="reintentarOperacion()">Reintentar</button>' +
				'<button type="button" class="btn btn-sm btn-secondary" onclick="$(this).closest(\'.alert\').alert(\'close\')">Cerrar</button>' +
				'</div>');
			
			$('body').append(errorDiv);
			
			// Auto-ocultar después de 10 segundos
			setTimeout(function() {
				errorDiv.alert('close');
			}, 10000);
		}

		// Función para reintentar la operación
		function reintentarOperacion() {
			// Cerrar el modal de error
			$('.alert-warning').alert('close');
			
			// Mostrar mensaje de reintento
			mostrarMensajeExito('Reintentando operación...');
			
			// Esperar un momento y recargar la página
			setTimeout(function() {
				location.reload();
			}, 2000);
		}

		// Función para mostrar errores de negocio con Bootstrap
		function mostrarErrorNegocio(mensaje) {
			var errorDiv = $('<div class="alert alert-danger alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); z-index: 9999; max-width: 90%; min-width: 300px;">' +
				'<strong>Error de Negocio!</strong><br>' + mensaje + '<br><br>' +
				'<button type="button" class="btn btn-sm btn-danger" onclick="$(this).closest(\'.alert\').alert(\'close\')">Entendido</button>' +
				'</div>');
			
			$('body').append(errorDiv);
			
			// Auto-ocultar después de 8 segundos
			setTimeout(function() {
				errorDiv.alert('close');
			}, 8000);
		}

		// Función para actualizar la fila en la tabla después de editar
		function actualizarFilaEnTabla() {
			// Obtener los datos del formulario
			var id = $('#edit-id').val();
			var estado = $('#edit-estado').val();
			var fechaDevolucion = $('#edit-fechaDevolucion').val();
			
			// Buscar la fila en la tabla
			var fila = $('tr[data-id="' + id + '"]');
			if (fila.length > 0) {
				// Actualizar la columna de estado
				var estadoColumna = fila.find('td:eq(6)'); // Columna de estado
				var estadoBadge = '';
				switch(estado) {
					case 'PENDIENTE':
						estadoBadge = '<span class="badge badge-warning">Pendiente</span>';
						break;
					case 'EN_CURSO':
						estadoBadge = '<span class="badge badge-primary">En Curso</span>';
						break;
					case 'DEVUELTO':
						estadoBadge = '<span class="badge badge-success">Devuelto</span>';
						break;
					default:
						estadoBadge = '<span class="badge badge-secondary">' + estado + '</span>';
				}
				estadoColumna.html(estadoBadge);
				
				// Actualizar la fecha de devolución si se cambió
				if (fechaDevolucion) {
					var fechaColumna = fila.find('td:eq(5)'); // Columna de fecha de devolución
					fechaColumna.text(formatearFecha(fechaDevolucion));
				}
				
				console.log('Fila actualizada dinámicamente');
			} else {
				console.log('No se encontró la fila para actualizar, recargando página...');
				setTimeout(function() {
					location.reload();
				}, 1000);
			}
		}


		// Función auxiliar para formatear fechas
		function formatearFecha(fechaStr) {
			if (!fechaStr) return '';
			
			// Si ya está en formato YYYY-MM-DD, convertir directamente sin usar Date
			if (/^\d{4}-\d{2}-\d{2}$/.test(fechaStr)) {
				var partes = fechaStr.split('-');
				var año = partes[0];
				var mes = partes[1];
				var dia = partes[2];
				return dia + '/' + mes + '/' + año;
			}
			
			// Para otros formatos, usar Date pero con manejo de zona horaria
			try {
				var fecha = new Date(fechaStr + 'T00:00:00'); // Agregar hora para evitar problemas de zona horaria
				return fecha.toLocaleDateString('es-ES');
			} catch (e) {
				return fechaStr; // Devolver la fecha original si hay error
			}
		}

		// Función para convertir fecha de la tabla al formato del input date
		function convertirFechaParaInput(fechaStr) {
			if (!fechaStr) return '';
			
			// Si ya está en formato YYYY-MM-DD, devolverlo tal como está
			if (/^\d{4}-\d{2}-\d{2}$/.test(fechaStr)) {
				return fechaStr;
			}
			
			// Si está en formato DD/MM/YYYY, convertir a YYYY-MM-DD
			if (/^\d{2}\/\d{2}\/\d{4}$/.test(fechaStr)) {
				var partes = fechaStr.split('/');
				var dia = partes[0];
				var mes = partes[1];
				var año = partes[2];
				return año + '-' + mes + '-' + dia;
			}
			
			// Si está en formato de fecha local, convertir
			try {
				var fecha = new Date(fechaStr);
				if (!isNaN(fecha.getTime())) {
					var año = fecha.getFullYear();
					var mes = String(fecha.getMonth() + 1).padStart(2, '0');
					var dia = String(fecha.getDate()).padStart(2, '0');
					return año + '-' + mes + '-' + dia;
				}
			} catch (e) {
				console.log('Error convirtiendo fecha:', e);
			}
			
			return '';
		}

		// Datos para los selects (cargados desde el servidor)
		var libros = [];
		var articulos = [];
		
		// Cargar libros desde el servidor
		<%
			DtLibro[] libros = (DtLibro[]) request.getAttribute("libros");
			if (libros != null && libros.length > 0) {
				for (DtLibro libro : libros) {
					String titulo = libro.getTitulo() != null ? libro.getTitulo() : "Sin título";
					int paginas = libro.getCantidadPaginas();
					String descripcion = titulo + " (" + paginas + " páginas)";
					descripcion = descripcion.replace("\"", "\\\"").replace("\n", " ").replace("\r", " ");
		%>
		libros.push({id: <%= libro.getId() %>, descripcion: "<%= descripcion %>"});
		<%
				}
			}
		%>
		
		// Cargar artículos desde el servidor
		<%
			DtArticulo[] articulos = (DtArticulo[]) request.getAttribute("articulos");
			if (articulos != null && articulos.length > 0) {
				for (DtArticulo articulo : articulos) {
					String descripcion = articulo.getDescripcion() != null ? articulo.getDescripcion() : "Sin descripción";
					descripcion = descripcion.replace("\"", "\\\"").replace("\n", " ").replace("\r", " ");
		%>
		articulos.push({id: <%= articulo.getId() %>, descripcion: "<%= descripcion %>"});
		<%
				}
			}
		%>

		// Limpiar formulario al abrir el modal de nuevo préstamo
		$('#agregarPrestamoModal').on('show.bs.modal', function() {
			console.log('Limpiando formulario de nuevo préstamo...');
			// Limpiar todos los campos del formulario
			$('#agregarPrestamoForm')[0].reset();
			// Limpiar el campo oculto del ID del material
			$('#agregar-idMaterial').val('');
			// Limpiar y deshabilitar el select de materiales
			$('#agregar-material').empty().prop('disabled', true);
			$('#agregar-material').append('<option value="">Primero seleccione el tipo</option>');
			console.log('Formulario limpiado exitosamente');
		});

		// Manejar cambio de tipo de material
		$(document).on('change', '#agregar-tipoMaterial', function() {
			var tipo = $(this).val();
			var selectMaterial = $('#agregar-material');
			
			// Limpiar opciones
			selectMaterial.empty();
			selectMaterial.prop('disabled', true);
			
			if (tipo === '') {
				selectMaterial.append('<option value="">Primero seleccione el tipo</option>');
			} else {
				selectMaterial.append('<option value="">Seleccione un material</option>');
				
				if (tipo === 'LIBRO') {
					libros.forEach(function(libro) {
						selectMaterial.append('<option value="' + libro.id + '">' + libro.descripcion + '</option>');
					});
				} else if (tipo === 'ARTICULO') {
					articulos.forEach(function(articulo) {
						selectMaterial.append('<option value="' + articulo.id + '">' + articulo.descripcion + '</option>');
					});
				}
				
				selectMaterial.prop('disabled', false);
			}
		});

		// Manejar cambio del select de material
		$(document).on('change', '#agregar-material', function() {
			var idMaterial = $(this).val();
			$('#agregar-idMaterial').val(idMaterial);
			console.log('ID Material seleccionado:', idMaterial);
		});
	</script>
</body>
</html>