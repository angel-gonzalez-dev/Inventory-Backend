package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;

public interface ICategoryService {

	/**
	 * Method get all categories
	 * @return
	 */
	public ResponseEntity<CategoryResponseRest> search();
	
	/**
	 * Method get category by id
	 * @param id 
	 * @return
	 */
	public ResponseEntity<CategoryResponseRest> searchById(Long id);
	
	/**
	 * Method save category
	 * @param category
	 * @return
	 */
	public ResponseEntity<CategoryResponseRest> saveCategory(Category category);
	
	/**
	 * Method update category
	 * @param category
	 * @return
	 */
	public ResponseEntity<CategoryResponseRest> updateCategory(Category category, Long id);
	
}
