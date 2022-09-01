package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IAgenciaDAO;
import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.service.spec.IAgenciaService;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IUsuarioService;

@Service
@Transactional(readOnly = false)
public class UsuarioService implements IUsuarioService {

	@Autowired
	IUsuarioDAO dao;

	@Autowired
	IAgenciaDAO agenciaDAO;

	@Autowired
	IClienteDAO clienteDAO;

	@Autowired
	IAgenciaService serviceAgencia;

	@Autowired
	IClienteService serviceCliente;

	public void salvar(Usuario usuario) {
		String role = usuario.getPapel();
		if (role.equals("CLIENTE") || role.equals("ADMIN")) {
			Long id = usuario.getId();
			Cliente cliente = clienteDAO.findById(id.longValue());
			serviceCliente.salvar(cliente);
		}
		if (role.equals("AGENCIA")) {
			Long id = usuario.getId();
			Agencia agencia = agenciaDAO.findById(id.longValue());
			serviceAgencia.salvar(agencia);
		}
		
		dao.save(usuario);
	}

	public void excluir(Long id) {
		Usuario usuario = dao.findById(id.longValue());
		String role = usuario.getPapel();
		if (role.equals("CLIENTE") || role.equals("ADMIN"))
			serviceCliente.excluir(id);
		if (role.equals("AGENCIA"))
			serviceAgencia.excluir(id);
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return dao.findById(id.longValue());
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public Usuario buscarPorEmail(String email) {
		return dao.findByEmail(email);
	}
}
