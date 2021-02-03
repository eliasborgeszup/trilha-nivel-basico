package br.com.zup.primeiro.desafio.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.apache.commons.io.IOUtils;

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
		String jsonContent = IOUtils
				.toString(getClass().getClassLoader().getResourceAsStream("customer/createCustomerRequest.json"));

		this.mockMvc.perform(post("/customers").content(jsonContent).contentType(APPLICATION_JSON))
				.andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldNotCreateCustomerWhenCpfExists() throws Exception {
		String jsonContent = IOUtils
				.toString(getClass().getClassLoader().getResourceAsStream("customer/createCustomerCpfExistRequest.json"));

		this.mockMvc.perform(post("/customers").content(jsonContent).contentType(APPLICATION_JSON))
				.andExpect(status().isUnprocessableEntity()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldNotCreateCustomerWhenCpfIsEmpty() throws Exception {
		String jsonContent = IOUtils.toString(
				getClass().getClassLoader().getResourceAsStream("customer/createCustomerAndNullCpfRequest.json"));

		this.mockMvc.perform(post("/customers").content(jsonContent).contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
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
		String jsonContent = IOUtils
				.toString(getClass().getClassLoader().getResourceAsStream("customer/updateCustomerRequest.json"));

		this.mockMvc.perform(put("/customers/{cpf}", buildCPF()).content(jsonContent).contentType(APPLICATION_JSON))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldNotUpdateCustomerWhenCpfNotExists() throws Exception {
		String jsonContent = IOUtils
				.toString(getClass().getClassLoader().getResourceAsStream("customer/updateCustomerRequest.json"));

		this.mockMvc.perform(put("/customers/{cpf}", " ").content(jsonContent).contentType(APPLICATION_JSON))
				.andExpect(status().isNotFound()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void shouldNotUpdateCustomerWhenEmailIsInvalidFormatter() throws Exception {
		String jsonContent = IOUtils.toString(
				getClass().getClassLoader().getResourceAsStream("customer/updateCustomerAndEmailInvalidRequest.json"));

		this.mockMvc.perform(put("/customers/{cpf}", buildCPF()).content(jsonContent).contentType(APPLICATION_JSON))
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
