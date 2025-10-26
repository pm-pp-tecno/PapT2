<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="publicadores.DtLibro" %>
<%@ page import="publicadores.DtArticulo" %>

<%
	// Obtener datos del servlet
	List<Object> materiales = (List<Object>) request.getAttribute("materiales");
	String errorMessage = (String) request.getAttribute("errorMessage");
	SimpleDateFormat dateFormat = (SimpleDateFormat) request.getAttribute("dateFormat");
	
	// Obtener datos del usuario de la sesión
	String usuario = (String) session.getAttribute("usuario");
	String tipoUsuario = (String) session.getAttribute("tipoUsuario");
    
	// Si no hay datos, inicializar lista vacía
	if (materiales == null) {
		materiales = new java.util.ArrayList();
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

<title>Gestion Materiales</title>
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
			<% if ("BIBLIOTECARIO".equals(tipoUsuario)) { %>
				<!-- Menú completo para BIBLIOTECARIOS -->
				<li class="nav-item">
					<a class="nav-link" href="gestionUsuarios">Gestion Usuarios</a>
				</li>
				<li class="nav-item active">
					<a class="nav-link" href="gestionMateriales">Gestion Materiales</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="gestionPrestamos">Gestion Prestamos</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="consultas">Consultas</a>
				</li>
			<% } else { %>
				<!-- Menú limitado para LECTORES -->
				<li class="nav-item">
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
				<h2 class="mb-4">Gestión de Materiales</h2>
				
				<% if (errorMessage != null) { %>
					<div class="alert alert-danger" role="alert">
						<%= errorMessage %>
					</div>
				<% } %>
				
				<!-- Filtro por rango de fechas -->
				<div class="card mb-3">
					<div class="card-header">
						<h6 class="mb-0">Filtrar por Fecha de Ingreso</h6>
					</div>
					<div class="card-body">
						<form id="filtroForm" class="row">
							<div class="col-md-4">
								<label for="fechaDesde">Desde:</label>
								<input type="date" class="form-control" id="fechaDesde" name="fechaDesde" />
							</div>
							<div class="col-md-4">
								<label for="fechaHasta">Hasta:</label>
								<input type="date" class="form-control" id="fechaHasta" name="fechaHasta" />
							</div>
							<div class="col-md-4 d-flex align-items-end">
								<button type="submit" class="btn btn-primary btn-sm mr-2">
									<i class="fas fa-search"></i> Filtrar
								</button>
								<button type="button" class="btn btn-secondary btn-sm" id="limpiarFiltro">
									<i class="fas fa-times"></i> Limpiar
								</button>
							</div>
						</form>
					</div>
				</div>

				<div class="card">
					<div class="card-header d-flex justify-content-between align-items-center">
						<h5 class="mb-0">Lista de Materiales</h5>
						<div>
							<button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#agregarLibroModal">
								<i class="fas fa-book"></i> Agregar Libro
							</button>
							<button type="button" class="btn btn-info btn-sm ml-2" data-toggle="modal" data-target="#agregarArticuloModal">
								<i class="fas fa-box"></i> Agregar Artículo
							</button>
						</div>
					</div>
					<div class="card-body">
						<% if (materiales.isEmpty()) { %>
							<div class="alert alert-info" role="alert">
								No hay materiales registrados en el sistema.
							</div>
						<% } else { %>
							<div class="table-responsive">
								<table class="table table-striped table-hover">
									<thead class="thead-dark">
										<tr>
											<th>ID</th>
											<th>Tipo</th>
											<th>Título/Descripción</th>
											<th>Detalles</th>
											<th>Fecha Ingreso</th>
											<th>Acciones</th>
										</tr>
									</thead>
									<tbody>
										<% for (Object material : materiales) { %>
											<tr>
												<% if (material instanceof DtLibro) { %>
													<% DtLibro libro = (DtLibro) material; %>
													<td><%= libro.getId() %></td>
													<td><span class="badge badge-primary">Libro</span></td>
													<td><%= libro.getTitulo() %></td>
													<td><%= libro.getCantidadPaginas() %> páginas</td>
													<% 
														Object fechaObj = libro.getFechaIngreso();
														String fechaStr = "N/A";
														if (fechaObj != null && dateFormat != null) {
															if (fechaObj instanceof java.util.Date) {
																fechaStr = dateFormat.format((java.util.Date) fechaObj);
															} else if (fechaObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
																fechaStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaObj).toGregorianCalendar().getTime());
															} else {
																fechaStr = fechaObj.toString();
															}
														}
													%>
													<td><%= fechaStr %></td>
													<td>
													<a href="#" 
													   class="btn btn-sm btn-primary edit-btn"
													   data-id='<%= libro.getId() %>'
													   data-tipo='LIBRO'
													   data-titulo='<%= libro.getTitulo() %>'
													   data-paginas='<%= libro.getCantidadPaginas() %>'
													   data-fecha='<%= fechaStr %>'>
													   Editar
													</a>
													</td>
												<% } else if (material instanceof DtArticulo) { %>
													<% DtArticulo articulo = (DtArticulo) material; %>
													<td><%= articulo.getId() %></td>
													<td><span class="badge badge-info">Artículo</span></td>
													<td><%= articulo.getDescripcion() %></td>
													<td><%= articulo.getPeso() %> kg, <%= articulo.getDimensiones() %></td>
													<% 
														Object fechaObj = articulo.getFechaIngreso();
														String fechaStr = "N/A";
														if (fechaObj != null && dateFormat != null) {
															if (fechaObj instanceof java.util.Date) {
																fechaStr = dateFormat.format((java.util.Date) fechaObj);
															} else if (fechaObj instanceof javax.xml.datatype.XMLGregorianCalendar) {
																fechaStr = dateFormat.format(((javax.xml.datatype.XMLGregorianCalendar) fechaObj).toGregorianCalendar().getTime());
															} else {
																fechaStr = fechaObj.toString();
															}
														}
													%>
													<td><%= fechaStr %></td>
													<td>
													<a href="#" 
													   class="btn btn-sm btn-primary edit-btn"
													   data-id='<%= articulo.getId() %>'
													   data-tipo='ARTICULO'
													   data-descripcion='<%= articulo.getDescripcion() %>'
													   data-peso='<%= articulo.getPeso() %>'
													   data-dimensiones='<%= articulo.getDimensiones() %>'
													   data-fecha='<%= fechaStr %>'>
													   Editar
													</a>
													</td>
												<% } %>
											</tr>
										<% } %>
									</tbody>
								</table>
							</div>
							<div class="mt-3">
								<small class="text-muted">Total de materiales: <%= materiales.size() %></small>
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
	      <form id="editForm" method="post" action="/editarMaterial">
	        <div class="modal-header">
	          <h5 class="modal-title" id="editModalLabel">Editar Material</h5>
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body">
	          <input type="hidden" name="id" id="edit-id" />
	          <input type="hidden" name="tipo" id="edit-tipo" />
	          
	          <!-- Campos para Libro -->
	          <div id="libro-fields" style="display: none;">
	            <div class="form-group">
	              <label for="edit-titulo">Título</label>
	              <input type="text" class="form-control" id="edit-titulo" name="titulo" />
	            </div>
	            <div class="form-group">
	              <label for="edit-paginas">Cantidad de Páginas</label>
	              <input type="number" class="form-control" id="edit-paginas" name="paginas" min="1" />
	            </div>
	          </div>
	          
	          <!-- Campos para Artículo -->
	          <div id="articulo-fields" style="display: none;">
	            <div class="form-group">
	              <label for="edit-descripcion">Descripción</label>
	              <textarea class="form-control" id="edit-descripcion" name="descripcion" rows="3"></textarea>
	            </div>
	            <div class="form-group">
	              <label for="edit-peso">Peso (kg)</label>
	              <input type="number" class="form-control" id="edit-peso" name="peso" step="0.1" min="0" />
	            </div>
	            <div class="form-group">
	              <label for="edit-dimensiones">Dimensiones</label>
	              <input type="text" class="form-control" id="edit-dimensiones" name="dimensiones" placeholder="ej: 20x30x5 cm" />
	            </div>
	          </div>
	          
	          <!-- Campos comunes -->
	          <div class="form-group">
	            <label for="edit-fecha">Fecha de Ingreso</label>
	            <input type="date" class="form-control" id="edit-fecha" name="fecha" />
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

	<!-- Modal para agregar libro -->
	<div class="modal fade" id="agregarLibroModal" tabindex="-1" role="dialog" aria-labelledby="agregarLibroModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <form id="agregarLibroForm" method="post" action="/agregarMaterial">
	        <div class="modal-header">
	          <h5 class="modal-title" id="agregarLibroModalLabel">Agregar Nuevo Libro</h5>
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body">
	          <input type="hidden" name="tipo" value="LIBRO" />
	          
	          <div class="form-group">
	            <label for="nuevo-titulo">Título del Libro *</label>
	            <input type="text" class="form-control" id="nuevo-titulo" name="titulo" required />
	          </div>
	          
	          <div class="form-group">
	            <label for="nuevo-paginas">Cantidad de Páginas *</label>
	            <input type="number" class="form-control" id="nuevo-paginas" name="paginas" min="1" required />
	          </div>
	          
	          <div class="form-group">
	            <label for="nuevo-fecha-libro">Fecha de Ingreso *</label>
	            <input type="date" class="form-control" id="nuevo-fecha-libro" name="fecha" required />
	          </div>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
	          <button type="submit" class="btn btn-success">Agregar Libro</button>
	        </div>
	      </form>
	    </div>
	  </div>
	</div>

	<!-- Modal para agregar artículo -->
	<div class="modal fade" id="agregarArticuloModal" tabindex="-1" role="dialog" aria-labelledby="agregarArticuloModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <form id="agregarArticuloForm" method="post" action="/agregarMaterial">
	        <div class="modal-header">
	          <h5 class="modal-title" id="agregarArticuloModalLabel">Agregar Nuevo Artículo</h5>
	          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	            <span aria-hidden="true">&times;</span>
	          </button>
	        </div>
	        <div class="modal-body">
	          <input type="hidden" name="tipo" value="ARTICULO" />
	          
	          <div class="form-group">
	            <label for="nuevo-descripcion">Descripción del Artículo *</label>
	            <textarea class="form-control" id="nuevo-descripcion" name="descripcion" rows="3" required></textarea>
	          </div>
	          
	          <div class="form-group">
	            <label for="nuevo-peso">Peso (kg) *</label>
	            <input type="number" class="form-control" id="nuevo-peso" name="peso" step="0.1" min="0" required />
	          </div>
	          
	          <div class="form-group">
	            <label for="nuevo-dimensiones">Dimensiones</label>
	            <input type="text" class="form-control" id="nuevo-dimensiones" name="dimensiones" placeholder="ej: 20x30x5 cm" />
	          </div>
	          
	          <div class="form-group">
	            <label for="nuevo-fecha-articulo">Fecha de Ingreso *</label>
	            <input type="date" class="form-control" id="nuevo-fecha-articulo" name="fecha" required />
	          </div>
	        </div>
	        <div class="modal-footer">
	          <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
	          <button type="submit" class="btn btn-info">Agregar Artículo</button>
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
	// Variable global para almacenar todos los materiales
	var todosLosMateriales = [];

	// Cargar todos los materiales al inicializar la página
	$(document).ready(function() {
		cargarMaterialesIniciales();
	});

	// Función para cargar materiales iniciales
	function cargarMaterialesIniciales() {
		// Los materiales ya están cargados en el JSP, los almacenamos en la variable global
		<% if (materiales != null && !materiales.isEmpty()) { %>
			todosLosMateriales = [
				<% for (int i = 0; i < materiales.size(); i++) { %>
					<% Object material = materiales.get(i); %>
					<% if (material instanceof DtLibro) { %>
						<% DtLibro libro = (DtLibro) material; %>
						{
							id: <%= libro.getId() %>,
							tipo: 'LIBRO',
							titulo: '<%= libro.getTitulo().replace("'", "\\'") %>',
							paginas: <%= libro.getCantidadPaginas() %>,
							fechaIngreso: '<%= libro.getFechaIngreso() %>',
						}
					<% } else if (material instanceof DtArticulo) { %>
						<% DtArticulo articulo = (DtArticulo) material; %>
						{
							id: <%= articulo.getId() %>,
							tipo: 'ARTICULO',
							descripcion: '<%= articulo.getDescripcion().replace("'", "\\'") %>',
							peso: <%= articulo.getPeso() %>,
							dimensiones: '<%= articulo.getDimensiones().replace("'", "\\'") %>',
							fechaIngreso: '<%= articulo.getFechaIngreso() %>',
						}
					<% } %>
					<% if (i < materiales.size() - 1) { %>,<% } %>
				<% } %>
			];
		<% } %>
	}

	// Rellenar y mostrar modal de edición
	$(document).on('click', '.edit-btn', function(e) {
		e.preventDefault();
		var btn = $(this);
		var tipo = btn.data('tipo');
		
		// Limpiar formulario
		$('#editForm')[0].reset();
		$('#libro-fields').hide();
		$('#articulo-fields').hide();
		
		// Establecer valores básicos
		$('#edit-id').val(btn.data('id'));
		$('#edit-tipo').val(tipo);
		
		// Llenar fecha de ingreso
		var fecha = btn.data('fecha') || '';
		console.log('Fecha original del botón:', fecha);
		if (fecha && fecha !== 'N/A') {
			// Convertir fecha del formato mostrado (dd/MM/yyyy) al formato del input date (yyyy-MM-dd)
			var fechaConvertida = convertirFechaParaInput(fecha);
			console.log('Fecha convertida:', fechaConvertida);
			$('#edit-fecha').val(fechaConvertida);
		} else {
			console.log('Fecha vacía o N/A, limpiando campo');
			$('#edit-fecha').val('');
		}
		
		if (tipo === 'LIBRO') {
			$('#libro-fields').show();
			$('#edit-titulo').val(btn.data('titulo') || '');
			$('#edit-paginas').val(btn.data('paginas') || '');
		} else if (tipo === 'ARTICULO') {
			$('#articulo-fields').show();
			$('#edit-descripcion').val(btn.data('descripcion') || '');
			$('#edit-peso').val(btn.data('peso') || '');
			$('#edit-dimensiones').val(btn.data('dimensiones') || '');
		}
		
		$('#editModal').modal('show');
	});

	// Envío AJAX del formulario de edición
	$('#editForm').on('submit', function(e) {
		e.preventDefault();
		var form = this;
		var url = '<%= request.getContextPath() %>/editarMaterial';
		var formData = new URLSearchParams(new FormData(form));
		
		// Log para debugging
		console.log('=== ENVIANDO FORMULARIO DE EDICION ===');
		console.log('Valor del campo fecha antes del envío:', $('#edit-fecha').val());
		console.log('FormData entries:');
		for (var pair of formData.entries()) {
			console.log(pair[0] + ': ' + pair[1]);
		}
		
		fetch(url, {
			method: 'POST',
			headers: { 'Accept': 'application/json' },
			body: formData
		}).then(function(res) {
			return res.json();
		}).then(function(data) {
			if (data && data.success) {
				$('#editModal').modal('hide');
				
				// Mostrar mensaje de éxito
				mostrarMensajeExito(data.message || 'Material actualizado correctamente');
				
				// Recargar solo la tabla usando AJAX
				recargarTablaMateriales();
			} else {
				var errorMsg = (data && data.message) ? data.message : 'Error al actualizar el material';
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

	// Manejar formulario de agregar libro
	$('#agregarLibroForm').on('submit', function(e) {
		e.preventDefault();
		enviarFormularioAgregar(this, 'LIBRO');
	});

	// Manejar formulario de agregar artículo
	$('#agregarArticuloForm').on('submit', function(e) {
		e.preventDefault();
		enviarFormularioAgregar(this, 'ARTICULO');
	});

	// Manejar formulario de filtro
	$('#filtroForm').on('submit', function(e) {
		e.preventDefault();
		aplicarFiltro();
	});

	// Manejar botón limpiar filtro
	$('#limpiarFiltro').on('click', function() {
		limpiarFiltro();
	});

	// Función genérica para enviar formularios de agregar
	function enviarFormularioAgregar(form, tipo) {
		var url = '<%= request.getContextPath() %>/agregarMaterial';
		var formData = new URLSearchParams(new FormData(form));
		
		fetch(url, {
			method: 'POST',
			headers: { 'Accept': 'application/json' },
			body: formData
		}).then(function(res) {
			return res.json();
		}).then(function(data) {
			if (data && data.success) {
				// Cerrar modal
				if (tipo === 'LIBRO') {
					$('#agregarLibroModal').modal('hide');
				} else {
					$('#agregarArticuloModal').modal('hide');
				}
				
				// Limpiar formulario
				form.reset();
				
				// Mostrar mensaje de éxito
				mostrarMensajeExito(data.message || 'Material agregado correctamente');
				
				// Recargar solo la tabla usando AJAX
				recargarTablaMateriales();
			} else {
				var errorMsg = (data && data.message) ? data.message : 'Error al agregar el material';
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
	}

	// Función para actualizar la fila en la tabla
	function actualizarFilaEnTabla(form) {
		var id = $('#edit-id').val();
		var tipo = $('#edit-tipo').val();
		var btn = $("a.edit-btn[data-id='" + id + "']");
		var row = btn.closest('tr');
		
		if (row.length) {
			if (tipo === 'LIBRO') {
				var titulo = $('#edit-titulo').val();
				var paginas = $('#edit-paginas').val();
				
				// Actualizar contenido de la fila
				row.find('td').eq(2).text(titulo); // Título/Descripción
				row.find('td').eq(3).text(paginas + ' páginas'); // Detalles
				
				// Actualizar atributos del botón
				btn.data('titulo', titulo);
				btn.data('paginas', paginas);
			} else if (tipo === 'ARTICULO') {
				var descripcion = $('#edit-descripcion').val();
				var peso = $('#edit-peso').val();
				var dimensiones = $('#edit-dimensiones').val();
				
				// Actualizar contenido de la fila
				row.find('td').eq(2).text(descripcion); // Título/Descripción
				row.find('td').eq(3).text(peso + ' kg, ' + dimensiones); // Detalles
				
				// Actualizar atributos del botón
				btn.data('descripcion', descripcion);
				btn.data('peso', peso);
				btn.data('dimensiones', dimensiones);
			}
		}
	}

	// Función para mostrar mensaje de éxito
	function mostrarMensajeExito(mensaje) {
		// Crear elemento de mensaje temporal
		var alertDiv = $('<div class="alert alert-success alert-dismissible fade show" role="alert" style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); z-index: 9999; max-width: 90%; min-width: 300px;">' +
			'<strong>Éxito!</strong> ' + mensaje +
			'<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
			'<span aria-hidden="true">&times;</span>' +
			'</button>' +
			'</div>');
		
		$('body').append(alertDiv);
		
		// Auto-ocultar después de 3 segundos
		setTimeout(function() {
			alertDiv.alert('close');
		}, 3000);
	}

	// Función para aplicar filtro
	function aplicarFiltro() {
		var fechaDesde = $('#fechaDesde').val();
		var fechaHasta = $('#fechaHasta').val();
		
		// Validar que al menos una fecha esté seleccionada
		if (!fechaDesde && !fechaHasta) {
			mostrarErrorNegocio('Por favor seleccione al menos una fecha para filtrar.');
			return;
		}
		
		// Validar que fechaDesde no sea mayor que fechaHasta
		if (fechaDesde && fechaHasta && fechaDesde > fechaHasta) {
			mostrarErrorNegocio('La fecha "Desde" no puede ser mayor que la fecha "Hasta".');
			return;
		}
		
		// Filtrar materiales localmente
		var materialesFiltrados = filtrarMaterialesLocalmente(fechaDesde, fechaHasta);
		
		// Actualizar la tabla con los resultados filtrados
		actualizarTablaConFiltro(materialesFiltrados);
		mostrarMensajeExito('Filtro aplicado correctamente. Se encontraron ' + materialesFiltrados.length + ' materiales.');
	}

	// Función para filtrar materiales localmente
	function filtrarMaterialesLocalmente(fechaDesde, fechaHasta) {
		var materialesFiltrados = [];
		
		for (var i = 0; i < todosLosMateriales.length; i++) {
			var material = todosLosMateriales[i];
			var fechaMaterial = new Date(material.fechaIngreso);
			
			var cumpleFiltro = true;
			
			// Aplicar filtro de fecha desde
			if (fechaDesde) {
				var fechaDesdeObj = new Date(fechaDesde);
				if (fechaMaterial < fechaDesdeObj) {
					cumpleFiltro = false;
				}
			}
			
			// Aplicar filtro de fecha hasta
			if (fechaHasta) {
				var fechaHastaObj = new Date(fechaHasta);
				if (fechaMaterial > fechaHastaObj) {
					cumpleFiltro = false;
				}
			}
			
			if (cumpleFiltro) {
				materialesFiltrados.push(material);
			}
		}
		
		return materialesFiltrados;
	}

	// Función para limpiar filtro
	function limpiarFiltro() {
		$('#fechaDesde').val('');
		$('#fechaHasta').val('');
		
		// Mostrar todos los materiales sin filtro
		actualizarTablaConFiltro(todosLosMateriales);
		mostrarMensajeExito('Filtro limpiado. Se muestran todos los materiales.');
	}


	// Función para actualizar la tabla con resultados filtrados
	function actualizarTablaConFiltro(materiales) {
		var tbody = $('table tbody');
		tbody.empty();
		
		if (materiales.length === 0) {
			tbody.append('<tr><td colspan="7" class="text-center text-muted">No se encontraron materiales en el rango de fechas seleccionado.</td></tr>');
		} else {
			// Procesar cada material y agregarlo a la tabla
			for (var i = 0; i < materiales.length; i++) {
				var material = materiales[i];
				var row = crearFilaMaterial(material);
				tbody.append(row);
			}
		}
		
		// Actualizar contador
		$('.text-muted').text('Total de materiales: ' + materiales.length);
	}

	// Función para crear una fila de material
	function crearFilaMaterial(material) {
		var row = $('<tr></tr>');
		
		if (material.tipo === 'LIBRO') {
			row.append('<td>' + material.id + '</td>');
			row.append('<td><span class="badge badge-primary">Libro</span></td>');
			row.append('<td>' + escapeHtml(material.titulo) + '</td>');
			row.append('<td>' + material.paginas + ' páginas</td>');
			row.append('<td>' + formatearFecha(material.fechaIngreso) + '</td>');
			row.append('<td><a href="#" class="btn btn-sm btn-primary edit-btn" data-id="' + material.id + '" data-tipo="LIBRO" data-titulo="' + escapeHtml(material.titulo) + '" data-paginas="' + material.paginas + '" data-fecha="' + formatearFecha(material.fechaIngreso) + '">Editar</a></td>');
		} else if (material.tipo === 'ARTICULO') {
			row.append('<td>' + material.id + '</td>');
			row.append('<td><span class="badge badge-info">Artículo</span></td>');
			row.append('<td>' + escapeHtml(material.descripcion) + '</td>');
			row.append('<td>' + material.peso + ' kg, ' + escapeHtml(material.dimensiones) + '</td>');
			row.append('<td>' + formatearFecha(material.fechaIngreso) + '</td>');
			row.append('<td><a href="#" class="btn btn-sm btn-primary edit-btn" data-id="' + material.id + '" data-tipo="ARTICULO" data-descripcion="' + escapeHtml(material.descripcion) + '" data-peso="' + material.peso + '" data-dimensiones="' + escapeHtml(material.dimensiones) + '" data-fecha="' + formatearFecha(material.fechaIngreso) + '">Editar</a></td>');
		}
		
		return row;
	}

	// Función para escapar HTML
	function escapeHtml(text) {
		if (!text) return '';
		return text.replace(/&/g, '&amp;')
				   .replace(/</g, '&lt;')
				   .replace(/>/g, '&gt;')
				   .replace(/"/g, '&quot;')
				   .replace(/'/g, '&#39;');
	}

	// Función para formatear fecha
	function formatearFecha(fechaStr) {
		if (!fechaStr) return 'N/A';
		try {
			// Si viene en formato ISO (2025-09-11T00:00:00-03:00), extraer solo la parte de fecha
			if (fechaStr.includes('T')) {
				var fechaParte = fechaStr.split('T')[0]; // Obtener solo 2025-09-11
				var partes = fechaParte.split('-');
				if (partes.length === 3) {
					// Convertir de YYYY-MM-DD a DD/MM/YYYY
					return partes[2] + '/' + partes[1] + '/' + partes[0];
				}
			}
			
			// Si ya está en formato dd/MM/yyyy, devolverlo tal como está
			if (fechaStr.includes('/')) {
				return fechaStr;
			}
			
			// Intentar crear Date como fallback
			var fecha = new Date(fechaStr);
			return fecha.toLocaleDateString('es-ES');
		} catch (e) {
			console.error('Error formateando fecha:', fechaStr, e);
			return fechaStr;
		}
	}

	// Función para convertir fecha del formato mostrado (dd/MM/yyyy) al formato del input date (yyyy-MM-dd)
	function convertirFechaParaInput(fechaStr) {
		if (!fechaStr || fechaStr === 'N/A') return '';
		try {
			// Si la fecha viene en formato dd/MM/yyyy, convertirla
			if (fechaStr.includes('/')) {
				var partes = fechaStr.split('/');
				if (partes.length === 3) {
					var dia = partes[0];
					var mes = partes[1];
					var anio = partes[2];
					// Formato yyyy-MM-dd
					return anio + '-' + mes.padStart(2, '0') + '-' + dia.padStart(2, '0');
				}
			}
			// Si ya está en formato yyyy-MM-dd, devolverla tal como está
			return fechaStr;
		} catch (e) {
			console.error('Error convirtiendo fecha:', e);
			return '';
		}
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

	// Función para recargar solo la tabla de materiales usando AJAX
	function recargarTablaMateriales() {
		console.log('Recargando tabla de materiales...');
		
		// Mostrar indicador de carga
		$('table tbody').html('<tr><td colspan="6" class="text-center"><i class="fas fa-spinner fa-spin"></i> Cargando materiales...</td></tr>');
		
		// Hacer petición AJAX para obtener los materiales en formato JSON
		fetch('<%= request.getContextPath() %>/materialesData', {
			method: 'GET',
			headers: { 'Accept': 'application/json' }
		}).then(function(response) {
			return response.json();
		}).then(function(materiales) {
			console.log('Datos recibidos:', materiales);
			
			// Construir la tabla dinámicamente
			var tbody = $('table tbody');
			console.log('Selector tbody encontrado:', tbody.length, 'elementos');
			console.log('Contenido actual del tbody:', tbody.html());
			
			tbody.empty();
			
			if (materiales && materiales.length > 0) {
				console.log('Procesando', materiales.length, 'materiales');
				var htmlContent = '';
				materiales.forEach(function(material) {
					console.log('Creando fila para material:', material);
					var row = crearFilaMaterialSimple(material);
					htmlContent += row[0].outerHTML;
				});
				console.log('HTML completo a insertar:', htmlContent);
				
				if (tbody.length > 0) {
					tbody.html(htmlContent);
					console.log('Contenido del tbody después de insertar:', tbody.html());
				} else {
					console.error('No se puede insertar HTML: elemento tbody no encontrado');
				}
			} else {
				console.log('No hay materiales disponibles');
				tbody.html('<tr><td colspan="6" class="text-center">No hay materiales disponibles</td></tr>');
			}
			
			console.log('Tabla de materiales recargada exitosamente');
		}).catch(function(error) {
			console.error('Error al recargar tabla:', error);
			$('table tbody').html('<tr><td colspan="6" class="text-center text-danger">Error al cargar materiales</td></tr>');
		});
	}

	// Función simplificada para crear fila de material para AJAX
	function crearFilaMaterialSimple(material) {
		console.log('Creando fila para material:', material);
		var row = $('<tr></tr>');
		
		if (material.tipo === 'LIBRO') {
			var fechaFormateada = formatearFecha(material.fechaIngreso);
			console.log('Fecha original:', material.fechaIngreso, 'Fecha formateada:', fechaFormateada);
			
			row.append('<td>' + material.id + '</td>');
			row.append('<td><span class="badge badge-primary">Libro</span></td>');
			row.append('<td>' + escapeHtml(material.titulo) + '</td>');
			row.append('<td>' + material.paginas + ' páginas</td>');
			row.append('<td>' + fechaFormateada + '</td>');
			row.append('<td><a href="#" class="btn btn-sm btn-primary edit-btn" data-id="' + material.id + '" data-tipo="LIBRO" data-titulo="' + escapeHtml(material.titulo) + '" data-paginas="' + material.paginas + '" data-fecha="' + fechaFormateada + '">Editar</a></td>');
		} else if (material.tipo === 'ARTICULO') {
			var fechaFormateada = formatearFecha(material.fechaIngreso);
			console.log('Fecha original:', material.fechaIngreso, 'Fecha formateada:', fechaFormateada);
			
			row.append('<td>' + material.id + '</td>');
			row.append('<td><span class="badge badge-info">Artículo</span></td>');
			row.append('<td>' + escapeHtml(material.descripcion) + '</td>');
			row.append('<td>' + material.peso + ' kg</td>');
			row.append('<td>' + fechaFormateada + '</td>');
			row.append('<td><a href="#" class="btn btn-sm btn-primary edit-btn" data-id="' + material.id + '" data-tipo="ARTICULO" data-descripcion="' + escapeHtml(material.descripcion) + '" data-peso="' + material.peso + '" data-dimensiones="' + escapeHtml(material.dimensiones) + '" data-fecha="' + fechaFormateada + '">Editar</a></td>');
		}
		
		console.log('Fila creada:', row[0].outerHTML);
		return row;
	}
	</script>
</body>
</html>