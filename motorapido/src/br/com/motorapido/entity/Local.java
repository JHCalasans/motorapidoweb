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
@Table(name = Local.nomeTabela, schema = Local.esquema, catalog = "diego")
public class Local extends Entidade{


	private static final long serialVersionUID = 838806589353314854L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "local";
	
	@Id
	@Column(name = "cod_local", nullable = false)
	@SequenceGenerator(name = "local_cod_local_seq", sequenceName = "diego.local_cod_local_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "local_cod_local_seq")
	private Integer codigo;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cep")
	private String cep; 
	
	@Column(name = "bairro")
	private String bairro; 
	
	@Column(name = "estado")
	private String estado; 
	
	@Column(name = "cidade")
	private String cidade; 
	
	@Column(name = "logradouro")
	private String logradouro; 
	
	@Column(name = "numero")
	private String numero; 
	
	@Column(name = "complemento")
	private String complemento; 
	
	@Column(name = "latitude")
	private String latitude; 
	
	@Column(name = "longitude")
	private String longitude; 
	

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


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getLogradouro() {
		return logradouro;
	}


	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getComplemento() {
		return complemento;
	}


	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
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

}
