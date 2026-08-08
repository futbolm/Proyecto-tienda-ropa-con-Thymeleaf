package com.tienda.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.model.Categoria;
import com.tienda.repository.ICategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private ICategoriaRepository repoCat; 
	
	public List<Categoria>listarTodas(){
		return repoCat.findAll(); 
	}
	
	public Optional<Categoria>obtenerPorId(int id){
		return repoCat.findById(id); 
	}
	
	public void grabar(Categoria categoria) {
		repoCat.save(categoria); 
	}
	
	public void activar(int id) {
		obtenerPorId(id).ifPresent(p -> {
			p.setEstCategoria(1); 
			repoCat.save(p); 
		});
	}
	
	public void desactivar(int id) {
		obtenerPorId(id).ifPresent(p -> {
			p.setEstCategoria(2); 
			repoCat.save(p); 
		});
	}
}
