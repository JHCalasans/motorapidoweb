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

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = BloqueioMotorista.nomeTabela, schema = BloqueioMotorista.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "BloqueioMotorista.obterUltimoBloqueioByMotorista", query = "select bm from BloqueioMotorista bm join fetch bm.motorista m "
				+ " where m.codigo = :codMotorista and bm.ativo = 'S' "),		
		})
public class BloqueioMotorista extends Entidade {

	private static final long serialVersionUID = -3896662335615502514L;

	public final static String esquema = "diego";
	public final static String nomeTabela = "bloqueio_motorista";

	@Id
	@Column(name = "cod_bloqueio_motorista", nullable = false)
	@SequenceGenerator(name = "bloqueio_motorista_cod_bloqueio_motorista_seq", sequenceName = "diego.bloqueio_motorista_cod_bloqueio_motorista_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bloqueio_motorista_cod_bloqueio_motorista_seq")
	private Integer codigo;

	@Column(name = "dt_inicio", nullable = false)
	private Date dataInicio;

	@Column(name = "dt_fim", nullable = true)
	private Date dataFim;
	
	@Column(name = "ds_motivo", nullable = true)
	private String motivo;
	
	@Column(name = "flg_ativo", nullable = false)
	private String ativo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_motorista", nullable = false)
	private Motorista motorista;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_funcionario", nullable = false)
	private Funcionario funcionario;

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

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

}
