package com.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.model.Producto;
import com.tienda.repository.IProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private IProductoRepository repoProd; 
	
	public List<Producto>listarTodos(){
		return repoProd.findAll(); 
	}
	
	public Optional<Producto>ObtenerPorId(String idProd){
		return repoProd.findById(idProd); 
	}
	
	public void grabar(Producto producto) {
		repoProd.save(producto); 
	}
	
	public void activar(String idProd) {
		ObtenerPorId(idProd).ifPresent(p -> {
			p.setEstProd(1); 
			repoProd.save(p); 
		});
	}
	
	public void desactivar(String idProd) {
		ObtenerPorId(idProd).ifPresent(p ->{
			p.setEstProd(2); 
			repoProd.save(p); 
		});
	}
	
	
}
