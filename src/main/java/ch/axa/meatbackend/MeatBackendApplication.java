package ch.axa.meatbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"ch.axa.meatbackend.config",
				"ch.axa.meatbackend.graphql",
				"ch.axa.meatbackend.rest"})
public class MeatBackendApplication{
	public static void main(String[] args) {
		SpringApplication.run(MeatBackendApplication.class, args);
	}
}
