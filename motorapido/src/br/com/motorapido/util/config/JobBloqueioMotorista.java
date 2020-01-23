package br.com.motorapido.util.config;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.mbean.SimpleController;
import br.com.motorapido.util.ExcecoesUtil;


public class JobBloqueioMotorista implements Job {

	public JobBloqueioMotorista() {

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			
			MotoristaBO.getInstance().desbloquearMotoristaRotina();
			
			
		} catch (Exception ex) {
			ExcecoesUtil.TratarExcecao(ex);
		}
	}
}
