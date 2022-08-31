package br.ufscar.dc.dsw;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;  
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
import br.ufscar.dc.dsw.dao.IUsuarioDAO;
import br.ufscar.dc.dsw.domain.Agencia;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Pacote;
import br.ufscar.dc.dsw.domain.Usuario;

@SpringBootApplication
public class TurismoMvcApplication {
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(TurismoMvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(IUsuarioDAO usuarioDAO, IClienteDAO clienteDAO, IAgenciaDAO agenciaDAO, IPacoteDAO pacoteDAO) {
		return (args) -> {
			
			Cliente c1 = new Cliente();
			c1.setEmail("admin@email.com");
			c1.setSenha(encoder.encode("admin"));
			c1.setCPF("01234567890");
			c1.setNome("Administrador");
			c1.setPapel("ADMIN");
			clienteDAO.save(c1);
			
			Cliente c2 = new Cliente();
			c2.setEmail("beltrano@email.com");
			c2.setSenha(encoder.encode("123"));
			c2.setCPF("98584961410");
			c2.setNome("Beltrano Andrade");
			c2.setPapel("USER");
			clienteDAO.save(c2);

			Agencia a1 = new Agencia();
			a1.setEmail("fulano@email.com");
			a1.setSenha(encoder.encode("123"));
			a1.setCNPJ("01234567890123");
			a1.setNome("Fulano Silva");
			agenciaDAO.save(a1);


			Pacote p1 = new Pacote();
			p1.setAgencia(a1);
			p1.setCidade("Cidade1");
			p1.setEstado("estado");
			p1.setPais("pais");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date dataPartida = formatter.parse("20/11/2015");
			p1.setDataPartida(dataPartida);
			p1.setDuracaoDias((long) 2);
			p1.setValor(BigDecimal.valueOf(54.9));
			pacoteDAO.save(p1);

			for (Usuario u : usuarioDAO.findAll())
				System.out.println(u.getEmail().toString() + " " + u.getSenha());
		};
	}
}
