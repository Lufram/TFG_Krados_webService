package com.edix.krados.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.edix.krados.model.Cart;
import com.edix.krados.model.ProductInCart;
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
	public ResponseEntity<List<Product>> getProductByCategoryId(@PathVariable ("categoryId") Long categoryId ){
		List<Product> p = categoryRepository.findById(categoryId).get().getProductList();
		if(!p.isEmpty()) {
			return ResponseEntity.ok(p);
		} else {
			return new ResponseEntity<List<Product>>(HttpStatus.NOT_FOUND);
		}
	}

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

	@GetMapping("/productInCart/{cartId}")
	public ResponseEntity<List<ProductInCartForm>>  getAllProductInCart(
			@PathVariable ("cartId") Long cartId){
		if(cartRepository.existsById(cartId)) {
			Cart cart = cartRepository.getById(cartId);
			List<ProductInCartForm> prodlist = new ArrayList<>();
			for (ProductInCart p : cart.getPInCart()) {
				ProductInCartForm pf = new ProductInCartForm();
				pf.setId(p.getId());
				pf.setName(productRepository.findById(p.getId()).get().getName());
				pf.setInfo(productRepository.findById(p.getId()).get().getInfo());
				pf.setUPrice(productRepository.findById(p.getId()).get().getuPrice());
				pf.setAmount(p.getAmount());

				prodlist.add(pf);
			}
			return new ResponseEntity<>(prodlist, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/productInCart")
	public ResponseEntity<Product>  addProductToCart(
			@RequestParam (name = "cartId") Long cartId,
			@RequestParam (name = "productId") Long productId,
			@RequestParam (name = "amount") int amount ){
		Cart c = cartRepository.findById(cartId).get();
		Product p = productRepository.findById(productId).get();

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
		ProductInCart pCart = pCartRepository.findByProductIdAndCartId(cartId, productId);
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
		ProductInCart pCart = (ProductInCart) pCartRepository.findByProductIdAndCartId(cartId, productId);
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
