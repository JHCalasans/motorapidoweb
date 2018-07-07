package br.com.motorapido.dao.impl.postgres;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.dao.IMensagemFuncionarioMotoristaDAO;
import br.com.motorapido.entity.MensagemFuncionarioMotorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresMensagemFuncionarioMotoristaDAOImpl extends GenericDAOImpl<MensagemFuncionarioMotorista, Long>
		implements IMensagemFuncionarioMotoristaDAO {

	PostgresMensagemFuncionarioMotoristaDAOImpl() {
		super();
	}

	@Override
	public List<MensagemFuncionarioMotorista> obterMensagensFuncionario(Integer codMotorista, Integer codFuncionario,
			EntityManager em) throws ExcecaoBanco {
		Map<String, Object> mapa = new HashMap<String, Object>();
		mapa.put("codMotorista", codMotorista);
		mapa.put("codFuncionario", codFuncionario);

		List<MensagemFuncionarioMotorista> lista = findByNamedQueryAndNamedParams(
				"MensagemFuncionarioMotorista.obterMensagensFuncionario", mapa, em);
		return lista;

	}

}
