package br.com.zup.primeiro.desafio.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.primeiro.desafio.controller.response.commons.ErrorResponse;
import br.com.zup.primeiro.desafio.controller.response.customer.CustomerIDResponse;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.NotFoundException;
import br.com.zup.primeiro.desafio.repository.CustomerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(scripts = "/script.sql")
@Transactional
public class CustomerControllerTest {

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private CustomerRepository repository;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void shouldCreateCustomerAndReturnId() throws Exception {

		String path = "customer/createCustomerRequest.json";

		String contentAsString = this.mockMvc
				.perform(post("/customers")
						.content(getFileContent(path))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("id", notNullValue()))
				.andReturn().getResponse().getContentAsString();

		CustomerIDResponse customerIDResponse = new ObjectMapper().readValue(contentAsString, CustomerIDResponse.class);

		Customer customer = repository.getOne(customerIDResponse.getId());

		assertNotNull(customer);
		assertEquals(customerIDResponse.getId(), customer.getId());

		assertEquals("Elias", customer.getName());
		assertEquals(LocalDate.of(1997, 01, 03), customer.getBirthDate());
		assertEquals("73539183060", customer.getCpf());
		assertEquals("eliasborges@unipam.edu.br", customer.getEmail());
		assertEquals("34988154428", customer.getPhone());
		assertEquals("Rua Vereador Justo Machado de Brito, 61", customer.getAddress());
	}

	@Test
	public void shouldNotCreateCustomerWhenCpfExists() throws Exception {

		String path = "customer/createCustomerCpfExistRequest.json";

		this.mockMvc.perform(post("/customers")
				.content(getFileContent(path))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity());
	}

	@Test
	public void shouldNotCreateCustomerWhenCpfIsEmpty() throws Exception {

		String path = "customer/createCustomerAndNullCpfRequest.json";

		this.mockMvc.perform(post("/customers").content(getFileContent(path)).contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest());
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
		String path = "customer/updateCustomerRequest.json";

		String contentAsString = this.mockMvc
				.perform(
						put("/customers/{cpf}", buildCPF())
						.content(getFileContent(path))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id", notNullValue()))
				.andReturn().getResponse().getContentAsString();

		CustomerIDResponse customerIDResponse = new ObjectMapper().readValue(contentAsString, CustomerIDResponse.class);

		Customer customer = repository.getOne(customerIDResponse.getId());

		assertNotNull(customer);
		assertEquals(customerIDResponse.getId(), customer.getId());

		assertEquals("Elias", customer.getName());
		assertEquals(LocalDate.of(1997, 01, 03), customer.getBirthDate());
		assertEquals("10502544651", customer.getCpf());
		assertEquals("eliasborges@unipam.edu.br", customer.getEmail());
		assertEquals("34992454428", customer.getPhone());
		assertEquals("Rua Vereador Justo Machado de Brito, 65", customer.getAddress());
	}

	@Test
	public void shouldNotUpdateCustomerWhenCpfNotExists() throws Exception {
		String path = "customer/updateCustomerRequest.json";

		String contentAsString = this.mockMvc
				.perform(put("/customers/{cpf}", "10502544652")
						.content(getFileContent(path))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString();

		NotFoundException notFoundException = new ObjectMapper().readValue(contentAsString, NotFoundException.class);

		assertEquals("Documento nao encontrado.", notFoundException.getMessage());
	}

	@Test
	public void shouldNotUpdateCustomerWhenFieldsInvalidFormatter() throws Exception {
		String path = "customer/updateCustomerAndEmailInvalidRequest.json";

		this.mockMvc
				.perform(put("/customers/{cpf}", buildCPF())
						.content(getFileContent(path))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("[EMAIL] - Email invalido.")))
				.andExpect(content().string(containsString("[PHONE] - O valor informado excede ou nao atende a quantidade de caracteres permitidos.")))
				.andExpect(content().string(containsString("[NAME] - Nao e permitido campos vazios.")));
	}

	@Test
	public void shouldDeleteCustomer() throws Exception {
		this.mockMvc.perform(delete("/customers/{cpf}", buildCPF())).andExpect(status().isNoContent());
	}

	@Test
	public void shouldNotDeleteCustomerWhenCpfNotExists() throws Exception {
		this.mockMvc.perform(delete("/customers/{cpf}", " ")).andExpect(status().isNotFound());
	}

	private String getFileContent(String path) throws IOException {
		return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(path));
	}

	private String buildCPF() {
		return "10502544651";
	}

}
