package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.motorapido.dao.IMotoristaPosicaoAreaDAO;
import br.com.motorapido.entity.Area;
import br.com.motorapido.entity.MotoristaPosicaoArea;

@PersistenceContext(unitName = "postgresPU")
public class PostgresMotoristaPosicaoAreaDAOImpl  extends GenericDAOImpl<MotoristaPosicaoArea, Integer>
implements IMotoristaPosicaoAreaDAO{

	PostgresMotoristaPosicaoAreaDAOImpl() {
		super();
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_POS_ASC });
	}

	@Override
	public Integer obterMaiorPosicaoArea(Integer codArea, EntityManager em) {
		
		Query sql = em.createNativeQuery("select max(posicao) from motorista_posicao_area  where cod_area = :codArea");
		sql.setParameter("codArea", codArea);
		//String sql = " select max(posicao) from diego.motorista_posicao_area where codArea = " + codArea;
		
		//Query q = em.createNativeQuery(sql);
		
		return  (Integer)sql.getSingleResult();
	}

	@Override
	public List<MotoristaPosicaoArea> obterMotoristasPorArea(Area area, EntityManager em) throws ExcecaoBancoConexao, ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codArea", area.getCodigo());
		return findByNamedQueryAndNamedParams("MotoristaPosicaoArea.obterMotoristasPorArea", params, em);
	}
	
	
	

}
