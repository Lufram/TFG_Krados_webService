package com.edix.krados.controller;

import com.edix.krados.model.*;
import com.edix.krados.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/krados/purchase")
public class PurchaseController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ProductInPurchaseRepository productInPurchaseRepository;
    @Autowired
    private ClientRepository clientRepository;

    // Devuelve todos los pedidos por cliente
    @GetMapping("/{clientId}")
    public ResponseEntity<List<Purchase>> getAllPurchase(
            @PathVariable("clientId") Long clientId) {
        Optional<Client> c = clientRepository.findById(clientId);
        if(c != null){
            return new ResponseEntity( clientRepository.findById(clientId).get().getPurchaseList(),HttpStatus.OK);
        } else {
            return new ResponseEntity( clientId ,HttpStatus.NOT_FOUND);
        }
    }
    // Devuelve un pedido por id
    @GetMapping("/{purchaseId}")
    public ResponseEntity<Purchase> getPurchaseById(
            @PathVariable("purchaseId") Long purchaseId) {
        Optional<ProductInPurchase> purchase = productInPurchaseRepository.findById(purchaseId);
        if(purchase != null){
            return new ResponseEntity( purchaseRepository.findById(purchaseId),HttpStatus.OK);
        } else {
            return new ResponseEntity( purchaseId ,HttpStatus.NOT_FOUND);
        }
    }
}
