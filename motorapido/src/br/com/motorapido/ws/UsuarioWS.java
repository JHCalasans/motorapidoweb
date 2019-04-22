package br.com.motorapido.ws;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.ChamadaBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.bo.UsuarioBO;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.Usuario;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.ws.params.NovaChamadaParam;
import br.com.motorapido.util.ws.retornos.RetornoChamadaUsuario;
import br.com.motorapido.util.ws.retornos.RetornoHistoricoUsuario;



@Path("/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioWS  {



	@POST
	@Path("/login")
	public Response login(Usuario usuario) {
		try {
	
			usuario = UsuarioBO.getInstance().login(usuario);
			return Response.status(Status.OK).entity(usuario).build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar efetuar login").build();
		}
	}
	
	
	@POST
	@Path("/cadastrar")
	public Response cadastrar(Usuario usuario) {
		try {
			usuario =  UsuarioBO.getInstance().salvarUsuario(usuario);
			return Response.status(Status.OK).entity(usuario).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/buscarHistorico")
	public Response buscarHistorico(String codUsuario) {
		try {
			
		    List<RetornoHistoricoUsuario> chamadas = UsuarioBO.getInstance().obterHistoricoUsuario(Integer.parseInt(codUsuario));		   
		
			GenericEntity<List<RetornoHistoricoUsuario>> entidade = new GenericEntity<List<RetornoHistoricoUsuario>>(chamadas) {
			};
					
			return Response.status(Status.OK).entity(entidade).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar obter histórico do usuário").build();
		}
		
	}
	
	

/*
	@POST
	@Path("/logoff")
	public Response logoff(String codUsuario) {
		try {
	
			MotoristaBO.getInstance().logoff(motorista);
			return Response.status(Status.OK).entity(motorista).build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar efetuar logoff").build();
		}
	}
	
*/
	
	@POST
	@Path("/enviarChamada")
	public Response enviarChamada(NovaChamadaParam param) {
		try {			
			
			/*ChamadaBO.getInstance().iniciarChamada(getChamada(), getFuncionarioLogado(),
					listaCaracteristicasSelecionadas)*/		
			
			
			RetornoChamadaUsuario retorno  = ChamadaBO.getInstance().iniciarChamadaApp(param);
			
			/*RetornoChamadaUsuario retorno = new RetornoChamadaUsuario();
			retorno.setCodChamada(3L);
			retorno.setCorVeiculo("Branco");
			retorno.setDataChamada(new Date());
			retorno.setDestino(param.getLogradouroDestino() + " - " + param.getBairroDestino());
			retorno.setModeloVeiculo("Pop");
			retorno.setNomeMotorista("José da Silva");
			retorno.setOrigem(param.getLogradouroOrigem() + " - " + param.getBairroOrigem());
			retorno.setPlacaVeiculo("TYU-6578");*/
		   
			return Response.status(Status.OK).entity(retorno).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar realizar chamada").build();
		}
		
	}
	
	


}
