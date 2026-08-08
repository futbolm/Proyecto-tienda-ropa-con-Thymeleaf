package com.tienda.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.tienda.model.Producto;
import com.tienda.service.BoletaService;
import com.tienda.service.CategoriaService;
import com.tienda.service.ProductoService;
import com.tienda.service.ProveedorService;
import com.tienda.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {
	@Autowired
	private ProductoService productoService; 
	
	@Autowired
	private ProveedorService proveedorService; 
	
	@Autowired
	private CategoriaService categoriaService; 
	
	@Autowired 
	private UsuarioService usuarioService; 
	
	@Autowired
	private BoletaService boletaService; 
	@GetMapping("/dashboard")
	
	public String mostrarDashboard(Model model, HttpSession session) {
		model.addAttribute("totalProductos", productoService.listarTodos().size()); 
		model.addAttribute("totalCategorias", categoriaService.listarTodas().size()); 
		model.addAttribute("totalProveedores", proveedorService.listarTodos().size()); 
		model.addAttribute("totalUsuarios", usuarioService.ListarTodos().size()); 
		model.addAttribute("totalBoleta", boletaService.listartodas().size());
		//System.out.println("=== DASHBOARD ALCANZADO ===");
		
		
		model.addAttribute("lstProductos", productoService.listarTodos());
		double totalHoy = boletaService.listartodas()
				.stream()
				.mapToDouble(b -> b.getTotal())
				.sum() ;
		model.addAttribute("totalHoy", totalHoy); 
		
		/*double totalHoy = boletaService.listarTodas().stream()
                .filter(b -> b.getFch_bol().equals(java.time.LocalDate.now()))
                .mapToDouble(b -> b.getTotal())
                .sum();
        model.addAttribute("totalHoy", totalHoy);*/
		
		List<Producto>stockBajo = productoService.listarTodos()
				.stream()
				.filter(p -> p.getStkProd() < 10)
				.collect(Collectors.toList()); 
		
		model.addAttribute("lstStockBajo", stockBajo); 
		model.addAttribute("totalStockBajo", stockBajo.size()); 
		return "dashboard"; 
	}
}
