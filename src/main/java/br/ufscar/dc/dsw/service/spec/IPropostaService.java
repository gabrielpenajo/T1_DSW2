package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Proposta;

public interface IPropostaService {

	Proposta buscarPorId(Long id);

	List<Proposta> buscarTodos();

	void salvar(Proposta proposta);

	void excluir(Long id);

	public List<Proposta> buscarTodosPorCliente_Id(Long id);
	public List<Proposta> buscarTodosPorCliente_CPF(String CPF);
	public List<Proposta> buscarTodosPorPacote_Id(Long id);
	// servico para propostas validas
}
