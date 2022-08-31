package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Agencia;

@SuppressWarnings("unchecked")
public interface IAgenciaDAO extends CrudRepository<Agencia, Long>{

	Agencia save(Agencia agencia);
	Agencia findById(long id);
	Agencia findByCNPJ(String CNPJ);
	Agencia findByEmail(String email);
	void deleteById(Long id);
	List<Agencia> findAll();
	
	// @Query("SELECT u FROM Agencia u WHERE u.email = :username")
    // public Agencia getAgencByUsername(@Param("username") String username);

}
