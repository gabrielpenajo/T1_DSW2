package br.ufscar.dc.dsw.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import br.ufscar.dc.dsw.validation.UniqueCPF;


@SuppressWarnings("serial")
@Entity
@Table(name = "Usuario")
public class Usuario extends AbstractEntity<Long> {

    @NotBlank(message = "{NotBlank.usuario.nome}")
	@Size(min = 3, max = 256)
    @Column(nullable = false, length = 256)
    private String nome;
	
	@NotBlank(message = "{NotBlank.usuario.email}")
	@Size(max = 256)
    @Column(nullable = false, length = 256, unique = true)
    private String email;
    
	@UniqueCPF(message = "{Unique.usuario.CPF}")
    @NotBlank(message = "{NotBlank.usuario.CPF}")
	@Size(min = 11, max = 11, message = "{Size.usuario.CPF}")
    @Column(nullable = false, length = 11, unique = true)
    private String CPF;
    
	@NotBlank(message = "{NotBlank.usuario.senha}")
	@Size(min = 3, max = 64)
    @Column(nullable = false, length = 64)
    private String senha;
    
	@Size(max = 1)
	@Column(nullable = true, length = 1)
	private String sexo;

	@Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date nascimento;

	@Size(max = 11)
	@Column(nullable = true)
	private String telefone;
    
    @NotBlank(message = "{NotBlank.usuario.papel}")
    @Column(nullable = false, length = 10)
    private String papel;

	@OneToMany(mappedBy = "usuario")
	private List<Proposta> propostas; 

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getNascimento() {
		return nascimento;
	}

	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getPapel() {
		return papel;
	}

	public void setPapel(String papel) {
		this.papel = papel;
	}

	public List<Proposta> getPropostas() {
		return propostas;
	}

	public void setPropostas(List<Proposta> propostas) {
		this.propostas = propostas;
	}
    		
}