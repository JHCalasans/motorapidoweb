package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IUsuarioAparelhoDAO;
import br.com.motorapido.entity.UsuarioAparelho;

@PersistenceContext(unitName = "postgresPU")
public class PostgresUsuarioAparelhoDAOImpl  extends GenericDAOImpl<UsuarioAparelho, Integer>
implements IUsuarioAparelhoDAO{

	
	
	@Override
	public List<UsuarioAparelho> obterAparelhoUsuario(String idPush, Integer codUsuario, EntityManager em)
			throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPush", idPush);
		params.put("codUsuario", codUsuario);
		List<UsuarioAparelho> lista = findByNamedQueryAndNamedParams("UsarioAparelho.obterAparelhoUsuario", params, em);
		return lista;
	}



}
