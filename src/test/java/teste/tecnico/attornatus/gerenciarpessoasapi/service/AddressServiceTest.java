package teste.tecnico.attornatus.gerenciarpessoasapi.service;

import static org.junit.jupiter.api.Assertions.*;

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
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.address.AddressFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.address.AddressOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Address;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Person;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.AddressRepository;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.PersonRepository;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private PersonRepository personRepository;
	
	@Mock
	private AddressRepository addressRepository;
	
	@InjectMocks
	private AddressService service;
	
	@Test
	void shouldNotRegisterAnAddressWithoutAValidPersonId() {
		AddressFormDto formDto = new AddressFormDto(9999l,"0000000","Alguma Rua", 0, "Alguma Cidade");
		
		assertThrows(EntityNotFoundException.class, () -> service.register(formDto));
	}
	
	@Test
	void shouldRegisterAnAddressWithAValidPersonId() {
		Person person = new Person("Pessoa Teste", LocalDate.now());
		
		Mockito.when(personRepository.findById(person.getId())).thenReturn(Optional.of(person));
		
		AddressFormDto formDto = new AddressFormDto(person.getId(),"0000000","Alguma Rua", 0, "Alguma Cidade");
		Address address = new Address(formDto.getZipCode(), formDto.getPublicPlace(), formDto.getNumber(), formDto.getCity(), false, person);
		
		Mockito.when(modelMapper.map(formDto, Address.class)).thenReturn(address);
		
		Mockito.when(modelMapper.map(address, AddressOutputDto.class)).thenReturn(new AddressOutputDto(
																						address.getId(),
																						address.getZipCode(),
																						address.getPublicPlace(),
																						address.getNumber(),
																						address.getCity(),
																						address.getMainAddress()));
		AddressOutputDto dto = service.register(formDto);
		
		Mockito.verify(addressRepository).save(Mockito.any());
		
		assertEquals(formDto.getZipCode(), dto.getZipCode());
		assertEquals(formDto.getPublicPlace(), dto.getPublicPlace());
		assertEquals(formDto.getNumber(), dto.getNumber());
		assertEquals(formDto.getCity(), dto.getCity());
		
	}
	
	@Test
	void shouldNotUpdateMainAddressWithWrongId() {
		assertThrows(EntityNotFoundException.class, () -> service.mainAddressUpdate(Mockito.anyLong()));
	}
	
	@Test
	void shouldUpdateMainAddressWithCorrectId() {
		Person person = new Person("Pessoa Teste", LocalDate.now());
		Address address = new Address(1l, "00000000", "Alguma Rua", 0, "Alguma Cidade", false, person);
		
		Mockito.when(addressRepository.findById(address.getId())).thenReturn(Optional.of(address));
		
		Mockito.when(modelMapper.map(address, AddressOutputDto.class)).thenReturn(new AddressOutputDto(
																						address.getId(),
																						address.getZipCode(),
																						address.getPublicPlace(),
																						address.getNumber(),
																						address.getCity(),
																						true));
		
		AddressOutputDto dto = service.mainAddressUpdate(address.getId());
		
		Mockito.verify(addressRepository).save(Mockito.any());
		
		assertEquals(address.getZipCode(), dto.getZipCode());
		assertEquals(address.getPublicPlace(), dto.getPublicPlace());
		assertEquals(address.getNumber(), dto.getNumber());
		assertEquals(address.getCity(), dto.getCity());
		assertEquals(address.getMainAddress(), dto.getMainAddress());
	}
}
