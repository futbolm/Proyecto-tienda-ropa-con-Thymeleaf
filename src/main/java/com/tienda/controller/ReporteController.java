package com.tienda.controller;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tienda.model.CabBoleta;
import com.tienda.model.DetBoleta;
import com.tienda.service.BoletaService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

@Controller
public class ReporteController {
	@Autowired
	private BoletaService boletaService; 
	
	@GetMapping("/boletas/imprimir/{numBol}")
	public ResponseEntity<byte[]> imprimirBoleta(@PathVariable int numBol) throws Exception{
		//esto agregamos
		System.setProperty("java.awt.headless", "true");
		
		CabBoleta boleta = boletaService.buscarPorId(numBol); 
		List<DetBoleta> detalle = boleta.getDetalle(); 
		
		Map<String, Object> params = new HashMap<>(); 
		params.put("numBoleta",boleta.getNumBol()); 
		params.put("fechaBoleta",boleta.getFecha().toString()); 
		params.put("clienteId",boleta.getCodUsua()); 
		params.put("totalBoleta",boleta.getTotal());
		
		String nombreCliente = boleta.getObjUsuario() != null ? boleta.getObjUsuario().getNomUsua() + " " + boleta.getObjUsuario().getApeUsua() : 
			"Cliente " + boleta.getCodUsua(); 
		params.put("nombreCliente", nombreCliente); 
		
		List<Map<String, Object>> datos = new ArrayList<>(); 
		for(DetBoleta det : detalle) {
			Map<String, Object> fila = new HashMap<>(); 
			fila.put("desProd", det.getObjProducto() != null ? det.getObjProducto().getDesProd() : det.getIdProd()); 
			fila.put("cantidad",det.getCantidad()); 
			fila.put("precio", det.getPrecio()); 
			datos.add(fila); 
		}
		
		InputStream stream = getClass().getResourceAsStream("/reports/boleta.jrxml");
		JasperReport jaspertReport = JasperCompileManager.compileReport(stream); 
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(datos); 
		JasperPrint jasperprint = JasperFillManager.fillReport(jaspertReport, params,dataSource); 
		byte[] pdf = JasperExportManager.exportReportToPdf(jasperprint); 
		
		HttpHeaders headers = new HttpHeaders(); 
		headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("inline", "boleta_" + numBol + ".pdf");
        return ResponseEntity.ok().headers(headers).body(pdf);
	}
}
