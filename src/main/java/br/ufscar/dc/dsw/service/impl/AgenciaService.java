package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IAgenciaDAO;
import br.ufscar.dc.dsw.dao.IPacoteDAO;
import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IAgenciaService;

@Service
@Transactional(readOnly = false)
public class AgenciaService implements IAgenciaService {

	@Autowired
	IAgenciaDAO dao;

	@Autowired
	IPacoteDAO daoPacote;

	@Autowired
	IPropostaDAO daoProposta;

	public void salvar(Agencia agencia) {
		dao.save(agencia);
	}

	public void excluir(Long id) {
		Agencia agencia = this.buscarPorId(id);
		List<Pacote> pacotes = agencia.getPacotes();
		for(Pacote pacote: pacotes) {
			List<Proposta> propostas = pacote.getPropostas();
			for(Proposta proposta: propostas)
				daoProposta.deleteById(proposta.getId());
			daoPacote.deleteById(pacote.getId());	
		}

		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Agencia buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Agencia> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public boolean agenciaTemPacotes(Long id) {
		return !dao.findById(id.longValue()).getPacotes().isEmpty(); 
	}

	@Override
	public Agencia buscarPorCNPJ(String CNPJ) {
		return dao.findByCNPJ(CNPJ);
	}

	@Override
	public Agencia buscarPorEmail(String email) {
		return dao.findByEmail(email);
	}
	
}
