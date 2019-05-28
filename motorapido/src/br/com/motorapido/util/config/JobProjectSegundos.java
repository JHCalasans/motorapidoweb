package br.com.motorapido.util.config;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.motorapido.bo.ChamadaBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.entity.Chamada;
import br.com.motorapido.enums.SituacaoChamadaEnum;
import br.com.motorapido.mbean.SimpleController;
import br.com.motorapido.util.ExcecoesUtil;


public class JobProjectSegundos implements Job {

	public JobProjectSegundos() {

	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			
			for(Chamada chamada : SimpleController.getListaChamadasEmEspera()){
				if(chamada.getSituacaoChamada().getCodigo().equals(SituacaoChamadaEnum.PENDENTE.getCodigo()))
					ChamadaBO.getInstance().enviarMsgChamadaMotorista(chamada);
			}
			
			
			
		} catch (Exception ex) {
			ExcecoesUtil.TratarExcecao(ex);
		}
	}
}
