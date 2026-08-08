package com.tienda.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tienda.model.CabBoleta;
import com.tienda.model.DetBoleta;
import com.tienda.model.Producto;
import com.tienda.model.Usuario;
import com.tienda.service.BoletaService;
import com.tienda.service.ProductoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tienda")

public class BoletaController {

	@Autowired
	private ProductoService productoService; 
	
	@Autowired
	private BoletaService boletaService; 
	
	@GetMapping("/inicio")
	public String mostrarTienda(Model model, HttpSession session ) {
		List<Producto> productosActivos = productoService.listarTodos()
				.stream()
				.filter(p -> p.getEstProd()==1)
				.collect(Collectors.toList()); 
		model.addAttribute("lstProductos", productosActivos);
		
		if(session.getAttribute("carrito")==null) {
			session.setAttribute("carrito", new ArrayList<DetBoleta>()); 
		}
		
		List<DetBoleta>carrito = (List<DetBoleta>) session.getAttribute("carrito");
		double total = carrito.stream()
				.mapToDouble(d -> d.getCantidad() * d.getPrecio())
				.sum(); 
		
		model.addAttribute("carrito", carrito); 
		model.addAttribute("totalCarrito", total); 
		
		Usuario usuario = (Usuario) session.getAttribute("usuarioSesion");  
		if(usuario != null) {
			model.addAttribute("nombreUsuario", usuario.getNomUsua() + " " + usuario.getApeUsua()); 
			
		}
		return "tienda";
	}
	
	
	@PostMapping("/agregar")
	public String agregarAlCarrito(@RequestParam String idProd, @RequestParam int cantidad,HttpSession session ) {
		Producto producto = productoService.ObtenerPorId(idProd).orElse(null); 
		
		if(producto == null) {
			return "redirect:/tienda/inicio"; 
		}
		
		List<DetBoleta> carrito =  (List<DetBoleta>) session.getAttribute("carrito"); 
		if(carrito == null) carrito = new ArrayList<>(); 
		
		boolean existe = false; 
		for(DetBoleta det: carrito ) {
			if(det.getIdProd().equals(idProd)) {
				det.setCantidad(det.getCantidad() + cantidad);
				existe = true; 
				break; 
			}
		}
		
		
		if(!existe) {
			DetBoleta det = new DetBoleta(); 
			det.setIdProd(idProd); 
			det.setCantidad(cantidad); 
			det.setPrecio(producto.getPreProd()); 
			det.setObjProducto(producto); 
			carrito.add(det); 
		}
		
		session.setAttribute("carrito", carrito); 
		return "redirect:/tienda/inicio"; 
	}
	
	@GetMapping("/eliminar-carrito/{idProd}")
	public String eliminarDelCarrito(@PathVariable String idProd , HttpSession session) {
		List<DetBoleta> carrito = (List<DetBoleta>) session.getAttribute("carrito"); 
		if(carrito !=null) {
			carrito.removeIf(det ->det.getIdProd().equals(idProd));
			session.setAttribute("carrito", carrito); 
		}
		
		return "redirect:/tienda/inicio"; 
	}
	
	@PostMapping("/confirmar")
	public String confirmarBoleta(HttpSession session) {
		List<DetBoleta> carrito = (List<DetBoleta>) session.getAttribute("carrito"); 
		if(carrito == null || carrito.isEmpty()) {
			return "redirect:/tienda/inicio?vacio"; 
		}
		
		Usuario usuario = (Usuario) session.getAttribute("usuarioSession"); 
		CabBoleta boleta = new CabBoleta();   
		boleta.setCodUsua(usuario.getCodUsua()); 
		boletaService.generarBoleta(boleta, carrito); 
		
		session.removeAttribute("carrito"); 
		return "redirect:/tienda/inicio?exito"; 
	}
	
	@GetMapping("/mis-boletas")
	public String misBoletas(Model model , HttpSession session) {
		Usuario usuario = (Usuario) session.getAttribute("usuarioSession")  ; 
		model.addAttribute("lstBoletas", boletaService.listarPorUsuario(usuario.getCodUsua())); 
		return "misboletas"; 
	}
	
	@GetMapping("/mi-boleta/{numBol}")
	public String miDetalleBoleta(@PathVariable int numBol , Model model) {
		model.addAttribute("boleta", boletaService.buscarPorId(numBol)); 
		return "midetalleboleta"; 
	}
}
