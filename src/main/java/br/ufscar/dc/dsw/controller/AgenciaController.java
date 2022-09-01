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

import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.service.spec.IAgenciaService;

@Controller
@RequestMapping("/agencias")
public class AgenciaController {
	
	@Autowired
    private BCryptPasswordEncoder encoder;
	
	@Autowired
	private IAgenciaService service;
	
	@GetMapping("/cadastrar")
	public String cadastrar(Agencia agencia) {
		return "agencia/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("agencias",service.buscarTodos());
		return "agencia/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Agencia agencia, BindingResult result, RedirectAttributes attr) {
		
		if (!isCnpjValid(agencia.getCNPJ())) {
			result.rejectValue("CNPJ", "Unique.agencia.CNPJ");
		}

		if (result.hasErrors()) {
			attr.addFlashAttribute("fail", "agencia.create.fail");
			return "agencia/cadastro";
		}

		agencia.setSenha(encoder.encode(agencia.getSenha()));
        agencia.setPapel("AGENCIA");

		service.salvar(agencia);
		attr.addFlashAttribute("sucess", "agencia.create.sucess");
		return "redirect:/agencias/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("agencia", service.buscarPorId(id));
		return "agencia/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(@Valid Agencia agencia, BindingResult result, RedirectAttributes attr) {
		
		// Apenas rejeita se o problema não for com o CNPJ (CNPJ campo read-only) 
		
		if ((result.getFieldErrorCount() > 1 || result.getFieldError("CNPJ") != null) &&
			service.buscarPorId(agencia.getId()) == null) {

			result.getFieldError("CNPJ");
			return "agencia/cadastro";
		}

		agencia.setSenha(encoder.encode(agencia.getSenha()));
		service.salvar(agencia);
		attr.addFlashAttribute("sucess", "agencia.edit.sucess");
		return "redirect:/agencias/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, ModelMap model) {
		// if (service.agenciaTemPacotes(id)) {
		// 	model.addAttribute("fail", "agencia.delete.fail");
		// } else {
		service.excluir(id);
		model.addAttribute("sucess", "agencia.delete.sucess");
		// }
		return listar(model);
	}

	private Boolean isCnpjValid(String cnpj) {
		return service.buscarPorCNPJ(cnpj) == null;
	}
}
