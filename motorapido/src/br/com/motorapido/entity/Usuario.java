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
	private boolean ativo;
	
	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;
	
	@Column(name = "dt_desativacao", nullable = true)
	private Date dataDesativacao;

	
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


	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
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

}
