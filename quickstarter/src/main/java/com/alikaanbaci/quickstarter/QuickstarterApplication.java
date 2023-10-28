package com.alikaanbaci.quickstarter;

import com.alikaanbaci.quickstarter.user.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class QuickstarterApplication {

	public static void main(String[] args) {

		SpringApplication.run(QuickstarterApplication.class, args);
	}
}
