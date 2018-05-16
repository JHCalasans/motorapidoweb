package br.com.motorapido.entity;

import java.io.Serializable;

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

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Modelo.nomeTabela, schema = Modelo.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "Modelo.obterModeloPorFabricanteETipo", query = "select mo from Modelo mo where mo.tipoVeiculo.codigo = :codTipoVeiculo and mo.fabricante.codigo = :codFabricante")
		})
public class Modelo extends Entidade{

	private static final long serialVersionUID = -3877658245725459939L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "modelo";
	
	@Id
	@Column(name = "cod_modelo", nullable = false)
	@SequenceGenerator(name = "modelo_cod_modelo_seq", sequenceName = "diego.modelo_cod_modelo_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "modelo_cod_modelo_seq")
	private Integer codigo;
	
	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;
	
	@Column(name = "flg_ativo", nullable = false, length = 1)
	private String flgAtivo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_fabricante", nullable = false, referencedColumnName = "cod_fabricante")
	private Fabricante fabricante;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_tipo_veiculo", nullable = false, referencedColumnName = "cod_tipo_veiculo")
	private TipoVeiculo tipoVeiculo;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFlgAtivo() {
		return flgAtivo;
	}

	public void setFlgAtivo(String flgAtivo) {
		this.flgAtivo = flgAtivo;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}
	
	

}
