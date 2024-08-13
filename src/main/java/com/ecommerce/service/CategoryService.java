package com.ecommerce.service;

import java.util.List;

import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.Category;


public interface CategoryService {
//	List<Category> getAllCategories();
//    void createCategory(Category category);
//
//    String deleteCategory(Long categoryId);
//
//    Category updateCategory(Category category, Long categoryId);
//    Category findCategoryById(Long id);
	 List<Category> getAllCategories();
	    Category findCategoryById(Long id) throws ResourceNotFoundException;
	    void createCategory(Category category);
	    String deleteCategory(Long id) throws ResourceNotFoundException;
	    Category updateCategory(Category category, Long id) throws ResourceNotFoundException;
}

