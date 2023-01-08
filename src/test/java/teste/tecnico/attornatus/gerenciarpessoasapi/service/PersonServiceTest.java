package teste.tecnico.attornatus.gerenciarpessoasapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import jakarta.persistence.EntityNotFoundException;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.DetailedPersonOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonUpdateFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Person;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private PersonRepository personRepository;
	
	@InjectMocks
	private PersonService service;
	
	@Test
	void shouldNotShowAPersonWithWrongId() {
		assertThrows(EntityNotFoundException.class, () -> service.detailed(Mockito.anyLong()));
	}
	
	@Test
	void shouldShowAPersonWithCorretId() {
		Person person = new Person(1l,"Pessoa Teste", LocalDate.now(),null);
		
		Mockito.when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));

		Mockito.when(modelMapper.map(person, DetailedPersonOutputDto.class))
			.thenReturn(new DetailedPersonOutputDto(person.getId(),
													person.getName(),
													person.getBirthDate(),
													null));
		
		DetailedPersonOutputDto dto = service.detailed(person.getId());
		
		assertEquals(person.getId(), dto.getId());
		assertEquals(person.getName(), dto.getName());
		assertEquals(person.getBirthDate(), dto.getBirthDate());
	}

	@Test
	void shouldRegisterAPerson() {
		PersonFormDto formDto = new PersonFormDto("Pessoa Teste", LocalDate.now());
		
		Person person = new Person(null, formDto.getName(), formDto.getBirthDate(), null);
		
		Mockito.when(modelMapper.map(formDto, Person.class)).thenReturn(person);
		
		Mockito.when(modelMapper.map(person, PersonOutputDto.class))
			.thenReturn(new PersonOutputDto(person.getId(),
											person.getName(),
											person.getBirthDate()));
		
		PersonOutputDto dto = service.register(formDto);
		
		Mockito.verify(personRepository).save(Mockito.any());
		
		assertEquals(formDto.getName(), dto.getName());
		assertEquals(formDto.getBirthDate(), dto.getBirthDate());
	}
	
	@Test
	void shouldNotUpdateAPersonWithWrongId() {
		PersonUpdateFormDto personUpdate = new PersonUpdateFormDto(10l, "Pessoa Teste", LocalDate.now());
		
		Mockito.when(personRepository.findById(personUpdate.getId())).thenThrow(EntityNotFoundException.class);
		
		assertThrows(EntityNotFoundException.class, () -> service.update(personUpdate));
	}
	
	@Test
	void shouldUpdateAPersonWithCorrectId(){
		PersonUpdateFormDto personUpdate = new PersonUpdateFormDto(10l, "Pessoa Teste 1", LocalDate.now());
		
		Person person = new Person(personUpdate.getId(), "Pessoa Teste", personUpdate.getBirthDate(), null);
		
		Mockito.when(personRepository.findById(personUpdate.getId())).thenReturn(Optional.of(person));
		
		Mockito.when(modelMapper.map(person, PersonOutputDto.class))
					.thenReturn(new PersonOutputDto(person.getId(),
													personUpdate.getName(),
													person.getBirthDate()));
		
		PersonOutputDto dto = service.update(personUpdate);
				
		assertEquals(dto.getName(), personUpdate.getName());
		assertEquals(dto.getBirthDate(), personUpdate.getBirthDate());
	}
}
