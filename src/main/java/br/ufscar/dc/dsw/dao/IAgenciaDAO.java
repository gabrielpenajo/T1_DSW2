package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Agencia;

@SuppressWarnings("unchecked")
public interface IAgenciaDAO extends CrudRepository<Agencia, Long>{

	Agencia findById(long id);
	
	Agencia findByCNPJ (String CNPJ);

	List<Agencia> findAll();
	
	Agencia save(Agencia editora);

	void deleteById(Long id);
}
