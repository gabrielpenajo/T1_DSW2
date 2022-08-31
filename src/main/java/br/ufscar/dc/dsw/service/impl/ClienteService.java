package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;

public class ClienteService implements IClienteService {

    @Autowired
    IClienteDAO dao;

    @Override
    public void salvar(Cliente cliente) {
        dao.save(cliente);
    }

    @Override
    public void excluir(Long id) {
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
