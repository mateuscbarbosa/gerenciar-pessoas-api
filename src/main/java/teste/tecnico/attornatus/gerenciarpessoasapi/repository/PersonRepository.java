package teste.tecnico.attornatus.gerenciarpessoasapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import teste.tecnico.attornatus.gerenciarpessoasapi.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

}
