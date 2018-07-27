package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IMotoristaDAO;
import br.com.motorapido.entity.Motorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresMotoristaDAOImpl extends GenericDAOImpl<Motorista, Integer> implements IMotoristaDAO {

	@Override
	public List<Motorista> obterMotoristas(String nome, String cpf, String cnh, String email, String identidade,
			EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nome", nome != null ? nome.toLowerCase() : "");
		params.put("cpf", cpf != null ? cpf.toLowerCase() : "");
		params.put("cnh", cnh != null ? cnh.toLowerCase() : "");
		params.put("email", email != null ? email.toLowerCase() : "");
		params.put("identidade", identidade != null ? identidade.toLowerCase() : "");
		return findByNamedQueryAndNamedParams("Motorista.obterMotoristas", params, em);
	}

	@Override
	public List<Motorista> obterTodos(EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		return findByNamedQueryAndNamedParams("Motorista.obterTodos", params, em);
	}

	@Override
	public Motorista obterPorCodigo(Integer codigo, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codigo", codigo);
		List<Motorista> lista = findByNamedQueryAndNamedParams("Motorista.obterPorCod", params, em);
		if (lista != null && lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}

	@Override
	public List<Motorista> obterMotoristasSemRestricoesCliente(Integer codCliente, EntityManager em)
			throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codCliente", codCliente);
		List<Motorista> lista = findByNamedQueryAndNamedParams("Motorista.obterSemRestricoesClientes", params, em);

		return lista;
	}

}
