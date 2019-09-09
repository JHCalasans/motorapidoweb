package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.entity.MotoristaAparelho;

@PersistenceContext(unitName = "postgresPU")
public class PostgresMotoristaAparelhoDAOImpl  extends GenericDAOImpl<MotoristaAparelho, Integer>
implements IMotoristaAparelhoDAO{

	@Override
	public List<MotoristaAparelho> obterOutrosAparelhosMotorista(Integer codMotorista, String idPush, EntityManager em)
			throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codMotorista", codMotorista);
		params.put("idPush", idPush);

		return findByNamedQueryAndNamedParams("MotoristaAparelho.obterOutrosAparelhosMotorista", params, em);
	}

	@Override
	public List<MotoristaAparelho> obterAparelhosMotoristas(String situacao, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("situacao", situacao);

		return findByNamedQueryAndNamedParams("MotoristaAparelho.obterAparelhosMotorista", params, em);
	}

	@Override
	public List<MotoristaAparelho> obterAparelhoPorIdPush(String idPush, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idPush", idPush);

		return findByNamedQueryAndNamedParams("MotoristaAparelho.obterAparelhosPorIdPush", params, em);
	}

	@Override
	public List<MotoristaAparelho> obterAparelhoPorMotorista(Integer codMotorista, EntityManager em)
			throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codMotorista", codMotorista);

		return findByNamedQueryAndNamedParams("MotoristaAparelho.obterAparelhosPorMotorista", params, em);
	}

	@Override
	public List<MotoristaAparelho> obterAparelhoPorIdAparelho(String idAparelho, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idAparelho", idAparelho);

		return findByNamedQueryAndNamedParams("MotoristaAparelho.obterAparelhosPorIdAparelho", params, em);
	}



}
