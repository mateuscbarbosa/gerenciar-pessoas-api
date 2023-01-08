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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PersonControllerTest {

	@Autowired
	private MockMvc mvc;
	
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
		configureObjectMapperToWriteDateAsString();
		PersonFormDto formDto = new PersonFormDto("Pessoa Teste", LocalDate.now());
		
		String json = objectMapper.writeValueAsString(formDto);
		String expectedJson = "{\"name\": \"Pessoa Teste\", \"birthDate\":"+LocalDate.now().toString()+"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/people")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
}
