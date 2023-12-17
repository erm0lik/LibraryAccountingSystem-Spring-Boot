package org.project.LibraryAccountingSystem.Spring.Boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.sql.DataSource;

@SpringBootApplication
public class LibraryAccountingSystemSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryAccountingSystemSpringBootApplication.class, args);
	}
	@Bean
	HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/libray");
		dataSource.setUsername("postgres");
		dataSource.setPassword("Vlad211003");
		return dataSource;
	}
}
