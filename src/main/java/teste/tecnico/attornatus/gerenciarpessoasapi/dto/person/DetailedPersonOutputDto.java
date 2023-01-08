package teste.tecnico.attornatus.gerenciarpessoasapi.dto.person;

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
}
