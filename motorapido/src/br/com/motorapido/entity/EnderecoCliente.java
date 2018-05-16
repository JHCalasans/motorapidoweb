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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = EnderecoCliente.nomeTabela, schema = EnderecoCliente.esquema, catalog = "diego")
public class EnderecoCliente extends Entidade{


	private static final long serialVersionUID = 4871828216627967252L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "endereco_cliente";
	
	
	
	@Id
	@Column(name = "cod_endereco_cliente", nullable = false)
	@SequenceGenerator(name = "endereco_cliente_cod_endereco_cliente_seq", sequenceName = "diego.endereco_cliente_cod_endereco_cliente_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "endereco_cliente_cod_endereco_cliente_seq")
	private Integer codigo;
	
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_cliente", nullable = false, referencedColumnName = "cod_cliente")
	private Cliente cliente;
	
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


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
