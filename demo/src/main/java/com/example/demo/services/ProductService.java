package com.example.demo.services;

import java.util.ArrayList;
//import java.util.List;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.ProductModel;
import com.example.demo.repositories.ProductRepository;


@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepository;
	
	public ArrayList<ProductModel> getProducts() {
		return (ArrayList<ProductModel>) this.productRepository.findAll();
	}
	
	public ProductModel getProductById(Long id) {
		return this.productRepository.findById(id).get();
	}
	
	public ProductModel newProduct(ProductModel productModel) {
		return this.productRepository.save(productModel);
	}
	
	public void deleteProduct(Long id) {
		this.productRepository.deleteById(id);
	}
	
	@Transactional
	public void saveAll(List<ProductModel> productList) {
		this.productRepository.saveAll(productList);
	}
}
