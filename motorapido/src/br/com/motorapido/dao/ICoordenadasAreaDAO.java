package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.CoordenadasArea;

public interface ICoordenadasAreaDAO  extends GenericDAO<CoordenadasArea, Integer>{

	static CriterioOrdenacao BY_AREA_ASC = CriterioOrdenacao.asc("area");

}
