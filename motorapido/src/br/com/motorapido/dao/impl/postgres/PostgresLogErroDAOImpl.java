package br.com.motorapido.dao.impl.postgres;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.minhaLib.util.EntityManagerUtil;
import br.com.minhaLib.util.excecao.Log;
import br.com.motorapido.dao.ILogErroDAO;
import br.com.motorapido.entity.Cliente;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.entity.LogErro;

@PersistenceContext(unitName = "postgresPU")
class PostgresLogErroDAOImpl extends GenericDAOImpl<LogErro, Long> implements ILogErroDAO, Log {

	private static final int MAX_SIZE = 32 * 1024 - 1;

	protected PostgresLogErroDAOImpl() {
		setOrdenacaoPadrao(new CriterioOrdenacao[] { BY_DTHORAERRO_ASC });
	}

	@Override
	public void logarErro(String erro) {
		EntityManagerUtil emUtil = new EntityManagerUtil();
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			FacesContext facesContext = FacesContext.getCurrentInstance();

			if (facesContext != null) {
				Funcionario funcionarioLogado = null;
				LogErro logErro = new LogErro();
				if (facesContext.getExternalContext().getSessionMap().containsKey("motoRapido.funcionario")) {
					funcionarioLogado = (Funcionario) facesContext.getExternalContext().getSessionMap()
							.get("motoRapido.funcionario");
					logErro.setCodFuncionario(funcionarioLogado.getCodigo());
				} else if (facesContext.getExternalContext().getSessionMap().containsKey("motoRapido.cliente")) {
					Cliente clienteLogado = (Cliente) facesContext.getExternalContext().getSessionMap()
							.get("motoRapido.cliente");
					logErro.setCodCliente(clienteLogado.getCodigo());
				}
				logErro.setDataHoraErro(new Date());
				logErro.setErro(erro);

				this.save(logErro, em);
			}
			emUtil.commitTransaction(transaction);
		} catch (Exception ex) {
			emUtil.rollbackTransaction(transaction);
			ex.printStackTrace();
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

	@Override
	public void logarErro(final Exception ex) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		ex.printStackTrace(printWriter);
		String res = result.toString();
		if (res.length() > MAX_SIZE) {
			res = res.substring(0, MAX_SIZE);
		}
		this.logarErro(res);
	}

	@Override
	public List<LogErro> obterPorData(Date dtInicial, Date dtFinal, EntityManager em)
			throws ExcecaoBancoConexao, ExcecaoBanco {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dtInicial", dtInicial);
		params.put("dtFinal", dtFinal);
		return findByNamedQueryAndNamedParams("LogErro.obterPorData", params, em);

	}

}
