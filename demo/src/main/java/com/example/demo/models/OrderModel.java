package com.example.demo.models;

import java.sql.Timestamp;
import java.util.ArrayList;

import jakarta.persistence.*;


@Entity 
@Table (name = "orders")
public class OrderModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long orderId;
	@Column
	private Long creationDate;
	@Column
	private Integer numProducts;
	@Column
	private Status status;
	@Column
	private Float subtotal;
	@Column
	private Float total;
	
	@OneToMany(mappedBy = "orderModel")
	private ArrayList<ProductModel> orderLines;
	
	public Long getId() {
		return orderId;
	}

	public void setId(Long id) {
		this.orderId = id;
	}

	public Long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Long creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getNumProducts() {
		return numProducts;
	}

	public void setNumProducts(Integer numProducts) {
		this.numProducts = numProducts;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ArrayList<ProductModel> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(ArrayList<ProductModel> orderLines) {
		this.orderLines = orderLines;
	}
	
	public OrderModel addOrderLine(ProductModel orderLine) {
		this.orderLines.add(orderLine);
		this.reCalculateTotals();
		return this;
	}
	
	public OrderModel removeOrderLine(ProductModel orderLine) {
		this.orderLines.remove(orderLine);
		this.reCalculateTotals();
		return this;
	}

	public Float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}
	
	public OrderModel(Integer numProducts, Status status, ArrayList<ProductModel> orderLines) {
		super();
		this.creationDate = new Timestamp(System.currentTimeMillis()).getTime();
		this.numProducts = numProducts;
		this.status = status;
		this.orderLines = orderLines;
	}
	
	public void reCalculateTotals() {
		this.numProducts = this.orderLines.size();
		this.orderLines.forEach((ProductModel p) -> this.subtotal += p.getPrice());
		this.total = this.subtotal + this.subtotal * 0.21f;
	}
}
