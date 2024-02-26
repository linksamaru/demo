package com.example.demo.controllers;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.models.ProductModel;
import com.example.demo.services.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public ArrayList<ProductModel> getProducts() {
		return this.productService.getProducts();
	}
	
	@GetMapping("{id}")
	public ProductModel getProduct(@PathVariable("id") Long id) {
		return this.productService.getProductById(id);
	}
	
	@PostMapping
	public ProductModel addProduct(@RequestBody ProductModel product) {
		return this.productService.newProduct(product);
	}
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestParam(name = "file") MultipartFile file) {
		String fileName = file.getOriginalFilename();
		try {
			Path path = Paths.get("src" + File.separator + "main" + File.separator + "resources" + File.separator + fileName);
			Files.createDirectories(path.getParent());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			
			JobParameters jobParameters = new JobParametersBuilder()
					.addDate("date", new Date())
					.addString("fileName", fileName)
					.toJobParameters();
			
			jobLauncher.run(job, jobParameters);
		} catch (Exception e) {
			Map<String, String> response = new HashMap<>();
			response.put("cause", e.getCause().toString());
			response.put("errorMessage", e.getMessage());
			return ResponseEntity.ok(response);
		}
		Map<String, String> response = new HashMap<>();
		response.put("file", fileName);
		return ResponseEntity.ok(response);
	}
}
