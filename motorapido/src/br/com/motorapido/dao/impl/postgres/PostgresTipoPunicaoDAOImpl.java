package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.ITipoPunicaoDAO;
import br.com.motorapido.entity.TipoPunicao;

@PersistenceContext(unitName = "postgresPU")
public class PostgresTipoPunicaoDAOImpl extends GenericDAOImpl<TipoPunicao, Integer> implements ITipoPunicaoDAO{

	@Override
	public List<TipoPunicao> obterTipoPunicoes(String desc, String ativo, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("ativo", ativo == null ? null : ativo.isEmpty() ? null : ativo);
		mapa.put("desc", desc == null ? "" : desc);
		List<TipoPunicao> lista = findByNamedQueryAndNamedParams("TipoPunicao.obterTipoPunicoes", mapa, em);
		return lista;
	}



	

}
