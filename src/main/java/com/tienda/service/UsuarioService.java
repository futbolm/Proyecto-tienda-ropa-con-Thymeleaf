package com.tienda.service;

import java.util.List;
import java.util.Optional;
import com.tienda.session.SesionConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.model.Usuario;
import com.tienda.repository.IUsuarioRepository;

@Service
public class UsuarioService {

   /* private final SesionConfig sesionConfig;*/
	@Autowired
	private IUsuarioRepository repoUsua;

    /*UsuarioService(SesionConfig sesionConfig) {
        this.sesionConfig = sesionConfig;
    } */
	
	/*
	 * 
	 * public Usuario autenticar(String username, String password) {
        Usuario usuario = repoUsuario.findByUsernameAndClave(username, password);
        if (usuario == null) return null;
        if (usuario.getEst_usua() != 1) return null;
        return usuario;
    }
	 * 
	 * */
	
	/*Para autenticar usuario*/
	/*public Usuario autenticar(String email , String password ) {
		Usuario usuario = repoUsua.findByEmailAndPassword(email, password); 
		if(usuario == null) return null; 
		if(usuario.getEstUsua()!=1) return null; 
		return usuario; 
		
	}*/
	/* Para autenticar usuario */
    public Usuario autenticar(String email, String password) {
        String emailLimpio    = (email    != null) ? email.trim()    : null;
        String passwordLimpia = (password != null) ? password.trim() : null;

        Usuario usuario = repoUsua.findByEmailAndPassword(emailLimpio, passwordLimpia);
        if (usuario == null) return null;
        if (usuario.getEstUsua() != 1) return null;
        return usuario;
    }
	
	public List<Usuario>ListarTodos(){
		return repoUsua.findAll(); 
	}
	
	public Optional<Usuario>ObtnerPorId(int id){
		return repoUsua.findById(id); 
	}
	
	public void grabar(Usuario usuario) {
		repoUsua.save(usuario); 
	}
	
	public Usuario buscarPorUsername(String email) {
		return repoUsua.findByEmail(email); 
	}
	
	public Usuario buscarPorDni(String dni) {
		return repoUsua.findByDni(dni); 
	}
	
	public void activar(int id) {
		ObtnerPorId(id).ifPresent(u -> {
			u.setEstUsua(1); 
			repoUsua.save(u); 
		});
	}
	
	public void desactivar(int id) {
		ObtnerPorId(id).ifPresent(u -> {
			u.setEstUsua(2); 
			repoUsua.save(u); 
		});
	}
	 
}
