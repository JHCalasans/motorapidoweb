package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.entity.ChamadaVeiculo;

public interface IChamadaVeiculoDAO extends GenericDAO<ChamadaVeiculo, Long> {

	
	public List<ChamadaVeiculo> obterHistoricoMotorista(Integer codMotorista, EntityManager em) throws ExcecaoBanco,ExcecaoNegocio;

	public List<ChamadaVeiculo> obterChamadaAtiva(Long codChamada, EntityManager em) throws ExcecaoBanco,ExcecaoNegocio;

	
	public ChamadaVeiculo obterChamadaVeiculoPorCodigo(Long codChamadavEICULO, EntityManager em) throws ExcecaoBanco,ExcecaoNegocio;
	
}
