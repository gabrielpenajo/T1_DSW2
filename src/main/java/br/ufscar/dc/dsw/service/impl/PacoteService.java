package br.ufscar.dc.dsw.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IPacoteDAO;
import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IPacoteService;

@Service
@Transactional(readOnly = false)
public class PacoteService implements IPacoteService {

    @Autowired
    IPacoteDAO dao;

    @Autowired
    IPropostaDAO daoProposta;


    public void salvar(Pacote pacote) {

        List<Proposta> propostas = pacote.getPropostas();
        if (propostas != null) {
            for (Proposta proposta : propostas) {
                proposta.setPacote(pacote);
                daoProposta.save(proposta);
            }
        }

        dao.save(pacote);
    }

    public void excluir(Long id) {
        Pacote pacote = this.buscarPorId(id);
        List<Proposta> propostas = pacote.getPropostas();
        if(propostas != null) {
            for (Proposta proposta : propostas)
                daoProposta.deleteById(proposta.getId());
        }
        dao.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Pacote buscarPorId(Long id) {
        return dao.findById(id.longValue());
    }

    @Transactional(readOnly = true)
    public List<Pacote> buscarTodos() {
        return dao.findAll();
    }

    @Override
    public List<Pacote> buscarPorAgencia(Agencia a) {
        return dao.findByAgencia(a);
    }

    @Override
    public List<Pacote> buscarPorCidade(String cidade) {
        return dao.findByCidade(cidade);
    }

    @Override
    public List<Pacote> buscarPorEstado(String estado) {
        return dao.findByEstado(estado);
    }

    @Override
    public List<Pacote> buscarPorPais(String pais) {
        return dao.findByPais(pais);
    }

    @Override
    public List<Pacote> BuscarPorDataPartida(Date data) {
        return dao.findByDataPartida(data);
    }

    @Override
    public List<Pacote> buscarTodosValidos() {
        List<Pacote> pacotes = this.buscarTodos();
        Date dataHoje = Date.from(Instant.now());
        pacotes = pacotes.stream().filter(x -> x.getDataPartida().after(dataHoje)).collect(Collectors.toList());

        return pacotes;
    }

    @Override
    public List<Pacote> buscarPorAgenciaValidos(Agencia a) {

        List<Pacote> pacotesAgencia = this.buscarPorAgencia(a);
        Date dataHoje = Date.from(Instant.now());
        pacotesAgencia = pacotesAgencia.stream().filter(x -> x.getDataPartida().after(dataHoje)).collect(Collectors.toList());

        return pacotesAgencia;
    }

    @Override
    public List<Pacote> buscarPorDestino(String destino) {

        List<Pacote> pacotes = this.buscarTodos();

        pacotes = pacotes.stream().filter(x -> x.getCidade() == destino).filter(x -> x.getEstado() == destino).filter(x -> x.getPais() == destino).collect(Collectors.toList());

        return pacotes;
    }

    @Override
    public List<Pacote> buscarEAplicarFltros(String destino, Agencia a, Date dataPartida, String validoStr) {
        // todos os filtros juntos
        List<Pacote> pacotes = this.buscarTodos();

        Boolean valido = validoStr.equals("on") ? true : false;

        Date hoje = Date.from(Instant.now());
        if (dataPartida != null)
            pacotes = pacotes.stream().filter(x -> x.getDataPartida().toString().equals(dataPartida)).collect(Collectors.toList());
        if (a != null)
            pacotes = pacotes.stream().filter(x -> x.getAgencia().equals(a)).collect(Collectors.toList());
        if (destino != "")
            pacotes = pacotes.stream().filter(x -> x.getCidade().equals(destino) || x.getEstado().equals(destino) || x.getPais().equals(destino)).collect(Collectors.toList());
        if (valido == true)
            pacotes = pacotes.stream().filter(x -> x.getDataPartida().after(hoje)).collect(Collectors.toList());

        return pacotes;
    }

	@Override
	public List<String> buscarImagens(String paths) {
		
        String[] imagens;
        List<String> listImagens = new ArrayList<>();
        if (paths != null && paths.split("\\|").length > 0) {
            imagens = paths.split("\\|");

            listImagens = Arrays.asList(imagens);
        }

		return listImagens;
	}


}
