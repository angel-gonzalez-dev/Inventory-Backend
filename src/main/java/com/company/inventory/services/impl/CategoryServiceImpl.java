package com.company.inventory.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.company.inventory.dao.ICategoryDao;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;
import com.company.inventory.services.ICategoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ICategoryDao categoryDao;

	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {
		
		CategoryResponseRest categoryResponseRest = new CategoryResponseRest();
		try {
			List<Category> listCategories = (List<Category>) categoryDao.findAll();
			categoryResponseRest.getCategoryResponse().setListCategories(listCategories);
			categoryResponseRest.setMetadata("Respuesta ok", "0000", "Respuesta Exitosa");
		}catch(Exception e) {
			categoryResponseRest.setMetadata("Respuesta nok", "0001", "Error al consultar");
			log.error("Error general to get all categories");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
			
			
		}
		
		return new ResponseEntity<CategoryResponseRest>(categoryResponseRest,HttpStatus.OK);
		//return ResponseEntity.ok(categoryResponseRest);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		
		CategoryResponseRest categoryResponseRest = new CategoryResponseRest();
		
		try {
			if(id == null) {
				categoryResponseRest.setMetadata("Respuesta nok", "0001", "Ingresar id");
				return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			List<Category> listCategories = new ArrayList<>();
			Optional<Category> category = categoryDao.findById(id);
			
			if(category.isPresent()) {
				listCategories.add(category.get());
				categoryResponseRest.getCategoryResponse().setListCategories(listCategories);
				categoryResponseRest.setMetadata("Respuesta ok", "0000", "Respuesta exitosa");
			}else {
				categoryResponseRest.setMetadata("Respuesta nok", "0001", "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(categoryResponseRest,HttpStatus.NOT_FOUND);
			}
			
			
			
		}catch(Exception e) {
			categoryResponseRest.setMetadata("Response nok", "0001", "Error al consultar");
			log.error("Error general al consultar categoria por Id");
			return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> saveCategory(Category category) {
		CategoryResponseRest response = new CategoryResponseRest();
		
		try {
			Category categorySaved = categoryDao.save(category);
			if(categorySaved != null) {
				List<Category> listResponse = new ArrayList<>();
				listResponse.add(category);
				response.getCategoryResponse().setListCategories(listResponse);
				response.setMetadata("Respuesta ok", "0000", "Categoria guardada correctamente");
			}else {
				response.setMetadata("Response nok", "0001", "Categoria no guardada");
				return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.BAD_REQUEST);
			}
			//response.setMetadata("Respuesta ok", "0000", "Categoria guardada correctamente");
			
		}catch(Exception e) {
			log.error("Error al crear la categoria");
			e.printStackTrace();
			response.setMetadata("Respuesta nok", "0001", "Error al guardar la categoria");
			return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<CategoryResponseRest>(response,HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> updateCategory(Category category, Long id) {
		CategoryResponseRest categoryResponseRest = new CategoryResponseRest();
		try {
			List<Category> listCategories = new ArrayList<>();
			Optional<Category> categorySearch = categoryDao.findById(id);
			if(categorySearch.isPresent()) {
				//Se procesara a actualizar el registro
				categorySearch.get().setName(category.getName());
				categorySearch.get().setDescription(category.getDescription());
				Category categoryUpdate = categoryDao.save(categorySearch.get());
				if(categoryUpdate != null) {
					log.info("Categoria actualizada correctamente");
					listCategories.add(categoryUpdate);
					categoryResponseRest.getCategoryResponse().setListCategories(listCategories);
					categoryResponseRest.setMetadata("Respuesta ok", "0000", "Categoria actualizada correctamente");
				}else {
					log.info("Categoria no actualizada");
					categoryResponseRest.setMetadata("Respuesta nok", "0001", "Categoria no actualizada");
					return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
			}else {
				log.info("Categoria no encontrada");
				categoryResponseRest.setMetadata("Respuesta nok", "0001", "No se encontro la categoria para actualizar");
				return new ResponseEntity<CategoryResponseRest>(categoryResponseRest,HttpStatus.NOT_FOUND);
			}
			
			
		}catch(Exception e) {
			log.error("Error general al actualizar categoria");
			e.printStackTrace();
			categoryResponseRest.setMetadata("Respuesta nok", "0001", "Error al actualizar categoria");
			return new ResponseEntity<CategoryResponseRest>(categoryResponseRest,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> deleteCategory(Long id) {
		
		CategoryResponseRest categoryResponseRest = new CategoryResponseRest();
		
		try {
			Optional<Category> categorySearch = categoryDao.findById(id);
			if(categorySearch.isPresent()) {
				
				categoryDao.deleteById(id);
				categoryResponseRest.setMetadata("Respuesta ok", "0000", "Categoria eliminada");
			}else {
				log.info("Categoria no encontrada");
				categoryResponseRest.setMetadata("Respuesta nok", "0002", "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.OK);
			}
			
			
		}catch (Exception e) {
			log.error("Error general al borrar Categoria");
			categoryResponseRest.setMetadata("Respuesta nok", "0001", "Error general en eliminar Categoria");
			e.printStackTrace();
			return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		return new ResponseEntity<CategoryResponseRest>(categoryResponseRest, HttpStatus.OK);
	}

}
