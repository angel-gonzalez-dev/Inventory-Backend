package com.company.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;

@RestController
@RequestMapping(produces = {"application/json; charset=UTF-8"}, path = "/api/v1")
public class CategoryRestController {
	
	@Autowired
	private ICategoryService categoryService;
	
	@GetMapping(path = "/categories")
	public ResponseEntity<CategoryResponseRest> searchCategories(){
		ResponseEntity<CategoryResponseRest> response = categoryService.search();
		return response;
	}

	@GetMapping(path = "/categories/{id}")
	public ResponseEntity<CategoryResponseRest> searchCategoryById(@PathVariable(name = "id") Long id){
		
		ResponseEntity<CategoryResponseRest> response = categoryService.searchById(id);
		return response;
		
	}
	
	@PostMapping(path = "/categories")
	public ResponseEntity<CategoryResponseRest> saveCategory(@RequestBody Category request){
		
		ResponseEntity<CategoryResponseRest> response = categoryService.saveCategory(request);
		return response;
	}
	
	
	@PutMapping(path = "/categories/{id}")
	public ResponseEntity<CategoryResponseRest> updateCategory(@RequestBody Category category, @PathVariable(name = "id") Long id){
		
		ResponseEntity<CategoryResponseRest> response = categoryService.updateCategory(category, id);
		
		return response;
	}
	
	@DeleteMapping(path = "/categories/{id}")
	public ResponseEntity<CategoryResponseRest> deleteCategory(@PathVariable(name = "id") Long id){
		
		ResponseEntity<CategoryResponseRest> response = categoryService.deleteCategory(id);
		
		return response;
	}
	
}
