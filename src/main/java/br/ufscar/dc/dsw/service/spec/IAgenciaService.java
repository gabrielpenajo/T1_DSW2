package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Agencia;

public interface IAgenciaService {

	Agencia buscarPorId(Long id);

	Agencia buscarPorCNPJ(String CNPJ);

	Agencia buscarPorEmail(String email);

	List<Agencia> buscarTodos();

	void salvar(Agencia agencia);

	void excluir(Long id);	

	public boolean agenciaTemPacotes(Long id);
}
