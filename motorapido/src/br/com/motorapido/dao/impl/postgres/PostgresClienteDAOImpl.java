package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IClienteDAO;
import br.com.motorapido.entity.Cliente;

@PersistenceContext(unitName = "postgresPU")
public class PostgresClienteDAOImpl  extends GenericDAOImpl<Cliente, Integer>
implements IClienteDAO{

	@Override
	public List<Cliente> obterporLoginSenha(String login, String senha, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("login", login.toLowerCase());
		params.put("senha", senha);
		return findByNamedQueryAndNamedParams("Cliente.obterPorLoginSenha", params, em);
	}

}
