package br.com.motorapido.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.MensagemMotoristaFuncionarioBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.bo.MotoristaPosicaoAreaBO;
import br.com.motorapido.entity.MensagemFuncionarioMotorista;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.ws.params.MensagemParam;
import br.com.motorapido.util.ws.params.VerificaPosicaoParam;
import br.com.motorapido.util.ws.retornos.RetornoVerificaPosicao;



@Path("/motorista")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MotoristaWS  {



	@POST
	@Path("/login")
	public Response login(Motorista motorista) {
		try {
	
			motorista = MotoristaBO.getInstance().login(motorista);
			return Response.status(Status.OK).entity("oi").build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
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

	
	@POST
	@Path("/enviarMensagem")
	public Response enviarMensagem(MensagemParam param) {
		try {
			
			MensagemMotoristaFuncionarioBO.getInstance().enviarMensagemDoMotorista(param);
			return Response.status(Status.OK).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar enviar mensagem").build();
		}
	}



}
