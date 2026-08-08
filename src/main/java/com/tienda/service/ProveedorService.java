package com.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.model.Proveedor;
import com.tienda.repository.IProveedorRepository;

@Service
public class ProveedorService {

	@Autowired
	private IProveedorRepository repoProv; 
	
	public List<Proveedor>listarTodos(){
		return repoProv.findAll(); 
	}
	
	public Optional<Proveedor>obtenerPorId(int id){
		return repoProv.findById(id); 
	}
	
	public void grabar(Proveedor proveedor) {
		repoProv.save(proveedor); 
	}
	
	public void activar(int id) {
		obtenerPorId(id).ifPresent(p->{
			p.setEstProveedor(1); 
			repoProv.save(p);
		});
	}
	
	public void desactivar(int id) {
		obtenerPorId(id).ifPresent(p->{
			p.setEstProveedor(2); 
			repoProv.save(p);
		});
	}
}
