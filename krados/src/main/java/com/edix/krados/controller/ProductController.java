package com.edix.krados.controller;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edix.krados.model.Product;
import com.edix.krados.repository.ProductRepository;

@RestController
@RequestMapping("/krados/products")
public class ProductController {

	    @Autowired
	    private ProductRepository productRepository;
	    
	    
	    @GetMapping
	    public List<Product> listProducts(){
	    	List<Product> productos = productRepository.findAll();
	    	//System.out.println(productos.toString());
	        return productos;
	    }

	    @GetMapping("/{name}") 
	    public ResponseEntity<List<Product>>getProductByName(@PathVariable ("name") String name){
	    	List<Product> p = productRepository.findByNameContaining(name);
	    	if(!p.isEmpty()) {
	    		return ResponseEntity.ok(productRepository.findByNameContaining(name));
	    	} else {
	    		return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
	    	}
	    }
	    
	    @GetMapping("/categoryId") 
	    public ResponseEntity<List<Product>>getProductByCategoryId(@RequestParam (name = "categoryId") int categoryId ){
	    	List<Product> p = productRepository.findByCategoryId(categoryId);
	    	if(!p.isEmpty()) {
	    		return ResponseEntity.ok(productRepository.findByCategoryId(categoryId));
	    	} else {
	    		return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
	    	}
	    }

	    @PostMapping
	    public ResponseEntity<Product>  saveProduct(@RequestBody Product p ){
	    	if(p.getName().isEmpty() || p.getInfo().isEmpty() || p.getuPrice() == 0 || p.getCategory().getId() == 0 || productRepository.findByName(p.getName()) !=null) {
	    		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
	    	}
	        return new ResponseEntity<Product>(productRepository.save(p), HttpStatus.CREATED);
	    }
	    
	    @PutMapping("/{id}")
	    public ResponseEntity<Product>  updateProduct(@PathVariable (name = "id") long id,@RequestBody Product p ){
	    	if(p.getName().isEmpty() || p.getInfo().isEmpty() || p.getuPrice() == 0 || p.getCategory().getId() == 0 || productRepository.findById(id).isEmpty()) {
	    		return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
	    	}else {
	    		productRepository.deleteById(id);
	    		return new ResponseEntity<Product>(productRepository.save(p), HttpStatus.CREATED);	    		
	    	}
	    }

	    @DeleteMapping("/{name}")
	    public ResponseEntity<Product> deleteProduct(@PathVariable (name = "name") String name){
	    	Product p = productRepository.findByName(name);
	    	if (p != null) {
	    		productRepository.delete(p);
	    		return new ResponseEntity<Product>(p, HttpStatus.OK);
	    	}else {
	    		return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
	    	}
	    }
}
