package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.ITipoVeiculoDAO;
import br.com.motorapido.entity.TipoVeiculo;

@PersistenceContext(unitName = "postgresPU")
public class PostgresTipoVeiculoDAOImpl extends GenericDAOImpl<TipoVeiculo, Integer>
implements ITipoVeiculoDAO{

}
