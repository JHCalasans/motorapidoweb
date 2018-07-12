package br.com.motorapido.dao.impl.postgres;

import javax.persistence.PersistenceContext;

import br.com.minhaLib.dao.impl.GenericDAOImpl;
import br.com.motorapido.dao.ICaracteristicaMotoristaDAO;
import br.com.motorapido.entity.CaracteristicaMotorista;

@PersistenceContext(unitName = "postgresPU")
public class PostgresCaracteristicaMotoristaDAOImpl extends GenericDAOImpl<CaracteristicaMotorista, Integer>
implements ICaracteristicaMotoristaDAO{

}
