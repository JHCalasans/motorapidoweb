package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IChamadaVeiculoDAO;
import br.com.motorapido.entity.ChamadaVeiculo;


@PersistenceContext(unitName = "postgresPU")
public class PostgresChamadaVeiculoDAOImpl extends GenericDAOImpl<ChamadaVeiculo, Integer>
implements IChamadaVeiculoDAO{
	
	PostgresChamadaVeiculoDAOImpl() {
		super();
	}

	
	@Override
	public List<ChamadaVeiculo> obterHistoricoMotorista(Integer codMotorista, EntityManager em)
			throws ExcecaoBanco, ExcecaoNegocio {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codMotorista", codMotorista);
		return findByNamedQueryAndNamedParams("ChamadaVeiculo.obterHistoricoMotorista", params, em);
	}

}
