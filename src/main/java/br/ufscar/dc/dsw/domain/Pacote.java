package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "Pacote")
public class Pacote {
    
    @Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

    @NotNull(message = "{NotNull.pacote.agencia}")
	@ManyToOne
	@JoinColumn(name = "agencia_id")
	private Agencia agencia;

    @NotBlank
    @Size(max = 256)
    @Column(nullable = false, length = 256)
    private String cidade;

    @NotBlank
    @Size(max = 256)
    @Column(nullable = false, length = 256)
    private String estado;

    @NotBlank
    @Size(max = 256)
    @Column(nullable = false, length = 256)
    private String pais;

    @NotBlank
    @Column(nullable = false)
	private Date dataPartida;

    @NotBlank
    @Column(nullable = false)
	private Long duracaoDias;

    @NotBlank
    @Column(nullable = false)
	private BigDecimal valor;

    @Size(max = 256)
    @Column(nullable = true, length = 256)
    private String descricao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Agencia getAgencia() {
        return agencia;
    }

    public void setAgencia(Agencia agencia) {
        this.agencia = agencia;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Date getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(Date dataPartida) {
        this.dataPartida = dataPartida;
    }

    public Long getDuracaoDias() {
        return duracaoDias;
    }

    public void setDuracaoDias(Long duracaoDias) {
        this.duracaoDias = duracaoDias;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
}
