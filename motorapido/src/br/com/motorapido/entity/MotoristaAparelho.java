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
@Table(name = MotoristaAparelho.nomeTabela, schema = MotoristaAparelho.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "MotoristaAparelho.obterOutrosAparelhosMotorista", query = "select ma from MotoristaAparelho ma where ma.motorista.codigo = :codMotorista "
						+ " and ma.idPush != :idPush "),
		@NamedQuery(name = "MotoristaAparelho.obterAparelhosMotorista", query = "select ma from MotoristaAparelho ma left join fetch ma.motorista moto"
				+ " where (:situacao = 'T' or ma.ativo = :situacao)"),
	    @NamedQuery(name = "MotoristaAparelho.obterAparelhosPorIdPush", query = "select ma from MotoristaAparelho ma left join fetch ma.motorista moto"
						+ " where ma.idPush = :idPush "),
	    @NamedQuery(name = "MotoristaAparelho.obterAparelhosPorIdAparelho", query = "select ma from MotoristaAparelho ma left join fetch ma.motorista moto"
				+ " where ma.idAparelho = :idAparelho "),
	    @NamedQuery(name = "MotoristaAparelho.obterAparelhosPorMotorista", query = "select ma from MotoristaAparelho ma join fetch ma.motorista moto"
				+ " where moto.codigo = :codMotorista and ma.ativo = 'S' " )
		})
public class MotoristaAparelho extends Entidade{


	private static final long serialVersionUID = 8604601906743979251L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "motorista_aparelho";
	
	@Id
	@Column(name = "cod_motorista_aparelho", nullable = false)
	@SequenceGenerator(name = "motorista_aparelho_cod_motorista_aparelho_seq", sequenceName = "diego.motorista_aparelho_cod_motorista_aparelho_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "motorista_aparelho_cod_motorista_aparelho_seq")
	private Integer codigo;

	@Column(name = "id_push_aparelho", nullable = true, length = 50)
	private String idPush;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_motorista", nullable = false, referencedColumnName = "cod_motorista")	
	private Motorista motorista;
	
	@Column(name = "flg_ativo", nullable = false)
	private String ativo;	
	
	@Column(name = "id_aparelho", nullable = false)
	private String idAparelho;	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_entrada", nullable = false)
	private Date entrada;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_desativacao", nullable = true)
	private Date desativacao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_ativacao", nullable = true)
	private Date ativacao;

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


	public Date getEntrada() {
		return entrada;
	}


	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}


	public Date getDesativacao() {
		return desativacao;
	}


	public void setDesativacao(Date desativacao) {
		this.desativacao = desativacao;
	}


	public String getIdAparelho() {
		return idAparelho;
	}


	public void setIdAparelho(String idAparelho) {
		this.idAparelho = idAparelho;
	}


	public Date getAtivacao() {
		return ativacao;
	}


	public void setAtivacao(Date ativacao) {
		this.ativacao = ativacao;
	}


	public Motorista getMotorista() {
		return motorista;
	}


	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}





}
