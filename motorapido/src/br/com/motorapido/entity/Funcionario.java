package br.com.motorapido.entity;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Funcionario.nomeTabela, schema = Funcionario.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "Funcionario.obterPorLoginSenha", query = "select f from Funcionario f join fetch f.perfil where lower(f.login) like :login and senha like :senha"),
		@NamedQuery(name = "Funcionario.obterFuncionarios", query = "select f from Funcionario f join fetch f.perfil where (:nome is null or lower(f.nome) like '%' || :nome || '%') and (:cpf is null or f.cpf like '%' || :cpf || '%')"),		
		@NamedQuery(name = "Funcionario.obterTodos", query = "select f from Funcionario f join fetch f.perfil"),
		@NamedQuery(name = "Funcionario.obterPorCod", query = "select f from Funcionario f join fetch f.perfil where f.codigo = :codFuncionario")
		})
public class Funcionario extends Entidade{

	private static final long serialVersionUID = 4250241797318775765L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "funcionario";
	
	@Id
	@Column(name = "cod_funcionario", nullable = false)
	@SequenceGenerator(name = "funcionario_cod_funcionario_seq", sequenceName = "diego.funcionario_cod_funcionario_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionario_cod_funcionario_seq")
	private Integer codigo;
	
	@Column(name = "nome", nullable = false)
	private String nome;
	
	@Column(name = "num_identidade", nullable = false)
	private String identidade;

	@Column(name = "cpf", nullable = true, length = 50)
	private String cpf;

	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Column(name = "login", nullable = false)
	private String login;
	
	@Column(name = "flg_ativo", nullable = true)
	private String ativo;
	
	
	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;
	
	@Column(name = "dt_desativacao", nullable = true)
	private Date dataDesativacao;
	
	@Column(name = "num_celular", nullable = true)
	private String celular;
	
	@Column(name = "logradouro", nullable = false)
	private String logradouro;
	
	@Column(name = "bairro", nullable = false)
	private String bairro;
	
	@Column(name = "cep", nullable = false)
	private String cep;
	
	@Column(name = "cidade_residencia", nullable = false)
	private String cidadeResidencia;
	
	@Column(name = "estado_residencia", nullable = false)
	private String estadoResidencia;
	
	@Column(name = "num_agencia", nullable = true)
	private String agencia;
	
	@Column(name = "num_conta", nullable = true)
	private String conta;
	
	@Column(name = "banco", nullable = true)
	private String banco;
	
	@Column(name = "flg_comprovante_residencia", nullable = true)
	private String comprovanteResidencial;
	
	@Column(name = "email", nullable = true)
	private String email;
	
	@Column(name = "foto", nullable = true)
	private byte[] foto;
	
	@Column(name = "dt_nascimento", nullable = false)
	private Date dataNascimento;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_perfil", nullable = false, referencedColumnName = "cod_perfil")
	private Perfil perfil;


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


	public String getIdentidade() {
		return identidade;
	}


	public void setIdentidade(String identidade) {
		this.identidade = identidade;
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


	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
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



	public String getAgencia() {
		return agencia;
	}


	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}


	public String getConta() {
		return conta;
	}


	public void setConta(String conta) {
		this.conta = conta;
	}


	public String getBanco() {
		return banco;
	}


	public void setBanco(String banco) {
		this.banco = banco;
	}


	public String getComprovanteResidencial() {
		return comprovanteResidencial;
	}


	public void setComprovanteResidencial(String comprovanteResidencial) {
		this.comprovanteResidencial = comprovanteResidencial;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public byte[] getFoto() {
		return foto;
	}


	public void setFoto(byte[] foto) {
		this.foto = foto;
	}


	public Date getDataNascimento() {
		return dataNascimento;
	}


	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


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
	}


	public Perfil getPerfil() {
		return perfil;
	}


	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}
