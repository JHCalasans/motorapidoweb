package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.ILogradouroDAO;
import br.com.motorapido.entity.Logradouro;

@PersistenceContext(unitName = "postgresPU")
public class PostgresLogradouroDAOImpl extends GenericDAOImpl<Logradouro, Integer> implements ILogradouroDAO{

	@Override
	public List<Logradouro> buscarLogradourosPorEstado(String estado, EntityManager em)
			throws ExcecaoNegocio, ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("estado", estado);
		return findByNamedQueryAndNamedParams("Logradouro.obterPorEstado", params ,em);
	}


	
	
}
