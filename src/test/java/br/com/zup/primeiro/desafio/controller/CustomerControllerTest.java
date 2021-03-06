package br.com.zup.primeiro.desafio.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.zup.primeiro.desafio.controller.response.customer.CustomerIDResponse;
import br.com.zup.primeiro.desafio.controller.response.customer.CustomerResponse;
import br.com.zup.primeiro.desafio.entity.Customer;
import br.com.zup.primeiro.desafio.exceptions.DocumentAlreadyExistsException;
import br.com.zup.primeiro.desafio.exceptions.NotFoundException;
import br.com.zup.primeiro.desafio.exceptions.PaginationSizeLimitExceededException;
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

		String contentAsString = this.mockMvc.perform(post("/customers")
				.content(getFileContent(path))
				.contentType(APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity())
				.andReturn().getResponse().getContentAsString();
		
		DocumentAlreadyExistsException documentAlreadyExistsException = new ObjectMapper().readValue(contentAsString, DocumentAlreadyExistsException.class);

		assertEquals("Documento existente.", documentAlreadyExistsException.getMessage());
	}

	@Test
	public void shouldNotCreateCustomerWhenCpfIsEmpty() throws Exception {

		String path = "customer/createCustomerAndNullCpfRequest.json";

		this.mockMvc.perform(post("/customers")
						.content(getFileContent(path))
						.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andExpect(content().string(containsString("[CPF] - CPF invalido.")));
	}

	@Test
	public void findAllCustomersAndReturnListSucess() throws Exception {

		String contentAsString = this.mockMvc.perform(get("/customers"))
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("9ee70d86-4e1a-4616-af7f-d18cd7c588bc")))
				.andExpect(content().string(containsString("Rua Vereador Justo Machado de Brito, 65")))
				.andExpect(content().string(containsString("eliasborges@unipam.edu.br")))
				.andExpect(content().string(containsString("Elias")))
				.andExpect(content().string(containsString("34992454428")))
				.andExpect(content().string(containsString("10502544651")))
				.andReturn().getResponse().getContentAsString();
		
		ObjectNode node = new ObjectMapper().readValue(contentAsString, ObjectNode.class);
		JsonNode content = node.get("content");
		assertNotNull(content);
		

		List<CustomerResponse> allCustomers = new ObjectMapper().readValue(content.toString(), new TypeReference<List<CustomerResponse>>() {});
		assertTrue(allCustomers != null && !allCustomers.isEmpty());
		assertEquals(1, allCustomers.size());
	}

	@Test
	public void shouldNotFindAllWhenPageSizeBigger() throws Exception {
		String url = "/customers?page=1000&size=1000&sort=id";
		
		String contentAsString = this.mockMvc.perform(get(url))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		
		PaginationSizeLimitExceededException exception = new ObjectMapper().readValue(contentAsString, PaginationSizeLimitExceededException.class);

		assertEquals("Quantidade de paginas maior que o permitido.", exception.getMessage());
	}

	@Test
	public void findByCpfAndReturnCustomer() throws Exception {
		
		String contentAsString = this.mockMvc.perform(get("/customers/{cpf}", buildCPF()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id", notNullValue()))
				.andReturn().getResponse().getContentAsString();

		CustomerResponse customerResponse = new ObjectMapper().readValue(contentAsString, CustomerResponse.class);
		
		assertNotNull(customerResponse);
		
		assertEquals("9ee70d86-4e1a-4616-af7f-d18cd7c588bc" , customerResponse.getId());
		assertEquals("Rua Vereador Justo Machado de Brito, 65", customerResponse.getAddress());
		assertEquals(LocalDate.of(1997, 01, 03), customerResponse.getBirthDate());
		assertEquals("10502544651", customerResponse.getCpf());
		assertEquals("eliasborges@unipam.edu.br", customerResponse.getEmail());
		assertEquals("Elias", customerResponse.getName());
		assertEquals("34992454428", customerResponse.getPhone());
	}

	@Test
	public void shouldNotFindCustomerByCpfWhenNotExists() throws Exception {
		String contentAsString = this.mockMvc.perform(get("/customers/{cpf}", "10502544652"))
				.andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString();
		
		NotFoundException notFoundException = new ObjectMapper().readValue(contentAsString, NotFoundException.class);

		assertEquals("Documento nao encontrado.", notFoundException.getMessage());
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
		
		Optional<Customer> customer = repository.findByCpf(buildCPF());
		
		assertEquals(customer.isEmpty(), true);
	}

	@Test
	public void shouldNotDeleteCustomerWhenCpfNotExists() throws Exception {
		String contentAsString = this.mockMvc.perform(delete("/customers/{cpf}", "10502544652"))
				.andExpect(status().isNotFound())
				.andReturn().getResponse().getContentAsString();
		
		NotFoundException notFoundException = new ObjectMapper().readValue(contentAsString, NotFoundException.class);

		assertEquals("Documento nao encontrado.", notFoundException.getMessage());
	}

	private String getFileContent(String path) throws IOException {
		return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(path));
	}

	private String buildCPF() {
		return "10502544651";
	}

}
