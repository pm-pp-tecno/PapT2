package servlets;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización del filtro
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        HttpSession session = httpRequest.getSession(false);
        String requestURI = httpRequest.getRequestURI();
        
        // Verificar si está intentando acceder a login.jsp, /login o al servlet /loginProcess
        boolean isLoginPage = requestURI.endsWith("login.jsp") || requestURI.endsWith("/login");
        boolean isLoginProcess = requestURI.endsWith("/loginProcess");
        
        // Verificar si hay una sesión activa
        boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);
        
        // Si el usuario NO está logueado y NO está en login ni en loginProcess, redirigir a login
        if (!isLoggedIn && !isLoginPage && !isLoginProcess) {
            System.out.println("Usuario no autenticado, redirigiendo a login desde: " + requestURI);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return;
        }
        
        // Si el usuario está logueado y trata de acceder a login, redirigir a index
        if (isLoggedIn && isLoginPage) {
            System.out.println("Usuario ya autenticado, redirigiendo a index desde login");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index");
            return;
        }
        
        // Control de acceso basado en roles
        if (isLoggedIn) {
            String tipoUsuario = (String) session.getAttribute("tipoUsuario");
            
            // Páginas que solo pueden ver los BIBLIOTECARIOS
            boolean isGestionUsuarios = requestURI.endsWith("gestionUsuarios.jsp") || requestURI.endsWith("/gestionUsuarios");
            boolean isGestionMateriales = requestURI.endsWith("gestionMateriales.jsp") || requestURI.endsWith("/gestionMateriales");
            boolean isConsultas = requestURI.endsWith("consultas.jsp") || requestURI.endsWith("/consultas");
            
            if ((isGestionUsuarios || isGestionMateriales || isConsultas) && !"BIBLIOTECARIO".equals(tipoUsuario)) {
                System.out.println("Usuario " + tipoUsuario + " intentando acceder a página restringida: " + requestURI);
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index");
                return;
            }
        }
        
        // Permitir que la request continúe
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Limpiar recursos si es necesario
    }
}

