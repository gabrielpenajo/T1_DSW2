package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Usuario;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long>{

	Proposta save(Proposta compra);
	Proposta findById(long id);
	void deleteById(Long id);
	List<Proposta> findAll();
	List<Proposta> findAllByUsuario(Usuario u); // Fazer o service para separar em propostas ativas e nao ativas
	List<Proposta> findAllByPacote(Pacote p);

}