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
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.address.AddressFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Address;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Person;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.AddressRepository;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.PersonRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AddressControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private AddressRepository addressRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	private void configureObjectMapperToWriteDateAsString() {
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);		
	}

	private Person registredPerson() {
		Person person = new Person("Pessoa Teste", LocalDate.now());
		Person createdperson = personRepository.save(person);
		
		return createdperson;
	}
	
	@Test
	void shouldNotRegisterAnAddressWithoutAValidPersonId() throws Exception{
		AddressFormDto formDto = new AddressFormDto(99999l,"00000000","Algum Lugar", 0, "Uma Cidade");
		
		String json = objectMapper.writeValueAsString(formDto);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/addresses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void shouldRegisterAnAddressWithAValidPerson() throws Exception{
		Person person = registredPerson();
		
		AddressFormDto formDto = new AddressFormDto(person.getId(),"00000000","Algum Lugar", 0, "Uma Cidade");
		
		String json = objectMapper.writeValueAsString(formDto);
		String expectedJson = "{\"zipCode\":\"00000000\",\"publicPlace\":\"Algum Lugar\",\"number\":0,\"city\":\"Uma Cidade\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/addresses")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
	
	@Test
	void shouldNotUpdateMainAddressWithWrongId() throws Exception{
				
		mvc.perform(MockMvcRequestBuilders
				.put("/addresses/"+9999l))
			.andExpect(MockMvcResultMatchers.status().isNotFound());
	}
	
	@Test
	void shouldUpdateMainAddressWithWrongId() throws Exception{
		Person person = registredPerson();
		
		Address address = new Address("00000000", "Algum Lugar", 0, "Alguma Cidade", true, person);
		Address addressRegistred = addressRepository.save(address);
		
		configureObjectMapperToWriteDateAsString();
		String json = objectMapper.writeValueAsString(addressRegistred);
		String expectedJson = "{\"zipCode\":\"00000000\",\"publicPlace\":\"Algum Lugar\",\"number\":0,\"city\":\"Alguma Cidade\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.put("/addresses/"+addressRegistred.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedJson));
	}
}
