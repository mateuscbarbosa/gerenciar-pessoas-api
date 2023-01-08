package teste.tecnico.attornatus.gerenciarpessoasapi.controller;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.transaction.Transactional;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonUpdateFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Person;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.PersonRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PersonControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private PersonRepository personRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private void configureObjectMapperToWriteDateAsString() {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);		
	}
	
	@Test
	void shouldNotShowAPersonWithWrongId() throws Exception {
		String json = "9999";
		
		mvc.perform(MockMvcRequestBuilders
				.get("/people/"+json))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void shouldNotRegisterAPersonWithoutData() throws Exception{
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/people")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void shouldRegisterAPersonWithCompleteData() throws Exception {
		PersonFormDto formDto = new PersonFormDto("Pessoa Teste", LocalDate.now());
		
		configureObjectMapperToWriteDateAsString();
		String json = objectMapper.writeValueAsString(formDto);
		String expectedJson = "{\"name\": \"Pessoa Teste\", \"birthDate\":"+LocalDate.now().toString()+"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/people")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
	
	@Test
	void shouldNotUpdateAPersonWithWrongId() throws Exception{		
		PersonUpdateFormDto updateForm = new PersonUpdateFormDto(99999l, "Pessoa Teste", LocalDate.now());
		
		configureObjectMapperToWriteDateAsString();
		String json = objectMapper.writeValueAsString(updateForm);
		
		mvc.perform(MockMvcRequestBuilders
					.put("/people")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void shouldUpdateAPerson() throws Exception{
		Person person = new Person("Pessoa Teste", LocalDate.now());
		Person savedPerson = personRepository.save(person);
		
		PersonUpdateFormDto personUpdate = new PersonUpdateFormDto(savedPerson.getId(), "Pessoa Teste 90", LocalDate.now());
		
		configureObjectMapperToWriteDateAsString();
		String json = objectMapper.writeValueAsString(personUpdate);
		String expectedJson = "{\"name\": \"Pessoa Teste 90\", \"birthDate\":"+LocalDate.now().toString()+"}";
		
		mvc.perform(MockMvcRequestBuilders
					.put("/people")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
}
