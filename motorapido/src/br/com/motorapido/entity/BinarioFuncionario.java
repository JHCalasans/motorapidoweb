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
@Table(name = BinarioFuncionario.nomeTabela, schema = BinarioFuncionario.esquema, catalog = "diego")
public class BinarioFuncionario extends Entidade{

	private static final long serialVersionUID = -3877658245725459939L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "binario_funcionario";
	
	@Id
	@Column(name = "cod_binario_funcionario", nullable = false)
	@SequenceGenerator(name = "binario_funcionario_cod_binario_funcionario_seq", sequenceName = "diego.binario_funcionario_cod_binario_funcionario_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "binario_funcionario_cod_binario_funcionario_seq")
	private Long codigo;
	
	@Column(name = "binario", nullable = false)
	private  byte[] binario;
	
	
	@Override
	public Serializable getIdentificador() {		
		return getCodigo();
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public byte[] getBinario() {
		return binario;
	}

	public void setBinario(byte[] binario) {
		this.binario = binario;
	}
	
	

}
