package br.com.motorapido.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;


@Entity
@Table(name = SituacaoChamada.nomeTabela, schema = SituacaoChamada.esquema, catalog = "diego")
public class SituacaoChamada extends Entidade{


	private static final long serialVersionUID = 4682498078111159603L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "situacao_chamada";
	
	
	@Id
	@Column(name = "cod_situacao_chamada", nullable = false)
	@SequenceGenerator(name = "situacao_chamada_cod_situacao_chamada_seq", sequenceName = "diego.situacao_chamada_cod_situacao_chamada_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "situacao_chamada_cod_situacao_chamada_seq")
	private Integer codigo;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;	
	

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

}
