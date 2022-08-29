package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.service.spec.IPacoteService;

@Component
public class PacoteConversor implements Converter<String, Pacote>{

	@Autowired
	private IPacoteService service;
	
	@Override
	public Pacote convert(String text) {
		
		if (text.isEmpty()) {
		 return null;	
		}
		
		Long id = Long.valueOf(text);	
		return service.buscarPorId(id);
	}
}
