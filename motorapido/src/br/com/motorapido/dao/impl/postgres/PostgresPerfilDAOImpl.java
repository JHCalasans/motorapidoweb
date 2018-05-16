package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IPerfilDAO;
import br.com.motorapido.entity.Perfil;

@PersistenceContext(unitName = "postgresPU")
public class PostgresPerfilDAOImpl  extends GenericDAOImpl<Perfil, Integer>
implements IPerfilDAO{

	@Override
	public List<Perfil> obterPerfis(String desc, String ativo, String acesso, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("ativo", ativo == null ? null : ativo.isEmpty() ? null : ativo.equals("S") ? true : false);
		mapa.put("acesso", acesso == null ? null :  acesso.isEmpty() ? null : acesso.equals("S") ? true : false);
		mapa.put("desc", desc == null ? "" : desc);
		List<Perfil> lista = findByNamedQueryAndNamedParams("Perfil.obterPerfis", mapa, em);
		return lista;
	}

	

}
