package com.edix.krados.controller;

import com.edix.krados.form.ProductInCartForm;
import com.edix.krados.form.ResponseForm;
import com.edix.krados.model.*;
import com.edix.krados.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/krados/cart")
public class CartController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductInCartRepository pCartRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;

    // Devuelve todos los productos del carrito
    @GetMapping("/productInCart/{cartId}")
    public ResponseEntity<List<ProductInCartForm>> getAllProductInCart(
            @PathVariable("cartId") Long cartId) {
        if (cartRepository.existsById(cartId)) {
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Añade un producto al carrito
    @PostMapping("/productInCart")
    public ResponseEntity<ProductInCart> createProductToCart(
            @RequestParam(name = "cartId") Long cartId,
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "amount") int amount) {
        Cart c = cartRepository.findById(cartId).get();
        Product p = productRepository.findById(productId).get();
        if (c != null && p != null) {
            for (ProductInCart pInCart : c.getPInCart()) {
                if (pInCart.getProduct().getId()== productId) {
                    for (ProductInCart pic : c.getPInCart()) {
                        if (pic.getProduct().getId().equals(productId)) {
                            ProductInCart newPic = new ProductInCart();
                            newPic.setId(pic.getId());
                            newPic.setProduct(pic.getProduct());
                            newPic.setAmount(pic.getAmount()+ amount);
                            newPic.setCart(pic.getCart());
                            pCartRepository.delete(pic);
                            pCartRepository.save(newPic);
                            return new ResponseEntity<>(pic, HttpStatus.ACCEPTED);
                        }
                    }
                }
            }
            ProductInCart pCart = new ProductInCart();
            pCart.setCart(c);
            pCart.setProduct(p);
            pCart.setAmount(amount);
            pCartRepository.save(pCart);
            return new ResponseEntity(pCart, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Actualiza la cantidad del producto en el carrito
    @PutMapping("/productInCart")
    public ResponseEntity<ProductInCart> updateProductToCart(
            @RequestParam(name = "cartId") Long cartId,
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "amount") int amount) {
        List<ProductInCart> pCart = cartRepository.findById(cartId).get().getPInCart();
        for (ProductInCart p : pCart) {
            if (p.getProduct().getId().equals(productId)) {
                p.setAmount(amount);
                pCartRepository.save(p);
                return new ResponseEntity<>(p, HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity(productId, HttpStatus.BAD_REQUEST);
    }

    // Elimina un producto del carrito
    @DeleteMapping("/productInCart")
    public ResponseEntity<ProductInCart> deleteProductToCart(
            @RequestParam(name = "cartId") Long cartId,
            @RequestParam(name = "productId") Long productId) {
        List<ProductInCart> pCart = cartRepository.findById(cartId).get().getPInCart();
        for (ProductInCart p : pCart) {
            if (p.getProduct().getId().equals(productId)) {
                pCartRepository.delete(p);
                return new ResponseEntity<>(p, HttpStatus.ACCEPTED);
            }
        }
        return new ResponseEntity(productId, HttpStatus.BAD_REQUEST);
    }


    // Elimina un carrito
    @DeleteMapping("/deleteCart")
    public ResponseEntity<Cart> deleteAllCart(
            @RequestParam(name = "cartId") Long cartId) {
        if (cartRepository.existsById(cartId)) {
            Cart currentCart = cartRepository.findById(cartId).get();
            cartRepository.delete(currentCart);
            return new ResponseEntity<>(currentCart, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // Elimina los productos de un carrito
    @DeleteMapping("/deleteProductsInCart")
    public ResponseEntity<List<ProductInCart>> deleteAllProductInCart(
            @RequestParam(name = "cartId") Long cartId) {
        if (cartRepository.existsById(cartId)) {
            List<ProductInCart> pCart = cartRepository.findById(cartId).get().getPInCart();
            for (ProductInCart p : pCart) {
                pCartRepository.delete(p);
            }
            return new ResponseEntity<>(pCart, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    // Crea un nuevo pedido con los productos del carrito y vacia el carrito
    @PostMapping
    public ResponseEntity<ResponseForm> trasnformCartInPurchase(
            @RequestParam(name = "cartId") Long cartId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        LocalDate currentLocalDate = LocalDate.now();
        ZoneId systemTimeZone = ZoneId.of("Etc/GMT+2");
        ZonedDateTime zonedDateTime = currentLocalDate.atStartOfDay(systemTimeZone);
        Date utilDate = Date.from(zonedDateTime.toInstant());

        Cart cart = cartRepository.findById(cartId).get();
        Purchase purchase = new Purchase();
        purchase.setClient(cart.getClient());
        purchase.setStatus("Preparando");
        purchase.setPurchaseDate(utilDate);
        purchaseRepository.save(purchase);

        for (ProductInCart p : cart.getPInCart()) {
            ProductInPurchase pip = new ProductInPurchase();
            pip.setProduct(p.getProduct());
            pip.setAmount(p.getAmount());
            pip.setPurchase(purchase);
            productInPurchaseRepository.save(pip);
        }

        return new ResponseEntity<ResponseForm>(new ResponseForm("ok"), HttpStatus.CREATED);
    }


}
