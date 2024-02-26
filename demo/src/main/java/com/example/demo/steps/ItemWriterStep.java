package com.example.demo.steps;

import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.ProductModel;
import com.example.demo.services.ProductService;

public class ItemWriterStep implements Tasklet{

	@Autowired
	ProductService productService;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

		@SuppressWarnings("unchecked")
		List<ProductModel> productList = (List<ProductModel>) chunkContext.getStepContext()
				.getStepExecution()
				.getJobExecution()
				.getExecutionContext()
				.get("prodcutList");
		
		productService.saveAll(productList);
		
		return RepeatStatus.FINISHED;
	}

}
