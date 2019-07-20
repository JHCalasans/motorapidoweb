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
@Table(name = LogErroMotorista.nomeTabela, schema = LogErroMotorista.esquema, catalog = "diego")
public class LogErroMotorista extends Entidade {

	public final static String esquema = "diego";
	public final static String nomeTabela = "log_erro_motorista";
	private static final long serialVersionUID = 704137618984710085L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_erro_motorista_cod_log_erro_motorista_seq")
	@SequenceGenerator(name = "log_erro_motorista_cod_log_erro_motorista_seq", allocationSize = 1, sequenceName ="diego.log_erro_motorista_cod_log_erro_motorista_seq")
	@Column(name = "cod_log_erro_motorista", nullable = false)
	private Long codigo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_hora", nullable = false)
	private Date dataHoraErro;

	@Column(name = "descricao", nullable = false)
	private String erro;
	
	@Column(name = "pagina", nullable = false)
	private String pagina;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_motorista", nullable = false, referencedColumnName = "cod_motorista")
	private Motorista motorista;


	public Long getCodLogErro() {
		return codigo;
	}

	public void setCodLogOperacao(Long codigo) {
		this.codigo = codigo;
	}

	public Date getDataHoraErro() {
		return dataHoraErro;
	}

	public void setDataHoraErro(Date dataHoraErro) {
		this.dataHoraErro = dataHoraErro;
	}

	

	@Override
	public Long getIdentificador() {
		return codigo;
	}

	

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
}
