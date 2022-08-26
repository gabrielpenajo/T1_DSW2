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
public class Agencia extends AbstractEntity<Long> {
    
    @UniqueCNPJ (message = "{Unique.agencia.CNPJ}")
	@NotBlank
	@Size(min = 14, max = 14, message = "{Size.agencia.CNPJ}")
	@Column(nullable = false, unique = true, length = 60)
	private String CNPJ;

    @NotBlank
    @Size(max = 256)
    @Column(nullable = false, length = 256)
    private String nome;

    @NotBlank
    @Size(max = 256)
    @Column(nullable = false, length = 256, unique = true)
    private String email;

    @NotBlank
    @Size(min = 3, max = 64)
    @Column(nullable = false, length = 64)
    private String senha;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
