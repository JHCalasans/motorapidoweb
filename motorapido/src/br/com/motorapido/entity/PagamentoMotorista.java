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
@Table(name = PagamentoMotorista.nomeTabela, schema = PagamentoMotorista.esquema, catalog = "diego")
public class PagamentoMotorista extends Entidade {

	private static final long serialVersionUID = -4529508040528792191L;
	public final static String esquema = "diego";
	public final static String nomeTabela = "pagamento_motorista";
	
	@Id
	@Column(name = "cod_pagamento_motorista", nullable = false)
	@SequenceGenerator(name = "pagamento_motorista_cod_pagamento_motorista_seq", sequenceName = "diego.pagamento_motorista_cod_pagamento_motorista_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamento_motorista_cod_pagamento_motorista_seq")
	private Long codigo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_funcionario_baixa", nullable = false)
	private Funcionario funcionario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_motorista", nullable = false)
	private Motorista motorista;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_pagamento", nullable = false)
	private Date dataPagamento;
	
	
	@Override
	public Serializable getIdentificador() {
		return null;
	}

	public Long  getCodigo() {
		return codigo;
	}


	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public Funcionario getFuncionario() {
		return funcionario;
	}


	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	public Date getDataPagamento() {
		return dataPagamento;
	}


	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	
	
}
