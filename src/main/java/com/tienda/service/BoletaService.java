package com.tienda.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.model.CabBoleta;
import com.tienda.model.DetBoleta;
import com.tienda.model.Producto;
import com.tienda.repository.ICabBoletaRepository;
import com.tienda.repository.IDetBoletaRepository;
import com.tienda.repository.IProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class BoletaService {

	@Autowired
	private ICabBoletaRepository repoBoleta; 
	@Autowired 
	private IDetBoletaRepository repoDetalle; 
	@Autowired
	private IProductoRepository repoProd; 
	
	
	public List<CabBoleta> listartodas(){
		return repoBoleta.findAll(); 
	}
	
	@Transactional
	public void generarBoleta(CabBoleta boleta,List<DetBoleta> detalle) {
		boleta.setFecha(LocalDate.now());
		double total = detalle.stream()
				.mapToDouble(d -> d.getCantidad() * d.getPrecio())
				.sum();
		boleta.setTotal(total);
		
		//CabBoleta boletaGuardada = repoBoleta.save(total);
		 CabBoleta boletaGuardada = repoBoleta.save(boleta); 
		for(DetBoleta det:detalle) {
			det.setNumBol(boletaGuardada.getNumBol());
			repoDetalle.save(det); 
			Producto producto = repoProd.findById(det.getIdProd()).get(); 
			producto.setStkProd(producto.getStkProd() - det.getCantidad());
			repoProd.save(producto);
		}
	}
	
	public CabBoleta buscarPorId(int numBol) {
		return repoBoleta.findById(numBol).orElse(null); 
	}
	
	public List<CabBoleta> listarPorUsuario(int codUsua){
		return repoBoleta.findByCodUsua(codUsua); 
	}
	
}
