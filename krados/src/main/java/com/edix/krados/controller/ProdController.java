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
				p.getuPrice() == 0 || p.getCategory().getId() == 0 || productRepository.findByName(p.getName()) !=null) {
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
		if(p.getName().isEmpty() || p.getInfo().isEmpty() || p.getuPrice() == 0 || p.getCategory().getId() == 0 || productRepository.findById(id).isEmpty()) {
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
	// Devuelve todos los productos del carrito
	@GetMapping("/productInCart/{cartId}")
	public ResponseEntity<List<ProductInCartForm>>  getAllProductInCart(
			@PathVariable ("cartId") Long cartId){
		if(cartRepository.existsById(cartId)) {
			Cart cart = cartRepository.getById(cartId);
			List<ProductInCartForm> prodlist = new ArrayList<>();
			for (ProductInCart p : cart.getPInCart()) {
				ProductInCartForm pf = new ProductInCartForm();
				pf.setId(p.getProduct().getId());
				pf.setName(productRepository.findById(p.getProduct().getId()).get().getName());
				pf.setInfo(productRepository.findById(p.getProduct().getId()).get().getInfo());
				pf.setUPrice(productRepository.findById(p.getProduct().getId()).get().getuPrice());
				pf.setAmount(p.getAmount());

				prodlist.add(pf);
			}
			return new ResponseEntity<>(prodlist, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
	}
	// Añade un producto al carrito
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

		return new ResponseEntity<>(p, HttpStatus.CREATED);
	}
	// Actualiza la cantidad del producto en el carrito
	@PutMapping("/productInCart")
	public ResponseEntity<ProductInCart>  modifyProductToCart(
			@RequestParam (name = "cartId") Long cartId,
			@RequestParam (name = "productId") Long productId,
			@RequestParam (name = "amount") int amount ){
		List<ProductInCart> pCart = cartRepository.findById(cartId).get().getPInCart();
		for (ProductInCart p : pCart) {
			if(p.getProduct().getId().equals(productId)){
				p.setAmount(amount);
				pCartRepository.save(p);
				return new ResponseEntity<>(p, HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity(productId, HttpStatus.BAD_REQUEST);
	}
	// Elimina un producto del carrito
	@DeleteMapping("/productInCart")
	public ResponseEntity<ProductInCart>  deleteProductToCart(
			@RequestParam (name = "cartId") Long cartId,
			@RequestParam (name = "productId") Long productId ){
		List<ProductInCart> pCart = cartRepository.findById(cartId).get().getPInCart();
		for (ProductInCart p : pCart) {
			if(p.getProduct().getId().equals(productId)){
				pCartRepository.delete(p);
				return new ResponseEntity<>(p, HttpStatus.ACCEPTED);
			}
		}
		return new ResponseEntity(productId, HttpStatus.BAD_REQUEST);
	}


}
