package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long>{

	Proposta findById(long id);

	List<Proposta> findAllByUsuario(Usuario u);
	
	Proposta save(Proposta compra);

	void deleteById(Long id);
}