package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.MensagemMotoristaFuncionario;

public interface IMensagemMotoristaFuncionarioDAO extends GenericDAO<MensagemMotoristaFuncionario, Integer> {

	public static CriterioOrdenacao BY_DTCRIACAO_ASC = CriterioOrdenacao
			.asc("dataCriacao");
}
