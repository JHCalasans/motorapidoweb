package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IMenuDAO;
import br.com.motorapido.entity.Menu;


@PersistenceContext(unitName = "postgresPU")
public class PostgresMenuDAOImpl extends GenericDAOImpl<Menu, Integer>
implements IMenuDAO{

	PostgresMenuDAOImpl() {
		super();
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_NOME_ASC });
	}
	
	@Override
	public List<Menu> obterMenusNaoVinculadosAoPerfil(Integer codPerfil, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codPerfil", codPerfil);
		return findByNamedQueryAndNamedParams("Menu.obterMenuNaoVinculadosAoPerfil", params ,em);
		
	}
	

}
