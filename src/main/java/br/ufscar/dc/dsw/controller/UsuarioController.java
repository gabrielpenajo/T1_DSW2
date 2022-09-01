package br.ufscar.dc.dsw.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.service.spec.IClienteService;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private IClienteService service;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Cliente cliente) {
		return "usuario/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("usuarios", service.buscarTodos());
		return "usuario/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "usuario/cadastro";
		}
		
		cliente.setSenha(encoder.encode(cliente.getSenha()));
		service.salvar(cliente);
		attr.addFlashAttribute("sucess", "usuario.create.sucess");
		return "redirect:/usuarios/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cliente", service.buscarPorId(id));
		return "usuario/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Cliente cliente, BindingResult result, RedirectAttributes attr) {
		
		if (result.hasErrors()) {
			return "usuario/cadastro";
		}

		cliente.setSenha(encoder.encode(cliente.getSenha()));
		service.salvar(cliente);
		attr.addFlashAttribute("sucess", "usuario.edit.sucess");
		return "redirect:/usuarios/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		service.excluir(id);
		model.addAttribute("sucess", "usuario.delete.sucess");
		return listar(model);
	}
}
