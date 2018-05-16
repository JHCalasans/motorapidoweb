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

import br.com.minhaLib.dao.Entidade;


@Entity
@Table(name = Chamada.nomeTabela, schema = Chamada.esquema, catalog = "diego")
public class ChamadaVeiculo extends Entidade{


	private static final long serialVersionUID = 132850483494763177L;
	

	public final static String esquema = "diego";
	public final static String nomeTabela = "chamada_veiculo";
	
	
	@Id
	@Column(name = "cod_chamada_veiculo", nullable = false)
	@SequenceGenerator(name = "chamada_motorista_cod_chamada_motorista_seq", sequenceName = "diego.chamada_motorista_cod_chamada_motorista_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chamada_motorista_cod_chamada_motorista_seq")
	private Integer codigo;
	
	@Column(name = "dt_hora_decisao", nullable = false)
	private Date dataDecisao;	
	
	@Column(name = "dt_hora_recebimento", nullable = true)
	private Date dataRecebimento;
	
	
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_veiculo", nullable = false)
	private Veiculo veiculo;
	

	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_chamada", nullable = false)
	private Chamada chamada;

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

	public Date getDataDecisao() {
		return dataDecisao;
	}

	public void setDataDecisao(Date dataDecisao) {
		this.dataDecisao = dataDecisao;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Chamada getChamada() {
		return chamada;
	}

	public void setChamada(Chamada chamada) {
		this.chamada = chamada;
	}

}
