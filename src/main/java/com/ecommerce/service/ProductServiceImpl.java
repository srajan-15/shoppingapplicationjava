package com.ecommerce.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	  @Autowired
	    private ProductRepository productRepository;
	  

	    public Product saveProduct(Product product) {
	        return productRepository.save(product);
	    }

	    public List<Product> findAllProducts() {
	        return productRepository.findAll();
	    }

	    public Product findProductById(Long id) throws ResourceNotFoundException {
	    	return productRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException( "id"));
	    }

		@Override
		public void deleteProduct(Long id) throws ResourceNotFoundException {
			
			
		}
}