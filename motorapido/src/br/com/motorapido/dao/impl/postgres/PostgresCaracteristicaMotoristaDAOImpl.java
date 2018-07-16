package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.ICaracteristicaMotoristaDAO;
import br.com.motorapido.entity.CaracteristicaMotorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresCaracteristicaMotoristaDAOImpl extends GenericDAOImpl<CaracteristicaMotorista, Integer>
implements ICaracteristicaMotoristaDAO{

	@Override
	public List<CaracteristicaMotorista> obterCaracteristicasPorMotorista(Integer codMotorista, EntityManager em)
			throws ExcecaoBanco {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("codMotorista", codMotorista);
		List<CaracteristicaMotorista> lista = findByNamedQueryAndNamedParams("CaracteristicaMotorista.obterCaracteristicasPorMotorista", mapa, em);
		return lista;
	}

}
