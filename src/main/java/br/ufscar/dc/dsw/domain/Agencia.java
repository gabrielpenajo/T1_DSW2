package br.ufscar.dc.dsw.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


import br.ufscar.dc.dsw.validation.UniqueCNPJ;

@SuppressWarnings("serial")
@Entity
@Table(name = "Agencia")
public class Agencia extends Usuario{
    
    @UniqueCNPJ (message = "{Unique.agencia.CNPJ}")
	@NotBlank(message = "{NotBlank.agencia.CNPJ}")
	@Size(min = 14, max = 14, message = "{Size.agencia.CNPJ}")
	@Column(nullable = false, unique = true, length = 60)
	private String CNPJ;

    @Size(max = 256)
    @Column(nullable = true, length = 256)
    private String descricao;

    @OneToMany(mappedBy = "agencia")
	private List<Pacote> pacotes;

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String cNPJ) {
        CNPJ = cNPJ;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Pacote> getPacotes() {
        return pacotes;
    }

    public void setPacotes(List<Pacote> pacotes) {
        this.pacotes = pacotes;
    }

    @Override
    public String toString() {
        return "Agencia [CNPJ=" + CNPJ + ", descricao=" + descricao + ", pacotes=" + pacotes + "]";
    }

}
