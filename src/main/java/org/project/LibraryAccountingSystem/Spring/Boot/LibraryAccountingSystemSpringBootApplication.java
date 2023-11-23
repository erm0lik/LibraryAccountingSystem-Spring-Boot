package org.project.LibraryAccountingSystem.Spring.Boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class LibraryAccountingSystemSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryAccountingSystemSpringBootApplication.class, args);
	}
	@Bean
	HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

}
