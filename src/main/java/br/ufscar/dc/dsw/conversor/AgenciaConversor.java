package br.ufscar.dc.dsw.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.service.spec.IAgenciaService;

@Component
public class AgenciaConversor implements Converter<String, Agencia>{

	@Autowired
	private IAgenciaService service;
	
	@Override
	public Agencia convert(String text) {
		
		if (text.isEmpty()) {
		 return null;	
		}
		
		Long id = Long.valueOf(text);	
		return service.buscarPorId(id);
	}
}
