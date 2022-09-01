package br.ufscar.dc.dsw.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.service.spec.IClienteService;

@Service
@Transactional(readOnly = false)
public class ClienteService implements IClienteService {

    @Autowired
    IClienteDAO dao;

    @Autowired
	IPropostaDAO daoProposta;

    @Override
    public void salvar(Cliente cliente) {

        List<Proposta> propostas = cliente.getPropostas();

        if (propostas != null)
            for(Proposta proposta: propostas) {
                proposta.setCliente(cliente);
                daoProposta.save(proposta);
            }

        dao.save(cliente);
    }

    @Override
    public void excluir(Long id) {
        List<Proposta> propostasCliente = daoProposta.findAllByCliente_Id(id);
        if (propostasCliente != null ) {
            for (Proposta proposta : propostasCliente) {
                daoProposta.deleteById(proposta.getId());
            }
        }

        dao.deleteById(id);
    }

    @Override
    public Cliente buscarPorId(Long id) {
        return dao.findById(id.longValue());
    }

    @Override
    public Cliente buscarPorCPF(String CPF) {
        return dao.findByCPF(CPF);
    }

    @Override
    public Cliente buscarPorEmail(String email) {
        return dao.findByEmail(email);
    }

    @Override
    public List<Cliente> buscarTodos() {
        return dao.findAll();
    }
    
}
