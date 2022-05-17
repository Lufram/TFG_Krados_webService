package com.edix.krados.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edix.krados.repository.CartRepository;
import com.edix.krados.repository.CategoryRepository;
import com.edix.krados.repository.ProductInCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edix.krados.model.Product;
import com.edix.krados.repository.ProductRepository;

@RestController
@RequestMapping("/krados/products")
public class ProdController {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private ProductInCartRepository pCartRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	// Devuelve todos los productos
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> listProducts(){
		List<Product> productos = productRepository.findAll();
		return productos;
	}
	// Añade un producto a la BBDD
	@PostMapping
	public ResponseEntity<Product>  createProduct(@RequestBody Product p ){
		if(p.getName().isEmpty() || p.getInfo().isEmpty() ||
				p.getUPrice() == 0 || p.getCategory().getId() == 0 || productRepository.findByName(p.getName()) !=null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(productRepository.save(p), HttpStatus.CREATED);
	}
	// Elimina un producto por id
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable ("id") Long id){
		Optional<Product> p = productRepository.findById(id);
		if (p.isPresent()) {
			productRepository.deleteById(id);
			return new ResponseEntity(p, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	// Actualiza la informacion de un producto
	@PutMapping("/{id}")
	public ResponseEntity<Product>  updateProduct(@PathVariable (name = "id") long id,@RequestBody Product p ){
		if(p.getName().isEmpty() || p.getInfo().isEmpty() || p.getUPrice() == 0 || p.getCategory().getId() == 0 || productRepository.findById(id).isEmpty()) {
			return new ResponseEntity<Product>(HttpStatus.BAD_REQUEST);
		}else {
			productRepository.deleteById(id);
			return new ResponseEntity<Product>(productRepository.save(p), HttpStatus.CREATED);
		}
	}
	// Devuelve todos los productos que contengan el texto
	@GetMapping(path="findByName/{name}")
	public ResponseEntity<List<Product>>getProductByName(@PathVariable ("name") String name){
		List<Product> p = productRepository.findByNameContaining(name);
		if(!p.isEmpty()) {
			return ResponseEntity.ok(p);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	// Devuelve todos los productos que pertenezcan a una categoria pasandole un id de categoría
	@GetMapping(path="findByCategory/{categoryId}")
	public ResponseEntity<List<Product>> getProductByCategoryId(@PathVariable ("categoryId") Long categoryId ){
		List<Product> p = categoryRepository.findById(categoryId).get().getProductList();
		if(!p.isEmpty()) {
			return ResponseEntity.ok(p);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Devuelve todos los productos de oferta
	@GetMapping(path="offer")
	public ResponseEntity<List<Product>> getProductByOffer(){
		List<Product> p = productRepository.findByInOfferTrue();
		if(!p.isEmpty()) {
			return ResponseEntity.ok(p);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	// Devuelve todos los productos de una categoría filtrando por nombre
	@GetMapping(path="findByCategoryAndName")
	public ResponseEntity<List<Product>> getProductByCategoryId(
			@RequestParam (name = "categoryId") Long categoryId,
			@RequestParam (name = "name") String pName){
		List<Product> plCategory = categoryRepository.findById(categoryId).get().getProductList();
		List<Product> plCategoryAndName = new ArrayList<>();
		if(!plCategory.isEmpty()) {
			for (Product pro: plCategory){
				if (pro.getName().contains(pName)){
					plCategoryAndName.add(pro);
				}
			}
			return ResponseEntity.ok(plCategoryAndName);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
