package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import publicadores.ControladorPublish;
import publicadores.ControladorPublishService;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public LoginServlet() {
        super();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Obtener parámetros del formulario
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            System.out.println("=== LOGIN SERVLET ===");
            System.out.println("Email: " + email);
            System.out.println("Password: " + password);
            
            if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
                // Enviar respuesta de error
                String jsonResponse = "{\"success\":false,\"message\":\"Por favor, complete todos los campos\",\"userType\":null,\"userName\":null}";
                response.getWriter().write(jsonResponse);
                return;
            }
            
            // Conectar con el servicio web
            ControladorPublishService service = new ControladorPublishService();
            ControladorPublish controlador = service.getControladorPublishPort();
            
            // Llamar al método de autenticación
            String resultado = controlador.autenticarUsuario(email, password);
            
            System.out.println("Resultado del login: " + resultado);
            
            // Procesar resultado
            String jsonResponse;
            if (resultado.startsWith("LECTOR:")) {
                String[] partes = resultado.split(":");
                String tipo = partes[0];
                String id = partes[1];
                String nombre = partes[2];
                
                // Guardar en sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", nombre);
                session.setAttribute("tipoUsuario", tipo);
                session.setAttribute("email", email);
                session.setAttribute("id", id);
                
                jsonResponse = String.format(
                    "{\"success\":true,\"message\":\"Login exitoso\",\"userType\":\"%s\",\"userName\":\"%s\"}",
                    tipo, nombre
                );
                response.getWriter().write(jsonResponse);
                
            } else if (resultado.startsWith("BIBLIOTECARIO:")) {
                String[] partes = resultado.split(":");
                String tipo = partes[0];
                String id = partes[1];
                String nombre = partes[2];
                
                // Guardar en sesión
                HttpSession session = request.getSession();
                session.setAttribute("usuario", nombre);
                session.setAttribute("tipoUsuario", tipo);
                session.setAttribute("email", email);
                session.setAttribute("id", id);
                
                jsonResponse = String.format(
                    "{\"success\":true,\"message\":\"Login exitoso\",\"userType\":\"%s\",\"userName\":\"%s\"}",
                    tipo, nombre
                );
                response.getWriter().write(jsonResponse);
                
            } else {
                // Error de autenticación
                jsonResponse = String.format(
                    "{\"success\":false,\"message\":\"%s\",\"userType\":null,\"userName\":null}",
                    resultado.replace("\"", "\\\"")
                );
                response.getWriter().write(jsonResponse);
            }
            
        } catch (Exception e) {
            System.err.println("ERROR en LoginServlet: " + e.getMessage());
            e.printStackTrace();
            
            String errorMessage = "Error al conectar con el servidor: " + e.getMessage();
            String jsonResponse = String.format(
                "{\"success\":false,\"message\":\"%s\",\"userType\":null,\"userName\":null}",
                errorMessage.replace("\"", "\\\"")
            );
            response.getWriter().write(jsonResponse);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirigir al login
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}

