package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IBinarioVeiculoDAO;
import br.com.motorapido.entity.BinarioVeiculo;

@PersistenceContext(unitName = "postgresPU")
public class PostgresBinarioVeiculoDAOImpl extends GenericDAOImpl<BinarioVeiculo, Long>
implements IBinarioVeiculoDAO{

	

}
