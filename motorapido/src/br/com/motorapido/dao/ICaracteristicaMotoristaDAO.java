package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.CaracteristicaMotorista;

public interface ICaracteristicaMotoristaDAO extends GenericDAO<CaracteristicaMotorista, Integer>{
	
	static CriterioOrdenacao BY_DT_CRIACAO_ASC = CriterioOrdenacao.asc("dataCriacao");
	
	List<CaracteristicaMotorista> obterCaracteristicasPorMotorista(Integer codMotorista, EntityManager em) throws ExcecaoBanco;


}
