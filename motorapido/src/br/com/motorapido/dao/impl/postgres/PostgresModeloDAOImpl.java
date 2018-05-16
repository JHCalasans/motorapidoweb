package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IModeloDAO;
import br.com.motorapido.entity.Modelo;

@PersistenceContext(unitName = "postgresPU")
public class PostgresModeloDAOImpl extends GenericDAOImpl<Modelo, Integer>
implements IModeloDAO{

	@Override
	public List<Modelo> obterPorFabricanteETipo(Integer codFabricante, Integer codTipoVeiculo, EntityManager em)
			throws ExcecaoBanco, ExcecaoNegocio {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codTipoVeiculo", codTipoVeiculo);
		params.put("codFabricante", codFabricante);
		return findByNamedQueryAndNamedParams("Modelo.obterModeloPorFabricanteETipo", params, em);
		
	}

}
