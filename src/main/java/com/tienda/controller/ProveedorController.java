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

import com.tienda.model.Proveedor;

import com.tienda.service.ProveedorService;

@Controller
@RequestMapping("/Proveedores")
public class ProveedorController {

   
	@Autowired
	private ProveedorService proveedorService;

    
	private void cargarLista(Model model) {
		model.addAttribute("lstProveedores", proveedorService.listarTodos()); 
	}
	
	@GetMapping("/cargar")
	public String cargarProveedor(Model model) {
		model.addAttribute("proveedor", new Proveedor()); 
		cargarLista(model); 
		return "crudProveedores"; 
	}
	
	@GetMapping("/editar/{id}")
	public String editarProveedor(@PathVariable("id") int id, Model model) {
		Optional<Proveedor>resultado = proveedorService.obtenerPorId(id); 
		if(resultado.isEmpty()) return "redirect:/Proveedores/cargar"; 
		model.addAttribute("proveedor", resultado.get()); 
		cargarLista(model); 
		//return "redirect:/Proveedores/cargar?exito"; 
		return "crudProveedores";
	}
	
	@PostMapping("/grabar")
	public String grabarProveedor(@Validated @ModelAttribute Proveedor proveedor , BindingResult result , Model model) {
		if(result.hasErrors()) {
			cargarLista(model); 
			return "crudProveedores"; 
		}
		
		proveedor.setEstProveedor(1); 
		proveedorService.grabar(proveedor); 
		return "redirect:/Proveedores/cargar?exito"; 
	}
	
	@GetMapping("/activar/{id}")
	public String activarCategoria(@PathVariable("id") int id) {
		proveedorService.activar(id); 
		return "redirect:/Proveedores/cargar?activado"; 
	}
	
	@GetMapping("/desactivar/{id}")
	public String desactivarCategoria(@PathVariable("id") int id) {
		proveedorService.desactivar(id); 
		return "redirect:/Proveedores/cargar?desactivado"; 
	}
	
}
