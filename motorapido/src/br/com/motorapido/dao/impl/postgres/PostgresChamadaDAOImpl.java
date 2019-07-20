package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IChamadaDAO;
import br.com.motorapido.entity.Chamada;


@PersistenceContext(unitName = "postgresPU")
public class PostgresChamadaDAOImpl  extends GenericDAOImpl<Chamada, Long>
implements IChamadaDAO{

	PostgresChamadaDAOImpl() {
		super();
	}
	


	@Override
	public List<Chamada> obterChamadasAbertas(EntityManager em) throws ExcecaoBanco, ExcecaoNegocio {
		Map<String, Object> params = new HashMap<String, Object>();
		return findByNamedQueryAndNamedParams("Chamada.obterChamadasAbertas", params, em);
	}



	@Override
	public List<Chamada> obterChamadasFiltro(Integer codSituacao, EntityManager em)
			throws ExcecaoBanco, ExcecaoNegocio {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codSituacao", codSituacao);
		return findByNamedQueryAndNamedParams("Chamada.obterChamadasFiltro", params, em);
	}



	@Override
	public List<Chamada> obterHistoricoUsuario(Integer codUsuario, EntityManager em)
			throws ExcecaoBanco, ExcecaoNegocio {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codUsuario", codUsuario);
		return findByNamedQueryAndNamedParams("Chamada.obterHistoricoUsuario", params, em);
	}



	@Override
	public Chamada obterChamadaPorCodigo(Long codChamda, EntityManager em) throws ExcecaoBanco, ExcecaoNegocio {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codChamada", codChamda);
		List<Chamada> lista = findByNamedQueryAndNamedParams("Chamada.obterChamadaPorCodigo", params, em);
		return lista.get(0);
	}

}
