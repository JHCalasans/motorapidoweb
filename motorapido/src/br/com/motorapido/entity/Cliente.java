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
@Table(name = Cliente.nomeTabela, schema = Cliente.esquema, catalog = "diego")
//@NamedQueries(value = { 
//		@NamedQuery(name = "Cliente.obterCientes", query = "select c from Cliente c where (:nome is null or c.nome like '%' || :nome || '%' ) and ( :ativo is null or p.ativo = :ativo) and"
//				+ " ( :acesso is null or p.acessaSistema = :acesso )")
//		})
public class Cliente extends Entidade{


	private static final long serialVersionUID = -6332954191367348380L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "cliente";
	
	@Id
	@Column(name = "cod_cliente", nullable = false)
	@SequenceGenerator(name = "cliente_cod_cliente_seq", sequenceName = "diego.cliente_cod_cliente_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_cod_cliente_seq")
	private Integer codigo;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	

	@Column(name = "flg_ativo", nullable = false)
	private String ativo;	
	
	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;
	
	@Column(name = "dt_desativacao", nullable = true)
	private Date dataDesativacao;
	
	@Column(name = "num_celular", nullable = true)
	private String celular;
	
/*	@Column(name = "logradouro", nullable = false)
	private String logradouro;
	
	@Column(name = "bairro", nullable = false)
	private String bairro;
	
	@Column(name = "cep", nullable = false)
	private String cep;
	
	@Column(name = "cidade_residencia", nullable = false)
	private String cidadeResidencia;
	
	@Column(name = "estado_residencia", nullable = false)
	private String estadoResidencia;*/
	
	@Column(name = "email", nullable = true)
	private String email;
/*	
	@Column(name = "complemento", nullable = true)
	private String complemento;*/
	
	

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



	public String getAtivo() {
		return ativo;
	}



	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}



	public Date getDataCriacao() {
		return dataCriacao;
	}



	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}



	public Date getDataDesativacao() {
		return dataDesativacao;
	}



	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}



	public String getCelular() {
		return celular;
	}



	public void setCelular(String celular) {
		this.celular = celular;
	}


/*
	public String getLogradouro() {
		return logradouro;
	}



	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}



	public String getBairro() {
		return bairro;
	}



	public void setBairro(String bairro) {
		this.bairro = bairro;
	}



	public String getCep() {
		return cep;
	}



	public void setCep(String cep) {
		this.cep = cep;
	}



	public String getCidadeResidencia() {
		return cidadeResidencia;
	}



	public void setCidadeResidencia(String cidadeResidencia) {
		this.cidadeResidencia = cidadeResidencia;
	}



	public String getEstadoResidencia() {
		return estadoResidencia;
	}



	public void setEstadoResidencia(String estadoResidencia) {
		this.estadoResidencia = estadoResidencia;
	}*/



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}


/*
	public String getComplemento() {
		return complemento;
	}



	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
*/
	
}
