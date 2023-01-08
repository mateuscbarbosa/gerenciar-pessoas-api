package teste.tecnico.attornatus.gerenciarpessoasapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.DetailedPersonOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonUpdateFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Person;
import teste.tecnico.attornatus.gerenciarpessoasapi.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Page<PersonOutputDto> list(Pageable pagination) {
		Page<Person> people = personRepository.findAll(pagination);
		return people.map(p -> modelMapper.map(p, PersonOutputDto.class));
	}

	public DetailedPersonOutputDto detailed(Long id) {
		Person person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		
		return modelMapper.map(person, DetailedPersonOutputDto.class);
	}
	
	@Transactional
	public PersonOutputDto register(PersonFormDto personForm) {
		Person person = modelMapper.map(personForm, Person.class);
		
		person.setId(null);
		personRepository.save(person);
		
		return modelMapper.map(person, PersonOutputDto.class);
	}

	@Transactional
	public PersonOutputDto update(PersonUpdateFormDto personUpdate) {
		Person person = personRepository.findById(personUpdate.getId()).orElseThrow(() -> new EntityNotFoundException());
		
		person.updateData(personUpdate.getName(), personUpdate.getBirthDate());
		
		return modelMapper.map(person, PersonOutputDto.class);
	}


}
