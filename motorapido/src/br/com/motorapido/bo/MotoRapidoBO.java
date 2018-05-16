package br.com.motorapido.bo;

import org.apache.log4j.Logger;

import br.com.minhaLib.bo.BaseBO;
import br.com.minhaLib.util.EntityManagerUtil;
import br.com.motorapido.dao.FabricaDAO;

public class MotoRapidoBO extends BaseBO{
	
	protected FabricaDAO fabricaDAO;
	protected EntityManagerUtil emUtil;
	private static Logger log = Logger.getLogger(BaseBO.class);

	public EntityManagerUtil getEmUtil() {
		return emUtil;
	}

	public void setEmUtil(EntityManagerUtil emUtil) {
		this.emUtil = emUtil;
	}

	public MotoRapidoBO() {
		super();
		fabricaDAO = FabricaDAO.getFabricaDAO();
		emUtil = new EntityManagerUtil("postgresPU");
	}

}
