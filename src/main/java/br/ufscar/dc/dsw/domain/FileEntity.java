package br.ufscar.dc.dsw.domain;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity
@Table(name = "FileDB")
public class FileEntity extends AbstractEntity<Long> {

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 30)
	private String type;

    @NotNull
    @JsonIgnore
    @ManyToOne
    private Pacote pacote;

    @Lob
    @JsonIgnore
    private byte[] data;

    public boolean isImage() {
		return this.type.contains("image");
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Pacote getPacote() {
        return pacote;
    }

    public void setPacote(Pacote pacote) {
        this.pacote = pacote;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FileEntity [data=" + Arrays.toString(data) + ", name=" + name + ", pacote=" + pacote + ", type=" + type
                + "]";
    }
    
}
