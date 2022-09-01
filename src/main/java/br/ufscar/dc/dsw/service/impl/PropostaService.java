package br.ufscar.dc.dsw.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IPropostaService;

@Service
@Transactional(readOnly = false)
public class PropostaService implements IPropostaService {

	@Autowired
	IPropostaDAO dao;

	public void salvar(Proposta proposta) {
		dao.save(proposta);
	}

	public void excluir(Long id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Proposta buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Proposta> buscarTodos() {
		return dao.findAll();
	}

	@Transactional(readOnly = true)
	public List<Proposta> buscarTodosPorCliente_Id(Long id) {
		
		return dao.findAllByCliente_Id(id);
	}
	
	@Override
	public List<Proposta> buscarTodosPorCliente_CPF(String CPF) {
		return dao.findAllByCliente_CPF(CPF);
	}

	@Override
	public List<Proposta> buscarTodosPorPacote_Id(Long id) {
		return dao.findAllByPacote_Id(id);
	}

	@Override
	public List<Proposta> buscarTodosAtivosPorCliente_Id(Long id) {
		List<Proposta> propostasAtivas = this.buscarTodosPorCliente_Id(id);

		propostasAtivas = propostasAtivas.stream().filter(x -> x.getStatusProposta() == 1).collect(Collectors.toList());

		return propostasAtivas;
	}

	@Override
	public List<Proposta> buscarTodosCanceladosPorCliente_Id(Long id) {
		List<Proposta> propostasCanceladas = this.buscarTodosPorCliente_Id(id);

		propostasCanceladas = propostasCanceladas.stream().filter(x -> x.getStatusProposta() == 0).collect(Collectors.toList());

		return propostasCanceladas;

	}


}
