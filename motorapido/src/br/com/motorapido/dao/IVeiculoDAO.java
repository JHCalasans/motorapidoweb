package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.Veiculo;

public interface IVeiculoDAO extends GenericDAO<Veiculo, Integer>{
	
	static CriterioOrdenacao BY_DATA_CADASTRO_ASC = CriterioOrdenacao.asc("dataCadastro");
	
	public List<Veiculo> obterVeiculosPorMotorista(Integer codMotorista, EntityManager em) throws ExcecaoBanco;
	
	public Veiculo obterVeiculoPorPlaca(String placa, EntityManager em) throws ExcecaoBanco;
	
	public Veiculo obterVeiculoPorChassi(String chassi, EntityManager em) throws ExcecaoBanco;

}
