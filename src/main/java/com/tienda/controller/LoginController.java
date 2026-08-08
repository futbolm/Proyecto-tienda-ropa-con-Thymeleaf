package com.tienda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.model.Usuario;
import com.tienda.service.UsuarioService;

import jakarta.servlet.http.HttpSession;



@Controller
public class LoginController {
	
	@Autowired
	private UsuarioService usuarioService; 
	
	@GetMapping("/login")
	public String mostrarLogin(@RequestParam(value="error", required = false) String error, 
			@RequestParam(value="logout", required = false) String logout,
			Model model) {
		if(error !=null) {
			model.addAttribute("mensaje", "Usuario o contraseña incorrecto"); 
		}
		if(logout !=null) {
			model.addAttribute("mensajeSalida", "Session cerrada correctamente"); 
		}
		
		return "login"; 
	}
	
	
	@PostMapping("/login")
	public String procesarLogin(
			@RequestParam("username") String username, 
			@RequestParam("password") String password, 
			HttpSession session, 
			Model model
			) {
		
		 System.out.println("=== LOGIN DEBUG ===");
		    System.out.println("Username recibido: " + username);
		    System.out.println("Password recibido: " + password);
		
		Usuario usuario = usuarioService.autenticar(username, password); 
		
		  System.out.println("Usuario encontrado: " + usuario);
		
		if(usuario == null) {
			model.addAttribute("mensaje", "Usuario o contrasela incorrecto");
			return "login"; 
		}
		
		session.setAttribute("usuarioSession", usuario); 
		
		if(usuario.getIdTipo()==1) {
			 System.out.println("Redirigiendo a /dashboard");  // ← aquí
			return "redirect:/dashboard"; 
		}
		else {
			return "redirect:/tienda/inicio"; 
		}
	}
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate(); 
		return "redirect:/login?logout"; 
	}
	
	@GetMapping("/")
	public String raiz() {
		return "redirec:/login"; 
	}
	
	
}
