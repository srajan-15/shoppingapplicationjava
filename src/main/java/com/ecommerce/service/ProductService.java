package com.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.Product;

public interface ProductService {
	  Product saveProduct(Product product);
	    List<Product> findAllProducts();
	    Product findProductById(Long id) throws ResourceNotFoundException;
	    void deleteProduct(Long id) throws ResourceNotFoundException;
}
