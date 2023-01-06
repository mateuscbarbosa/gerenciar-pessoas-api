package teste.tecnico.attornatus.gerenciarpessoasapi.dto.person;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonFormDto {
	
	@NotNull
	@JsonAlias("nome")
	private String name;
	
	@PastOrPresent
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonAlias("dataNascimento")
	private LocalDate birthDate;
}
