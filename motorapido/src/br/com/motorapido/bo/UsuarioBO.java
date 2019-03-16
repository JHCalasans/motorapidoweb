
package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IChamadaDAO;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.dao.IMotoristaDAO;
import br.com.motorapido.dao.IUsuarioAparelhoDAO;
import br.com.motorapido.dao.IUsuarioDAO;
import br.com.motorapido.entity.Chamada;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaAparelho;
import br.com.motorapido.entity.Usuario;
import br.com.motorapido.entity.UsuarioAparelho;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.util.FuncoesUtil;
import br.com.motorapido.util.JWTUtil;
import br.com.motorapido.util.ws.retornos.RetornoHistoricoUsuario;

public class UsuarioBO extends MotoRapidoBO {

	private static UsuarioBO instance;

	private UsuarioBO() {

	}

	public static UsuarioBO getInstance() {
		if (instance == null)
			instance = new UsuarioBO();

		return instance;
	}

	public Usuario login(Usuario usuario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();

		try {
			
			transaction.begin();
			
			IUsuarioDAO usuarioDAO = fabricaDAO.getPostgresUsuarioDAO();
			Usuario usuTemp = new Usuario();
			usuTemp.setEmail(usuario.getEmail());
			List<Usuario> listaTemp = usuarioDAO.findByExample(usuTemp, em);
			if (listaTemp != null && listaTemp.size() > 0) {				
				String idPush = usuario.getIdPush();				
				usuario.setCodigo(null);
				usuario.setDataCriacao(null);
				usuario.setDataDesativacao(null);
				//List<Usuario> lista = usuarioDAO.findByExample(usuario, em);				
				//verifica se existe usuário com a senha
				if (listaTemp.get(0).getSenha().equals(usuario.getSenha())) {
					IUsuarioAparelhoDAO usuarioAparelhoDAO = fabricaDAO.getPostgresUsuarioAparelhoDAO();
					usuario = listaTemp.get(0);
					List<UsuarioAparelho> listaAparelhos = usuarioAparelhoDAO. obterAparelhoUsuario(idPush, usuario.getCodigo(), em);
					//Verifico se esse aparelho já tinha sido usado por este usuário
					if(listaAparelhos != null && listaAparelhos.size() > 0){
						listaAparelhos.get(0).setAtivo("S");
						listaAparelhos.get(0).setDataDesativacao(null);
						usuarioAparelhoDAO.save(listaAparelhos.get(0), em);
					}//No caso de não ter sido usado ainda cria novo registro
					else{
						UsuarioAparelho usuarioAparelho = new UsuarioAparelho();
						usuarioAparelho.setAtivo("S");
						usuarioAparelho.setDataCriacao(new Date());
						usuarioAparelho.setIdPush(idPush);
						usuarioAparelho.setUsuario(usuario);
						usuarioAparelhoDAO.save(usuarioAparelho, em);
					}		
					
					String chave = FuncoesUtil.getParam(ParametroEnum.CHAVE_SEGURANCA.getCodigo(), em);
					usuario.setChaveServicos(JWTUtil.create(usuario.getEmail(), chave));
					
					emUtil.commitTransaction(transaction);
				} else
					throw new ExcecaoNegocio("Senha incorreta");

			}else
				throw new ExcecaoNegocio("Não existe usuário cadastrado com este email");

			return usuario;
		} catch (ExcecaoNegocio e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio(e.getMessage());
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar efetuar login.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	/*public void logoff(String codUsuario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IUsuarioDAO usuarioDAO = fabricaDAO.getPostgresUsuarioDAO();
			IUsuarioAparelhoDAO usuarioAparelhoDAO = fabricaDAO.getPostgresUsuarioAparelhoDAO();
			UsuarioAparelho usuarioAparelho = new UsuarioAparelho();
			usuarioDAO.findBy
			usuarioAparelho.setCodUsuario(codUsuario);
			motoristaAparelho.setIdPush(motorista.getIdPush());
			List<MotoristaAparelho> lista = motoristaAparelhoDAO.findByExample(motoristaAparelho, em);
			motoristaAparelho = lista.get(0);
			motoristaAparelho.setAtivo("N");
			motoristaAparelhoDAO.save(motoristaAparelho, em);

			motorista = motoristaDAO.findById(motorista.getCodigo(), em);

			motorista.setDisponivel("N");

			motoristaDAO.save(motorista, em);

			emUtil.commitTransaction(transaction);
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar realizar logoff.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}*/

	public Usuario salvarUsuario(Usuario usuario) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IUsuarioDAO usuarioDAO = fabricaDAO.getPostgresUsuarioDAO();
			usuario.setDataCriacao(new Date());
			usuario.setAtivo("S");
			Usuario usuTemp = new Usuario();
			usuTemp.setEmail(usuario.getEmail());
			
			List<Usuario> listaTemp = usuarioDAO.findByExample(usuTemp, em);
			if (listaTemp != null && listaTemp.size() > 0) 
				throw new ExcecaoNegocio("Já existe usuário cadastrado com este email");	
			
			IUsuarioAparelhoDAO usuarioAparelhoDAO = fabricaDAO.getPostgresUsuarioAparelhoDAO();
			UsuarioAparelho usuarioAparelho = new UsuarioAparelho();
			usuarioAparelho.setAtivo("S");
			usuarioAparelho.setDataCriacao(new Date());
			usuarioAparelho.setIdPush(usuario.getIdPush());
			
			
			usuario = usuarioDAO.save(usuario, em);
			
			usuarioAparelho.setUsuario(usuario);
			
		//	usuarioAparelhoDAO.save(usuarioAparelho, em);

			emUtil.commitTransaction(transaction);
			return usuario;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar gravar usuario.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}
	
	public List<RetornoHistoricoUsuario> obterHistoricoUsuario(Integer codigo) throws ExcecaoNegocio {
		EntityManager em = emUtil.getEntityManager();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			IChamadaDAO chamadaDAO = fabricaDAO.getPostgresChamadaDAO();
			List<Chamada> lista = chamadaDAO.obterHistoricoUsuario(codigo, em);
			List<RetornoHistoricoUsuario> retorno = new ArrayList<RetornoHistoricoUsuario>();
			for(Chamada chamada : lista){
				RetornoHistoricoUsuario retHistorico = new RetornoHistoricoUsuario();
				retHistorico.setDataChamada(chamada.getDataInicioCorrida());
				retHistorico.setSituacao(chamada.getSituacaoChamada().getDescricao());
				retHistorico.setDestino(chamada.getLogradouroDestino() + " - " + chamada.getBairroDestino());
				retHistorico.setOrigem(chamada.getLogradouroOrigem() + " - " + chamada.getBairroOrigem());
				retHistorico.setValor(chamada.getValorFinal());
				retorno.add(retHistorico);
			}
			emUtil.commitTransaction(transaction);
			return retorno;
		} catch (Exception e) {
			emUtil.rollbackTransaction(transaction);
			throw new ExcecaoNegocio("Falha ao tentar obter histórico do usuário.", e);
		} finally {
			emUtil.closeEntityManager(em);
		}
	}

}
