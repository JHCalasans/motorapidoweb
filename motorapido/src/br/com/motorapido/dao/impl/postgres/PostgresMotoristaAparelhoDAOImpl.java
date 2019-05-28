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



}
