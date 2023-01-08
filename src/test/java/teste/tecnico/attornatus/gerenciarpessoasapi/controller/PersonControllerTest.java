package teste.tecnico.attornatus.gerenciarpessoasapi.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import jakarta.transaction.Transactional;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.PersonRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PersonControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	void shouldNotShowAPersonWithWrongId() throws Exception {
		String json = "9999";
		
		mvc.perform(MockMvcRequestBuilders
				.get("/people/"+json))
				.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
