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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = MensagemFuncionarioMotorista.nomeTabela, schema = MensagemFuncionarioMotorista.esquema, catalog = "diego")
public class MensagemFuncionarioMotorista extends Entidade{


	private static final long serialVersionUID = -6164673932745809913L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "mensagem_funcionario_motorista";
	
	
	@Id
	@Column(name = "cod_mensagem_funcionario_motorista", nullable = false)
	@SequenceGenerator(name = "mensagem_funcionario_motorist_cod_mensagem_funcionario_moto_seq", sequenceName = "diego.mensagem_funcionario_motorist_cod_mensagem_funcionario_moto_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mensagem_funcionario_motorist_cod_mensagem_funcionario_moto_seq")
	private Long codigo;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_motorista", nullable = false, referencedColumnName = "cod_motorista")
	private Motorista motorista;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_mensagem_funcionario", nullable = false, referencedColumnName = "cod_mensagem_funcionario")
	private MensagemFuncionario mensagemFuncionario;
	

	

	@Override
	public Serializable getIdentificador() {
		return this.codigo;
	}




	public Long getCodigo() {
		return codigo;
	}



	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}




	public Motorista getMotorista() {
		return motorista;
	}




	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}




	public MensagemFuncionario getMensagemFuncionario() {
		return mensagemFuncionario;
	}




	public void setMensagemFuncionario(MensagemFuncionario mensagemFuncionario) {
		this.mensagemFuncionario = mensagemFuncionario;
	}

}
