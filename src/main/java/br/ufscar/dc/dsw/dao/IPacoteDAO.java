package br.ufscar.dc.dsw.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Pacote;

@SuppressWarnings("unchecked")
public interface IPacoteDAO extends CrudRepository<Pacote, Long>{

	Pacote save(Pacote livro);
	Pacote findById(long id);
	void deleteById(Long id);
	List<Pacote> findAll();
	List<Pacote> findByAgencia(Agencia a);
	List<Pacote> findByDestino(String destino);
	List<Pacote> findByDataPartida(Date data);
	// Fazer os filtros no service e validar pacotes ainda ativos no service tb.
	// Pacotes ativos sao aqueles que o voo nao partiu ainda.
}