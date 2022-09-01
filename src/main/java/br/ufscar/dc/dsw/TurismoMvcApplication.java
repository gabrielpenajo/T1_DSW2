package br.ufscar.dc.dsw;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.dao.IAgenciaDAO;
import br.ufscar.dc.dsw.dao.IClienteDAO;
import br.ufscar.dc.dsw.dao.IPacoteDAO;
import br.ufscar.dc.dsw.dao.IPropostaDAO;
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Usuario;

@SpringBootApplication
public class TurismoMvcApplication {
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(TurismoMvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, IClienteDAO clienteDAO, IAgenciaDAO agenciaDAO, IPacoteDAO pacoteDAO, IPropostaDAO propostaDAO) {
		return (args) -> {
			
			Cliente c1 = new Cliente();
			c1.setEmail("admin@email.com");
			c1.setSenha(encoder.encode("admin"));
			c1.setCPF("01234567890");
			c1.setNome("Administrador");
			c1.setPapel("ADMIN");
			clienteDAO.save(c1);
			
			Cliente c2 = new Cliente();
			c2.setEmail("cliente@email.com");
			c2.setSenha(encoder.encode("123"));
			c2.setCPF("98584961410");
			c2.setNome("Beltrano Andrade");
			c2.setPapel("CLIENTE");
			clienteDAO.save(c2);

			Agencia a1 = new Agencia();
			a1.setEmail("agencia@email.com");
			a1.setSenha(encoder.encode("123"));
			a1.setCNPJ("01.234.567/8901-23");
			a1.setPapel("AGENCIA");
			a1.setNome("Fulano Silva");
			agenciaDAO.save(a1);


			Pacote p1 = new Pacote();
			p1.setAgencia(a1);
			p1.setCidade("São Carlos");
			p1.setEstado("São Paulo");
			p1.setPais("São Paulo");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dataPartida = formatter.parse("20/11/2015");
			p1.setDataPartida(dataPartida);
			p1.setDuracaoDias((long) 2);
			p1.setValor(BigDecimal.valueOf(54.9));
			pacoteDAO.save(p1);

			Proposta prop1 = new Proposta();
			prop1.setId(1L);
			prop1.setCliente(c2);
			prop1.setPacote(p1);
			prop1.setDataProposta(Date.from(Instant.now()));
			prop1.setValor(p1.getValor());
			prop1.setStatusProposta(1);
			propostaDAO.save(prop1);

			for (Usuario u : usuarioDAO.findAll())
				System.out.println(u.getEmail().toString() + " " + u.getSenha());
		};
	}
}
