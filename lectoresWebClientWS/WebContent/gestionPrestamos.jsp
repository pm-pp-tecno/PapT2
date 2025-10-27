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
	
	// Obtener datos para los select de los modales
	DtLector[] lectores = (DtLector[]) request.getAttribute("lectores");
	DtBibliotecario[] bibliotecarios = (DtBibliotecario[]) request.getAttribute("bibliotecarios");
	DtLibro[] libros = (DtLibro[]) request.getAttribute("libros");
	DtArticulo[] articulos = (DtArticulo[]) request.getAttribute("articulos");
    
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
<link rel="stylesheet" 
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	integrity="sha512-1ycn6IcaQQ40/MKBW2W4Rhis/DbILU74C1vSrLJxCq57o941Ym01SwNsOMqvEBFlcgUa6xLiPY/NS5R+E6ztJQ=="
	crossorigin="anonymous">

<style>
	/* Estilos para el dropdown de reportes */
	.nav-item.dropdown:hover .dropdown-menu {
		display: block;
		margin-top: 0;
	}
	
	.dropdown-menu {
		border: none;
		box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
		border-radius: 8px;
		padding: 0.5rem 0;
	}
	
	.dropdown-item {
		padding: 0.75rem 1.5rem;
		transition: background-color 0.2s ease;
	}
	
	.dropdown-item:hover {
		background-color: #f8f9fa;
		color: #495057;
	}
	
	.dropdown-item i {
		margin-right: 0.5rem;
		width: 16px;
		text-align: center;
	}
	
	/* Estilos para iconos del menú principal */
	.nav-link i {
		margin-right: 0.5rem;
		width: 16px;
		text-align: center;
	}
	
	.nav-link:hover i {
		transform: scale(1.1);
		transition: transform 0.2s ease;
	}
</style>

<title>Gestion Prestamos</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="index"><i class="fas fa-book-open text-primary mr-2"></i> Lectores UY</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarSupportedContent"
		aria-controls="navbarSupportedContent" aria-expanded="false"
		aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarSupportedContent">
		<ul class="navbar-nav mr-auto">
			<% if ("BIBLIOTECARIO".equals(tipoUsuario)) { %>
				<!-- Menú completo para BIBLIOTECARIOS -->
				<li class="nav-item">
					<a class="nav-link" href="gestionUsuarios">
						<i class="fas fa-users"></i> Gestion Usuarios
					</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="gestionMateriales">
						<i class="fas fa-book"></i> Gestion Materiales
					</a>
				</li>
				<li class="nav-item active">
					<a class="nav-link" href="gestionPrestamos">
						<i class="fas fa-book-reader"></i> Gestion Prestamos
					</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link" href="reportes" id="reportesDropdown" role="button">
						<i class="fas fa-chart-bar"></i> Reportes
					</a>
					<div class="dropdown-menu" aria-labelledby="reportesDropdown">
						<a class="dropdown-item" href="historialPrestamos">
							<i class="fas fa-history"></i> Historial de Préstamos
						</a>
						<a class="dropdown-item" href="prestamosZona">
							<i class="fas fa-map-marker-alt"></i> Préstamos por Zona
						</a>
						<a class="dropdown-item" href="prestamosPendientes">
							<i class="fas fa-clock"></i> Préstamos Pendientes
						</a>
					</div>
				</li>
			<% } else { %>
				<!-- Menú limitado para LECTORES -->
				<li class="nav-item active">
					<a class="nav-link" href="gestionPrestamos">Mis Préstamos</a>
				</li>
			<% } %>
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
						<div>
							<%
								// Obtener el estado del lector desde la base de datos
								String emailLector = (String) session.getAttribute("email");
								boolean lectorSuspendido = false;
								if (lectores != null && emailLector != null) {
									for (DtLector lector : lectores) {
										if (lector.getEmail().equals(emailLector)) {
											lectorSuspendido = (lector.getEstado() != null && 
												lector.getEstado().toString().equals("SUSPENDIDO"));
											break;
										}
									}
								}
							%>
							<button type="button" 
							        class="btn btn-success btn-sm solicitar-prestamo-btn" 
							        data-suspendido="<%= lectorSuspendido %>">
								<i class="fas fa-plus"></i> Solicitar Préstamo
							</button>
						</div>
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
					<h5 class="modal-title" id="agregarPrestamoModalLabel">
						<% if ("LECTOR".equals(tipoUsuario)) { %>
							Solicitar Préstamo
						<% } else { %>
							Nuevo Préstamo
						<% } %>
					</h5>
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
									<% if ("LECTOR".equals(tipoUsuario)) { %>
									<%
										String emailLector = (String) session.getAttribute("email");
										String nombreCompleto = usuario;
										if (lectores != null) {
											for (DtLector lector : lectores) {
												if (lector.getEmail().equals(emailLector)) {
													nombreCompleto = lector.getNombre() + " (" + lector.getEmail() + ")";
													break;
												}
											}
										}
									%>
										<input type="text" class="form-control" value="<%= nombreCompleto %>" disabled>
										<input type="hidden" id="agregar-lector" name="emailLector" value="<%= emailLector %>">
										<small class="form-text text-muted">Solicitando préstamo como: <%= usuario %></small>
									<% } else { %>
										<select class="form-control" id="agregar-lector" name="emailLector" required>
											<option value="">Seleccione un lector</option>
											<%
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
									<% } %>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="agregar-bibliotecario">Bibliotecario *</label>
									<select class="form-control" id="agregar-bibliotecario" name="numeroBibliotecario" required>
										<option value="">Seleccione un bibliotecario</option>
										<%
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
									<% if ("LECTOR".equals(tipoUsuario)) { %>
										<small class="form-text text-muted">Será procesado por un bibliotecario</small>
									<% } else { %>
										<small class="form-text text-muted">Seleccione el bibliotecario que procesa el préstamo</small>
									<% } %>
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
						<button type="submit" class="btn btn-success">
							<% if ("LECTOR".equals(tipoUsuario)) { %>
								Solicitar Préstamo
							<% } else { %>
								Agregar Préstamo
							<% } %>
						</button>
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
						
						<!-- Sección de Estado del Préstamo (diferenciada) -->
						<div class="card border-primary mb-3">
							<div class="card-header bg-primary text-white">
								<h6 class="mb-0"><i class="fas fa-info-circle"></i> Estado del Préstamo</h6>
							</div>
							<div class="card-body">
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<label for="edit-estado"><strong>Estado del Préstamo *</strong></label>
											<select class="form-control form-control-lg" id="edit-estado" name="estado" required>
												<option value="PENDIENTE">Pendiente</option>
												<option value="EN_CURSO">En Curso</option>
												<option value="DEVUELTO">Devuelto</option>
											</select>
										</div>
									</div>
								</div>
							</div>
						</div>
						
						<!-- Información del Préstamo -->
						<div class="alert alert-light border">
							<strong>ID Préstamo:</strong> <span id="edit-info-id"></span><br>
							<span id="edit-info-prestamo"></span>
						</div>
						
						<!-- Campos editables -->
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="edit-lector">Lector *</label>
								<select class="form-control" id="edit-lector" name="emailLector" required>
									<option value="">Seleccione un lector</option>
									<%
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
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="edit-bibliotecario">Bibliotecario *</label>
								<select class="form-control" id="edit-bibliotecario" name="numeroBibliotecario" required>
									<option value="">Seleccione un bibliotecario</option>
									<%
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
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="edit-material">Material</label>
								<select class="form-control" id="edit-material" name="idMaterial">
									<option value="">-- No disponible --</option>
									<%
										if (libros != null) {
												for (DtLibro libro : libros) {
													String titulo = libro.getTitulo() != null ? libro.getTitulo() : "Sin título";
													int paginas = libro.getCantidadPaginas();
													String descripcion = titulo + " (" + paginas + " páginas) - LIBRO";
													descripcion = descripcion.replace("\"", "\\\"").replace("\n", " ").replace("\r", " ");
										%>
										<option value="LIBRO_<%= libro.getId() %>"><%= descripcion %></option>
										<%
												}
											}
											
											if (articulos != null) {
												for (DtArticulo articulo : articulos) {
													String descripcion = articulo.getDescripcion() != null ? articulo.getDescripcion() : "Sin descripción";
													descripcion = descripcion.replace("\"", "\\\"").replace("\n", " ").replace("\r", " ");
										%>
										<option value="ARTICULO_<%= articulo.getId() %>"><%= descripcion %> - ARTÍCULO</option>
										<%
												}
											}
										%>
									</select>
									<small class="form-text text-muted">Material actual</small>
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="edit-fechaSolicitud">Fecha de Solicitud</label>
									<input type="date" class="form-control" id="edit-fechaSolicitud" name="fechaSolicitud">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="edit-fechaDevolucion">Fecha de Devolución Estimada *</label>
									<input type="date" class="form-control" id="edit-fechaDevolucion" name="fechaDevolucion" required>
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
		// Validar antes de abrir modal de solicitar préstamo para lectores
		$(document).on('click', '.solicitar-prestamo-btn', function(e) {
			e.preventDefault();
			var suspendido = $(this).data('suspendido') === 'true' || $(this).data('suspendido') === true;
			if (suspendido) {
				// Mostrar mensaje de error
				var errorDiv = $('<div class="alert alert-danger alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); z-index: 9999; max-width: 90%; min-width: 300px;">' +
					'<strong><i class="fas fa-exclamation-triangle"></i> Error!</strong><br>' +
					'<p class="mb-0">Tu cuenta se encuentra <strong>SUSPENDIDA</strong>. No puedes solicitar nuevos préstamos hasta que tu cuenta sea reactivada.</p>' +
					'<br><button type="button" class="btn btn-sm btn-danger" onclick="$(this).closest(\'.alert\').alert(\'close\')">Entendido</button>' +
					'</div>');
				
				$('body').append(errorDiv);
				
				// Auto-ocultar después de 8 segundos
				setTimeout(function() {
					errorDiv.alert('close');
				}, 8000);
			} else {
				// Si no está suspendido, abrir el modal manualmente
				$('#agregarPrestamoModal').modal('show');
			}
		});

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
			var fechaSolicitud = row.find('td:eq(4)').text().trim(); // Columna de fecha de solicitud (índice 4)
			var fechaDevolucion = row.find('td:eq(5)').text().trim(); // Columna de fecha de devolución (índice 5)
			var lectorTd = row.find('td:eq(1)').text().trim();
			var materialTd = row.find('td:eq(2)').text().trim();
			var bibliotecarioTd = row.find('td:eq(3)').text().trim();
			
			console.log('Estado encontrado:', estadoTexto);
			console.log('Fecha de solicitud encontrada:', fechaSolicitud);
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
			$('#edit-info-id').text(id);
			
			// Convertir la fecha de solicitud al formato correcto para el input date
			if (fechaSolicitud && fechaSolicitud !== 'N/A' && fechaSolicitud !== '') {
				var fechaSolicitudFormateada = convertirFechaParaInput(fechaSolicitud);
				$('#edit-fechaSolicitud').val(fechaSolicitudFormateada);
				console.log('Fecha de solicitud convertida:', fechaSolicitudFormateada);
			} else {
				$('#edit-fechaSolicitud').val('');
			}
			
			// Convertir la fecha de devolución al formato correcto para el input date
			if (fechaDevolucion && fechaDevolucion !== 'N/A' && fechaDevolucion !== '') {
				var fechaFormateada = convertirFechaParaInput(fechaDevolucion);
				$('#edit-fechaDevolucion').val(fechaFormateada);
				console.log('Fecha convertida para input:', fechaFormateada);
			} else {
				$('#edit-fechaDevolucion').val('');
			}
			
			// Llenar información del material
			// Extraer el ID del material desde la fila. Necesitamos buscar en la fila del HTML
			// Primero intentar por el tipo de material (LIBRO o ARTICULO) y luego buscar el ID
			var materialTexto = materialTd.split('\n')[0].trim();
			console.log('Material encontrado:', materialTexto);
			
			// Llenar información de lector (extraer email del texto)
			// El formato es: "Nombre (email)"
			var lectorTexto = lectorTd.split('\n')[0].trim();
			$('#edit-lector').val(function() {
				var emailMatch = lectorTexto.match(/\(([^)]+)\)/);
				return emailMatch ? emailMatch[1] : '';
			});
			
			// Llenar información de bibliotecario (extraer número de empleado)
			// El formato es: "Nombre (numero)"
			var biblTexto = bibliotecarioTd.split('\n')[0].trim();
			$('#edit-bibliotecario').val(function() {
				var numMatch = biblTexto.match(/\(([^)]+)\)/);
				return numMatch ? numMatch[1] : '';
			});
			
			// Mostrar información del préstamo (datos actuales de solo lectura)
			$('#edit-info-prestamo').html(
				'<strong>Lector actual:</strong> ' + lectorTd.split('\n')[0] + '<br>' +
				'<strong>Material actual:</strong> ' + materialTd.split('\n')[0] + '<br>' +
				'<strong>Bibliotecario actual:</strong> ' + bibliotecarioTd.split('\n')[0]
			);
			
			console.log('ID asignado al campo oculto:', $('#edit-id').val());
			console.log('Estado asignado al select:', $('#edit-estado').val());
			console.log('Lector seleccionado:', $('#edit-lector').val());
			console.log('Bibliotecario seleccionado:', $('#edit-bibliotecario').val());
			console.log('Fecha solicitud asignada:', $('#edit-fechaSolicitud').val());
			console.log('Fecha devolución asignada:', $('#edit-fechaDevolucion').val());
			
			// Intentar seleccionar el material basándose en el texto
			// El material se muestra como un texto descriptivo, necesitamos buscar en las opciones del select
			// La mejor forma es buscar por el texto que contiene
			var materialSelect = $('#edit-material');
			materialSelect.find('option').each(function() {
				if ($(this).text().includes(materialTexto.split('(')[0].trim())) {
					materialSelect.val($(this).val());
					console.log('Material seleccionado:', $(this).val());
					return false; // salir del loop
				}
			});
			
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
			var emailLector = $('#edit-lector').val();
			var numeroBibliotecario = $('#edit-bibliotecario').val();
			var fechaDevolucion = $('#edit-fechaDevolucion').val();
			var fechaSolicitud = $('#edit-fechaSolicitud').val();
			var idMaterialRaw = $('#edit-material').val();
			
			// Extraer el ID del material del formato "TIPO_ID"
			var idMaterial = '';
			var tipoMaterial = '';
			if (idMaterialRaw && idMaterialRaw !== '') {
				if (idMaterialRaw.startsWith('LIBRO_')) {
					tipoMaterial = 'LIBRO';
					idMaterial = idMaterialRaw.replace('LIBRO_', '');
				} else if (idMaterialRaw.startsWith('ARTICULO_')) {
					tipoMaterial = 'ARTICULO';
					idMaterial = idMaterialRaw.replace('ARTICULO_', '');
				} else {
					idMaterial = idMaterialRaw;
				}
			}
			
			console.log('Datos obtenidos:');
			console.log('- ID Préstamo:', idPrestamo);
			console.log('- Estado:', estado);
			console.log('- Lector:', emailLector);
			console.log('- Bibliotecario:', numeroBibliotecario);
			console.log('- Fecha Devolución:', fechaDevolucion);
			console.log('- Fecha Solicitud:', fechaSolicitud);
			console.log('- Tipo Material:', tipoMaterial);
			console.log('- ID Material:', idMaterial);
			
			// Crear objeto JSON
			var datos = {
				id_prestamo: idPrestamo,
				estado: estado,
				emailLector: emailLector,
				numeroBibliotecario: numeroBibliotecario,
				fechaDevolucion: fechaDevolucion,
				fechaSolicitud: fechaSolicitud,
				tipoMaterial: tipoMaterial,
				idMaterial: idMaterial
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
			var fechaSolicitud = $('#edit-fechaSolicitud').val();
			var lectorTexto = $('#edit-lector option:selected').text();
			var bibliotecarioTexto = $('#edit-bibliotecario option:selected').text();
			var materialTexto = $('#edit-material option:selected').text();
			
			// Buscar la fila en la tabla
			var fila = $('tr[data-id="' + id + '"]');
			if (fila.length > 0) {
				// Actualizar lector
				fila.find('td:eq(1)').html(lectorTexto);
				
				// Actualizar material
				fila.find('td:eq(2)').html(materialTexto);
				
				// Actualizar bibliotecario
				fila.find('td:eq(3)').html(bibliotecarioTexto);
				
				// Actualizar fecha solicitud
				if (fechaSolicitud) {
					fila.find('td:eq(4)').text(formatearFecha(fechaSolicitud));
				}
				
				// Actualizar fecha de devolución
				if (fechaDevolucion) {
					fila.find('td:eq(5)').text(formatearFecha(fechaDevolucion));
				}
				
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