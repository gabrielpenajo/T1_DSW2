package br.ufscar.dc.dsw.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.core.io.FileSystemResource;

import br.ufscar.dc.dsw.Utils.FileUploadUtil;
import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.service.spec.IAgenciaService;
import br.ufscar.dc.dsw.service.spec.IPacoteService;

@Controller
@RequestMapping("/pacotes")
public class PacoteController {

	@Autowired
	private IPacoteService pacoteService;

	@Autowired
	private IAgenciaService agenciaService;

	@GetMapping("/cadastrar")
	public String cadastrar(Pacote pacote) {
		return "pacote/cadastro";
	}

	@GetMapping("/listar")
	public String listar(
		ModelMap model,
		@RequestParam(name = "validos", required = false) String validos,
		@RequestParam(name = "destino", required = false) String destino,
		@RequestParam(name = "agencia", required = false) Long agenciaId,
		@RequestParam(name = "data", required = false) String dataStr
	) throws ParseException {
		if (validos != null) {
			validos = "on";
		} else {
			validos = "off";
		}

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Agencia agencia = agenciaService.buscarPorEmail(authentication.getName());

		if (agenciaId != null && agencia == null) {
			agencia = agenciaService.buscarPorId(agenciaId);
		}

		Date data = null;
		if (dataStr != null && dataStr.length() > 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			data = format.parse(dataStr);

			dataStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(data);
		} else {
			dataStr = null;
		}

		model.addAttribute("pacotes", pacoteService.buscarEAplicarFltros(destino, agencia, dataStr, validos));
		return "pacote/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Pacote pacote, BindingResult result, RedirectAttributes attr, @RequestParam("image") MultipartFile file, ModelMap model) throws IOException {

		if (result.hasErrors()) {
			return "pacote/cadastro";
		}
		pacote.initPictures();
		pacoteService.salvar(pacote);
		attr.addFlashAttribute("sucess", "pacote.create.sucess");
		return savePacoteFoto(pacote, file, attr, model);
	}

	public String savePacoteFoto(Pacote pacote, @RequestParam("image") MultipartFile multipartFile, RedirectAttributes attr, ModelMap model) throws IOException {

		if (pacoteService.buscarImagens(pacote.getPictures()).size() > 10) {
			model.addAttribute("errorFoto", "proposta.fotos.failed");
			return "pacote/cadastro";
		}

		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		if (fileName != null && fileName.length() > 0) {
			pacote.setPictures(pacoteService.buscarPorId(pacote.getId()).getPictures() + fileName);
			
			if (pacoteService.buscarPorId(pacote.getId()).getPictures().equals(fileName + "|"))
				pacoteService.salvar(pacote);
			String uploadDir = "/image/pacoteFotos/" + pacote.getId();

			FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
		}
		return "redirect:/pacotes/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("pacote", pacoteService.buscarPorId(id));
		return "pacote/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Pacote pacote, BindingResult result, RedirectAttributes attr,@RequestParam("image") MultipartFile file, ModelMap model) throws IOException {

		if (result.hasErrors()) {
			return "pacote/cadastro";
		}

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		pacote.setPictures(pacoteService.buscarPorId(pacote.getId()).getPictures() + fileName);
		pacoteService.salvar(pacote);
		attr.addFlashAttribute("sucess", "pacote.edit.sucess");
		return savePacoteFoto(pacote, file, attr, model);
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		pacoteService.excluir(id);
		attr.addFlashAttribute("sucess", "pacote.delete.sucess");
		return "redirect:/pacotes/listar";
	}

	@ModelAttribute("agencias")
	public List<Agencia> getAgencia() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<Agencia> agenciaLogadaList = new ArrayList<>();
		Agencia agenciaLogada = agenciaService.buscarPorEmail(authentication.getName());

		if(agenciaLogada != null) {
			agenciaLogadaList.add(agenciaLogada);
			return agenciaLogadaList;
		} else return agenciaService.buscarTodos();
	}

	@ModelAttribute("imagens")
	public Map<Long, List<String>> listaImagens() {
		Map<Long, List<String>> imagens = new HashMap<>();

		List<Pacote> pacotes = pacoteService.buscarTodos();

		if (!pacotes.isEmpty())
			for (Pacote pacote : pacotes) {
				List<String> paths = new ArrayList<>();

				List<String> pathsImagens = pacoteService.buscarImagens(pacote.getPictures());
				if (!pathsImagens.isEmpty()) {
					for (String path : pathsImagens) {
						paths.add("/image" + File.separator + "pacoteFotos"+ File.separator + pacote.getId() + File.separator + path);
					}
				}
				imagens.put(pacote.getId(), paths);
			}

		return imagens;
	}
}
