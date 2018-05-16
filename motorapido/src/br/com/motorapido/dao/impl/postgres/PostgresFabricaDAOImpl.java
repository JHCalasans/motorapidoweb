package br.com.motorapido.dao.impl.postgres;

import org.apache.log4j.Logger;

import br.com.motorapido.dao.FabricaDAO;
import br.com.motorapido.dao.IParametroDAO;
import br.com.motorapido.dao.IValorParametroDAO;

final class PostgresFabricaDAOImpl extends FabricaDAO {

	private static Logger log = Logger.getLogger(PostgresFabricaDAOImpl.class);
	static PostgresFabricaDAOImpl instance;

	static {
		log.info("Instanciando e registrando como fábrica principal.");
		instance = new PostgresFabricaDAOImpl();
		instance.registerAsMain();
	}

	private static PostgresGenericDAOImplBO postgresGenericDAOImplBO;
	private static PostgresFuncionarioDAOImpl postgresFuncionarioDAOImpl;
	private static PostgresPerfilDAOImpl postgresPerfilDAOImpl;
	private static PostgresFuncionarioPerfilDAOImpl postgresFuncionarioPerfilDAOImpl;
	private static PostgresMotoristaDAOImpl postgresMotoristaDAOImpl;
	private static PostgresParametroDAOImpl postgresParametroDAOImpl;
	private static PostgresValorParametroDAOImpl postgresValorParametroDAOImpl;
	private static PostgresLogErroDAOImpl postgresLogErroDAOImpl;
	private static PostgresMenuDAOImpl postgresMenuDAOImpl;
	private static PostgresPerfilMenuDAOImpl postgresPerfilMenuDAOImpl;
	private static PostgresAreaDAOImpl postgresAreaDAOImpl;
	private static PostgresCoordenadasAreaDAOImpl postgresCoordenadasAreaDAOImpl;
	private static PostgresModeloDAOImpl postgresModeloDAOImpl;
	private static PostgresVeiculoDAOImpl postgresVeiculoDAOImpl;
	private static PostgresFabricanteDAOImpl postgresFabricanteDAOImpl;
	private static PostgresTipoVeiculoDAOImpl postgresTipoVeiculoDAOImpl;
	private static PostgresMotoristaPosicaoAreaDAOImpl postgresMotoristaPosicaoAreaDAOImpl;
	private static PostgresChamadaDAOImpl postgresChamadaDAOImpl;
	private static PostgresChamadaVeiculoDAOImpl postgresChamadaVeiculoDAOImpl;
	private static PostgresSituacaoChamadaDAOImpl postgresSituacaoChamadaDAOImpl;
	private static PostgresClienteDAOImpl postgresClienteDAOImpl;
	private static PostgresEnderecoClienteDAOImpl postgresEnderecoClienteDAOImpl;
	private static PostgresLocalDAOImpl postgresLocalDAOImpl;
	private static PostgresBloqueioMotoristaDAOImpl postgresBloqueioMotoristaDAOImpl;

	private PostgresFabricaDAOImpl() {

	}

	public static void registerMeAsMain() {
		getInstance().registerAsMain();
	}

	protected static PostgresFabricaDAOImpl getInstance() {
		return instance;
	}

	protected static PostgresFabricaDAOImpl getNewInstance() {
		return new PostgresFabricaDAOImpl();
	}

	public PostgresGenericDAOImplBO getGenericDAOImplBO() {
		if (postgresGenericDAOImplBO == null) {
			postgresGenericDAOImplBO = new PostgresGenericDAOImplBO();
		}
		return postgresGenericDAOImplBO;
	}
	
	@Override
	public PostgresFuncionarioDAOImpl getPostgresFuncionarioDAO() {
		if (postgresFuncionarioDAOImpl == null) {
			postgresFuncionarioDAOImpl = new PostgresFuncionarioDAOImpl();
		}
		return postgresFuncionarioDAOImpl;
	}
	
	@Override
	public PostgresPerfilDAOImpl getPostgresPerfilDAO() {
		if (postgresPerfilDAOImpl == null) {
			postgresPerfilDAOImpl = new PostgresPerfilDAOImpl();
		}
		return postgresPerfilDAOImpl;
	}
	
	@Override
	public PostgresFuncionarioPerfilDAOImpl getPostgresFuncionarioPerfilDAO() {
		if (postgresFuncionarioPerfilDAOImpl == null) {
			postgresFuncionarioPerfilDAOImpl = new PostgresFuncionarioPerfilDAOImpl();
		}
		return postgresFuncionarioPerfilDAOImpl;
	}
	
	@Override
	public PostgresMotoristaDAOImpl getPostgresMotoristaDAO() {
		if (postgresMotoristaDAOImpl == null) {
			postgresMotoristaDAOImpl = new PostgresMotoristaDAOImpl();
		}
		return postgresMotoristaDAOImpl;
	}


	@Override
	public IParametroDAO getPostgresParametroDAO() {
		if (postgresParametroDAOImpl == null) {
			postgresParametroDAOImpl = new PostgresParametroDAOImpl();
		}
		return postgresParametroDAOImpl;
	}

	@Override
	public IValorParametroDAO getPostgresValorParametroDAO() {
		if (postgresValorParametroDAOImpl == null) {
			postgresValorParametroDAOImpl = new PostgresValorParametroDAOImpl();
		}
		return postgresValorParametroDAOImpl;
	}
	
	@Override
	public PostgresLogErroDAOImpl getPostgresLogErroDAO() {
		if (postgresLogErroDAOImpl == null) {
			postgresLogErroDAOImpl = new PostgresLogErroDAOImpl();
		}
		return postgresLogErroDAOImpl;
	}
	
	@Override
	public PostgresMenuDAOImpl getPostgresMenuDAO() {
		if (postgresMenuDAOImpl == null) {
			postgresMenuDAOImpl = new PostgresMenuDAOImpl();
		}
		return postgresMenuDAOImpl;
	}
	
	@Override
	public PostgresPerfilMenuDAOImpl getPostgresPerfilMenuDAO() {
		if (postgresPerfilMenuDAOImpl == null) {
			postgresPerfilMenuDAOImpl = new PostgresPerfilMenuDAOImpl();
		}
		return postgresPerfilMenuDAOImpl;
	}
	
	@Override
	public PostgresAreaDAOImpl getPostgresAreaDAO() {
		if (postgresAreaDAOImpl == null) {
			postgresAreaDAOImpl = new PostgresAreaDAOImpl();
		}
		return postgresAreaDAOImpl;
	}
	
	@Override
	public PostgresCoordenadasAreaDAOImpl getPostgresCoordenadasAreaDAO() {
		if (postgresCoordenadasAreaDAOImpl == null) {
			postgresCoordenadasAreaDAOImpl = new PostgresCoordenadasAreaDAOImpl();
		}
		return postgresCoordenadasAreaDAOImpl;
	}
	
	@Override
	public PostgresModeloDAOImpl getPostgresModeloDAO() {
		if (postgresModeloDAOImpl == null) {
			postgresModeloDAOImpl = new PostgresModeloDAOImpl();
		}
		return postgresModeloDAOImpl;
	}
	
	@Override
	public PostgresVeiculoDAOImpl getPostgresVeiculoDAO() {
		if (postgresVeiculoDAOImpl == null) {
			postgresVeiculoDAOImpl = new PostgresVeiculoDAOImpl();
		}
		return postgresVeiculoDAOImpl;
	}
	
	@Override
	public PostgresFabricanteDAOImpl getPostgresFabricanteDAO() {
		if (postgresFabricanteDAOImpl == null) {
			postgresFabricanteDAOImpl = new PostgresFabricanteDAOImpl();
		}
		return postgresFabricanteDAOImpl;
	}
	
	@Override
	public PostgresTipoVeiculoDAOImpl getPostgresTipoVeiculoDAO() {
		if (postgresTipoVeiculoDAOImpl == null) {
			postgresTipoVeiculoDAOImpl = new PostgresTipoVeiculoDAOImpl();
		}
		return postgresTipoVeiculoDAOImpl;
	}
	

	@Override
	public PostgresMotoristaPosicaoAreaDAOImpl getPostgresMotoristaPosicaoAreaDAO() {
		if (postgresMotoristaPosicaoAreaDAOImpl == null) {
			postgresMotoristaPosicaoAreaDAOImpl = new PostgresMotoristaPosicaoAreaDAOImpl();
		}
		return postgresMotoristaPosicaoAreaDAOImpl;
	}

	
	@Override
	public PostgresChamadaDAOImpl getPostgresChamadaDAO() {
		if (postgresChamadaDAOImpl == null) {
			postgresChamadaDAOImpl = new PostgresChamadaDAOImpl();
		}
		return postgresChamadaDAOImpl;
	}
	
	@Override
	public PostgresChamadaVeiculoDAOImpl getPostgresChamadaVeiculoDAO() {
		if (postgresChamadaVeiculoDAOImpl == null) {
			postgresChamadaVeiculoDAOImpl = new PostgresChamadaVeiculoDAOImpl();
		}
		return postgresChamadaVeiculoDAOImpl;
	}
	
	@Override
	public PostgresSituacaoChamadaDAOImpl getPostgresSituacaoChamadaDAO() {
		if (postgresSituacaoChamadaDAOImpl == null) {
			postgresSituacaoChamadaDAOImpl = new PostgresSituacaoChamadaDAOImpl();
		}
		return postgresSituacaoChamadaDAOImpl;
	}

	@Override
	public PostgresClienteDAOImpl getPostgresClienteDAO() {
		if (postgresClienteDAOImpl == null) {
			postgresClienteDAOImpl = new PostgresClienteDAOImpl();
		}
		return postgresClienteDAOImpl;
	}
	
	@Override
	public PostgresEnderecoClienteDAOImpl getPostgresEnderecoClienteDAO() {
		if (postgresEnderecoClienteDAOImpl == null) {
			postgresEnderecoClienteDAOImpl = new PostgresEnderecoClienteDAOImpl();
		}
		return postgresEnderecoClienteDAOImpl;
	}
	
	@Override
	public PostgresLocalDAOImpl getPostgresLocalDAO() {
		if (postgresLocalDAOImpl == null) {
			postgresLocalDAOImpl = new PostgresLocalDAOImpl();
		}
		return postgresLocalDAOImpl;
	}
	
	@Override
	public PostgresBloqueioMotoristaDAOImpl getPostgresBloqueioMotoristaDAO() {
		if (postgresBloqueioMotoristaDAOImpl == null) {
			postgresBloqueioMotoristaDAOImpl = new PostgresBloqueioMotoristaDAOImpl();
		}
		return postgresBloqueioMotoristaDAOImpl;
	}
	
	
	
	
}
