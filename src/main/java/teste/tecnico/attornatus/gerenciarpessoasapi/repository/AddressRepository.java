package teste.tecnico.attornatus.gerenciarpessoasapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import teste.tecnico.attornatus.gerenciarpessoasapi.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
