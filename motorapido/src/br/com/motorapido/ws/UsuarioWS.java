package br.com.motorapido.ws;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.ClienteBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.bo.UsuarioBO;
import br.com.motorapido.entity.Usuario;
import br.com.motorapido.util.ExcecoesUtil;



@Path("/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioWS  {


/*
	@POST
	@Path("/login")
	public Response login(Usuario usuario) {
		try {
	
			cliente = MotoristaBO.getInstance().login(cliente);
			return Response.status(Status.OK).entity(motorista).build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar efetuar login").build();
		}
	}*/
	
	
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
	
	


}
