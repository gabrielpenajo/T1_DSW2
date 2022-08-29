package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Usuario;

public interface IPropostaService {

	Proposta buscarPorId(Long id);

	List<Proposta> buscarTodos();

	void salvar(Proposta proposta);

	void excluir(Long id);

	public List<Proposta> buscarTodosPorUsuario(Usuario u);
}
