package teste.tecnico.attornatus.gerenciarpessoasapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.service.PersonService;

@RestController
@RequestMapping("/people")
public class PersonController {
	
	@Autowired
	private PersonService service;
	
	@GetMapping
	public Page<PersonOutputDto> list(Pageable pagination){
		return service.list(pagination);
	}
}
