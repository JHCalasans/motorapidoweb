package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IPagamentoMotoristaDAO;
import br.com.motorapido.entity.PagamentoMotorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresPagamentoMotoristaDAOImpl extends GenericDAOImpl<PagamentoMotorista, Long>
implements IPagamentoMotoristaDAO{

	@Override
	public List<PagamentoMotorista> obterPagamentosMotorista(Integer codMotorista, EntityManager em)
			throws ExcecaoBanco, ExcecaoNegocio {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codMoto", codMotorista);
		return findByNamedQueryAndNamedParams("PagamentoMotorista.obterPagamentosMotorista", params, em);
	}

	

}
