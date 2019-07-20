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
@Table(name = MotoristaPosicaoArea.nomeTabela, schema = MotoristaPosicaoArea.esquema, catalog = "diego")
@NamedQueries(value = {
		@NamedQuery(name = "MotoristaPosicaoArea.obterMotoristasPorArea", query = "select mpa from MotoristaPosicaoArea mpa join fetch mpa.motorista moto "
				+ " where mpa.area.codigo = :codArea and mpa.ativo = 'S' order by mpa.posicao asc"),
		@NamedQuery(name = "MotoristaPosicaoArea.obterMotoristaAtivoCodigo", query = "select mpa from MotoristaPosicaoArea mpa "
				+ " join fetch mpa.motorista moto join fetch mpa.area a "
				+ " where moto.codigo = :codMotorista and mpa.ativo = 'S' "),
		@NamedQuery(name = "MotoristaPosicaoArea.obterSituacaoMotoristaAnterior", query = "select mpa from MotoristaPosicaoArea mpa join fetch mpa.motorista moto "
				+ " where mpa.area.codigo = :codArea and mpa.posicao < :posicao "),
		@NamedQuery(name = "MotoristaPosicaoArea.obterMotoristasChamadaPorArea", query = "select mpa from MotoristaPosicaoArea mpa join fetch mpa.motorista moto "
				+ " where mpa.area.codigo = :codArea and mpa.ativo = 'S' and moto.disponivel = 'S' "
				+ " and moto.codigo not in (select mo.codigo from ChamadaVeiculo cv join cv.veiculo ve join ve.motorista mo where cv.chamada.codigo = :codChamada) "
				+ "order by mpa.posicao asc")})

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
	
	@Column(name = "flg_ativo", nullable = false)
	private String ativo;	
	
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

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

}
