package br.ufscar.dc.dsw.service.spec;

import java.util.Date;
import java.util.List;

import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Pacote;

public interface IPacoteService {

	Pacote buscarPorId(Long id);

	
	void salvar(Pacote pacote);
	
	void excluir(Long id);	
	
	List<Pacote> buscarTodos();
	List<Pacote> buscarTodosValidos();
	List<Pacote> buscarPorAgenciaValidos(Agencia a);
	List<Pacote> buscarPorDestino(String destino);
	List<Pacote> buscarPorAgencia(Agencia a);
	List<Pacote> buscarPorCidade(String cidade);
	List<Pacote> buscarPorEstado(String estado);
	List<Pacote> buscarPorPais(String pais);
	List<Pacote> BuscarPorDataPartida(Date data);
	List<Pacote> buscarEAplicarFltros(String destino, Agencia a, Date dataPartida, String validoStr);
	List<String> buscarImagens(String paths);

}
