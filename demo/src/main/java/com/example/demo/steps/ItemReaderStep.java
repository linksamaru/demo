package com.example.demo.steps;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import com.example.demo.models.ProductModel;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

public class ItemReaderStep implements Tasklet {

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Reader reader = new FileReader(resourceLoader.getResource("classpath:files/products.csv").getFile());
		CSVParser parser = new CSVParserBuilder()
				.withSeparator(';')
				.build();
		CSVReader csvReader = new CSVReaderBuilder(reader)
				.withCSVParser(parser)
				.withSkipLines(1)
				.build();
		
		List<ProductModel> productList = new ArrayList<>();
		
		String[] actualLine;
		
		while((actualLine = csvReader.readNext()) != null) {
			System.out.println(actualLine);
			ProductModel product = new ProductModel(actualLine[0], actualLine[1],Float.parseFloat(actualLine[2]));
			productList.add(product);
		}
		csvReader.close();
		reader.close();
		
		chunkContext.getStepContext()
			.getStepExecution()
			.getJobExecution()
			.getExecutionContext()
			.put("prodcutList", productList);
		
		return RepeatStatus.FINISHED;
	}
}
