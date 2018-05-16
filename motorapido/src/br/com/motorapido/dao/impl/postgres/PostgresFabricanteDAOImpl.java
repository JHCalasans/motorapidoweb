package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IFabricanteDAO;
import br.com.motorapido.entity.Fabricante;

@PersistenceContext(unitName = "postgresPU")
public class PostgresFabricanteDAOImpl extends GenericDAOImpl<Fabricante, Integer>
implements IFabricanteDAO{

	@Override
	public List<Fabricante> obterPorTipoVeiculo(Integer codTipoVeiculo, EntityManager em)
			throws ExcecaoBanco, ExcecaoNegocio {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codTipoVeiculo", codTipoVeiculo);
		return findByNamedQueryAndNamedParams("Fabricante.obterFabricantePorTipoVeiculo", params, em);
		
	}

}
