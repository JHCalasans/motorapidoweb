package br.com.motorapido.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;




@Entity
@Table(name = Fabricante.nomeTabela, schema = Fabricante.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "Fabricante.obterFabricantePorTipoVeiculo", query = "select distinct(mo.fabricante) from Modelo mo where mo.tipoVeiculo.codigo = :codTipoVeiculo")
		})
public class Fabricante extends Entidade{

	private static final long serialVersionUID = 2050702945825947089L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "fabricante";
	
	@Id
	@Column(name = "cod_fabricante", nullable = false)
	@SequenceGenerator(name = "fabricante_cod_fabricante_seq", sequenceName = "diego.fabricante_cod_fabricante_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fabricante_cod_fabricante_seq")
	private Integer codigo;
	
	@Column(name = "descricao", nullable = false, length = 100)
	private String descricao;

	@Column(name = "flg_ativo", nullable = false, length = 1)
	private String flgAtivo;
	
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
	
	

}
