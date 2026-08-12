package com.tienda.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tienda.model.CabBoleta;
import com.tienda.model.Usuario;
import com.tienda.service.BoletaService;
import com.tienda.service.UsuarioService;

@Controller
@RequestMapping("/boletas")
public class BoletaAdminController {

	@Autowired
	private BoletaService boletaService; 
	@Autowired 
	private UsuarioService usuarioService; 
	
	@GetMapping("/listar")
	public String listarBoletas(Model model) {
		List<CabBoleta> todas = boletaService.listartodas(); 
		
		Map<Usuario, List<CabBoleta>> boletasPorCliente = new LinkedHashMap<>(); 
		
		Map<Integer, Double> totalesPorCliente = new LinkedHashMap<>(); 
		
		for(CabBoleta b :todas) {
			Usuario u = b.getObjUsuario(); 
			if(u!=null) {
				boletasPorCliente
				.computeIfAbsent(u,k -> new java.util.ArrayList<>())
				.add(b); 
				
				totalesPorCliente.merge(u.getCodUsua(),b.getTotal(),Double::sum); 
			}
		}
		
		
		
		
		/*model.addAttribute("boletasPorCliente", boletasPorCliente); 
		model.addAttribute("totalesPorCliente", totalesPorCliente); 
		model.addAttribute("totalBoletas", todas.size());
		return "crudBoletas"; */
		// ✅ NUEVO
	    double totalRecaudado = totalesPorCliente.values()
	            .stream()
	            .mapToDouble(Double::doubleValue)
	            .sum();
	    
	    model.addAttribute("boletasPorCliente", boletasPorCliente); 
	    model.addAttribute("totalesPorCliente", totalesPorCliente); 
	    model.addAttribute("totalBoletas", todas.size());
	    model.addAttribute("totalRecaudado", totalRecaudado); // ✅ NUEVO
	    return "crudBoletas";
	}
	
	@GetMapping("/cliente/{codUsua}") 
	public String boletasPorCliente(@PathVariable int codUsua, Model model) {
		Usuario usuario = usuarioService.ObtnerPorId(codUsua).orElse(null); 
		
		List<CabBoleta> boletas = boletaService.listarPorUsuario(codUsua); 
		
		double totalGastado = boletas.stream()
				.mapToDouble(CabBoleta::getTotal)
				.sum(); 
		
		model.addAttribute("usuario",usuario);
		model.addAttribute("lstBoletas",boletas);
		model.addAttribute("totalGastado",totalGastado);
		return "boletascliente"; 
		
	}
	
	@GetMapping("/detalle/{numBol}")
	public String verDetalle(@PathVariable int numBol, Model model) {
		model.addAttribute("boleta", boletaService.buscarPorId(numBol)); 
		return "detalleboleta"; 
	}
}
