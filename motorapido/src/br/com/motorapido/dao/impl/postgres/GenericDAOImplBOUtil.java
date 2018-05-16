package br.com.motorapido.dao.impl.postgres;


public class GenericDAOImplBOUtil {	
	
	public static PostgresGenericDAOImplBO getGenericDAOImplBO(){
		PostgresFabricaDAOImpl fabrica = PostgresFabricaDAOImpl.getInstance();
		return fabrica.getGenericDAOImplBO();
	}
}
