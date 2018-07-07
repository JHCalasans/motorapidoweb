package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.MensagemFuncionarioMotorista;

public interface IMensagemFuncionarioMotoristaDAO extends GenericDAO<MensagemFuncionarioMotorista, Long> {

	public List<MensagemFuncionarioMotorista> obterMensagensFuncionario(Integer codMotorista, Integer codFuncionario,
			EntityManager em) throws ExcecaoBanco  ;

}
