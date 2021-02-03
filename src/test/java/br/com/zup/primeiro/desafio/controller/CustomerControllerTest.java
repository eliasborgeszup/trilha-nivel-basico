package br.com.zup.primeiro.desafio.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerControllerTest {

	@Autowired
	public WebApplicationContext context;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void shouldCreateCustomerAndReturnId() throws Exception {
		this.mockMvc
				.perform(post("/customers")
						.content("{\r\n" + "    \"name\": \"Elias\",\r\n" + "    \"birthDate\": \"2021-01-02\",\r\n"
								+ "    \"cpf\": \"73539183060\", \r\n"
								+ "    \"email\": \"eliasborges@zup.com.br\",\r\n"
								+ "    \"phone\": \"34992454428\",\r\n" + "    \"address\": \"Rua X\"\r\n" + "}")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldNotCreateCustomerWhenCpfExists() throws Exception {
		this.mockMvc
				.perform(post("/customers")
						.content("{\"name\": \"Elias\",\r\n" + "    \"birthDate\": \"2021-01-02\",\r\n"
								+ "    \"cpf\": \"10502544651\", \r\n"
								+ "    \"email\": \"eliasborges@zup.com.br\",\r\n"
								+ "    \"phone\": \"34992454428\",\r\n" + "    \"address\": \"Rua X\"}")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldNotCreateCustomerWhenCpfIsEmpty() throws Exception {
		this.mockMvc.perform(post("/customers")
				.content("{\"name\": \"Elias\",\r\n" + "    \"birthDate\": \"2021-01-02\",\r\n"
						+ "    \"cpf\": \"\", \r\n" + "    \"email\": \"eliasborges@zup.com.br\",\r\n"
						+ "    \"phone\": \"34992454428\",\r\n" + "    \"address\": \"Rua X\"}")
				.contentType(APPLICATION_JSON)).andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void findAllCustomersAndReturnListSucess() throws Exception {
		this.mockMvc.perform(get("/customers")).andExpect(status().isOk())
				.andExpect(content().string(containsString(buildCPF())));
	}

	@Test
	public void shouldNotFindAllWhenPageSizeBigger() throws Exception {
		this.mockMvc.perform(get("/customers?page=1000&size=1000&sort=id")).andExpect(status().isBadRequest());
	}

	@Test
	public void findByCpfAndReturnCustomer() throws Exception {
		this.mockMvc.perform(get("/customers/{cpf}", buildCPF())).andExpect(status().isOk())
				.andExpect(jsonPath("cpf", equalTo(buildCPF())));
	}

	@Test
	public void shouldNotFindCustomerByCpfWhenNotExists() throws Exception {
		this.mockMvc.perform(get("/customers/{cpf}", " ")).andExpect(status().isNotFound());
	}

	@Test
	public void shouldUpdateCustomerAndReturnId() throws Exception {
		this.mockMvc.perform(put("/customers/{cpf}", buildCPF())
				.content("{\r\n" + "    \"name\": \"Elias\",\r\n" + "    \"birthDate\": \"1997-01-03\", \r\n"
						+ "    \"email\": \"eliasborges@unipam.edu.br\",\r\n" + "    \"phone\": \"34992454428\",\r\n"
						+ "    \"address\": \"Rua Vereador Justo Machado de Brito, 65\"\r\n" + "}")
				.contentType(APPLICATION_JSON)).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldNotUpdateCustomerWhenCpfNotExists() throws Exception {
		this.mockMvc
				.perform(put("/customers/{cpf}", " ")
						.content("{\r\n" + "    \"name\": \"Elias\",\r\n" + "    \"birthDate\": \"1997-01-03\", \r\n"
								+ "    \"email\": \"eliasborges@unipam.edu.br\",\r\n"
								+ "    \"phone\": \"34992454428\",\r\n"
								+ "    \"address\": \"Rua Vereador Justo Machado de Brito, 65\"\r\n" + "}")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldNotUpdateCustomerWhenEmailIsInvalidFormatter() throws Exception {
		this.mockMvc
				.perform(put("/customers/{cpf}", buildCPF())
						.content("{\r\n" + "    \"name\": \"Elias\",\r\n" + "    \"birthDate\": \"1997-01-03\", \r\n"
								+ "    \"email\": \"eliasborgesunipam.edu.br\",\r\n"
								+ "    \"phone\": \"34992454428\",\r\n"
								+ "    \"address\": \"Rua Vereador Justo Machado de Brito, 65\"\r\n" + "}")
						.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldDeleteCustomer() throws Exception {
		this.mockMvc.perform(delete("/customers/{cpf}", "73539183060")).andExpect(status().isNoContent());
	}

	@Test
	public void shouldNotDeleteCustomerWhenCpfNotExists() throws Exception {
		this.mockMvc.perform(delete("/customers/{cpf}", " ")).andExpect(status().isNotFound());
	}

	private String buildCPF() {
		return "10502544651";
	}

}
