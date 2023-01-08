package teste.tecnico.attornatus.gerenciarpessoasapi.dto.person;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonOutputDto {
	private Long id;
	private String name;
	private LocalDate birthDate;
}
