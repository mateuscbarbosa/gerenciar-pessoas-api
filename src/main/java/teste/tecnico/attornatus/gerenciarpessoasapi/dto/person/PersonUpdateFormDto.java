package teste.tecnico.attornatus.gerenciarpessoasapi.dto.person;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonUpdateFormDto extends PersonFormDto{

	@NotNull
	private Long id;
	
	public PersonUpdateFormDto(Long id, String name, LocalDate birthDate) {
		super(name,birthDate);
		this.id = id;
	}
}
