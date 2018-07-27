package br.com.motorapido.dao;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.motorapido.entity.PerfilMenu;

public interface IPerfilMenuDAO  extends GenericDAO<PerfilMenu, Integer>{

	static CriterioOrdenacao BY_MENU_ASC = CriterioOrdenacao.asc("menu.codigo");
}
