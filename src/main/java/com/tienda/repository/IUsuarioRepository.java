package com.tienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tienda.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer> {

	
	/*De esta manera de realiza sin jqpl cuando el formato de mi base de datos esta de manera camelcase*/
	Usuario findByEmail(String email);

	Usuario findByDni(String dni);

	Usuario findByEmailAndPassword(String email, String password);
}
