package com.edix.krados.bbdd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edix.krados.model.Category;
import com.edix.krados.model.Product;
import com.edix.krados.repository.CategoryRepository;
import com.edix.krados.repository.ProductRepository;

@Service
public class InsertProductData {

	public static final String FILE_NAME = "./BBDDData/ProductData.txt";
	
	@Autowired
	private static ProductRepository productRepository;
	
	@Autowired
	private static CategoryRepository categoryRepository;
	


}
