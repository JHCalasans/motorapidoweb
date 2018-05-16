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
@Table(name = Perfil.nomeTabela, schema = Perfil.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "Perfil.obterPerfis", query = "select p from Perfil p where (:desc is null or p.descricao like '%' || :desc || '%' ) and ( :ativo is null or p.ativo = :ativo) and"
				+ " ( :acesso is null or p.acessaSistema = :acesso )")
		})
public class Perfil extends Entidade{


	private static final long serialVersionUID = 2123994573311705281L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "perfil";
	
	@Id
	@Column(name = "cod_perfil", nullable = false)
	@SequenceGenerator(name = "perfil_cod_perfil_seq", sequenceName = "diego.perfil_cod_perfil_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_cod_perfil_seq")
	private Integer codigo;
	
	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;
	
	@Column(name = "flg_ativo", nullable = false)
	private String ativo;
	
	@Column(name = "flg_sistema", nullable = false)
	private String acessaSistema;

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

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getAcessaSistema() {
		return acessaSistema;
	}

	public void setAcessaSistema(String acessaSistema) {
		this.acessaSistema = acessaSistema;
	}

}
