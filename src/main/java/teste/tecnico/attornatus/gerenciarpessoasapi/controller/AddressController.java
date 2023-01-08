package teste.tecnico.attornatus.gerenciarpessoasapi.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.address.AddressFormDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.dto.address.AddressOutputDto;
import teste.tecnico.attornatus.gerenciarpessoasapi.service.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {

	@Autowired
	private AddressService service;
	
	@GetMapping
	public Page<AddressOutputDto> list(Pageable pagination){
		return service.list(pagination);
	}
	
	@PostMapping
	public ResponseEntity<AddressOutputDto> register(@RequestBody @Valid AddressFormDto addressForm, UriComponentsBuilder uriBuilder){
		AddressOutputDto dto = service.register(addressForm);
		
		URI uri = uriBuilder.path("/addresses/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
}
