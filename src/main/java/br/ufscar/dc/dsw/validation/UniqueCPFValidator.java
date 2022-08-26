package br.ufscar.dc.dsw.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Usuario;

@Component
public class UniqueCPFValidator implements ConstraintValidator<UniqueCPF, String> {

	@Autowired
	private IUsuarioDAO dao;

	@Override
	public boolean isValid(String CPF, ConstraintValidatorContext context) {
		if (dao != null) {
			Usuario usuario = dao.findbyCPF(CPF);
			return usuario == null;
		} else {
			// Durante a execução da classe LivrariaMvcApplication
			// não há injeção de dependência
			return true;
		}

	}
}