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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = MensagemMotoristaFuncionario.nomeTabela, schema = MensagemMotoristaFuncionario.esquema, catalog = "diego")
public class MensagemMotoristaFuncionario extends Entidade{


	private static final long serialVersionUID = 4433806570768356752L;
	
	
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "mensagem_motorista_funcionario";	
	
	
	@Id
	@Column(name = "cod_mensagem_motorista_funcionario", nullable = false)
	@SequenceGenerator(name = "mensagem_motorista_funcionari_cod_mensagem_motorista_funcio_seq", sequenceName = "diego.mensagem_motorista_funcionari_cod_mensagem_motorista_funcio_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mensagem_motorista_funcionari_cod_mensagem_motorista_funcio_seq")
	private Integer codigo;
	
	@Column(name = "ds_mensagem", nullable = false, length = 100)
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_funcionario", nullable = false, referencedColumnName = "cod_funcionario")
	private Funcionario funcionario;
	
	
	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_motorista", nullable = false, referencedColumnName = "cod_motorista")
	private Motorista motorista;
	
	@Column(name = "flg_enviada_por_motorista", nullable = false, length = 1)
	private String enviadaPorMotorista;
	

	@Override
	public Serializable getIdentificador() {
		return this.codigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public String getEnviadaPorMotorista() {
		return enviadaPorMotorista;
	}

	public void setEnviadaPorMotorista(String enviadaPorMotorista) {
		this.enviadaPorMotorista = enviadaPorMotorista;
	}

}
