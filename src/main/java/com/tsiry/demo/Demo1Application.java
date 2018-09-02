package com.tsiry.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.tsiry.demo.job.HelloJob;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

import org.quartz.JobDetail;

import static org.quartz.SimpleScheduleBuilder.*;

@SpringBootApplication
public class Demo1Application {

	public static void main(String[] args) {
		SpringApplication.run(Demo1Application.class, args);

		// define the job and tie it to our MyJob class
		JobDetail job = newJob(HelloJob.class).withIdentity("HelloJob", "group1").build();

		// Trigger the job to run now, and then repeat every 40 seconds
		Trigger trigger = newTrigger().withIdentity("trigger1", "group1").startNow()
				.withSchedule(simpleSchedule().withIntervalInSeconds(2).repeatForever()).build();

		// Grab the Scheduler instance from the Factory
		Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			// and start it off
			scheduler.start();
			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

	}
}
