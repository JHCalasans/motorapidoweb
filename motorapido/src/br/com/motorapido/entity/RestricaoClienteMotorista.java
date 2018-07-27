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
@Table(name = RestricaoClienteMotorista.nomeTabela, schema = RestricaoClienteMotorista.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "RestricaoClienteMotorista.obterRestricoesCliente",
				query = "select rcm from RestricaoClienteMotorista rcm join fetch rcm.motorista m "
				+ " where rcm.cliente.codigo = :codCliente ")
		})
public class RestricaoClienteMotorista extends Entidade{


	private static final long serialVersionUID = 7451700736831201470L;
	
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "restricao_cliente_motorista";
	
	@Id
	@Column(name = "cod_restricao_cliente_motorista", nullable = false)
	@SequenceGenerator(name = "restricao_cliente_motorista_cod_restricao_cliente_motorista_seq", sequenceName = "diego.restricao_cliente_motorista_cod_restricao_cliente_motorista_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restricao_cliente_motorista_cod_restricao_cliente_motorista_seq")
	private Integer codigo;	
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_motorista", nullable = false)
	private Motorista motorista;	

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_cliente", nullable = false)
	private Cliente cliente;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;
		
	

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




	public Motorista getMotorista() {
		return motorista;
	}




	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}




	public Cliente getCliente() {
		return cliente;
	}




	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}




	public Date getDataCriacao() {
		return dataCriacao;
	}




	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
