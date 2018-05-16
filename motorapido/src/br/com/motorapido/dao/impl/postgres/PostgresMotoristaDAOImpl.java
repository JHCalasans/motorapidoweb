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
public class PostgresMotoristaDAOImpl  extends GenericDAOImpl<Motorista, Integer>
implements IMotoristaDAO{

	@Override
	public List<Motorista> obterMotoristas(String nome, String cpf, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nome", nome != null ? nome.toLowerCase() : "");
		params.put("cpf", cpf != null ? cpf.toLowerCase() : "");
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
		if(lista != null && lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}

}
