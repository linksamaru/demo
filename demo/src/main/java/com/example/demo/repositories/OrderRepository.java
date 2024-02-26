package com.example.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.OrderModel;
import com.example.demo.models.Status;

@Repository
public interface OrderRepository extends JpaRepository<OrderModel, Long>{
	public ArrayList<OrderModel> getOrdersByCreationDateAndStatusAndTotal(Long creationDate, Status status, float total);
}
