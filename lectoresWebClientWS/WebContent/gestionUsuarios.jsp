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
		<a class="navbar-brand" href="index">Lectores UY</a>
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
					<a class="nav-link" href="gestionMateriales">Gestion Materiales</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="gestionPrestamos">Gestion Prestamos</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="consultas">Consultas</a>
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
											<th>Acciones</th>
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
												<td>
													<a href="#" 
													   class="btn btn-sm btn-primary edit-btn"
													   data-id='<%= lector.getId() %>'
													   data-nombre='<%= lector.getNombre() != null ? lector.getNombre().replace("\"","&quot;") : "" %>'
													   data-email='<%= lector.getEmail() != null ? lector.getEmail().replace("\"","&quot;") : "" %>'
													   data-direccion='<%= lector.getDireccion() != null ? lector.getDireccion().replace("\"","&quot;") : "" %>'
													   data-estado='<%= lector.getEstado() != null ? lector.getEstado().name() : "" %>'
													   data-zona='<%= lector.getZona() != null ? lector.getZona().replace("\"","&quot;") : "" %>'>
													   Editar
													</a>
												</td>
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

	<!-- Edit modal -->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <form id="editForm" method="post" action="/editarUsuario">
	        <div class="modal-header">
	          <h5 class="modal-title" id="editModalLabel">Editar Lector</h5>
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body">
	          <input type="hidden" name="id" id="edit-id" />
	          <input type="hidden" name="email" id="edit-email-hidden" />
								<div class="form-group">
									<label for="edit-nombre">Nombre</label>
									<p class="form-control-plaintext" id="edit-nombre"></p>
								</div>
								<div class="form-group">
									<label for="edit-email">Email</label>
									<p class="form-control-plaintext" id="edit-email"></p>
								</div>
								<div class="form-group">
									<label for="edit-direccion">Dirección</label>
									<p class="form-control-plaintext" id="edit-direccion"></p>
								</div>
	          <div class="form-group">
	            <label for="edit-estado">Estado</label>
	            <select class="form-control" id="edit-estado" name="estado">
	              <% for (EstadoLector st : EstadoLector.values()) { %>
	                <option value="<%= st.name() %>"><%= st.name() %></option>
	              <% } %>
	            </select>
	          </div>
	          <div class="form-group">
	            <label for="edit-zona">Zona</label>
	            <select class="form-control" id="edit-zona" name="zona">
	              <option value="BIBLIOTECA CENTRAL">BIBLIOTECA CENTRAL</option>
	              <option value="SUCURSAL ESTE">SUCURSAL ESTE</option>
	              <option value="SUCURSAL OESTE">SUCURSAL OESTE</option>
	              <option value="BIBLIOTECA INFANTIL">BIBLIOTECA INFANTIL</option>
	              <option value="ARCHIVO GENERAL">ARCHIVO GENERAL</option>
	            </select>
	          </div>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
	          <button type="submit" class="btn btn-primary">Guardar</button>
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
	// Rellenar y mostrar modal de edición
	$(document).on('click', '.edit-btn', function(e) {
		e.preventDefault();
		var btn = $(this);
		$('#edit-id').val(btn.data('id'));
		$('#edit-email-hidden').val(btn.data('email')); // Establecer email en campo oculto
		$('#edit-nombre').text(btn.data('nombre'));
		$('#edit-email').text(btn.data('email'));
		$('#edit-direccion').text(btn.data('direccion'));
		$('#edit-estado').val(btn.data('estado'));
		$('#edit-zona').val(btn.data('zona'));
		$('#editModal').modal('show');
	});

	// Envío AJAX del formulario de edición
	$('#editForm').on('submit', function(e) {
		e.preventDefault();
		var form = this;
		var url = '<%= request.getContextPath() %>/editarUsuario';
		var formData = new URLSearchParams(new FormData(form));
		fetch(url, {
			method: 'POST',
			headers: { 'Accept': 'application/json' },
			body: formData
		}).then(function(res) {
			return res.json();
		}).then(function(data) {
			if (data && data.success) {
				// Actualizar fila en la tabla
				var id = $('#edit-id').val();
				var row = $("a.edit-btn[data-id='" + id + "']").closest('tr');
									if (row.length) {
										// Solo actualizamos estado y zona (nombre/email/dirección no editables)
										var estadoVal = $('#edit-estado').val();
										var estadoHtml = '<span class="badge badge-secondary">Desconocido</span>';
										if (estadoVal === 'ACTIVO') estadoHtml = '<span class="badge badge-success">Activo</span>';
										if (estadoVal === 'SUSPENDIDO') estadoHtml = '<span class="badge badge-danger">Suspendido</span>';
										row.find('td').eq(5).html(estadoHtml);
										// Zona
										row.find('td').eq(6).text($('#edit-zona').val() || 'N/A');
										// Actualizar atributos del botón (solo estado/zona)
										var btn = row.find('a.edit-btn');
										btn.data('estado', $('#edit-estado').val());
										btn.data('zona', $('#edit-zona').val());
									}
				$('#editModal').modal('hide');
				// Mostrar mensaje de éxito con Bootstrap
				mostrarMensajeExito(data.message || 'Usuario actualizado correctamente');
			} else {
				var errorMsg = (data && data.message) ? data.message : 'Error al guardar';
				if (errorMsg.includes('conexión') || errorMsg.includes('base de datos')) {
					mostrarErrorConexion(errorMsg);
				} else {
					mostrarErrorNegocio(errorMsg);
				}
			}
		}).catch(function(err) {
			console.error(err);
			mostrarErrorConexion('Error de comunicación con el servidor');
		});
	});

	// Función para mostrar mensaje de éxito con Bootstrap
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

	// Función para mostrar errores de negocio con Bootstrap
	function mostrarErrorNegocio(mensaje) {
		var errorDiv = $('<div class="alert alert-danger alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); z-index: 9999; max-width: 90%; min-width: 300px;">' +
			'<strong>Error!</strong><br>' + mensaje + '<br><br>' +
			'<button type="button" class="btn btn-sm btn-danger" onclick="$(this).closest(\'.alert\').alert(\'close\')">Entendido</button>' +
			'</div>');
		
		$('body').append(errorDiv);
		
		// Auto-ocultar después de 8 segundos
		setTimeout(function() {
			errorDiv.alert('close');
		}, 8000);
	}

	// Función para mostrar errores de conexión con Bootstrap
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
	</script>
</body>
</html>