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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Chamada.nomeTabela, schema = Chamada.esquema, catalog = "diego")
@NamedQueries(value = {
		@NamedQuery(name = "Chamada.obterChamadasAbertas", query = "select ch from Chamada ch join fetch ch.situacaoChamada sc left join fetch ch.cliente cl"
				+ " left join fetch ch.origem ori left join fetch ch.enderecoClienteOrigem eclori "
				+ " where ch.situacaoChamada.codigo in (2,3,5)"),
		@NamedQuery(name = "Chamada.obterChamadasFiltro", query = "select ch from Chamada ch join fetch ch.situacaoChamada sc left join fetch ch.cliente cl"
				+ " left join fetch ch.origem ori left join fetch ch.enderecoClienteOrigem eclori "
				+ " where (:codSituacao = -1 or ch.situacaoChamada.codigo = :codSituacao)")})
@XmlRootElement
public class Chamada extends Entidade {

	private static final long serialVersionUID = 5895083303813489402L;

	public final static String esquema = "diego";
	public final static String nomeTabela = "chamada";

	@Id
	@Column(name = "cod_chamada", nullable = false)
	@SequenceGenerator(name = "chamada_cod_chamada_seq", sequenceName = "diego.chamada_cod_chamada_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chamada_cod_chamada_seq")
	private Integer codigo;

	@Column(name = "dt_criacao", nullable = false)
	private Date dataCriacao;

	@Column(name = "dt_fim_corrida", nullable = true)
	private Date dataFimCorrida;

	@Column(name = "dt_inicio_corrida", nullable = true)
	private Date dataInicioCorrida;
	
	@Column(name = "dt_cancelamento", nullable = true)
	private Date dataCancelamento;

	@Column(name = "dt_inicio_espera", nullable = true)
	private Date dataInicioEspera;

	@Column(name = "pt_motorista", nullable = true)
	private Integer pontosMotorista;

	@Column(name = "pt_usuario", nullable = true)
	private Integer pontosUsuario;

	@Column(name = "observacao", nullable = true)
	private String observacao;
	
	@Column(name = "cep_origem", nullable = false)
	private String cepOrigem; 
	
	@Column(name = "bairro_origem", nullable = false)
	private String bairroOrigem; 
	
	@Column(name = "cidade_origem", nullable = false)
	private String cidadeOrigem; 
	
	@Column(name = "logradouro_origem", nullable = false)
	private String logradouroOrigem; 
	
	@Column(name = "numero_origem")
	private String numeroOrigem; 
	
	@Column(name = "complemento_origem")
	private String complementoOrigem; 
	
	@Column(name = "latitude_origem")
	private String latitudeOrigem; 
	
	@Column(name = "longitude_origem")
	private String longitudeOrigem; 
	
	@Column(name = "cep_destino")
	private String cepDestino; 
	
	@Column(name = "bairro_destino")
	private String bairroDestino; 
	
	@Column(name = "cidade_destino")
	private String cidadeDestino; 
	
	@Column(name = "logradouro_destino")
	private String logradouroDestino; 
	
	@Column(name = "numero_destino")
	private String numeroDestino; 
	
	@Column(name = "complemento_destino")
	private String complementoDestino; 
	
	@Column(name = "latitude_destino")
	private String latitudeDestino; 
	
	@Column(name = "longitude_destino")
	private String longitudeDestino; 

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_usuario", nullable = true)
	private Usuario usuario;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_funcionario", nullable = true)
	private Funcionario funcionario;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_local_destino", nullable = true, referencedColumnName = "cod_local")
	private Local destino;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_local_origem", nullable = true, referencedColumnName = "cod_local")
	private Local origem;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_endereco_cliente_origem", nullable = true, referencedColumnName = "cod_endereco_cliente")	
	private EnderecoCliente enderecoClienteOrigem;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_situacao_chamada", nullable = false)	
	private SituacaoChamada situacaoChamada;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_cliente", nullable = true)
	private Cliente cliente;		
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_area", nullable = true)
	private Area area;		

	
	public Chamada(){
		
	}
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

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataFimCorrida() {
		return dataFimCorrida;
	}

	public void setDataFimCorrida(Date dataFimCorrida) {
		this.dataFimCorrida = dataFimCorrida;
	}

	public Date getDataInicioCorrida() {
		return dataInicioCorrida;
	}

	public void setDataInicioCorrida(Date dataInicioCorrida) {
		this.dataInicioCorrida = dataInicioCorrida;
	}

	public Date getDataInicioEspera() {
		return dataInicioEspera;
	}

	public void setDataInicioEspera(Date dataInicioEspera) {
		this.dataInicioEspera = dataInicioEspera;
	}

	public Integer getPontosMotorista() {
		return pontosMotorista;
	}

	public void setPontosMotorista(Integer pontosMotorista) {
		this.pontosMotorista = pontosMotorista;
	}

	public Integer getPontosUsuario() {
		return pontosUsuario;
	}

	public void setPontosUsuario(Integer pontosUsuario) {
		this.pontosUsuario = pontosUsuario;
	}

	@XmlTransient
	public SituacaoChamada getSituacaoChamada() {
		return situacaoChamada;
	}

	public void setSituacaoChamada(SituacaoChamada situacaoChamada) {
		this.situacaoChamada = situacaoChamada;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@XmlTransient
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	public Local getDestino() {
		return destino;
	}

	public void setDestino(Local destino) {
		this.destino = destino;
	}

	public Local getOrigem() {
		return origem;
	}

	public void setOrigem(Local origem) {
		this.origem = origem;
	}

	public EnderecoCliente getEnderecoClienteOrigem() {
		return enderecoClienteOrigem;
	}

	public void setEnderecoClienteOrigem(EnderecoCliente enderecoClienteOrigem) {
		this.enderecoClienteOrigem = enderecoClienteOrigem;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getEnderecoFormatado() {
		
		return getLogradouroOrigem() + "; " + getBairroOrigem() + "; " + getComplementoOrigem();
		/*
		if (origem != null)
			return origem.getLogradouro() + "; " + origem.getBairro() + "; " 
					+ origem.getComplemento();
		else
			return enderecoClienteOrigem.getLogradouro() + "; " + enderecoClienteOrigem.getBairro() + "; "
					 + enderecoClienteOrigem.getComplemento();
*/
	}

	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getCepOrigem() {
		return cepOrigem;
	}

	public void setCepOrigem(String cepOrigem) {
		this.cepOrigem = cepOrigem;
	}

	public String getBairroOrigem() {
		return bairroOrigem;
	}

	public void setBairroOrigem(String bairroOrigem) {
		this.bairroOrigem = bairroOrigem;
	}

	public String getCidadeOrigem() {
		return cidadeOrigem;
	}

	public void setCidadeOrigem(String cidadeOrigem) {
		this.cidadeOrigem = cidadeOrigem;
	}

	public String getLogradouroOrigem() {
		return logradouroOrigem;
	}

	public void setLogradouroOrigem(String logradouroOrigem) {
		this.logradouroOrigem = logradouroOrigem;
	}

	public String getNumeroOrigem() {
		return numeroOrigem;
	}

	public void setNumeroOrigem(String numeroOrigem) {
		this.numeroOrigem = numeroOrigem;
	}

	public String getComplementoOrigem() {
		return complementoOrigem;
	}

	public void setComplementoOrigem(String complementoOrigem) {
		this.complementoOrigem = complementoOrigem;
	}

	public String getLatitudeOrigem() {
		return latitudeOrigem;
	}

	public void setLatitudeOrigem(String latitudeOrigem) {
		this.latitudeOrigem = latitudeOrigem;
	}

	public String getLongitudeOrigem() {
		return longitudeOrigem;
	}

	public void setLongitudeOrigem(String longitudeOrigem) {
		this.longitudeOrigem = longitudeOrigem;
	}

	public String getCepDestino() {
		return cepDestino;
	}

	public void setCepDestino(String cepDestino) {
		this.cepDestino = cepDestino;
	}

	public String getBairroDestino() {
		return bairroDestino;
	}

	public void setBairroDestino(String bairroDestino) {
		this.bairroDestino = bairroDestino;
	}

	public String getCidadeDestino() {
		return cidadeDestino;
	}

	public void setCidadeDestino(String cidadeDestino) {
		this.cidadeDestino = cidadeDestino;
	}

	public String getLogradouroDestino() {
		return logradouroDestino;
	}

	public void setLogradouroDestino(String logradouroDestino) {
		this.logradouroDestino = logradouroDestino;
	}

	public String getNumeroDestino() {
		return numeroDestino;
	}

	public void setNumeroDestino(String numeroDestino) {
		this.numeroDestino = numeroDestino;
	}

	public String getComplementoDestino() {
		return complementoDestino;
	}

	public void setComplementoDestino(String complementoDestino) {
		this.complementoDestino = complementoDestino;
	}

	public String getLatitudeDestino() {
		return latitudeDestino;
	}

	public void setLatitudeDestino(String latitudeDestino) {
		this.latitudeDestino = latitudeDestino;
	}

	public String getLongitudeDestino() {
		return longitudeDestino;
	}

	public void setLongitudeDestino(String longitudeDestino) {
		this.longitudeDestino = longitudeDestino;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	
	@XmlTransient
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}

}
