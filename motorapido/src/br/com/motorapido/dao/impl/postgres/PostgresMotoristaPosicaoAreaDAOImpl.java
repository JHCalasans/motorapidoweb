package br.com.motorapido.dao.impl.postgres;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IMotoristaPosicaoAreaDAO;
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
	
	
	

}
