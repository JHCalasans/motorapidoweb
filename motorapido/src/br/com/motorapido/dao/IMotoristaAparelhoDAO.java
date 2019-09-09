package br.com.motorapido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.minhaLib.dao.GenericDAO;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.entity.MotoristaAparelho;

public interface IMotoristaAparelhoDAO extends GenericDAO<MotoristaAparelho, Integer> {


	public List<MotoristaAparelho> obterOutrosAparelhosMotorista(Integer codMotorista, String idPush ,EntityManager em) throws ExcecaoBanco;
	
	public List<MotoristaAparelho> obterAparelhosMotoristas(String situacao,EntityManager em) throws ExcecaoBanco;
	
	public List<MotoristaAparelho> obterAparelhoPorIdPush(String idPush,EntityManager em) throws ExcecaoBanco;
	
	public List<MotoristaAparelho> obterAparelhoPorIdAparelho(String idAparelho,EntityManager em) throws ExcecaoBanco;
	
	public List<MotoristaAparelho> obterAparelhoPorMotorista(Integer codMotorista,EntityManager em) throws ExcecaoBanco;
}
