package br.com.motorapido.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Cidade.nomeTabela, schema = Cidade.esquema, catalog = "diego")
public class Cidade extends Entidade{
	

	private static final long serialVersionUID = 8317113934629517945L;
	public final static String esquema = "diego";
	public final static String nomeTabela = "cidade";
	
	

	@Id
	@Column(name = "cod_cidade", nullable = false)
	@SequenceGenerator(name = "cidade_cod_cidade_seq", sequenceName = "diego.cidade_cod_cidade_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cidade_cod_cidade_seq")
	private Integer codigo;
	
	@Column(name = "ds_estado", nullable = false)
	private String estado;
	

	@Column(name = "ds_cidade", nullable = false)
	private String cidade;	
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Override
	public Serializable getIdentificador() {
		// TODO Auto-generated method stub
		return null;
	}

}
