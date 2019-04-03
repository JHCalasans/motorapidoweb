package br.com.motorapido.dao;



public abstract class FabricaDAO {

	private static FabricaDAO instance;

	
	public abstract IFuncionarioDAO getPostgresFuncionarioDAO();
	
	public abstract IPerfilDAO getPostgresPerfilDAO();
	
	public abstract IFuncionarioPerfilDAO getPostgresFuncionarioPerfilDAO();
	
	public abstract IMotoristaDAO getPostgresMotoristaDAO();
	
	public abstract IParametroDAO getPostgresParametroDAO();
	
	public abstract IValorParametroDAO getPostgresValorParametroDAO();
	
	public abstract ILogErroDAO getPostgresLogErroDAO();
	
	public abstract IMenuDAO getPostgresMenuDAO();
	
	public abstract IPerfilMenuDAO getPostgresPerfilMenuDAO();
	
	public abstract IAreaDAO getPostgresAreaDAO();
	
	public abstract ICoordenadasAreaDAO getPostgresCoordenadasAreaDAO();
	
	public abstract IModeloDAO getPostgresModeloDAO();
	
	public abstract IVeiculoDAO getPostgresVeiculoDAO();
	
	public abstract IFabricanteDAO getPostgresFabricanteDAO();
	
	public abstract ITipoVeiculoDAO getPostgresTipoVeiculoDAO();
	
	public abstract IMotoristaPosicaoAreaDAO getPostgresMotoristaPosicaoAreaDAO();
	
	public abstract IChamadaDAO getPostgresChamadaDAO();
	
	public abstract IChamadaVeiculoDAO getPostgresChamadaVeiculoDAO();
	
	public abstract ISituacaoChamadaDAO getPostgresSituacaoChamadaDAO();

	public abstract IClienteDAO getPostgresClienteDAO();
	
	public abstract ILocalDAO getPostgresLocalDAO();
	
	public abstract IEnderecoClienteDAO getPostgresEnderecoClienteDAO();
	
	public abstract IBloqueioMotoristaDAO getPostgresBloqueioMotoristaDAO();
	
	public abstract IMensagemFuncionarioDAO getPostgresMensagemFuncionarioDAO();
	
	public abstract IMensagemFuncionarioMotoristaDAO getPostgresMensagemFuncionarioMotoristaDAO();
	
	public abstract IMensagemMotoristaFuncionarioDAO getPostgresMensagemMotoristaFuncionarioDAO();
	
	public abstract IMotoristaAparelhoDAO getPostgresMotoristaAparelhoDAO();
	
	public abstract ICaracteristicaDAO getPostgresCaracteristicaDAO();
	
	public abstract ICaracteristicaMotoristaDAO getPostgresCaracteristicaMotoristaDAO();
	
	public abstract IRestricaoClienteMotoristaDAO getPostgresRestricaoClienteMotoristaDAO();
	
	public abstract ITipoPunicaoDAO getPostgresTipoPunicaoDAO();
	
	public abstract IBairroDAO getPostgresBairroDAO();
	
	public abstract ICidadeDAO getPostgresCidadeDAO();
	
	public abstract ILogradouroDAO getPostgresLogradouroDAO();
	
	public abstract IUsuarioDAO getPostgresUsuarioDAO();
	
	public abstract IUsuarioAparelhoDAO getPostgresUsuarioAparelhoDAO();
	
	public abstract IBinarioMotoristaDAO getPostgresBinarioMotoristaDAO();
	
	public abstract IBinarioFuncionarioDAO getPostgresBinarioFuncionarioDAO();
	
	public abstract IBinarioVeiculoDAO getPostgresBinarioVeiculoDAO();
	
	public abstract IBinarioUsuarioDAO getPostgresBinarioUsuarioDAO();


	/**
	 * Para utilizar de fato a classe {@link FabricaDAO}, uma implementação
	 * sua deve se registrar como sendo a instância principal usando
	 * {@code instancia.registerAsMain()}.
	 */
	protected final void registerAsMain() {
		FabricaDAO.instance = this;
	}

	public static FabricaDAO getFabricaDAO() {
		return instance;
	}

}
