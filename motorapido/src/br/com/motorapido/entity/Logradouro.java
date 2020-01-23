package br.com.motorapido.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Logradouro.nomeTabela, schema = Logradouro.esquema, catalog = "diego")
@NamedQueries(value = {
		@NamedQuery(name = "Logradouro.obterPorEstado", query = "select lo from Logradouro lo join fetch lo.cidade ci join fetch lo.bairro ba"
				+ " where lo.estado like :estado"), })
public class Logradouro extends Entidade {


	private static final long serialVersionUID = -5234107195155097874L;
	public final static String esquema = "diego";
	public final static String nomeTabela = "logradouro";

	@Id
	@Column(name = "cod_logradouro", nullable = false)
	@SequenceGenerator(name = "logradouro_cod_logradouro_seq", sequenceName = "diego.logradouro_cod_logradouro_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "logradouro_cod_logradouro_seq")
	private Integer codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_cidade", nullable = false)
	private Cidade cidade;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_bairro", nullable = false)
	private Bairro bairro;

	@Column(name = "cep", nullable = false)
	private String cep;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "estado", nullable = false)
	private String estado;

	@Column(name = "ds_logradouro", nullable = false)
	private String descricao;

	@Transient
	private String logradouroComCidade;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Override
	public Serializable getIdentificador() {
		// TODO Auto-generated method stub
		return null;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public String getLogradouroComCidade() {
		if(this.logradouroComCidade == null || this.logradouroComCidade.isEmpty()){
			if(descricao == null || cidade == null )
				return null;
			
			return descricao + " - " + cidade.getCidade();
		}
		
		else
			return this.logradouroComCidade;
	}

	public void setLogradouroComCidade(String logradouroComCidade) {
		this.logradouroComCidade = logradouroComCidade;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
