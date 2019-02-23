package br.com.motorapido.bo;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IUsuarioDAO;
import br.com.motorapido.entity.Usuario;

public class UsuarioBO extends MotoRapidoBO {

	private static UsuarioBO instance;

	private UsuarioBO() {

	}

	public static UsuarioBO getInstance() {
		if (instance == null)
			instance = new UsuarioBO();

		return instance;
	}


	
	public Usuario salvarUsuario(Usuario usuario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IUsuarioDAO usuarioDAO = fabricaDAO.getPostgresUsuarioDAO();
			usuario.setDataCriacao(new Date());
			usuario.setAtivo("S");

			usuario = usuarioDAO.save(usuario, em);			

			emUtil.commitTransaction(transaction);
			return usuario;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar usuario.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
