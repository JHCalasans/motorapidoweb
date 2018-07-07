package br.com.motorapido.bo;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IMensagemMotoristaFuncionarioDAO;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.entity.MensagemMotoristaFuncionario;
import br.com.motorapido.entity.Motorista;

public class MensagemMotoristaFuncionarioBO extends MotoRapidoBO {

	private static MensagemMotoristaFuncionarioBO instance;

	private MensagemMotoristaFuncionarioBO() {

	}

	public static MensagemMotoristaFuncionarioBO getInstance() {
		if (instance == null)
			instance = new MensagemMotoristaFuncionarioBO();

		return instance;
	}

	public List<MensagemMotoristaFuncionario> obterMensagens(Integer codMotorista, Integer codFuncionario)
			throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMensagemMotoristaFuncionarioDAO mensagemMotoristaFuncionarioDAO = fabricaDAO
					.getPostgresMensagemMotoristaFuncionarioDAO();

			Funcionario func = new Funcionario();
			func.setCodigo(codFuncionario);

			Motorista moto = new Motorista();
			moto.setCodigo(codMotorista);

			MensagemMotoristaFuncionario mensag = new MensagemMotoristaFuncionario();
			mensag.setFuncionario(func);
			mensag.setMotorista(moto);

			List<MensagemMotoristaFuncionario> lista = mensagemMotoristaFuncionarioDAO.findByExample(mensag, em);

			Collections.sort(lista, new Comparator<MensagemMotoristaFuncionario>(){

				@Override
				public int compare(MensagemMotoristaFuncionario o1, MensagemMotoristaFuncionario o2) {
					return o1.getDataCriacao().compareTo(o2.getDataCriacao());
				}
				
			});
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter mensagens.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
}
