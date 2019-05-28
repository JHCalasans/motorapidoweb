package br.com.motorapido.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.ChamadaBO;
import br.com.motorapido.bo.MensagemMotoristaFuncionarioBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.bo.MotoristaPosicaoAreaBO;
import br.com.motorapido.bo.VeiculoBO;
import br.com.motorapido.entity.Chamada;
import br.com.motorapido.entity.ChamadaVeiculo;
import br.com.motorapido.entity.MensagemMotoristaFuncionario;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.entity.Veiculo;
import br.com.motorapido.excecao.ExcecaoMotoristaPosicaoArea;
import br.com.motorapido.mbean.SimpleController;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.GoogleWSUtil;
import br.com.motorapido.util.ws.params.MensagemParam;
import br.com.motorapido.util.ws.params.SelecaoChamadaParam;
import br.com.motorapido.util.ws.params.VerificaPosicaoParam;
import br.com.motorapido.util.ws.retornos.Message;
import br.com.motorapido.util.ws.retornos.RetornoHistoricoMotorista;
import br.com.motorapido.util.ws.retornos.RetornoVeiculosMotorista;
import br.com.motorapido.util.ws.retornos.RetornoVerificaPosicao;
import jersey.repackaged.com.google.common.collect.Lists;

@Path("/motorista")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MotoristaWS {

	@POST
	@Path("/login")
	public Response login(Motorista motorista) {
		try {

			motorista = MotoristaBO.getInstance().login(motorista);
			return Response.status(Status.OK).entity(motorista).build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar efetuar login").build();
		}
	}

	@POST
	@Path("/logoff")
	public Response logoff(Motorista motorista) {
		try {

			MotoristaBO.getInstance().logoff(motorista);
			return Response.status(Status.OK).entity(motorista).build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar efetuar logoff").build();
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
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar alterar disponibilidade")
					.build();
		}
	}

	@POST
	@Path("/atualizarVeiculos")
	public Response atualizarVeiculos(String codMotorista) {
		try {
			List<Veiculo> lista = VeiculoBO.getInstance().obterVeiculosPorMotorista(Integer.parseInt(codMotorista));

			List<RetornoVeiculosMotorista> retorno = new ArrayList<RetornoVeiculosMotorista>();
			for (Veiculo veiculo : lista) {
				RetornoVeiculosMotorista retornoVeiculo = new RetornoVeiculosMotorista();
				retornoVeiculo.setModelo(veiculo.getModelo().getDescricao());
				retornoVeiculo.setPlaca(veiculo.getPlaca());
				retornoVeiculo.setTipoVeiculo(veiculo.getModelo().getTipoVeiculo().getDescricao());
				retornoVeiculo.setCodVeiculo(veiculo.getCodigo());
				retorno.add(retornoVeiculo);
			}

			
			GenericEntity<List<RetornoVeiculosMotorista>> entidade = new GenericEntity<List<RetornoVeiculosMotorista>>(
					retorno) {
			};
			return Response.status(Status.OK).entity(entidade).build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Falha ao tentar obter informações dos veículos").build();
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
		} catch (ExcecaoMotoristaPosicaoArea e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.NOT_ACCEPTABLE).entity(e.getMessage()).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/alterarSenha")
	public Response alterarSenha(Motorista motorista) {
		try {
			motorista = MotoristaBO.getInstance().alterarSenha(motorista);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/iniciarCorrida")
	public Response iniciarCorrida(SelecaoChamadaParam param) {
		try {
			ChamadaBO.getInstance().iniciarCorrida(param);
			return Response.status(Status.OK).build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar iniciar corrida").build();
		}
	}

	@POST
	@Path("/buscarInformacoesBase")
	public Response buscarInformacoesBase() {
		try {
			System.out.println("Chamou atualização");
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar obter informações da base")
					.build();
		}
	}

	@POST
	@Path("/atualizarMensagens")
	public Response atualizarMensagens(MensagemParam param) {
		try {
			List<MensagemMotoristaFuncionario> lista = MensagemMotoristaFuncionarioBO.getInstance()
					.obterMensagens(param.getCodMotorista());
			Message message = null;
			List<Message> retorno = new ArrayList<Message>();
			for (MensagemMotoristaFuncionario msg : lista) {
				message = new Message();
				message.setText(msg.getDescricao());
				message.setMessageDateTime(msg.getDataCriacao());
				message.setIsTextIn(msg.getEnviadaPorMotorista().equals("N"));
				retorno.add(message);
			}

			retorno = Lists.reverse(retorno);
			GenericEntity<List<Message>> entidade = new GenericEntity<List<Message>>(retorno) {
			};
			return Response.status(Status.OK).entity(entidade).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar obter informações da base")
					.build();
		}
	}

	@POST
	@Path("/enviarMensagem")
	public Response enviarMensagem(MensagemParam param) {
		try {

			MensagemMotoristaFuncionarioBO.getInstance().enviarMensagemDoMotorista(param);
			return Response.status(Status.OK).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar enviar mensagem").build();
		}
	}

	@GET
	@Path("/buscarChamadasPendentes")
	public Response buscarChamadasPendentes() {
		try {

			List<Chamada> chamadas = new ArrayList<Chamada>();
			chamadas.addAll(SimpleController.getListaChamadasEmEsperaGeral());
			/*
			 * for(Chamada chamada : chamadas){ chamada.setFuncionario(null); }
			 */
			GenericEntity<List<Chamada>> entidade = new GenericEntity<List<Chamada>>(chamadas) {
			};

			return Response.status(Status.OK).entity(entidade).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar obter chamadas pendentes")
					.build();
		}

	}

	@POST
	@Path("/buscarHistorico")
	public Response buscarHistorico(String codMotorista) {
		try {

			List<RetornoHistoricoMotorista> chamadas = MotoristaBO.getInstance()
					.obterHistoricoMotorista(Integer.parseInt(codMotorista));

			GenericEntity<List<RetornoHistoricoMotorista>> entidade = new GenericEntity<List<RetornoHistoricoMotorista>>(
					chamadas) {
			};

			return Response.status(Status.OK).entity(entidade).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar obter histórico do motorista")
					.build();
		}

	}

	@POST
	@Path("/selecionarChamada")
	public Response selecionarChamada(SelecaoChamadaParam param) {
		try {
			Chamada chamada = ChamadaBO.getInstance().selecionarChamadaPendentePeloApp(param);

			chamada.setPolylines(GoogleWSUtil.buscarRota(chamada));

			return Response.status(Status.OK).entity(chamada).build();
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao tentar selecionar chamada").build();
		}

	}

}
