package br.com.motorapido.bo;


import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.ILogRequisicaoSocketDAO;
import br.com.motorapido.entity.LogRequisicaoSocket;
import br.com.motorapido.entity.Motorista;

public class LogRequisicaoSocketBO  extends MotoRapidoBO {

	private static LogRequisicaoSocketBO instance;

	private LogRequisicaoSocketBO() {

	}

	public static LogRequisicaoSocketBO getInstance() {
		if (instance == null)
			instance = new LogRequisicaoSocketBO();

		return instance;
	}
	
	public LogRequisicaoSocket logarRequisicao(String codMotorista) throws ExcecaoNegocio{
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			ILogRequisicaoSocketDAO logRequisicaoSocketDAO = fabricaDAO.getPostgresLogRequisicaoSocketDAO();
			LogRequisicaoSocket logRequisicaoSocket = new LogRequisicaoSocket();
			Motorista motorista = new Motorista(Integer.parseInt(codMotorista));
			logRequisicaoSocket.setAtivo("S");
			logRequisicaoSocket.setDataRequisicao(new Date());
			logRequisicaoSocket.setMotorista(motorista);
			LogRequisicaoSocket requisicao = logRequisicaoSocketDAO.save(logRequisicaoSocket, em);
			emUtil.commitTransaction(transaction);
			return requisicao;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar registrar requisição de motorista - " + codMotorista, e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
