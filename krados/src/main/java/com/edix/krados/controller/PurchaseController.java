package com.edix.krados.controller;

import com.edix.krados.form.PurchaseForm;
import com.edix.krados.model.*;
import com.edix.krados.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public ResponseEntity<List<PurchaseForm>> getAllPurchase(
            @PathVariable("clientId") Long clientId) {
        Client c = clientRepository.findById(clientId).get();
        List<PurchaseForm> purchaseList= new ArrayList<>();
        for(Purchase p: clientRepository.findById(clientId).get().getPurchaseList()){
            PurchaseForm pForm = new PurchaseForm();
            pForm.setId(p.getId());
            pForm.setPurchaseDate(p.getPurchaseDate());
            pForm.setStatus(p.getStatus());
            purchaseList.add(pForm);
        }
        if(c != null){
            return new ResponseEntity( purchaseList,HttpStatus.OK);
        } else {
            return new ResponseEntity( clientId ,HttpStatus.NOT_FOUND);
        }
    }
    // Devuelve un pedido por id
//    @GetMapping("purchaseById/{purchaseId}")
//    public ResponseEntity<Purchase> getPurchaseById(
//            @PathVariable("purchaseId") Long purchaseId) {
//        ProductInPurchase purchase = productInPurchaseRepository.findById(purchaseId).get();
//        if(purchase != null){
//            return new ResponseEntity( purchaseRepository.findById(purchaseId),HttpStatus.OK);
//        } else {
//            return new ResponseEntity( purchaseId ,HttpStatus.NOT_FOUND);
//        }
//    }
}
