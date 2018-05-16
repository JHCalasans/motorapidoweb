package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IFuncionarioDAO;
import br.com.motorapido.entity.Funcionario;

@PersistenceContext(unitName = "postgresPU")
public class PostgresFuncionarioDAOImpl  extends GenericDAOImpl<Funcionario, Integer>
implements IFuncionarioDAO{

	@Override
	public List<Funcionario> obterporLoginSenha(String login, String senha, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("login", login.toLowerCase());
		params.put("senha", senha);
		return findByNamedQueryAndNamedParams("Funcionario.obterPorLoginSenha", params, em);
	}

	@Override
	public List<Funcionario> obterporFuncionarios(String nome, String cpf, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nome", nome != null ? nome.toLowerCase() : "");
		params.put("cpf", cpf != null ? cpf.toLowerCase() : "");
		return findByNamedQueryAndNamedParams("Funcionario.obterFuncionarios", params, em);
	}

	@Override
	public List<Funcionario> obterTodos(EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		return findByNamedQueryAndNamedParams("Funcionario.obterTodos", params, em);
	}

	@Override
	public Funcionario obterPorCodigo(Integer codFuncionario, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codFuncionario", codFuncionario);
		List<Funcionario> lista = findByNamedQueryAndNamedParams("Funcionario.obterPorCod", params, em);
		if(lista != null && lista.size() > 0)
			return lista.get(0);
		else
			return null;
	}

}
