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
@Table(name = Bairro.nomeTabela, schema = Bairro.esquema, catalog = "diego")
public class Bairro  extends Entidade{


	private static final long serialVersionUID = 2422010721322522023L;
	public final static String esquema = "diego";
	public final static String nomeTabela = "bairro";
	

	@Id
	@Column(name = "cod_bairro", nullable = false)
	@SequenceGenerator(name = "diego_cod_bairro_seq", sequenceName = "diego.diego_cod_bairro_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "diego_cod_bairro_seq")
	private Integer codigo;
	
	@Column(name = "ds_estado", nullable = false)
	private String estado;
	

	@Column(name = "ds_bairro", nullable = false)
	private String bairro;	
	
	
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


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	@Override
	public Serializable getIdentificador() {
		// TODO Auto-generated method stub
		return null;
	}
}
