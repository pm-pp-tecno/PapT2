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
        
        // Verificar si está intentando acceder a login.jsp o al servlet /login
        boolean isLoginPage = requestURI.endsWith("login.jsp") || requestURI.endsWith("/login");
        
        // Verificar si hay una sesión activa
        boolean isLoggedIn = (session != null && session.getAttribute("usuario") != null);
        
        // Si el usuario NO está logueado y NO está en login, redirigir a login
        if (!isLoggedIn && !isLoginPage) {
            System.out.println("Usuario no autenticado, redirigiendo a login desde: " + requestURI);
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
            return;
        }
        
        // Si el usuario está logueado y trata de acceder a login, redirigir a index
        if (isLoggedIn && isLoginPage) {
            System.out.println("Usuario ya autenticado, redirigiendo a index desde login");
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.jsp");
            return;
        }
        
        // Permitir que la request continúe
        chain.doFilter(request, response);
    }
    
    @Override
    public void destroy() {
        // Limpiar recursos si es necesario
    }
}

