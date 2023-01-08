package teste.tecnico.attornatus.gerenciarpessoasapi.dto.address;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressOutputDto {

	private Long id;
	private String zipCode;
	private String publicPlace;
	private Integer number;
	private String city;
	private Boolean mainAddress;
}
