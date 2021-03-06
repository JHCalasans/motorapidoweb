package br.com.motorapido.util.config;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.mbean.SimpleController;
import br.com.motorapido.util.ExcecoesUtil;


public class JobProject implements Job {

	public JobProject() {

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			
			MotoristaBO.getInstance().desbloquearMotoristaRotina();
			
			//a cada 5 min atualiza a lista de chamadas
			SimpleController.iniciarListaChamadas();
		} catch (Exception ex) {
			ExcecoesUtil.TratarExcecao(ex);
		}
	}
}
