package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.IChamadaVeiculoDAO;
import br.com.motorapido.entity.ChamadaVeiculo;


@PersistenceContext(unitName = "postgresPU")
public class PostgresChamadaVeiculoDAOImpl extends GenericDAOImpl<ChamadaVeiculo, Integer>
implements IChamadaVeiculoDAO{
	
	PostgresChamadaVeiculoDAOImpl() {
		super();
	}

}
