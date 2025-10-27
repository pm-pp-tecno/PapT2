<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	// Obtener datos del usuario de la sesión
	String usuario = (String) session.getAttribute("usuario");
	String tipoUsuario = (String) session.getAttribute("tipoUsuario");
	
	// Obtener estadísticas del servlet
	Integer cantidadPrestamos = (Integer) request.getAttribute("cantidadPrestamos");
	Integer cantidadLibros = (Integer) request.getAttribute("cantidadLibros");
	Integer cantidadArticulos = (Integer) request.getAttribute("cantidadArticulos");
	Integer cantidadLectores = (Integer) request.getAttribute("cantidadLectores");
	String errorMessage = (String) request.getAttribute("errorMessage");
	
	// Valores por defecto si no están disponibles
	if (cantidadPrestamos == null) cantidadPrestamos = 0;
	if (cantidadLibros == null) cantidadLibros = 0;
	if (cantidadArticulos == null) cantidadArticulos = 0;
	if (cantidadLectores == null) cantidadLectores = 0;
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
	.stat-card {
		transition: transform 0.3s ease, box-shadow 0.3s ease;
		border: none;
		border-radius: 15px;
		overflow: hidden;
	}
	
	.stat-card:hover {
		transform: translateY(-5px);
		box-shadow: 0 10px 25px rgba(0,0,0,0.15);
	}
	
	.stat-icon {
		font-size: 3rem;
		opacity: 0.8;
	}
	
	.stat-number {
		font-size: 2.5rem;
		font-weight: bold;
		margin: 0;
	}
	
	.stat-label {
		font-size: 1rem;
		color: #6c757d;
		margin: 0;
	}
	
	.card-header-custom {
		background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
		color: white;
		border: none;
		padding: 1.5rem;
	}
	
	.summary-card {
		background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
		color: white;
		border-radius: 15px;
		border: none;
	}
	
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

<title>Reportes - Lectores UY</title>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> <a
		class="navbar-brand" href="index"><i class="fas fa-book-open text-primary mr-2"></i> Lectores UY</a>
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
					<a class="nav-link active" href="reportes" id="reportesDropdown" role="button">
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
    	<!-- Mensaje de error si existe -->
    	<% if (errorMessage != null) { %>
    		<div class="alert alert-danger" role="alert">
    			<i class="fas fa-exclamation-triangle"></i> <%= errorMessage %>
    		</div>
    	<% } %>
    	
    	<!-- Título principal -->
    	<div class="row mb-4">
    		<div class="col-12">
    			<h1 class="display-4 text-center mb-3">
    				<i class="fas fa-chart-bar text-primary"></i> Reportes del Sistema
    			</h1>
    			<p class="lead text-center text-muted">Resumen estadístico de la biblioteca</p>
    		</div>
    	</div>
    	
    	<!-- Tarjeta de resumen general -->
    	<div class="row mb-4">
    		<div class="col-12">
    			<div class="card summary-card">
    				<div class="card-body text-center">
    					<h3 class="card-title mb-3">
    						<i class="fas fa-university"></i> Biblioteca Lectores UY
    					</h3>
    					<p class="card-text h5 mb-0">
    						Sistema de gestión bibliotecaria con <%= cantidadLectores + cantidadLibros + cantidadArticulos %> elementos registrados
    					</p>
    				</div>
    			</div>
    		</div>
    	</div>
    	
    	<!-- Estadísticas principales -->
    	<div class="row">
    		<!-- Usuarios Lectores -->
    		<div class="col-lg-3 col-md-6 mb-4">
    			<div class="card stat-card h-100">
    				<div class="card-body text-center p-4">
    					<div class="stat-icon text-primary mb-3">
    						<i class="fas fa-users"></i>
    					</div>
    					<h2 class="stat-number text-primary"><%= cantidadLectores %></h2>
    					<p class="stat-label">Usuarios Lectores</p>
    				</div>
    			</div>
    		</div>
    		
    		<!-- Préstamos -->
    		<div class="col-lg-3 col-md-6 mb-4">
    			<div class="card stat-card h-100">
    				<div class="card-body text-center p-4">
    					<div class="stat-icon text-success mb-3">
    						<i class="fas fa-book-reader"></i>
    					</div>
    					<h2 class="stat-number text-success"><%= cantidadPrestamos %></h2>
    					<p class="stat-label">Préstamos Totales</p>
    				</div>
    			</div>
    		</div>
    		
    		<!-- Libros -->
    		<div class="col-lg-3 col-md-6 mb-4">
    			<div class="card stat-card h-100">
    				<div class="card-body text-center p-4">
    					<div class="stat-icon text-warning mb-3">
    						<i class="fas fa-book"></i>
    					</div>
    					<h2 class="stat-number text-warning"><%= cantidadLibros %></h2>
    					<p class="stat-label">Libros Registrados</p>
    				</div>
    			</div>
    		</div>
    		
    		<!-- Artículos -->
    		<div class="col-lg-3 col-md-6 mb-4">
    			<div class="card stat-card h-100">
    				<div class="card-body text-center p-4">
    					<div class="stat-icon text-info mb-3">
    						<i class="fas fa-newspaper"></i>
    					</div>
    					<h2 class="stat-number text-info"><%= cantidadArticulos %></h2>
    					<p class="stat-label">Artículos Registrados</p>
    				</div>
    			</div>
    		</div>
    	</div>
    	
    	<!-- Información adicional -->
    	<div class="row mt-5">
    		<div class="col-12">
    			<div class="card">
    				<div class="card-header card-header-custom">
    					<h4 class="mb-0">
    						<i class="fas fa-info-circle"></i> Información del Sistema
    					</h4>
    				</div>
    				<div class="card-body">
    					<div class="row">
    						<div class="col-md-6">
    							<h5><i class="fas fa-chart-pie text-primary"></i> Distribución de Materiales</h5>
    							<p class="text-muted">
    								El sistema cuenta con <strong><%= cantidadLibros + cantidadArticulos %></strong> materiales en total:
    							</p>
    							<ul class="list-unstyled">
    								<li><i class="fas fa-book text-warning"></i> <strong><%= cantidadLibros %></strong> libros</li>
    								<li><i class="fas fa-newspaper text-info"></i> <strong><%= cantidadArticulos %></strong> artículos</li>
    							</ul>
    						</div>
    						<div class="col-md-6">
    							<h5><i class="fas fa-users text-success"></i> Actividad de Usuarios</h5>
    							<p class="text-muted">
    								<strong><%= cantidadLectores %></strong> usuarios registrados han realizado 
    								<strong><%= cantidadPrestamos %></strong> préstamos en total.
    							</p>
    							<% if (cantidadLectores > 0) { %>
    								<p class="text-muted">
    									Promedio de préstamos por usuario: 
    									<strong><%= String.format("%.1f", (double) cantidadPrestamos / cantidadLectores) %></strong>
    								</p>
    							<% } %>
    						</div>
    					</div>
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
