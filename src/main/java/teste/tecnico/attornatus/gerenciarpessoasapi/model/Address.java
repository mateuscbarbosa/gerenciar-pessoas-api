package teste.tecnico.attornatus.gerenciarpessoasapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
	
	private Long id;
	private String publicPlace;
	private String zipCode;
	private Integer number;
	private String city;
	private Boolean mainAddress;
}
