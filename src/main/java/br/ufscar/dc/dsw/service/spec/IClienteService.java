package br.ufscar.dc.dsw.service.spec;

import java.util.List;


import br.ufscar.dc.dsw.domain.Cliente;


public interface IClienteService {
    
    void salvar(Cliente cliente);
	void excluir(Long id);
    Cliente buscarPorId(Long id);
    Cliente buscarPorCPF(String CPF);
    Cliente buscarPorEmail(String email);
    List<Cliente> buscarTodos();


}
