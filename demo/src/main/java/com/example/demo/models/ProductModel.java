package com.example.demo.models;

import java.util.Objects;

import jakarta.persistence.*;


@Entity
@Table (name = "products")
public class ProductModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long productId;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private Float price;
	
	@ManyToOne
	@JoinColumn(name = "id")
	private OrderModel orderModel;
	
	public ProductModel(String name, String description, Float price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Long getId() {
		return productId;
	}

	public void setId(Long id) {
		this.productId = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, productId, name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductModel other = (ProductModel) obj;
		return Objects.equals(description, other.description) && Objects.equals(productId, other.productId)
				&& Objects.equals(name, other.name) && Objects.equals(price, other.price);
	}

	@Override
	public String toString() {
		return "ProductModel [id=" + productId + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}
}
