package com.edix.krados.controller;


import com.edix.krados.model.Category;
import com.edix.krados.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/krados/category")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Category> allCategory(){
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }
    // Añade una categoria a la BBDD
    @PostMapping
    public ResponseEntity<Category>  createCategory(@RequestBody Category c ){
        categoryRepository.save(c);
        return new ResponseEntity(c, HttpStatus.CREATED);
    }
    // Elimina una categoria por id
    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") Long id){
        categoryRepository.deleteById(id);
        return new ResponseEntity(id, HttpStatus.OK);
    }
    // Actualiza la informacion de una categoria
    @PutMapping("/{id}")
    public ResponseEntity<Category>  updateCategory(@PathVariable (name = "id") long id, @RequestBody Category category ){
        Optional<Category> c = categoryRepository.findById(id);
        c.get().setName(category.getName());
        return new ResponseEntity(c, HttpStatus.OK);
    }

}
