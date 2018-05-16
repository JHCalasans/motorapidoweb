package br.com.motorapido.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Parametro.nomeTabela, schema = Parametro.esquema, catalog = "diego")
public class Parametro extends Entidade{


	private static final long serialVersionUID = 6383188788786597417L;

	public final static String esquema = "diego";
	public final static String nomeTabela = "Parametro";


	@Id
	@Column(name = "cod_parametro", nullable = false)
	private Long codParametro;

	@Column(name = "chave", nullable = false)
	private String chave;

	@Column(name = "descricao", nullable = false)
	private String descricao;


	public void setChave(String chave) {
		this.chave = chave;
	}


	public String getChave() {
		return chave;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setCodParametro(Long codParametro) {
		this.codParametro = codParametro;
	}


	public Long getCodParametro() {
		return codParametro;
	}

	@Override
	public Long getIdentificador() {
		return codParametro;
	}

	@Override
	public String toString() {
		return "Parametro [codParametro=" + codParametro + ", chave=" + chave + "]";
	}

}
