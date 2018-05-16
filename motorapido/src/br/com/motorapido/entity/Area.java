package br.com.motorapido.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Area.nomeTabela, schema = Area.esquema, catalog = "diego")
public class Area extends Entidade{

	
	private static final long serialVersionUID = -8355119951592881743L;

	
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "area";
	
	@Id
	@Column(name = "cod_area", nullable = false)
	@SequenceGenerator(name = "area_cod_area_seq", sequenceName = "diego.area_cod_area_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "area_cod_area_seq")
	private Integer codigo;
	
	@Column(name = "descricao", nullable = false)
	private String descricao;
	
	
	@Column(name = "cor", nullable = false)
	private String cor;
	
	@Transient
	private String corPura;
	
	@Override
	public Serializable getIdentificador() {
		
		return this.codigo;
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


	public String getCor() {
		return cor;
	}


	public void setCor(String cor) {
		this.cor = cor;
	}


	public String getCorPura() {
		if(corPura == null || corPura.isEmpty())
			return cor.replace("#", "");
		else
			return corPura;
	}


	public void setCorPura(String corPura) {
		this.corPura = corPura;
	}
	


}
