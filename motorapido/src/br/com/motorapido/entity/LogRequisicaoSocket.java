package br.com.motorapido.entity;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.minhaLib.dao.Entidade;


@Entity
@Table(name = LogRequisicaoSocket.nomeTabela, schema = LogRequisicaoSocket.esquema, catalog = "diego")
public class LogRequisicaoSocket extends Entidade {

	public final static String esquema = "diego";
	public final static String nomeTabela = "log_requisicao_socket";
	private static final long serialVersionUID = 704137618984710085L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_requisicao_socket_cod_log_requisicao_socket_seq")
	@SequenceGenerator(name = "log_requisicao_socket_cod_log_requisicao_socket_seq", allocationSize = 1, sequenceName ="diego.log_requisicao_socket_cod_log_requisicao_socket_seq")
	@Column(name = "cod_log_requisicao_socket", nullable = false)
	private Long codigo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_requisicao", nullable = false)
	private Date dataRequisicao;

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_motorista", nullable = false)
	private Motorista motorista;
	
	@Column(name = "flg_ativo", nullable = false)
	private String ativo;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_fechamento", nullable = false)
	private Date dataFechamento;


	@Override
	public Long getIdentificador() {
		return codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDataRequisicao() {
		return dataRequisicao;
	}

	public void setDataRequisicao(Date dataRequisicao) {
		this.dataRequisicao = dataRequisicao;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	
}
