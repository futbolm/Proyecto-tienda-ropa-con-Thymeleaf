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

import com.tienda.model.Categoria;
import com.tienda.service.CategoriaService;

@Controller
@RequestMapping("/Categorias")
public class CategoriaController {

	@Autowired private 
	CategoriaService categoriaService;
	
	private void cargarLista(Model model) {
		model.addAttribute("lstCategorias", categoriaService.listarTodas()); 
	}
	
	
	@GetMapping("/cargar")
	public String cargarPagina(Model model) {
		model.addAttribute("categoria", new Categoria()); 
		cargarLista(model); 
		return "crudCategorias"; 
	}
	
	@GetMapping("/editar/{id}")
	public String editarCategoria(@PathVariable("id") int id, Model model) {
		Optional<Categoria> resultado = categoriaService.obtenerPorId(id); 
		if(resultado.isEmpty()) return "redirect:/Categorias/cargar"; 
		model.addAttribute("categoria", resultado.get()); 
		cargarLista(model); 
		return "crudCategorias"; 
	}
	
	@PostMapping("/grabar")
	public String grabarCategoria(@Validated @ModelAttribute Categoria categoria, BindingResult  result ,Model model) {
		if(result.hasErrors()) {
			cargarLista(model); 
			return "crudCategorias"; 
		}
		
		
		categoria.setEstCategoria(1); 
		categoriaService.grabar(categoria); 
		return "redirect:/Categorias/cargar?exito"; 
	}
	
	
	@GetMapping("/activar/{id}")
	public String activarCategoria(@PathVariable("id") int id) {
		categoriaService.activar(id); 
		return "redirect:/Categorias/cargar?activado"; 
	}
	
	@GetMapping("/desactivar/{id}")
	public String desctivarCategoria(@PathVariable("id") int id) {
		categoriaService.desactivar(id); 
		return "redirect:/Categorias/cargar?desactivado"; 
	}
	
	
	
	
	
}
