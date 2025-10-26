<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

<style>
	body {
		background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
		height: 100vh;
		display: flex;
		align-items: center;
		justify-content: center;
	}
	
	.login-container {
		max-width: 450px;
		width: 100%;
		padding: 20px;
	}
	
	.login-card {
		background: white;
		border-radius: 10px;
		box-shadow: 0 10px 40px rgba(0,0,0,0.2);
		padding: 40px;
	}
	
	.login-header {
		text-align: center;
		margin-bottom: 30px;
	}
	
	.login-header h2 {
		color: #333;
		font-weight: 600;
		margin-bottom: 10px;
	}
	
	.login-header p {
		color: #666;
		font-size: 14px;
	}
	
	.login-icon {
		width: 80px;
		height: 80px;
		background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
		border-radius: 50%;
		display: flex;
		align-items: center;
		justify-content: center;
		margin: 0 auto 20px;
	}
	
	.login-icon svg {
		width: 40px;
		height: 40px;
		fill: white;
	}
	
	.form-group label {
		font-weight: 500;
		color: #333;
		margin-bottom: 8px;
	}
	
	.form-control {
		height: 45px;
		border-radius: 5px;
		border: 1px solid #ddd;
		padding: 10px 15px;
		transition: all 0.3s ease;
	}
	
	.form-control:focus {
		border-color: #667eea;
		box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
	}
	
	.btn-login {
		background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
		border: none;
		color: white;
		padding: 12px;
		font-weight: 600;
		border-radius: 5px;
		width: 100%;
		margin-top: 10px;
		transition: transform 0.2s ease;
	}
	
	.btn-login:hover {
		transform: translateY(-2px);
		color: white;
	}
	
	.forgot-password {
		text-align: center;
		margin-top: 20px;
	}
	
	.forgot-password a {
		color: #667eea;
		text-decoration: none;
		font-size: 14px;
	}
	
	.forgot-password a:hover {
		text-decoration: underline;
	}
</style>

<title>Login - Lectores UY</title>
</head>

<body>
	<div class="login-container">
		<div class="login-card">
			<div class="login-header">
				<div class="login-icon">
					<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
						<path d="M12 12c2.21 0 4-1.79 4-4s-1.79-4-4-4-4 1.79-4 4 1.79 4 4 4zm0 2c-2.67 0-8 1.34-8 4v2h16v-2c0-2.66-5.33-4-8-4z"/>
					</svg>
				</div>
				<h2>Bienvenido</h2>
				<p>Sistema de Biblioteca Lectores UY</p>
			</div>
			
			<form id="loginForm">
				<div class="form-group">
					<label for="email">Email</label>
					<input type="email" 
						   class="form-control" 
						   id="email" 
						   placeholder="ingrese su email"
						   required>
				</div>
				
				<div class="form-group">
					<label for="password">Contraseña</label>
					<input type="password" 
						   class="form-control" 
						   id="password" 
						   placeholder="ingrese su contraseña"
						   required>
				</div>
				
				<button type="submit" class="btn btn-login">
					Iniciar Sesión
				</button>
			</form>
			
			<div class="forgot-password">
				<a href="#">¿Olvidaste tu contraseña?</a>
			</div>
		</div>
	</div>

	<!-- Optional JavaScript -->
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"
		integrity="sha384-xBuQ/xzmlsLoJpyjoggmTEz8OWEU(M/6ekJ6ZkDJFpiCkX49Gb9+uuT0pNVosQ=="
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
		$(document).ready(function() {
			$('#loginForm').on('submit', function(e) {
				e.preventDefault();
				
				var email = $('#email').val();
				var password = $('#password').val();
				
				if (email && password) {
					// Deshabilitar el botón mientras se procesa
					var submitBtn = $(this).find('button[type="submit"]');
					submitBtn.prop('disabled', true).text('Iniciando sesión...');
					
					// Enviar datos al servlet
					$.ajax({
						url: 'login',
						type: 'POST',
						data: {
							email: email,
							password: password
						},
						success: function(data) {
							console.log('Respuesta del servidor:', data);
							
							// Parsear respuesta JSON
							var response = typeof data === 'string' ? JSON.parse(data) : data;
							
							if (response.success) {
								// Login exitoso
								var tipoUsuario = response.userType;
								var nombreUsuario = response.userName;
								
								// Mostrar alerta de éxito más clara
								if (tipoUsuario === 'LECTOR') {
									alert('✓ Usuario correcto\n\nBienvenido: ' + nombreUsuario + '\nTipo de usuario: Lector');
								} else if (tipoUsuario === 'BIBLIOTECARIO') {
									alert('✓ Usuario correcto\n\nBienvenido: ' + nombreUsuario + '\nTipo de usuario: Bibliotecario');
								} else {
									alert('✓ Usuario correcto\n\nBienvenido: ' + nombreUsuario);
								}
								
								// Redirigir a la página principal
								window.location.href = 'index.jsp';
							} else {
								// Error de login - mostrar mensaje claro
								var mensaje = '✗ Usuario incorrecto\n\n';
								if (response.message && response.message.includes('Credenciales inválidas')) {
									mensaje += 'Las credenciales ingresadas no son válidas. Por favor, verifique su email y contraseña.';
								} else {
									mensaje += (response.message || 'Error de autenticación');
								}
								alert(mensaje);
							}
						},
						error: function(xhr, status, error) {
							console.error('Error en AJAX:', error);
							alert('✗ Error al conectar con el servidor\n\nPor favor, intente nuevamente más tarde.');
						},
						complete: function() {
							// Rehabilitar el botón
							submitBtn.prop('disabled', false).text('Iniciar Sesión');
						}
					});
				} else {
					alert('Por favor, complete todos los campos');
				}
			});
		});
	</script>
</body>
</html>

