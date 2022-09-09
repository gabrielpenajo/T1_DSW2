package br.ufscar.dc.dsw.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

import br.ufscar.dc.dsw.validation.UniqueCPF;

@SuppressWarnings("serial")
@Entity
@Table(name = "Cliente")
public class Cliente extends Usuario {

    @UniqueCPF(message = "{Unique.usuario.CPF}")
    @NotBlank(message = "{NotBlank.usuario.CPF}")
	@Size(min = 11, max = 14, message = "{Size.usuario.CPF}")
    @Column(nullable = false, length = 11, unique = true)
    private String CPF;
    
    @Size(max = 1)
	@Column(nullable = true, length = 1)
	private String sexo;

    @Column(nullable = true)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date nascimento;

    @Size(max = 11)
	@Column(nullable = true)
	private String telefone;

    @OneToMany(mappedBy = "cliente")
    @JsonManagedReference
	private List<Proposta> propostas;

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String cPF) {
        CPF = cPF;
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

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }

    @Override
    public String toString() {
        return "Cliente [CPF=" + CPF + ", nascimento=" + nascimento + ", propostas=" + propostas + ", sexo=" + sexo
                + ", telefone=" + telefone + "]";
    } 

}
