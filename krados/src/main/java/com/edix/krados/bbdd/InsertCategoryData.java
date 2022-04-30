package com.edix.krados.bbdd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edix.krados.model.Category;
import com.edix.krados.repository.CategoryRepository;

public class InsertCategoryData {
	
	public static final String FILE_NAME = "./BBDDData/CategoryData.txt";
	
	@Autowired
	private static CategoryRepository categoryRepository;
	
	public static void main(String[] args) {
		
		try (FileReader fr = new FileReader(FILE_NAME);
				 BufferedReader br = new BufferedReader(fr);) {
				String data = br.readLine();
				while(data != null){
					System.out.println(data);
					Category c = new Category();
					c.setName(data);
//					categoryRepository.save(c);
					data = br.readLine();
				}
				System.out.println("Fichero leido correctamente");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
