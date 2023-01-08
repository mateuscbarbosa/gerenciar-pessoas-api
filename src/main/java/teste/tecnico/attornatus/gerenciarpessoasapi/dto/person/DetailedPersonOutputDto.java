package teste.tecnico.attornatus.gerenciarpessoasapi.dto.person;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import teste.tecnico.attornatus.gerenciarpessoasapi.model.Address;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailedPersonOutputDto extends PersonOutputDto{

	private List<Address> addresses;
	
	public DetailedPersonOutputDto(Long id, String name, LocalDate birthDate, List<Address> addresses) {
		super(id, name, birthDate);
		this.addresses = addresses;
	}
}
