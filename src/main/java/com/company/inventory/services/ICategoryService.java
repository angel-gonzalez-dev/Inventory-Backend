package com.company.inventory.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.company.inventory.response.CategoryResponseRest;

public interface ICategoryService {

	/**
	 * Method get categories
	 * @return
	 */
	public ResponseEntity<CategoryResponseRest> search();
}
