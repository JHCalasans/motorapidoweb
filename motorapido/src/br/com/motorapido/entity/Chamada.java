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

import br.com.minhaLib.dao.Entidade;


@Entity
@Table(name = Chamada.nomeTabela, schema = Chamada.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "Chamada.obterChamadasAbertas",
				query = "select ch from Chamada ch join fetch ch.situacaoChamada sc left join fetch ch.cliente cl"
						+ " left join fetch ch.origem ori left join fetch ch.enderecoClienteOrigem eclori "
						+ " where ch.situacaoChamada.codigo in (2,3,4,5)")
		})
public class Chamada extends Entidade{

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
	
	@Column(name = "dt_inicio_espera", nullable = true)
	private Date dataInicioEspera;
	
	@Column(name = "pt_motorista", nullable = true)
	private Integer pontosMotorista;
	
	@Column(name = "pt_usuario", nullable = true)
	private Integer pontosUsuario;
	
	@Column(name = "observacao", nullable = true)
	private String observacao;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_usuario", nullable = true)
	private Usuario usuario;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_funcionario", nullable = true)
	private Funcionario funcionario;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_local_destino", nullable = true)
	private Local destino;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_local_origem", nullable = true)
	private Local origem;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_endereco_cliente_origem", nullable = true)
	private EnderecoCliente enderecoClienteOrigem;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_situacao_chamada", nullable = false)
	private SituacaoChamada situacaoChamada;
	
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name = "cod_cliente", nullable = true)
	private Cliente cliente;
	

	@Override
	public Serializable getIdentificador() {
	
		return getCodigo();
	}

	
	public SituacaoChamada getSituacaoChamada() {
		return situacaoChamada;
	}

	public void setSituacaoChamada(SituacaoChamada situacaoChamada) {
		this.situacaoChamada = situacaoChamada;
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


	public Usuario getUsuario() {
		return usuario;
	}


	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	public Funcionario getFuncionario() {
		return funcionario;
	}


	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}


	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
	
	public String getEnderecoFormatado(){
		if(origem != null)
			return origem.getLogradouro() + "; " + origem.getBairro() + "; " + origem.getComplemento();
		else
			return enderecoClienteOrigem.getLogradouro() + "; " + enderecoClienteOrigem.getBairro() + "; " + enderecoClienteOrigem.getComplemento();
		
	}

}
