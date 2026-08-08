package com.tienda.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.tienda.model.Usuario;



@Component
public class SesionInterceptor implements HandlerInterceptor {

	 @Override
	    public boolean preHandle(HttpServletRequest request,
	                             HttpServletResponse response,
	                             Object handler) throws Exception {

	        HttpSession session = request.getSession(false);
	        String uri = request.getRequestURI();

	        // Rutas públicas que no requieren sesión
	        if (uri.equals("/login") || uri.equals("/") ||
	            uri.startsWith("/css/") || uri.startsWith("/js/") ||
	            uri.startsWith("/img/") || uri.startsWith("/webjars/")) {
	            return true;
	        }

	        // Verificar si hay sesión activa
	        if (session == null || session.getAttribute("usuarioSession") == null) {
	            response.sendRedirect("/login");
	            return false;
	        }

	        Usuario usuario = (Usuario) session.getAttribute("usuarioSession");

	        
	     // ✅ AGREGA ESTA LÍNEA - permite /boletas/imprimir a clientes Y admins
	        if (uri.startsWith("/boletas/imprimir")) {
	            return true; // cualquier usuario logueado puede acceder 
	        }
	        
	        // Rutas solo para ADMIN (idtipo == 1)
	        boolean esAdmin = usuario.getIdTipo() == 1;
	        boolean esCliente = usuario.getIdTipo() == 2;

	        if (uri.startsWith("/dashboard") || uri.startsWith("/Productos") ||
	            uri.startsWith("/Categorias") || uri.startsWith("/Proveedores") ||
	            uri.startsWith("/Usuarios") || uri.startsWith("/boletas")) {
	            if (!esAdmin) {
	                response.sendRedirect("/acceso-denegado");
	                return false;
	            }
	        }

	        // Rutas solo para CLIENTE (idtipo == 2)
	        if (uri.startsWith("/tienda") ) {
	            if (!esCliente) {
	                response.sendRedirect("/acceso-denegado");
	                return false;
	            }
	        }

	        return true;
	    }
}
