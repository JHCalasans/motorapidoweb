package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IRestricaoClienteMotoristaDAO;
import br.com.motorapido.entity.RestricaoClienteMotorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresRestricaoClienteMotoristaDAOImpl extends GenericDAOImpl<RestricaoClienteMotorista, Integer>
		implements IRestricaoClienteMotoristaDAO {

	@Override
	public List<RestricaoClienteMotorista> obterRestricoesCliente(Integer codCliente, EntityManager em)
			throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codCliente", codCliente);
		List<RestricaoClienteMotorista> lista = findByNamedQueryAndNamedParams(
				"RestricaoClienteMotorista.obterRestricoesCliente", params, em);
		return lista;
	}

}
