package teste.tecnico.attornatus.gerenciarpessoasapi.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.address.AddressFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.address.AddressOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Address;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Person;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.AddressRepository;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.PersonRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	public AddressOutputDto register(AddressFormDto addressForm) {
		Person person = findPerson(addressForm.getPersonId());
		
		Address address = modelMapper.map(addressForm, Address.class);
		address.setId(null);
		address.setMainAddress(false);
		addressRepository.save(address);
		
		person.addAddress(address);
		personRepository.save(person);
		
		return modelMapper.map(address, AddressOutputDto.class);
	}

	private Person findPerson(Long personId) {
		return personRepository.findById(personId).orElseThrow(() -> new EntityNotFoundException());
	}

	@Transactional
	public AddressOutputDto mainAddressUpdate(Long id) {
		Address address = addressRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		
		address.setMainAddress(true);
		addressRepository.save(address);
		
		List<Address> addresses = addressRepository.findAllByPersonId(address.getPerson().getId());
		
		for (Address addressFound : addresses) {
			if(!addressFound.getId().equals(address.getId())) {
				addressFound.setMainAddress(false);
				addressRepository.save(addressFound);
			}
		}
		
		return modelMapper.map(address, AddressOutputDto.class);
	}

	
}
