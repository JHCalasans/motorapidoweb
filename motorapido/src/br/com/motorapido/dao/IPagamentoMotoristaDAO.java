package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.entity.PagamentoMotorista;

public interface IPagamentoMotoristaDAO extends GenericDAO<PagamentoMotorista, Long> {
	
	public List<PagamentoMotorista> obterPagamentosMotorista(Integer codMotorista, EntityManager em) throws ExcecaoBanco,ExcecaoNegocio;

}
