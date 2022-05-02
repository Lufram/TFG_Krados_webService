package com.edix.krados.bbdd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edix.krados.model.Category;
import com.edix.krados.repository.CategoryRepository;

@Service
public class InsertCategoryData {
	
	public static final String FILE_NAME = "./BBDDData/CategoryData.txt";
	
	@Autowired
	private CategoryRepository categoryRepository;
	

}
