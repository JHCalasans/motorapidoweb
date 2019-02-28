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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = UsuarioAparelho.nomeTabela, schema = UsuarioAparelho.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "UsarioAparelho.obterAparelhoUsuario", query = "select ua from UsuarioAparelho ua join fetch ua.usuario usu "
				+ " where ua.idPush = :idPush and ua.usuario.codigo = :codUsuario ")	
			})
public class UsuarioAparelho extends Entidade{


	private static final long serialVersionUID = 8604601906743979251L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "usuario_aparelho";
	
	@Id
	@Column(name = "cod_usuario_aparelho", nullable = false)
	@SequenceGenerator(name = "usuario_aparelho_cod_usuario_aparelho_seq", sequenceName = "diego.usuario_aparelho_cod_usuario_aparelho_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_aparelho_cod_usuario_aparelho_seq")
	private Integer codigo;

	@Column(name = "id_push_aparelho", nullable = true, length = 50)
	private String idPush;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_usuario", nullable = false)
	private Usuario usuario;
	
	@Column(name = "flg_ativo", nullable = false)
	private String ativo;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_desativacao", nullable = false)
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


	public String getAtivo() {
		return ativo;
	}


	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}


	public String getIdPush() {
		return idPush;
	}


	public void setIdPush(String idPush) {
		this.idPush = idPush;
	}


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
