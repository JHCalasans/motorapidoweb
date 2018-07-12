package br.com.motorapido.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Caracteristica.nomeTabela, schema = Caracteristica.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "Caracteristica.obterCaracteristicas", query = "select c from Caracteristica c where (:desc is null or c.descricao like '%' || :desc || '%' ) and ( :ativo is null or c.ativo = :ativo) ")
		})
public class Caracteristica extends Entidade{


	private static final long serialVersionUID = 3130450263461080912L;
	
	public final static String esquema = "diego";
	
	public final static String nomeTabela = "caracteristica";
	
	

	@Id
	@Column(name = "cod_caracteristica", nullable = false)
	@SequenceGenerator(name = "caracteristica_cod_caracteristica_seq", sequenceName = "diego.caracteristica_cod_caracteristica_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caracteristica_cod_caracteristica_seq")
	private Integer codigo;
	
	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;

	@Column(name = "flg_ativo", nullable = false, length = 1)
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
