package teste.tecnico.attornatus.gerenciarpessoasapi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonOutputDto;
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

}
