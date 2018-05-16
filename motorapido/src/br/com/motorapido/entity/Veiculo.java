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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Veiculo.nomeTabela, schema = Veiculo.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "Veiculo.obterVeiculosPorMotorista", query = "select v from Veiculo v join fetch v.motorista m join fetch v.modelo mo "
				+ " join fetch mo.tipoVeiculo tv join fetch mo.fabricante f where :codMotorista = m.codigo and v.flgAtivo = 'S' "),	
		@NamedQuery(name = "Veiculo.obterVeiculosPorPlaca", query = "select v from Veiculo v join fetch v.motorista m join fetch v.modelo mo "
				+ " join fetch mo.tipoVeiculo tv join fetch mo.fabricante f where :placa = v.placa and v.flgAtivo = 'S' "),		
		@NamedQuery(name = "Veiculo.obterVeiculosPorChassi", query = "select v from Veiculo v join fetch v.motorista m join fetch v.modelo mo "
				+ " join fetch mo.tipoVeiculo tv join fetch mo.fabricante f where :chassi = v.chassi and v.flgAtivo = 'S' ")
		})
public class Veiculo extends Entidade{

	private static final long serialVersionUID = -6580795583352441116L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "veiculo";
	
	@Id
	@Column(name = "cod_veiculo", nullable = false)
	@SequenceGenerator(name = "veiculo_cod_veiculo_seq", sequenceName = "diego.veiculo_cod_veiculo_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "veiculo_cod_veiculo_seq")
	private Integer codigo;
	
	@Column(name = "chassi", nullable = false, length = 50)
	private String chassi;
	
	@Column(name = "cor", nullable = false, length = 50)
	private String cor;
	
	@Column(name = "categoria",  length = 1)
	private String categoria;
	
	@Column(name = "placa", nullable = false, length = 10)
	private String placa;
	
	@Column(name = "dt_cadastro", nullable = false)
	private Date dataCadastro;
	
	@Column(name = "dt_desativacao", nullable = true)
	private Date dataDesativacao;
	
	@Column(name = "flg_ativo", nullable = false, length = 1)
	private String flgAtivo;
	
	@Column(name = "documento", nullable = false)
	private byte[] documento;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_motorista", nullable = false, referencedColumnName = "cod_motorista")
	private Motorista motorista;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_modelo", nullable = false, referencedColumnName = "cod_modelo")
	private Modelo modelo;

	@Override
	public Serializable getIdentificador() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataDesativacao() {
		return dataDesativacao;
	}

	public void setDataDesativacao(Date dataDesativacao) {
		this.dataDesativacao = dataDesativacao;
	}

	public String getFlgAtivo() {
		return flgAtivo;
	}

	public void setFlgAtivo(String flgAtivo) {
		this.flgAtivo = flgAtivo;
	}

	public byte[] getDocumento() {
		return documento;
	}

	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	

}
