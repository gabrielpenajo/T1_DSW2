package br.ufscar.dc.dsw.service.spec;

import java.util.Date;
import java.util.List;

import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Pacote;

public interface IPacoteService {

	Pacote buscarPorId(Long id);

	List<Pacote> buscarTodos();

	void salvar(Pacote pacote);

	void excluir(Long id);	

	List<Pacote> buscarPorAgencia(Agencia a);
	List<Pacote> buscarPorCidade(String cidade);
	List<Pacote> buscarPorEstado(String estado);
	List<Pacote> buscarPorPais(String pais);
	List<Pacote> BuscarPorDataPartida(Date data);

}
