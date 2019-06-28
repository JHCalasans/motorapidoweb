package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.CriterioOrdenacao;
import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.motorapido.entity.Area;
import br.com.motorapido.entity.MotoristaPosicaoArea;

public interface IMotoristaPosicaoAreaDAO extends GenericDAO<MotoristaPosicaoArea, Integer> {
	
	static CriterioOrdenacao BY_POS_ASC = CriterioOrdenacao.asc("posicao");
	
	public Integer obterMaiorPosicaoArea(Integer codArea, EntityManager em);
	
	public List<MotoristaPosicaoArea> obterMotoristasPorArea(Area area, EntityManager em) throws ExcecaoBancoConexao, ExcecaoBanco;

	public List<MotoristaPosicaoArea> obterMotoristaAtivoCodigo(Integer codigo, EntityManager em) throws ExcecaoBancoConexao, ExcecaoBanco;

	
	public List<MotoristaPosicaoArea> obterMotoristasChamadaPorArea(Area area,Long codChamada ,EntityManager em) throws ExcecaoBancoConexao, ExcecaoBanco;

}
