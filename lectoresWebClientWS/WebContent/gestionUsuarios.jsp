<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="publicadores.DtLector" %>
<%@ page import="publicadores.EstadoLector" %>

<%
	// Obtener datos del servlet
	List<DtLector> lectores = (List<DtLector>) request.getAttribute("lectores");
	String errorMessage = (String) request.getAttribute("errorMessage");
	SimpleDateFormat dateFormat = (SimpleDateFormat) request.getAttribute("dateFormat");
    
	// Si no hay datos, inicializar lista vacía
	if (lectores == null) {
		lectores = new java.util.ArrayList();
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

<title>Gestion Usuarios</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> 
		<a class="navbar-brand" href="#">Lectores UY</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link" href="gestionUsuarios">Gestion Usuarios</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="gestionMateriales.jsp">Gestion Materiales</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="gestionPrestamos.jsp">Gestion Prestamos</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="consultas.jsp">Consultas</a>
				</li>
			</ul>
		</div>
	</nav>

	<div class="container mt-4">
		<div class="row">
			<div class="col-12">
				<h2 class="mb-4">Gestión de Usuarios Lectores</h2>
				
				<% if (errorMessage != null) { %>
					<div class="alert alert-danger" role="alert">
						<%= errorMessage %>
					</div>
				<% } %>
				
				<div class="card">
					<div class="card-header">
						<h5 class="mb-0">Lista de Lectores</h5>
					</div>
					<div class="card-body">
						<% if (lectores.isEmpty()) { %>
							<div class="alert alert-info" role="alert">
								No hay lectores registrados en el sistema.
							</div>
						<% } else { %>
							<div class="table-responsive">
								<table class="table table-striped table-hover">
									<thead class="thead-dark">
										<tr>
											<th>ID</th>
											<th>Nombre</th>
											<th>Email</th>
											<th>Dirección</th>
											<th>Fecha Registro</th>
											<th>Estado</th>
											<th>Zona</th>
										</tr>
									</thead>
									<tbody>
										<% for (DtLector lector : lectores) { %>
											<tr>
												<td><%= lector.getId() %></td>
												<td><%= lector.getNombre() %></td>
												<td><%= lector.getEmail() %></td>
												<td><%= lector.getDireccion() %></td>
												<% 
													Object fechaObj = lector.getFechaRegistro();
													String fechaStr = "N/A";
													if (fechaObj != null && dateFormat != null) {
														if (fechaObj instanceof java.util.Date) {
															fechaStr = dateFormat.format((java.util.Date) fechaObj);
														} else if (fechaObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
															fechaStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaObj).toGregorianCalendar().getTime());
														} else {
															// Fallback: usar toString() si no es un tipo esperado
															fechaStr = fechaObj.toString();
														}
													}
												%>
												<td><%= fechaStr %></td>
												<td>
													<% if (lector.getEstado() == EstadoLector.ACTIVO) { %>
														<span class="badge badge-success">Activo</span>
													<% } else if (lector.getEstado() == EstadoLector.SUSPENDIDO) { %>
														<span class="badge badge-danger">Suspendido</span>
													<% } else { %>
														<span class="badge badge-secondary">Desconocido</span>
													<% } %>
												</td>
												<td><%= lector.getZona() != null ? lector.getZona() : "N/A" %></td>
											</tr>
										<% } %>
									</tbody>
								</table>
							</div>
							<div class="mt-3">
								<small class="text-muted">Total de lectores: <%= lectores.size() %></small>
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