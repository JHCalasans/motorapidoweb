package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.MensagemFuncionario;

public interface IMensagemFuncionarioDAO extends GenericDAO<MensagemFuncionario, Integer> {

	public static CriterioOrdenacao BY_DTCRIACAO_ASC = CriterioOrdenacao
			.asc("dataCriacao");
}
