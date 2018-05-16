package br.com.motorapido.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Motorista.nomeTabela, schema = Motorista.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "Motorista.obterMotoristas", query = "select m from Motorista m  where (:nome is null or lower(m.nome) like '%' || :nome || '%') and (:cpf is null or m.cpf like '%' || :cpf || '%')"),		
		@NamedQuery(name = "Motorista.obterTodos", query = "select m from Motorista m "),
		@NamedQuery(name = "Motorista.obterPorCod", query = "select m from Motorista m where m.codigo = :codigo")
		})
@XmlRootElement
public class Motorista extends Entidade{


	private static final long serialVersionUID = 8604601906743979251L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "motorista";
	
	@Id
	@Column(name = "cod_motorista", nullable = false)
	@SequenceGenerator(name = "motorista_cod_motorista_seq", sequenceName = "diego.motorista_cod_motorista_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "motorista_cod_motorista_seq")
	private Integer codigo;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "num_identidade", nullable = false)
	private String identidade;

	@Column(name = "cpf", nullable = true, length = 50)
	private String cpf;

	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Column(name = "login", nullable = false)
	private String login;
	
	@Column(name = "flg_ativo", nullable = false)
	private String ativo;	
	
	@Column(name = "flg_bloqueado", nullable = false)
	private String bloqueado;	
	
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
	

	
	@Column(name = "email", nullable = true)
	private String email;
	
	@Column(name = "foto", nullable = true)
	private byte[] foto;
	
	@Column(name = "dt_nascimento", nullable = false)
	private Date dataNascimento;
	
	@Column(name = "num_cnh", nullable = false)
	private String cnh;
	
	@Column(name = "dt_vencimento_cnh", nullable = false)
	private Date dataVencimentoCNH;
	
	@Column(name = "documentos_criminais", nullable = false)
	private byte[] docCriminais;
	
	@Column(name = "flg_disponivel", nullable = false)
	private String disponivel;
	
	@Column(name = "comprovante_residencia", nullable = false)
	private byte[] comprovanteResidencia;
	
	@Transient
	private String chaveServicos;
	

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


	public String getCnh() {
		return cnh;
	}


	public void setCnh(String cnh) {
		this.cnh = cnh;
	}


	public Date getDataVencimentoCNH() {
		return dataVencimentoCNH;
	}


	public void setDataVencimentoCNH(Date dataVencimentoCNH) {
		this.dataVencimentoCNH = dataVencimentoCNH;
	}


	public byte[] getDocCriminais() {
		return docCriminais;
	}


	public void setDocCriminais(byte[] docCriminais) {
		this.docCriminais = docCriminais;
	}


	public String getDisponivel() {
		return disponivel;
	}


	public void setDisponivel(String disponivel) {
		this.disponivel = disponivel;
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


	public String getChaveServicos() {
		return chaveServicos;
	}


	public void setChaveServicos(String chaveServicos) {
		this.chaveServicos = chaveServicos;
	}


	public String getBloqueado() {
		return bloqueado;
	}


	public void setBloqueado(String bloqueado) {
		this.bloqueado = bloqueado;
	}


	public byte[] getComprovanteResidencia() {
		return comprovanteResidencia;
	}


	public void setComprovanteResidencia(byte[] comprovanteResidencia) {
		this.comprovanteResidencia = comprovanteResidencia;
	}

}
