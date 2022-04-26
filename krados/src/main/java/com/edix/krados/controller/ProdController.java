package com.edix.krados.controller;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edix.krados.model.Product;
import com.edix.krados.repository.ProductRepository;

@RestController
@RequestMapping("/krados/products")
public class ProdController {

	    @Autowired
	    private ProductRepository productRepository;
	    
	    // TODO
	    // Terminar endpoints
	    
	    @GetMapping
	    public List<Product> listProducts(){
	    	List<Product> productos = productRepository.findAll();
	    	System.out.println(productos.toString());
	        return productos;
	    }

//	    @GetMapping("/{id}")
//	    public ResponseEntity<PublicationDTO>  getPublicationById(@PathVariable (name = "id") long id){
//	        return ResponseEntity.ok(publicationService.getPublicationById(id));
//	    }
//
//	    @PostMapping
//	    public ResponseEntity<PublicationDTO>  savePublication(@RequestBody PublicationDTO publicationDTO ){
//	        return new ResponseEntity<>(publicationService.addPublication(publicationDTO), HttpStatus.CREATED);
//	    }
//
//	    @PutMapping("/{id}")
//	    public ResponseEntity<PublicationDTO> updatePublication(@RequestBody PublicationDTO publicationDTO,
//	                                                            @PathVariable(name = "id") long id){
//	        PublicationDTO publicationResponse = publicationService.updatePublication(publicationDTO, id);
//	        return new ResponseEntity<>(publicationResponse, HttpStatus.OK);
//	    }
//
//	    @DeleteMapping("/{id}")
//	    public ResponseEntity<String> deletePublication(@PathVariable (name = "id") long id){
//	        publicationService.deletePublication(id);
//	        return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
//	    }
	    
	    public static String objectToJson(Object object) {   
	         StringBuilder json = new StringBuilder();   
	        if (object == null) {   
	             json.append("\"\"");   
	         } else if (object instanceof String || object instanceof Integer) { 
	             json.append("\"").append(object.toString()).append("\"");  
	         } else {   
	             json.append(beanToJson(object));   
	         }   
	        return json.toString();   
	     }  
	    
	    public static String beanToJson(Object bean) {   
	         StringBuilder json = new StringBuilder();   
	         json.append("{");   
	         PropertyDescriptor[] props = null;   
	        try {   
	             props = Introspector.getBeanInfo(bean.getClass(), Object.class)   
	                     .getPropertyDescriptors();   
	         } catch (IntrospectionException e) {   
	         }   
	        if (props != null) {   
	            for (int i = 0; i < props.length; i++) {   
	                try {  
	                     String name = objectToJson(props[i].getName());   
	                     String value = objectToJson(props[i].getReadMethod().invoke(bean));  
	                     json.append(name);   
	                     json.append(":");   
	                     json.append(value);   
	                     json.append(",");  
	                 } catch (Exception e) {   
	                 }   
	             }   
	             json.setCharAt(json.length() - 1, '}');   
	         } else {   
	             json.append("}");   
	         }   
	        return json.toString();   
	     }   
	    
	    public static String listToJson(List<?> list) {   
	         StringBuilder json = new StringBuilder();   
	         json.append("[");   
	        if (list != null && list.size() > 0) {   
	            for (Object obj : list) {   
	                 json.append(objectToJson(obj));   
	                 json.append(",");   
	             }   
	             json.setCharAt(json.length() - 1, ']');   
	         } else {   
	             json.append("]");   
	         }   
	        return json.toString();   
	     }
}
