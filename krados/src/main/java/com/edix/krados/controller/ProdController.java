package com.edix.krados.controller;

import java.util.List;

import com.edix.krados.model.Cart;
import com.edix.krados.model.ProductInCart;
import com.edix.krados.repository.CartRepository;
import com.edix.krados.repository.ProductInCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
public class ProdController {

	    @Autowired
	    private ProductRepository productRepository;
		@Autowired
		private CartRepository cartRepository;
		@Autowired
		private ProductInCartRepository pCartRepository;

	    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	    public List<Product> listProducts(){
	    	List<Product> productos = productRepository.findAll();
	        return productos;
	    }

	    @GetMapping(path="findByName/{name}") 
	    public ResponseEntity<List<Product>>getProductByName(@PathVariable ("name") String name){
	    	List<Product> p = productRepository.findByNameContaining(name);
	    	if(!p.isEmpty()) {
	    		return ResponseEntity.ok(p);
	    	} else {
	    		return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
	    	}
	    }
	    
	    @GetMapping(path="findByCategory/{categoryId}")
	    public ResponseEntity<List<Product>>getProductByCategoryId(@PathVariable ("categoryId") int categoryId ){
	    	List<Product> p = productRepository.findByCategoryId(categoryId);
	    	if(!p.isEmpty()) {
	    		return ResponseEntity.ok(p);
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

		@PostMapping("/productInCar")
		public ResponseEntity<Product>  addProductToCart(
				@RequestParam (name = "cartId") Long cartId,
				@RequestParam (name = "productId") Long productId,
				@RequestParam (name = "amount") int amount ){
			Cart c = cartRepository.getById(cartId);
			Product p = productRepository.getById(productId);

			ProductInCart pCart = new ProductInCart();
			pCart.setCart(c);
			pCart.setProduct(p);
			pCart.setAmount(amount);
			pCartRepository.save(pCart);

			return new ResponseEntity<Product>(p, HttpStatus.CREATED);
		}

		@PutMapping("/productInCart")
		public ResponseEntity<ProductInCart>  modifyProductToCart(
				@RequestParam (name = "cartId") Long cartId,
				@RequestParam (name = "productId") Long productId,
				@RequestParam (name = "amount") int amount ){
			ProductInCart pCart = (ProductInCart) pCartRepository.findByProductAndCart(cartId, productId);
			if(pCart == null){
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else {
				if (amount < 1) {
					pCartRepository.delete(pCart);
				} else {
					pCart.setAmount(amount);
				}
			}
			return new ResponseEntity<ProductInCart>(pCart, HttpStatus.ACCEPTED);
		}

		@DeleteMapping("/productInCart")
		public ResponseEntity<ProductInCart>  deleteProductToCart(
				@RequestParam (name = "cartId") Long cartId,
				@RequestParam (name = "productId") Long productId ){
			ProductInCart pCart = (ProductInCart) pCartRepository.findByProductAndCart(cartId, productId);
			if(pCart == null){
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}else {
				pCartRepository.delete(pCart);
			}
			return new ResponseEntity<ProductInCart>(pCart, HttpStatus.ACCEPTED);
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
