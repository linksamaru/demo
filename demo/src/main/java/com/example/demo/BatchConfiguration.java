package com.example.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.demo.steps.ItemReaderStep;
import com.example.demo.steps.ItemWriterStep;

@Configuration
public class BatchConfiguration {
	
	@Bean
	@JobScope
	public ItemReaderStep itemReaderStep() {
		return new ItemReaderStep();
	}
	
	@Bean
	@JobScope
	public ItemWriterStep itemWriterStep() {
		return new ItemWriterStep();
	}
	
	@Bean
	public Step readCSVStep(JobRepository jobRepository, PlatformTransactionManager manager) {
		return new StepBuilder("itemReaderStep", jobRepository)
				.tasklet(itemReaderStep(), manager)
				.build();
	}
	
	@Bean
	public Step writeDBStep(JobRepository jobRepository, PlatformTransactionManager manager) {
		return new StepBuilder("itemWriterStep", jobRepository)
				.tasklet(itemWriterStep(), manager)
				.build();
	}
	
	@Bean
	public Job processCSVJob(JobRepository jobRepository, PlatformTransactionManager manager) {
		return new JobBuilder("processCSVJob", jobRepository)
				.start(readCSVStep(jobRepository, manager))
				.next(writeDBStep(jobRepository, manager))
				.build();
	}
}
