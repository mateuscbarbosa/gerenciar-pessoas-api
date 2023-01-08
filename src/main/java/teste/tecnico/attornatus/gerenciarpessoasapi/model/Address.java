package teste.tecnico.attornatus.gerenciarpessoasapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String zipCode;
	private String publicPlace;
	private Integer number;
	private String city;
	private Boolean mainAddress;
	
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;

	public Address(String zipCode, String publicPlace, Integer number, String city, Boolean mainAddress,
			Person person) {
		this.zipCode = zipCode;
		this.publicPlace = publicPlace;
		this.number = number;
		this.city = city;
		this.mainAddress = mainAddress;
		this.person = person;
	}
	
}
