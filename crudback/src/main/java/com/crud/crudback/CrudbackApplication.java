package com.crud.crudback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CrudbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudbackApplication.class, args);
	}

}
