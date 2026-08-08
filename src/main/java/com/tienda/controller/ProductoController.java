package com.tienda.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tienda.model.Categoria;
import com.tienda.model.Proveedor;
import com.tienda.model.Producto;
import com.tienda.service.CategoriaService;
import com.tienda.service.ProductoService;
import com.tienda.service.ProveedorService;

@Controller
@RequestMapping("/Productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService; 
	
	@Autowired
	private CategoriaService categoriaService; 
	
	@Autowired
	private ProveedorService proveedorService; 
	
	private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/productos/"; 
	
	private void cargarLista(Model model) {
		
		List<Categoria>categoriasActivas = categoriaService.listarTodas()
				.stream()
				.filter(c-> c.getEstCategoria() == 1)
				.collect(Collectors.toList()); 
		
		List<Proveedor>proveedoresActivos = proveedorService.listarTodos()
				.stream()
				.filter(p->p.getEstProveedor() == 1)
				.collect(Collectors.toList()); 
		
		model.addAttribute("lstCategoria", categoriasActivas); 
		model.addAttribute("lstProveedores", proveedoresActivos); 
		model.addAttribute("lstProductos", productoService.listarTodos()); 
		
	}
	
	@GetMapping("/cargar")
	public String cargarPag(Model model) {
		model.addAttribute("producto", new Producto()); 
		cargarLista(model); 
		return "crudproductos"; 
	}
	
	@GetMapping("/nuevo")
	public String nuevoProducto(Model model) {
		model.addAttribute("producto", new Producto()); 
		cargarLista(model); 
		return "registrarProducto";
	}
	
	@PostMapping("/grabar")
	public String grabarProducto(@Validated @ModelAttribute Producto producto , BindingResult result, Model model, 
				@RequestParam("archivoImagen") MultipartFile archivo
			) {
		
		if(result.hasErrors()) {
			cargarLista(model); 
			return "registrarProducto"; 
		}
		
		if(!archivo.isEmpty()) {
			try {
				String nombreArchivo = producto.getIdProd() + "_" + archivo.getOriginalFilename(); 
				 Path ruta = Paths.get(UPLOAD_DIR + nombreArchivo); 
				 Files.write(ruta, archivo.getBytes()); 
				 producto.setImagen(nombreArchivo); 
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace(); 
			}
		}
		else {
			producto.setImagen("default.jpg"); 
		}
		
		//producto.setEstProd(1);
		productoService.grabar(producto); 
		return "redirect:/Productos/cargar?exito"; 
		
	}
	
	@GetMapping("/editar/{idProd}")
	public String editarProducto(@PathVariable("idProd") String idProd, Model model) {
		Optional<Producto> resultado = productoService.ObtenerPorId(idProd); 
		if(resultado.isEmpty()) return "redirect:/Productos/cargar"; 
		model.addAttribute("producto", resultado.get()); 
		cargarLista(model); 
		return "editarProducto"; 
	}
	
	@PostMapping("/actualizar")
	public String actualizarProducto(@Validated @ModelAttribute Producto producto, BindingResult result, Model model, 
			@RequestParam("archivoImagen") MultipartFile archivo
			) {
		
		if(result.hasErrors()) {
			cargarLista(model); 
			return "editarProducto"; 
		}
		
		Optional<Producto> anterior = productoService.ObtenerPorId(producto.getIdProd()); 
		
		if(!archivo.isEmpty()) {
			try {
				String nombreArchivo = producto.getIdProd() + "_" + archivo.getOriginalFilename(); 
				Path ruta = Paths.get(UPLOAD_DIR + nombreArchivo); 
				Files.write(ruta,archivo.getBytes());
				producto.setImagen(nombreArchivo); 
			}catch (Exception e) {
				e.printStackTrace(); 
			}
		}else {
			//Optional<Producto> anterior = productoService.ObtenerPorId(producto.getIdProd()); 
			anterior.ifPresent(p -> producto.setImagen(p.getImagen())); 
		}
		
		 // ✅ Preservar fechaRegistro del producto original
	    anterior.ifPresent(p -> producto.setFechaRegistro(p.getFechaRegistro()));
		
		
		productoService.grabar(producto); 
		//return "redirect:/Productos/editar/" + producto.getIdProd() + "?exito"; 
		return "redirect:/Productos/cargar?exito";
		
	}
	
	@GetMapping("/activar/{idProd}")
	public String activarProducto(@PathVariable("idProd")String idProd) {
		productoService.activar(idProd); 
		return "redirect:/Productos/cargar?activado"; 
	}
	
	@GetMapping("/desactivar/{idProd}")
	public String desactivar(@PathVariable("idProd")String idProd) {
		productoService.desactivar(idProd); 
		return "redirect:/Productos/cargar?desactivado"; 
	}
	

}
