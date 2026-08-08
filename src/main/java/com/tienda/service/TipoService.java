package com.tienda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.model.Tipo;
import com.tienda.repository.ITipoRepository;

@Service
public class TipoService {
	@Autowired
	private ITipoRepository repoTipo;
	
	public List<Tipo>listarTipos(){
		return repoTipo.findAll(); 
	}
}
