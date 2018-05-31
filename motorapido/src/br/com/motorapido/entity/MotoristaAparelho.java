package br.com.motorapido.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = MotoristaAparelho.nomeTabela, schema = MotoristaAparelho.esquema, catalog = "diego")
public class MotoristaAparelho extends Entidade{


	private static final long serialVersionUID = 8604601906743979251L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "motorista_aparelho";
	
	@Id
	@Column(name = "cod_motorista_aparelho", nullable = false)
	@SequenceGenerator(name = "motorista_aparelho_cod_motorista_aparelho_seq", sequenceName = "diego.motorista_aparelho_cod_motorista_aparelho_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "motorista_aparelho_cod_motorista_aparelho_seq")
	private Integer codigo;

	@Column(name = "id_push_aparelho", nullable = true, length = 50)
	private String idPush;
	
	@Column(name = "cod_motorista", nullable = false)
	private Integer codMotorista;
	
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


	


	public String getAtivo() {
		return ativo;
	}


	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}


	


	public String getIdPush() {
		return idPush;
	}


	public void setIdPush(String idPush) {
		this.idPush = idPush;
	}


	public Integer getCodMotorista() {
		return codMotorista;
	}


	public void setCodMotorista(Integer codMotorista) {
		this.codMotorista = codMotorista;
	}





}
