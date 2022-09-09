package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings("serial")
@Entity
@Table(name = "Pacote")
public class Pacote extends AbstractEntity<Long> {
    
    @NotNull(message = "{NotNull.pacote.agencia}")
	@ManyToOne
	@JoinColumn(name = "agencia_id")
    @JsonManagedReference
	private Agencia agencia;

    @NotBlank(message = "{NotBlank.pacote.cidade}")
    @Size(max = 256)
    @Column(nullable = false, length = 256)
    private String cidade;

    @NotBlank(message = "{NotBlank.pacote.estado}")
    @Size(max = 256)
    @Column(nullable = false, length = 256)
    private String estado;

    @NotBlank(message = "{NotBlank.pacote.pais}")
    @Size(max = 256)
    @Column(nullable = false, length = 256)
    private String pais;

    @NotNull(message = "{NotNull.pacote.dataPartida}")
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dataPartida;

    @NotNull(message = "{NotNull.pacote.duracaoDias}")
    @Column(nullable = false)
	private Long duracaoDias;

    @NotNull(message = "{NotNull.pacote.valor}")
    @Column(nullable = false, columnDefinition = "DECIMAL(8,2) DEFAULT 0.0")
	private BigDecimal valor;

    @Size(max = 256)
    @Column(nullable = true, length = 256)
    private String descricao;

    @OneToMany(mappedBy = "pacote")
    @JsonBackReference
    private List<Proposta> propostas;

    @Column(nullable = true)
    private String pictures;

    public void initPictures() {
        this.pictures = "";
    }

    public void setPictures(String _pictures) {
        if (this.pictures == null || this.pictures.length() == 0) {
            this.pictures = _pictures + "|";
        } else {
            this.pictures += _pictures + "|";
        }
    }

    public String getPictures() {
        return pictures;
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

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }

    public String getDestino() {
        return this.getCidade() + " - " + this.getEstado() + ", " + this.getPais();
    }
}
