package br.com.motorapido.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.minhaLib.util.FacesUtil;
import br.com.minhaLib.util.excecao.MsgUtil;
import br.com.motorapido.dao.FabricaDAO;
import br.com.motorapido.entity.Funcionario;



public class ExcecoesUtil {

	private static final int MAX_SIZE = 32 * 1024 - 1;
	private static final Logger log = Logger.getLogger(ExcecoesUtil.class);
	private static final String GENERAL_ERROR = "Sistema indisponível";

	public static void TratarExcecao(Exception ex) {
		log.error("Erro", ex);
		
		try {
			Throwable cause = ExceptionUtils.getRootCause(ex);
			
			if (cause == null)
				cause = ex;
			if (cause instanceof ExcecaoNegocio) {
				logarErro(ex);
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, cause.getMessage(),
						cause.getMessage());
			} else if (ex instanceof ExcecaoNegocio) {
				logarErro(ex);
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ex.getMessage());
			} else {
				logarErro(ex);
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, GENERAL_ERROR, GENERAL_ERROR);
			}

		} catch (Exception e) {
			logarErro(ex);
		}
	}
	
	

	public static void logarErro(final Exception ex) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		ex.printStackTrace(printWriter);
		ex.printStackTrace();
		String res = result.toString();
		if (res.length() > MAX_SIZE) {
			res = res.substring(0, MAX_SIZE);
		}
		FabricaDAO.getFabricaDAO().getPostgresLogErroDAO().logarErro(res);
	}
	
	public static void logarErroMotorista(final Exception ex, final Integer codMotorista, final String servico) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		ex.printStackTrace(printWriter);
		ex.printStackTrace();
		String res = result.toString();
		if (res.length() > MAX_SIZE) {
			res = res.substring(0, MAX_SIZE);
		}
		FabricaDAO.getFabricaDAO().getPostgresLogErroMotoristaDAO().logarErroMotorista(res, codMotorista, servico);
	}
}
