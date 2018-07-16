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
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = CaracteristicaMotorista.nomeTabela, schema = CaracteristicaMotorista.esquema, catalog = "diego")
@NamedQuery(name = "CaracteristicaMotorista.obterCaracteristicasPorMotorista", query = "select cm from CaracteristicaMotorista cm join fetch cm.caracteristica c "
		+ " where cm.motorista.codigo = :codMotorista ")
public class CaracteristicaMotorista extends Entidade {

	private static final long serialVersionUID = -5155306784315437652L;

	public final static String esquema = "diego";

	public final static String nomeTabela = "caracteristica_motorista";
	

	@Id
	@Column(name = "cod_caracteristica_motorista", nullable = false)
	@SequenceGenerator(name = "caracteristica_motorista_cod_caracteristica_motorista_seq", sequenceName = "diego.caracteristica_motorista_cod_caracteristica_motorista_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caracteristica_motorista_cod_caracteristica_motorista_seq")
	private Integer codigo;	
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_motorista", nullable = false)
	private Motorista motorista;	

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_caracteristica", nullable = false)
	private Caracteristica caracteristica;
	
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


	public Caracteristica getCaracteristica() {
		return caracteristica;
	}


	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}


	public Date getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

}
