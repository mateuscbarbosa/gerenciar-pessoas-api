package teste.tecnico.attornatus.gerenciarpessoasapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.DetailedPersonOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.person.PersonUpdateFormDto;
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
	
	@GetMapping("/{id}")
	public ResponseEntity<DetailedPersonOutputDto> detalied(@PathVariable @NotNull Long id){
		DetailedPersonOutputDto dto = service.detailed(id);
		
		return ResponseEntity.ok(dto);
	}
	
	@PostMapping
	public ResponseEntity<PersonOutputDto> register(@RequestBody @Valid PersonFormDto personForm, UriComponentsBuilder uriBuilder){
		PersonOutputDto dto = service.register(personForm);
		
		URI uri = uriBuilder.path("/people/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping
	public ResponseEntity<PersonOutputDto> update(@RequestBody @Valid PersonUpdateFormDto personUpdate){
		PersonOutputDto dto = service.update(personUpdate);
		
		return ResponseEntity.ok(dto);
	}
}
