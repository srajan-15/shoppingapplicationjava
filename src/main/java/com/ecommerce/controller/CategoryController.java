package com.ecommerce.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.model.Category;
import com.ecommerce.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class CategoryController {

	 @Autowired
	    private CategoryService categoryService;

	    @GetMapping("/public/categories")
	    public ResponseEntity<List<Category>> getAllCategories() {
	        List<Category> categories = categoryService.getAllCategories();
	        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	        HttpServletRequest httpServletRequest = servletRequestAttributes.getRequest();
	        HttpSession httpSession = httpServletRequest.getSession(true);

	        if (httpSession != null) {
	            System.out.println("Session ID: " + httpSession.getId());

	            long sessionCreationTime = httpSession.getCreationTime();
	            long inactiveIntervalTime = httpSession.getMaxInactiveInterval(); // in seconds
	            long currentSystemTime = System.currentTimeMillis();

	            long remainingTime = (sessionCreationTime + (inactiveIntervalTime * 1000)) - currentSystemTime;

	            if (remainingTime <= 0) {
	                System.out.println("Session has expired.");
	                return new ResponseEntity<>(categories,HttpStatus.UNAUTHORIZED);
	            } else {
	                long remainingMinutes = remainingTime / (60 * 1000);
	                long remainingSeconds = (remainingTime / 1000) % 60;

	                System.out.println("Session Remaining Time: " + remainingMinutes + " minutes " + remainingSeconds + " seconds");
	                System.out.println("Max Inactive Interval: " + inactiveIntervalTime + " seconds");
	            }
	        }
	        return new ResponseEntity<>(categories, HttpStatus.OK);
	    }

	    @PostMapping("/public/addcategories")
	    public ResponseEntity<String> createCategory(@RequestBody Category category) {
	        categoryService.createCategory(category);
	        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
	    }

	    @DeleteMapping("/admin/{categoryId}")
	    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
	        try {
	            String status = categoryService.deleteCategory(categoryId);
	            return ResponseEntity.status(HttpStatus.OK).body(status);
	        } catch (ResponseStatusException e) {
	            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
	        }
	    }

	    @PutMapping("/{categoryId}")
	    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId) {
	        try {
	            categoryService.updateCategory(category, categoryId);
	            return new ResponseEntity<>("Category updated successfully", HttpStatus.OK);
	        } catch (ResponseStatusException e) {
	            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
	        }
    }
}