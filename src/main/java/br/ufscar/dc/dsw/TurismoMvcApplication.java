package br.ufscar.dc.dsw;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;  
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.IAgenciaDAO;
import br.ufscar.dc.dsw.dao.IPacoteDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.domain.Usuario;

@SpringBootApplication
public class TurismoMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurismoMvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, IAgenciaDAO agenciaDAO, IPacoteDAO pacoteDAO) {
		return (args) -> {
			
			Usuario u1 = new Usuario();
			u1.setEmail("admin@admin.com");
			u1.setSenha("admin");
			u1.setCPF("01234567890");
			u1.setNome("Administrador");
			u1.setPapel("admin");
			usuarioDAO.save(u1);
			
			Usuario u2 = new Usuario();
			u2.setEmail("beltrano@email.com");
			u2.setSenha("123");
			u2.setCPF("98584961410");
			u2.setNome("Beltrano Andrade");
			u2.setPapel("user");
			usuarioDAO.save(u2);
			
			Agencia a1 = new Agencia();
			a1.setEmail("fulano@email.com");
			a1.setSenha("123");
			a1.setCNPJ("01234567890123");
			a1.setNome("Fulano Silva");
			agenciaDAO.save(a1);
			
			
			Pacote p1 = new Pacote();
			p1.setAgencia(a1);
			p1.setCidade("Cidade1");
			p1.setEstado("estado");
			p1.setPais("pais");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dataPartida = formatter.parse("20/11/2015")
			p1.setDataPartida(dataPartida);
			p1.setDuracaoDias((long) 2);
			p1.setValor(BigDecimal.valueOf(54.9));
			pacoteDAO.save(p1);
		};
	}
}
