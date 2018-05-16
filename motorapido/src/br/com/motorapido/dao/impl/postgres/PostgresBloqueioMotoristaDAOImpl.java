package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IBloqueioMotoristaDAO;
import br.com.motorapido.entity.BloqueioMotorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresBloqueioMotoristaDAOImpl extends GenericDAOImpl<BloqueioMotorista, Integer>
implements IBloqueioMotoristaDAO{

	@Override
	public BloqueioMotorista obterUltimoPorMotorista(Integer codMotorista, EntityManager em) throws ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codMotorista",codMotorista);
		List<BloqueioMotorista> lista = null;
		lista =  findByNamedQueryAndNamedParams("BloqueioMotorista.obterUltimoBloqueioByMotorista", params, em);
		if(lista != null && lista.size() > 0)
			return lista.get(0);
		return null;
	}
	
	

}
