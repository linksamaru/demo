package com.example.demo.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.OrderModel;
import com.example.demo.models.ProductModel;
import com.example.demo.models.Status;
import com.example.demo.services.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping
	public ArrayList<OrderModel> getOrders() {
		return this.orderService.getOrders();
	}
	
	@GetMapping("/{id}")
	public OrderModel getProduct(@PathVariable("id") Long id) {
		return this.orderService.getOrderById(id);
	}
	
	@PostMapping("/getByFilters")
	public ArrayList<OrderModel> getOrdersByFilters(Long creationDate, Status status, float price) {
		return orderService.getOrdersByFilters(creationDate, status, price);
	}
	
	@PostMapping
	public OrderModel addProduct(@RequestBody OrderModel order,@RequestBody ProductModel product) {
		return this.orderService.newOrder(order, product);
	}
	
	@PutMapping("/add/{id}")
	public OrderModel addOrderLine(@RequestBody Long productId, @PathVariable("id") Long orderId) {
		return this.orderService.addProductToOrder(orderId, productId);
	}
	
	@PutMapping("/remove/{id}")
	public OrderModel deleteOrderLine(@RequestBody Long productId, @PathVariable("id") Long orderId) {
		return this.orderService.deleteOrderLine(orderId, productId);
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrder(@PathVariable("id") Long id) {
		this.orderService.deleteOrder(id);
	}
}
