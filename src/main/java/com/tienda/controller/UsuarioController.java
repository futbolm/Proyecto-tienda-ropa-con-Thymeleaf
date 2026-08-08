package com.tienda.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienda.model.Usuario;
import com.tienda.service.TipoService;
import com.tienda.service.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/Usuarios")
public class UsuarioController {

	@Autowired
	private  UsuarioService usuarioService; 
	@Autowired TipoService tipoService;
	
	@GetMapping("/cargar")
	public String cargarPag(Model model) {
		model.addAttribute("lstUsuarios", usuarioService.ListarTodos()); 
		return "crudUsuarios"; 
	}
	
	@GetMapping("/nuevo")
	public String nuevoUsuario(Model model ) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("lstTipos", tipoService.listarTipos()); // o como lo tengas
		return "registrarUsuario";
	}
	
	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") int id , Model model) {
		Optional<Usuario> resultado = usuarioService.ObtnerPorId(id); 
		if(resultado.isEmpty()) return "redirect:/Usuarios/cargar"; 
		model.addAttribute("usuario", resultado.get()); 
		model.addAttribute("lstTipos", tipoService.listarTipos()); // o como lo tengas
		return "editarUsuario"; 
	}
	
	@PostMapping("/grabar")
	public String grabarUsuario(@Validated @ModelAttribute Usuario usuario , BindingResult result , Model model) {
		model.addAttribute("lstTipos", tipoService.listarTipos()); // o como lo tengas
		
		if(usuario.getPassword() != null && !usuario.getPassword().isEmpty() && usuario.getPassword().length() < 6){
	        result.rejectValue("password", "error", "La contraseña debe tener mínimo 6 caracteres");
	    }
		
		if(result.hasErrors()) {
			model.addAttribute("lstTipos", tipoService.listarTipos()); // o como lo tengas
			return "registrarUsuario"; 
		}
		
		Usuario ExistenteCorreo = usuarioService.buscarPorUsername(usuario.getEmail()); 
		if(ExistenteCorreo != null) {
			result.rejectValue("email", "usuario.error","Este correo ya esta registrado , ingrese otro"); 
			return "registrarUsuario"; 
		}
		
		Usuario ExistenteDni = usuarioService.buscarPorDni(usuario.getDni()); 
		if(ExistenteDni != null ) {
			result.rejectValue("dni", "dni.error", "Dni existente , ingrese otro por favor "); 
			return "registrarUsuario"; 
		}
		
		usuario.setFnaUsua(java.time.LocalDate.now()); 
		usuarioService.grabar(usuario); 
		return "redirect:/Usuarios/cargar?exito";
		
	}
	
	/*@PostMapping("/actualizar")
	public String actualizarUsuario(@Validated @ModelAttribute Usuario usuario , BindingResult result , Model model) {
		
		if(result.hasErrors()) {
			model.addAttribute("lstTipos", tipoService.listarTipos()); 
			return "editarUsuario"; 
		}
		
		Optional<Usuario> original = usuarioService.ObtnerPorId(usuario.getCodUsua()); 
		original.ifPresent(u -> {
			usuario.setFnaUsua(u.getFnaUsua()); 
			usuario.setPassword(u.getPassword()); 
		}); 
		
		usuarioService.grabar(usuario); 
		return "redirect:/Usuarios/cargar?Exito"; 
		
	}*/
	
	@PostMapping("/actualizar")
	public String actualizarUsuario(@Validated @ModelAttribute Usuario usuario , BindingResult result , Model model) {
		model.addAttribute("lstTipos", tipoService.listarTipos()); // o como lo tengas

	    Optional<Usuario> original = usuarioService.ObtnerPorId(usuario.getCodUsua()); 

	    // validacion para el password que debe contener 6 caracteres minimos 
	    // en el model usuario en el atributo password le quitamos el @notBlack y @Size , por que aqui 
	    // vamos a validar que minimo son 6 digitos para el password, y le quitamos el @notBlack para poder hacer esto 
	    // cuando editemos podamos modificar la contraseña o dejarla vacia y funcione todo 
	    if(usuario.getPassword() != null && !usuario.getPassword().isEmpty() && usuario.getPassword().length() < 6){
	        result.rejectValue("password", "error", "La contraseña debe tener mínimo 6 caracteres");
	    }

	    if(result.hasErrors()){
	        model.addAttribute("lstTipos", tipoService.listarTipos());
	        return "editarUsuario"; 
	    }
	    
	    
	    Usuario ExistenteCorreo = usuarioService.buscarPorUsername(usuario.getEmail()); 
		if(ExistenteCorreo != null) {
			result.rejectValue("email", "usuario.error","Este correo ya esta registrado , ingrese otro"); 
			return "registrarUsuario"; 
		}
		
		Usuario ExistenteDni = usuarioService.buscarPorDni(usuario.getDni()); 
		if(ExistenteDni != null ) {
			result.rejectValue("dni", "dni.error", "Dni existente , ingrese otro por favor "); 
			return "registrarUsuario"; 
		}
	    

	    original.ifPresent(u -> {
	        usuario.setFnaUsua(u.getFnaUsua()); 
	        
	        // Si el campo del password lo dejemos vacio , mantiene su contraseña actual 
	        if(usuario.getPassword() == null || usuario.getPassword().isEmpty()){
	            usuario.setPassword(u.getPassword());
	        }
	    });

	    usuarioService.grabar(usuario); 
	    return "redirect:/Usuarios/cargar?Exito"; 
	}
	
	@GetMapping("/activar/{id}")
	public String activarUsuario(@PathVariable("id") int id) {
		usuarioService.activar(id); 
		return "redirect:/Usuarios/cargar?activado";
	}
	
	@GetMapping("/desactivar/{id}")
	public String desactivarUsuario(@PathVariable("id") int id) {
		usuarioService.desactivar(id); 
		return "redirect:/Usuarios/cargar?desactivado";
	}
	
}
