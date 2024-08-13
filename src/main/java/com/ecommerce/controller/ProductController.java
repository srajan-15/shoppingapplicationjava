package com.ecommerce.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.service.CategoryService;
import com.ecommerce.service.ProductService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;




@RestController
@RequestMapping("/api")
public class ProductController {
	 
        @Autowired
        private ProductService productService;
	    @Autowired
	    private CategoryService categoryService;
	    
	    @Autowired
	    private ProductRepository productRepository;

	    private final String uploadDir = "/shopping1/src/main/resources/static/images";
	    
	    @PostMapping("/public/add")
	    public ResponseEntity<Product> createProduct(@RequestBody Product product) throws ResourceNotFoundException {
	        if (product.getCategory() != null) {
	            categoryService.findCategoryById(product.getCategory().getCategoryId());
	        }
	        Product savedProduct = productService.saveProduct(product);
	        return ResponseEntity.ok(savedProduct);
	    }

	    @GetMapping("/admin/getProduct")
	    public List<Product> getAllProducts() {
	        return productService.findAllProducts();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Product> getProductById(@PathVariable Long id) throws ResourceNotFoundException {
	        Product product = productService.findProductById(id);
	        return ResponseEntity.ok(product);
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) throws ResourceNotFoundException {
	        Product product = productService.findProductById(id);
	        product.setProductName(productDetails.getProductName());
	        product.setImage(productDetails.getImage());
	        product.setDescription(productDetails.getDescription());
	        product.setQuantity(productDetails.getQuantity());
	        product.setPrice(productDetails.getPrice());
	        product.setDiscount(productDetails.getDiscount());
	        product.setSpecialPrice(productDetails.getSpecialPrice());

	        if (productDetails.getCategory() != null) {
	            categoryService.findCategoryById(productDetails.getCategory().getCategoryId());
	            product.setCategory(productDetails.getCategory());
	        }

	        Product updatedProduct = productService.saveProduct(product);
	        return ResponseEntity.ok(updatedProduct);
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws ResourceNotFoundException {
	        productService.deleteProduct(id);
	        return ResponseEntity.ok().build();
	    }
	    
	    @PostMapping("/public/products/uploadImage/{id}")
	    public String uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
	        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

	        try {
	            // Create the directory if it does not exist
	            Path uploadPath = Paths.get(uploadDir);
	            if (!Files.exists(uploadPath)) {
	                Files.createDirectories(uploadPath);
	            }

	            String fileName = file.getOriginalFilename();
	            Path copyLocation = Paths.get(uploadDir + File.separator + fileName);
	            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);

	            // Save the image path or URL to the product
	            product.setImage(copyLocation.toString());
	            productRepository.save(product);

	            return "File uploaded successfully: " + fileName;
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "Could not upload the file: " + e.getMessage();
	        }
	    }

}