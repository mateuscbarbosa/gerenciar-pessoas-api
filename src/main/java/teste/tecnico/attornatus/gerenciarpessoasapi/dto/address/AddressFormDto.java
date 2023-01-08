package teste.tecnico.attornatus.gerenciarpessoasapi.dto.address;

import com.fasterxml.jackson.annotation.JsonAlias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressFormDto {

	@NotNull
	@JsonAlias("pessoaId")
	private Long personId;
	
	@NotBlank
	@JsonAlias("CEP")
	private String zipCode;
	
	@NotBlank
	@JsonAlias("logradouro")
	private String publicPlace;
	
	@NotNull
	@JsonAlias("numero")
	private Integer number;
	
	@NotBlank
	@JsonAlias("cidade")
	private String city;
}
