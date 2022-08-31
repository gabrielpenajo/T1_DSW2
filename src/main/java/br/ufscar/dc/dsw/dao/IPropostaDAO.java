package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Proposta;

@SuppressWarnings("unchecked")
public interface IPropostaDAO extends CrudRepository<Proposta, Long>{

	Proposta save(Proposta compra);
	Proposta findById(long id);
	void deleteById(Long id);
	List<Proposta> findAll();
	List<Proposta> findAllByCliente_Id(Long id);
	List<Proposta> findAllByCliente_CPF(String cpf);
	List<Proposta> findAllByPacote_Id(Long id);

}