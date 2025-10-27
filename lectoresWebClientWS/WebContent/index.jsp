<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String usuario = (String) session.getAttribute("usuario");
	String tipoUsuario = (String) session.getAttribute("tipoUsuario");
	if (usuario == null) {
		response.sendRedirect("login");
		return;
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
	
	.dropdown-divider {
		margin: 0.5rem 0;
		border-top: 1px solid #e9ecef;
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

<title>Homepage</title>
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

	<!--div id="carouselExampleSlidesOnly" class="carousel slide"
		data-ride="carousel">
		<div class="carousel-inner">
			<div class="carousel-item active">
				<img class="d-block w-100" src="imagenes/img1.jpg" alt="First slide">
			</div>
			<div class="carousel-item">
				<img class="d-block w-100" src="imagenes/img2.jpg"
					alt="Second slide">
			</div>
			<div class="carousel-item">
				<img class="d-block w-100" src="imagenes/img3.jpg" alt="Third slide">
			</div>
			<div class="carousel-item">
				<img class="d-block w-100" src="imagenes/img4.jpg" alt="Third slide">
			</div>
		</div>
	</div-->



	<!-- Contenedor para mensaje de bienvenida -->
	<div id="mensajeBienvenidaContainer" style="display: none;" class="container mt-4">
		<div class="alert alert-success alert-dismissible fade show" role="alert">
			<h4 class="alert-heading"><i class="fas fa-check-circle"></i> ¡Bienvenido!</h4>
			<p class="mb-0">Sesión iniciada correctamente como <strong><%= usuario %></strong> (<%= tipoUsuario %>)</p>
			<button type="button" class="close" data-dismiss="alert" aria-label="Close">
				<span aria-hidden="true">&times;</span>
			</button>
		</div>
	</div>

	<div class="container mt-4">
		<h1>Bienvenido <%= usuario %></h1>
		<p>Estás logeado como: <strong><%= tipoUsuario %></strong></p>
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
		// Mostrar mensaje de bienvenida si viene de un login exitoso
		window.addEventListener('DOMContentLoaded', function() {
			var urlParams = new URLSearchParams(window.location.search);
			if (urlParams.get('nuevoLogin') === 'true') {
				var container = document.getElementById('mensajeBienvenidaContainer');
				if (container) {
					container.style.display = 'block';
					
					// Auto-ocultar después de 5 segundos
					setTimeout(function() {
						container.style.transition = 'opacity 0.5s';
						container.style.opacity = '0';
						setTimeout(function() {
							container.style.display = 'none';
						}, 500);
					}, 5000);
					
					// Limpiar el parámetro de la URL
					window.history.replaceState({}, document.title, 'index');
				}
			}
		});
	</script>
</body>
</html>