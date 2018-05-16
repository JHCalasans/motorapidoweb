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
@Table(name = TipoVeiculo.nomeTabela, schema = TipoVeiculo.esquema, catalog = "diego")

public class TipoVeiculo extends Entidade{


	private static final long serialVersionUID = 3485944317260792318L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "tipo_veiculo";
	
	
	@Id
	@Column(name = "cod_tipo_veiculo", nullable = false)
	@SequenceGenerator(name = "tipo_veiculo_cod_tipo_veiculo_seq", sequenceName = "diego.tipo_veiculo_cod_tipo_veiculo_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_veiculo_cod_tipo_veiculo_seq")
	private Integer codigo;
	
	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;
	
	@Column(name = "flg_ativo", nullable = false)
	private String ativo;
	
	

	@Override
	public Serializable getIdentificador() {
		return getCodigo();
	}


	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getAtivo() {
		return ativo;
	}


	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

}
