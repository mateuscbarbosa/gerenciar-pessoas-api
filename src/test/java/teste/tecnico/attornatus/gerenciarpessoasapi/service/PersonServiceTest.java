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
													person.getAddresses()));
		
		DetailedPersonOutputDto dto = service.detailed(person.getId());
		
		assertEquals(dto.getId(), person.getId());
		assertEquals(dto.getName(), person.getName());
		assertEquals(dto.getBirthDate(), person.getBirthDate());
	}

}
