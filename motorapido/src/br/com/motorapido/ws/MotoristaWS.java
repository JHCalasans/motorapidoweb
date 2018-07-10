package br.com.motorapido.ws;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.minhaLib.util.excecao.MsgUtil;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.bo.MotoristaPosicaoAreaBO;
import br.com.motorapido.entity.MensagemFuncionarioMotorista;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.mbean.SimpleController;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.ws.params.VerificaPosicaoParam;
import br.com.motorapido.util.ws.retornos.RetornoVerificaPosicao;



@Path("/motorista")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MotoristaWS  {

	String summary = "Nova Mensagem";
	String detail = "Teste";
	String CHANELL = "/notify";

	@POST
	@Path("/login")
	public Response login(Motorista motorista) {
		try {
			
			EventBus eventBus = EventBusFactory.getDefault().eventBus();
			eventBus.publish(CHANELL, new FacesMessage(StringEscapeUtils.escapeHtml3(summary),
					StringEscapeUtils.escapeHtml3(detail)));
			SimpleController.setUltimoMotMsg(1);
			//motorista = MotoristaBO.getInstance().login(motorista);
			return Response.status(Status.OK).entity("oi").build();
	/*	} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();*/
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar efetuar login").build();
		}
	}
	

	@GET
	@Path("/alterarDisponivel")
	public Response alterarDisponivel(@QueryParam("codMotorista") Integer codMotorista) {
		try {
			MotoristaBO.getInstance().alterarDisponivel(codMotorista);
			return Response.status(Status.OK).build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar alterar disponibilidade").build();
		}
	}
	
	@POST
	@Path("/verificarPosicao")
	public Response verificarPosicao(VerificaPosicaoParam param) {
		try {
			MotoristaPosicaoArea motoPosicao = MotoristaPosicaoAreaBO.getInstance().obterPosicaoMotoristaArea(param);
			RetornoVerificaPosicao retorno = new RetornoVerificaPosicao();
			retorno.setAreaAtual(motoPosicao.getArea());
			retorno.setPosicaoNaArea(motoPosicao.getPosicao());			
			return Response.status(Status.OK).entity(retorno).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/aceitarChamada")
	public Response aceitarChamada() {
		try {
			System.out.println("aceitarChamada");
			return Response.status(Status.OK).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar aceitar chamada").build();
		}
	}
	
	@POST
	@Path("/buscarInformacoesBase")
	public Response buscarInformacoesBase() {
		try {
			System.out.println("Chamou atualização");
			return Response.status(Status.OK).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar obter informações da base").build();
		}
	}
	
	
	
	
	
	@POST
	@Path("/atualizarMensagem")
	public Response atualizarMensagem(MensagemFuncionarioMotorista mensagem) {
		try {
		
			return Response.status(Status.OK).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar obter informações da base").build();
		}
	}



}
