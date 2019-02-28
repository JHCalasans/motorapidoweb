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
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.minhaLib.dao.Entidade;

@XmlRootElement
@Entity
@Table(name = Usuario.nomeTabela, schema = Usuario.esquema, catalog = "diego")
public class Usuario extends Entidade{

	private static final long serialVersionUID = -1797317904162176130L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "usuario";

	@Id
	@Column(name = "cod_usuario", nullable = false)
	@SequenceGenerator(name = "usuario_cod_usuario_seq", sequenceName = "diego.usuario_cod_usuario_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_cod_usuario_seq")
	private Integer codigo;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;

	@Column(name = "cpf", nullable = true, length = 50)
	private String cpf;

	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Column(name = "flg_ativo", nullable = false)
	private String ativo;
	
	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;
	
	@Column(name = "dt_desativacao", nullable = true)
	private Date dataDesativacao;
	
	@Column(name = "email", nullable = true)
	private String email;
	
	@Column(name = "num_telefone", nullable = true)
	private String numeroTelefone;
	
	@Transient
	private String chaveServicos;
	
	@Transient
	private String idPush;

	
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


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String isAtivo() {
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


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getNumeroTelefone() {
		return numeroTelefone;
	}


	public void setNumeroTelefone(String numeroTelefone) {
		this.numeroTelefone = numeroTelefone;
	}


	public String getChaveServicos() {
		return chaveServicos;
	}


	public void setChaveServicos(String chaveServicos) {
		this.chaveServicos = chaveServicos;
	}


	public String getIdPush() {
		return idPush;
	}


	public void setIdPush(String idPush) {
		this.idPush = idPush;
	}


	public String getAtivo() {
		return ativo;
	}

}
