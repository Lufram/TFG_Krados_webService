package com.edix.krados.bbdd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.edix.krados.model.Category;
import com.edix.krados.model.Product;
import com.edix.krados.repository.CategoryRepository;
import com.edix.krados.repository.ProductRepository;

public class InsertProductData {

public static final String FILE_NAME = "./BBDDData/ProductData.txt";
	
	@Autowired
	private static ProductRepository productRepository;
	
	@Autowired
	private static CategoryRepository categoryRepository;
	
	public static void main(String[] args) {
		
		try (FileReader fr = new FileReader(FILE_NAME);
				 BufferedReader br = new BufferedReader(fr);) {
			
				String[] data = null;
			
				String row;
				
				while((row = br.readLine()) != null){
					data = row.split("\\t");
//						System.out.println("Info= " + data[0]);
//						System.out.println("Name= " + data[1]);
//						System.out.println("Price= " + data[2]);
//						System.out.println("Category= " + data[3]);
					Product p = new Product();
					p.setInfo(data[0]);
					p.setName(data[1]);
					p.setuPrice(Double.parseDouble(data[2]));
//					Category c = categoryRepository.findById(Long.parseLong(data[3]) ).orElse(null);
//					if(c == null) {
//						System.out.println("No existe esta categoria");
//					}else {
//						p.setCategory(c);
//						System.out.println(p.toString());
//						categoryRepository.save(c);	
//					}
					
				}
				System.out.println("Fichero leido correctamente");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
