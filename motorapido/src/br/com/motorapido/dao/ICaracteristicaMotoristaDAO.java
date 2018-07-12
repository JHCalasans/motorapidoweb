package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.CaracteristicaMotorista;

public interface ICaracteristicaMotoristaDAO extends GenericDAO<CaracteristicaMotorista, Integer>{
	
	static CriterioOrdenacao BY_DT_CRIACAO_ASC = CriterioOrdenacao.asc("dataCriacao");

}
