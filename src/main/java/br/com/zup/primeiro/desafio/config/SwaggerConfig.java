package br.com.zup.primeiro.desafio.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	private List<ResponseMessage> responseMessageForGET() {
		return new ArrayList<ResponseMessage>() {
			private static final long serialVersionUID = 1L;

			{
				add(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Error"))
						.build());
				add(new ResponseMessageBuilder().code(422).message("Unprocessable Entity").build());
				add(new ResponseMessageBuilder().code(404).message("Not found").build());
				add(new ResponseMessageBuilder().code(400).message("Bad request").build());
			}
		};
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.zup.primeiro.desafio.controller"))
				.paths(PathSelectors.any()).build().useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, responseMessageForGET()).apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Trilha - Desafio")
				.description("REST API - Customer").version("1.0.0")
				.build();
	}
}
