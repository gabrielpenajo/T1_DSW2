package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Pacote;

@SuppressWarnings("unchecked")
public interface IPacoteDAO extends CrudRepository<Pacote, Long>{

	Pacote findById(long id);

	List<Pacote> findAll();
	
	Pacote save(Pacote livro);

	void deleteById(Long id);
}