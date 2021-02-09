package br.com.zup.primeiro.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TrilhaNivelBasicoApplication {
	public static void main(String[] args) {
		SpringApplication.run(TrilhaNivelBasicoApplication.class, args);
	}
}
