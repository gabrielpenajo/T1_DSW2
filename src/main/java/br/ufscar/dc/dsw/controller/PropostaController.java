package br.ufscar.dc.dsw.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.spec.IPropostaService;
import br.ufscar.dc.dsw.service.spec.IClienteService;
import br.ufscar.dc.dsw.service.spec.IPacoteService;

@Controller
@RequestMapping("/propostas")
public class PropostaController {
	
	@Autowired
	private IPropostaService service;
	
	@Autowired
	private IClienteService clientService;

	@Autowired
	private IPacoteService pacoteService;
	

	@GetMapping("/cadastrar")
	public String cadastrar(ModelMap model, Proposta proposta) {
		
		model.addAttribute("pacotes", service.buscarTodosPorCliente_Id(this.getUsuario().getId()));

		return "proposta/cadastro";
	}
	
	private Usuario getUsuario() {
		UsuarioDetails usuarioDetails = (UsuarioDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return usuarioDetails.getUsuario();
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
					
		model.addAttribute("propostas",service.buscarTodosPorCliente_Id(this.getUsuario().getId()));
		
		return "proposta/lista";
	}
	
	@PostMapping("/salvar")
	public String salvar(@Valid Proposta proposta, BindingResult result, RedirectAttributes attr) throws ParseException {
		
		proposta.setDataProposta(Date.from(Instant.now()));
		proposta.setValor(proposta.getPacote().getValor());


		if (result.hasErrors() && (
			result.getFieldError("cliente") == null &&
			result.getFieldError("dataProposta") == null &&
			result.getFieldError("value") == null
		    )) {

			return "proposta/cadastro";
		}
		
		String dataString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
		Cliente cliente = clientService.buscarPorId(this.getUsuario().getId());   
		proposta.setCliente(cliente);

		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date data = formato.parse(dataString);

		proposta.setDataProposta(data);
		service.salvar(proposta);
		attr.addFlashAttribute("sucess", "proposta.create.sucess");
		return "redirect:/propostas/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		pacoteService.excluir(id);
		attr.addFlashAttribute("sucess", "proposta.delete.sucess");
		return "redirect:/propostas/listar";
	}

	@ModelAttribute("pacotes")
	public List<Pacote> listaPacotes() {
		return pacoteService.buscarTodos();
	}
}
