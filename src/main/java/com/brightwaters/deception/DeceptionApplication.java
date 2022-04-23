package com.brightwaters.deception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"com.brightwaters.deception.*", "com.brightwaters.deception.model.h2.*" , "com.brightwaters.deception.repository.h2.*","com.brightwaters.deception.repository.postgres.*", "com.brightwaters.deception.model.postgres.*"})
@EntityScan("com.brightwaters.deception.model.h2.*, com.brightwaters.deception.model.postgres.*")
@EnableScheduling
@EnableCaching
public class DeceptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeceptionApplication.class, args);
		
	}

}
