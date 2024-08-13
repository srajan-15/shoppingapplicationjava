package com.ecommerce.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Category;
import com.ecommerce.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	 @Autowired
	    private CategoryRepository categoryRepository;

	    @Override
	    public List<Category> getAllCategories() {
	        return categoryRepository.findAll();
	    }

	    @Override
	    public void createCategory(Category category) {
	        categoryRepository.save(category);
	    }

	    @Override
	    public String deleteCategory(Long categoryId) {
	        Category category = findCategoryById(categoryId);
	        categoryRepository.delete(category);
	        return "Category with id deleted successfully.";
	    }

	    @Override
	    public Category updateCategory(Category category, Long categoryId) {
	        Category existingCategory = findCategoryById(categoryId);
	        existingCategory.setCategoryName(category.getCategoryName());
	        return categoryRepository.save(existingCategory);
	    }

	    @Override
	    public Category findCategoryById(Long id) {
	        return categoryRepository.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id));
	    }
}