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
@Table(name = Perfil.nomeTabela, schema = Perfil.esquema, catalog = "diego")
public class MotoristaPosicaoArea extends Entidade{

	private static final long serialVersionUID = 629906485401895302L;


	public final static String esquema = "diego";
	public final static String nomeTabela = "motorista_posicao_area";
	
	@Id
	@Column(name = "cod_motorista_posicao_area", nullable = false)
	@SequenceGenerator(name = "motorista_posicao_area_cod_motorista_posicao_area_seq", sequenceName = "diego.motorista_posicao_area_cod_motorista_posicao_area_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "motorista_posicao_area_cod_motorista_posicao_area_seq")
	private Integer codigo;
	
	@Column(name = "posicao", nullable = false)
	private Integer posicao;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_entrada", nullable = false)
	private Date entrada;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_area")
	private Area area;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_motorista")
	private Motorista motorista;
	
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

	public Integer getPosicao() {
		return posicao;
	}

	public void setPosicao(Integer posicao) {
		this.posicao = posicao;
	}

	public Date getEntrada() {
		return entrada;
	}

	public void setEntrada(Date entrada) {
		this.entrada = entrada;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

}
