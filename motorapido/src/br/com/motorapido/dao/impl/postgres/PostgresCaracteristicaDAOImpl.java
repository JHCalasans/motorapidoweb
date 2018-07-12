package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.ICaracteristicaDAO;
import br.com.motorapido.entity.Caracteristica;

@PersistenceContext(unitName = "postgresPU")
public class PostgresCaracteristicaDAOImpl extends GenericDAOImpl<Caracteristica, Integer> implements ICaracteristicaDAO{

	@Override
	public List<Caracteristica> obterCaracteristicas(String desc, String ativo, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("ativo", ativo == null ? null : ativo.isEmpty() ? null : ativo.equals("S") ? true : false);
		mapa.put("desc", desc == null ? "" : desc);
		List<Caracteristica> lista = findByNamedQueryAndNamedParams("Caracteristica.obterCaracteristicas", mapa, em);
		return lista;
	}

}
