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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;


@Entity
@Table(name = FuncionarioPerfil.nomeTabela, schema = FuncionarioPerfil.esquema, catalog = "diego")
public class FuncionarioPerfil extends Entidade {

	private static final long serialVersionUID = 9164220880567317461L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "funcionario_perfil";
	
	
	@Id
	@Column(name = "cod_funcionario_perfil", nullable = false)
	@SequenceGenerator(name = "funcionario_perfil_cod_funcionario_perfil_seq", sequenceName = "diego.funcionario_perfil_cod_funcionario_perfil_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "funcionario_perfil_cod_funcionario_perfil_seq")
	private Integer codigo;
	
	@Column(name = "flg_ativo", nullable = false)
	private boolean ativo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_funcionario", nullable = false, referencedColumnName = "cod_funcionario")
	private Funcionario funcionario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_perfil", nullable = false, referencedColumnName = "cod_perfil")
	private Perfil perfil;
	
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


	public boolean isAtivo() {
		return ativo;
	}


	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public Funcionario getFuncionario() {
		return funcionario;
	}


	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	public Perfil getPerfil() {
		return perfil;
	}


	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
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

}
