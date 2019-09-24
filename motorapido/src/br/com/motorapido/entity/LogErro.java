package br.com.motorapido.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.minhaLib.dao.Entidade;


@Entity
@Table(name = LogErro.nomeTabela, schema = LogErro.esquema, catalog = "diego")
public class LogErro extends Entidade {

	public final static String esquema = "diego";
	public final static String nomeTabela = "log_erro";
	private static final long serialVersionUID = 704137618984710085L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "log_erro_cod_erro_seq")
	@SequenceGenerator(name = "log_erro_cod_erro_seq", allocationSize = 1, sequenceName ="diego.log_erro_cod_erro_seq")
	@Column(name = "cod_erro", nullable = false)
	private Long codigo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_hora_erro", nullable = false)
	private Date dataHoraErro;

	@Column(name = "descricao", nullable = false)
	private String erro;
	
	@Column(name = "pagina", nullable = true)
	private String pagina;
	
	@Column(name = "cod_funcionario", nullable = true)
	private Integer codFuncionario;


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

	public Integer getCodFuncionario() {
		return codFuncionario;
	}

	public void setCodFuncionario(Integer codFuncionario) {
		this.codFuncionario = codFuncionario;
	}
}
