package it.nextworks.nfvmano;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SebastianApplication {

	@Value("${crossorigin.origin}")
	public static String crossOrigin;
	
	public static void main(String[] args) {
		SpringApplication.run(SebastianApplication.class, args);
	}
}
