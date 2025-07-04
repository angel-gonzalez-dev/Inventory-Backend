package com.company.inventory.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.inventory.response.CategoryResponseRest;

public interface ICategoryService {

	/**
	 * Metodo para obtener todas las categorias
	 * @return
	 */
	public ResponseEntity<CategoryResponseRest> search();
	
	/**
	 * Metodo para obtener categoria por id
	 * @param id Identificador unico de cada categoria
	 * @return
	 */
	public ResponseEntity<CategoryResponseRest> searchById(Long id);
}
