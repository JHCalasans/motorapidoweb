package br.com.motorapido.bo;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.joda.time.DateTime;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaAparelho;

public class MotoristaAparelhoBO extends MotoRapidoBO {

	private static MotoristaAparelhoBO instance;

	private MotoristaAparelhoBO() {

	}

	public static MotoristaAparelhoBO getInstance() {
		if (instance == null)
			instance = new MotoristaAparelhoBO();

		return instance;
	}

	public void enviarID(Motorista motorista) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
			List<MotoristaAparelho> lista = motoristaAparelhoDAO.obterAparelhoPorIdAparelho(motorista.getIdAparelho(),
					em);
			if (lista != null && lista.size() > 0) {

			} else {
				MotoristaAparelho motoristaAparelho = new MotoristaAparelho();
				motoristaAparelho.setEntrada(new Date());
				motoristaAparelho.setIdAparelho(motorista.getIdAparelho());
				motoristaAparelho.setIdPush(motorista.getIdPush());
				motoristaAparelho.setAtivo("N");
				motoristaAparelhoDAO.save(motoristaAparelho, em);
			}
			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar id de aparelho.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public void vincularMotorista(Integer codMotorista, String idAparelho) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
			MotoristaAparelho motoristaAparelho = new MotoristaAparelho();
			motoristaAparelho.setIdAparelho(idAparelho);
			List<MotoristaAparelho> lista = motoristaAparelhoDAO.obterAparelhoPorIdAparelho(idAparelho, em);
			boolean encontrou = false;
			Date dtEntrada = null;
			String idPush = null;
			for (MotoristaAparelho motoAp : lista) {
				if (motoAp.getMotorista() != null) {
					if (motoAp.getMotorista().getCodigo().equals(codMotorista)) {
						motoAp.setDesativacao(null);
						motoAp.setAtivacao(new Date());
						motoAp.setAtivo("S");
						encontrou = true;
					} else if (motoAp.getAtivo().equals("S")) {
						motoAp.setDesativacao(new Date());
						motoAp.setAtivo("N");
					}

				} else {
					motoAp.setDesativacao(null);
					motoAp.setAtivacao(new Date());
					motoAp.setAtivo("S");
					motoAp.setMotorista(new Motorista(codMotorista));
					encontrou = true;
				}
				dtEntrada = motoAp.getEntrada();
				idPush = motoAp.getIdPush();

				motoristaAparelhoDAO.save(motoAp, em);
			}
			if (!encontrou) {
				motoristaAparelho.setDesativacao(null);
				motoristaAparelho.setAtivacao(new Date());
				motoristaAparelho.setAtivo("S");
				motoristaAparelho.setMotorista(new Motorista(codMotorista));
				motoristaAparelho.setEntrada(dtEntrada);
				motoristaAparelho.setIdPush(idPush);
				motoristaAparelhoDAO.save(motoristaAparelho, em);
			}

			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar vincular motorista ao aparelho.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	public List<MotoristaAparelho> obterAparelhosMotoristas(String ativo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IMotoristaAparelhoDAO motoristaAparelhoDAO = fabricaDAO.getPostgresMotoristaAparelhoDAO();
			List<MotoristaAparelho> lista = motoristaAparelhoDAO.obterAparelhosMotoristas(ativo, em);
			emUtil.commitTransaction(transaction);
			return lista;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter aparelhos.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
