package com.example.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.models.OrderModel;
import com.example.demo.models.ProductModel;
import com.example.demo.models.Status;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.ProductRepository;

@Service
public class OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Transactional(readOnly = true)
	public ArrayList<OrderModel> getOrdersByFilters(Long creationDate, Status status, float price) {
		return orderRepository.getOrdersByCreationDateAndStatusAndTotal(creationDate, status, price);
	}
	
	public ArrayList<OrderModel> getOrders() {
		return (ArrayList<OrderModel>) this.orderRepository.findAll();
	}
	
	public OrderModel getOrderById(Long id) {
		return this.orderRepository.findById(id).get();
	}
	
	public OrderModel newOrder(OrderModel order,ProductModel product) {
		OrderModel newOrder = this.orderRepository.save(order);
		return this.addProductToOrder(newOrder.getId(), product.getId());
	}
	
	public void deleteOrder(Long id) {
		this.orderRepository.deleteById(id);
	}
	
	public OrderModel addProductToOrder(Long orderId, Long productId) {
		ProductModel product = productRepository.findById(productId).get();
		OrderModel order = orderRepository.findById(orderId).get();
		order.reCalculateTotals();
		return order.addOrderLine(product);
	}
	
	
	public OrderModel deleteOrderLine(Long orderId, Long productId) {
		ProductModel product = productRepository.findById(productId).get();
		OrderModel order = orderRepository.findById(orderId).get();
		order.reCalculateTotals();
		return order.removeOrderLine(product);
	}
	
}
