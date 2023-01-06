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
public class PersonOutputDto {
	private Long id;
	private String name;
	private LocalDate birthDate;
	private List<Address> addresses;
}
