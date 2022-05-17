package com.edix.krados.controller;

import com.edix.krados.form.ProductInPurchaseForm;
import com.edix.krados.form.PurchaseForm;
import com.edix.krados.model.*;
import com.edix.krados.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<List<PurchaseForm>> getAllPurchase(
            @PathVariable("clientId") Long clientId) {
        Client c = clientRepository.findById(clientId).get();
        List<PurchaseForm> purchaseList= new ArrayList<>();
        for(Purchase p: clientRepository.findById(clientId).get().getPurchaseList()){
            double totalPurchasePrice = 0;
            for(ProductInPurchase pip : p.getPInPurchase()){
                totalPurchasePrice = totalPurchasePrice + pip.getProduct().getUPrice() * pip.getAmount();
            }
            PurchaseForm pForm = new PurchaseForm();
            pForm.setId(p.getId());
            pForm.setPurchaseDate(p.getPurchaseDate());
            pForm.setStatus(p.getStatus());
            pForm.setTotalPrice(totalPurchasePrice);
            purchaseList.add(pForm);
        }
        if(c != null){
            return new ResponseEntity( purchaseList,HttpStatus.OK);
        } else {
            return new ResponseEntity( clientId ,HttpStatus.NOT_FOUND);
        }
    }
    // Devuelve un pedido por id
    @GetMapping("purchaseById/{purchaseId}")
    public ResponseEntity<List<ProductInPurchaseForm>> getPurchaseById(
            @PathVariable("purchaseId") Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).get();
        ArrayList<ProductInPurchaseForm> purchaseProductList = new ArrayList<>();
        for(ProductInPurchase p : purchase.getPInPurchase()){
            ProductInPurchaseForm pf = new ProductInPurchaseForm();
            pf.setName(p.getProduct().getName());
            pf.setUPrice(p.getProduct().getUPrice());
            pf.setAmount(p.getAmount());
            purchaseProductList.add(pf);
        }
        if(purchase != null){
            return new ResponseEntity( purchaseProductList,HttpStatus.OK);
        } else {
            return new ResponseEntity( purchaseId ,HttpStatus.NOT_FOUND);
        }
    }
    // Duplica un pedido
    @PostMapping("buyAgainPurchaseById/{purchaseId}")
    public ResponseEntity<List<ProductInPurchaseForm>> buyAgainPurchaseById(
            @PathVariable("purchaseId") Long purchaseId) {
        Purchase purchase = purchaseRepository.findById(purchaseId).get();
        Purchase newPurchase = new Purchase();
        newPurchase.setClient(purchase.getClient());
        newPurchase.setPInPurchase(new ArrayList<>());
        purchaseRepository.save(newPurchase);
        for(ProductInPurchase p : purchase.getPInPurchase()){
            newPurchase.getPInPurchase().add(p);
        }
        if(purchase != null){
            return new ResponseEntity( newPurchase,HttpStatus.OK);
        } else {
            return new ResponseEntity( purchaseId ,HttpStatus.NOT_FOUND);
        }
    }
}
