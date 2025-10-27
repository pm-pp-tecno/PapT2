<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="publicadores.DtPrestamo" %>
<%@ page import="publicadores.DtLibro" %>
<%@ page import="publicadores.DtArticulo" %>
<%@ page import="publicadores.DtLector" %>
<%
	// Obtener datos del servlet
	List<DtPrestamo> materialesConPrestamosPendientes = (List<DtPrestamo>) request.getAttribute("materialesConPrestamosPendientes");
	Map<Long, Integer> cantidadPrestamosPorMaterial = (Map<Long, Integer>) request.getAttribute("cantidadPrestamosPorMaterial");
	String errorMessage = (String) request.getAttribute("errorMessage");
	SimpleDateFormat dateFormat = (SimpleDateFormat) request.getAttribute("dateFormat");
	
	// Obtener datos del usuario de la sesión
	String usuario = (String) session.getAttribute("usuario");
	String tipoUsuario = (String) session.getAttribute("tipoUsuario");
	
	// Si no hay datos, inicializar lista vacía
	if (materialesConPrestamosPendientes == null) {
		materialesConPrestamosPendientes = new java.util.ArrayList();
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

<title>Préstamos Pendientes - Lectores UY</title>
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
					<li class="nav-item"><a class="nav-link" href="gestionUsuarios">
						<i class="fas fa-users"></i> Gestion Usuarios
					</a></li>
					<li class="nav-item"><a class="nav-link" href="gestionMateriales">
						<i class="fas fa-book"></i> Gestion Materiales
					</a></li>
					<li class="nav-item"><a class="nav-link" href="gestionPrestamos">
						<i class="fas fa-book-reader"></i> Gestion Prestamos
					</a></li>
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
					<li class="nav-item"><a class="nav-link" href="gestionPrestamos">Mis Préstamos</a></li>
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
				<h2 class="mb-4">
					<i class="fas fa-clock text-warning"></i> Préstamos Pendientes
				</h2>
				
				<% if (errorMessage != null) { %>
					<div class="alert alert-danger" role="alert">
						<i class="fas fa-exclamation-triangle"></i> <%= errorMessage %>
					</div>
				<% } %>
				
				<div class="card">
					<div class="card-header bg-warning">
						<h5 class="mb-0">
							<i class="fas fa-list"></i> Lista de Préstamos Pendientes
						</h5>
					</div>
					<div class="card-body">
						<% if (materialesConPrestamosPendientes.isEmpty()) { %>
							<div class="alert alert-info" role="alert">
								<i class="fas fa-info-circle"></i> No hay materiales con préstamos pendientes en el momento.
							</div>
						<% } else { %>
							<div class="alert alert-warning" role="alert">
								<i class="fas fa-info-circle"></i> Total de materiales con préstamos pendientes: <strong><%= materialesConPrestamosPendientes.size() %></strong>
							</div>
							
							<div class="table-responsive">
								<table class="table table-striped table-hover">
									<thead class="thead-dark">
										<tr>
											<th>ID Material</th>
											<th>Material</th>
											<th>Cantidad Préstamos Pendientes</th>
											<th>Fecha Ingreso</th>
										</tr>
									</thead>
									<tbody>
										<% for (DtPrestamo prestamo : materialesConPrestamosPendientes) { %>
											<% 
												Long idMaterial = prestamo.getMaterial() != null ? prestamo.getMaterial().getId() : 0L;
												Integer cantidad = cantidadPrestamosPorMaterial != null ? cantidadPrestamosPorMaterial.getOrDefault(idMaterial, 0) : 0;
											%>
											<tr>
												<td><%= idMaterial %></td>
												<td>
													<% if (prestamo.getMaterial() instanceof DtLibro) { %>
														<% DtLibro libro = (DtLibro) prestamo.getMaterial(); %>
														<strong><%= libro.getTitulo() %></strong><br>
														<small class="text-muted">Libro - <%= libro.getCantidadPaginas() %> páginas</small>
													<% } else if (prestamo.getMaterial() instanceof DtArticulo) { %>
														<% DtArticulo articulo = (DtArticulo) prestamo.getMaterial(); %>
														<strong><%= articulo.getDescripcion() %></strong><br>
														<small class="text-muted">Artículo - <%= articulo.getPeso() %> kg</small>
													<% } %>
												</td>
												<td class="text-center">
													<span class="badge badge-warning badge-lg" style="font-size: 1rem; padding: 0.5rem 1rem;">
														<i class="fas fa-book-reader"></i> <%= cantidad %>
													</span>
												</td>
												<td>
													<%
														Object fechaIngresoObj = prestamo.getMaterial() != null ? prestamo.getMaterial().getFechaIngreso() : null;
														String fechaIngresoStr = "N/A";
														if (fechaIngresoObj != null && dateFormat != null) {
															if (fechaIngresoObj instanceof java.util.Date) {
																fechaIngresoStr = dateFormat.format((java.util.Date) fechaIngresoObj);
															} else if (fechaIngresoObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
																fechaIngresoStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaIngresoObj).toGregorianCalendar().getTime());
															} else {
																fechaIngresoStr = fechaIngresoObj.toString();
															}
														}
													%>
													<%= fechaIngresoStr %>
												</td>
											</tr>
										<% } %>
									</tbody>
								</table>
							</div>
						<% } %>
					</div>
				</div>
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
</body>
</html>

