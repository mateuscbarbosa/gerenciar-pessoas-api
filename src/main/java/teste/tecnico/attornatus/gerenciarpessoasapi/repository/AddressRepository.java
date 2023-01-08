package teste.tecnico.attornatus.gerenciarpessoasapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import teste.tecnico.attornatus.gerenciarpessoasapi.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

	List<Address> findAllByPersonId(Long personId);
}
