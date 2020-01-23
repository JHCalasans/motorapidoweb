package br.com.motorapido.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.apache.commons.lang3.StringEscapeUtils;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.IBinarioMotoristaDAO;
import br.com.motorapido.dao.IBloqueioMotoristaDAO;
import br.com.motorapido.dao.ICaracteristicaMotoristaDAO;
import br.com.motorapido.dao.IChamadaVeiculoDAO;
import br.com.motorapido.dao.IMotoristaAparelhoDAO;
import br.com.motorapido.dao.IMotoristaDAO;
import br.com.motorapido.dao.IMotoristaPosicaoAreaDAO;
import br.com.motorapido.dao.IPagamentoMotoristaDAO;
import br.com.motorapido.dao.IVeiculoDAO;
import br.com.motorapido.entity.BinarioMotorista;
import br.com.motorapido.entity.BloqueioMotorista;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.CaracteristicaMotorista;
import br.com.motorapido.entity.ChamadaVeiculo;
import br.com.motorapido.entity.Funcionario;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.MotoristaAparelho;
import br.com.motorapido.entity.MotoristaPosicaoArea;
import br.com.motorapido.entity.PagamentoMotorista;
import br.com.motorapido.entity.TipoPunicao;
import br.com.motorapido.entity.Veiculo;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.util.ControleSessaoWS;
import br.com.motorapido.util.FuncoesUtil;
import br.com.motorapido.util.JWTUtil;
import br.com.motorapido.util.ws.retornos.RetornoHistoricoMotorista;
import br.com.motorapido.util.ws.retornos.RetornoVeiculosMotorista;

public class PagamentoMotoristaBO extends MotoRapidoBO {

	private static PagamentoMotoristaBO instance;

	private PagamentoMotoristaBO() {

	}

	public static PagamentoMotoristaBO getInstance() {
		if (instance == null)
			instance = new PagamentoMotoristaBO();

		return instance;
	}

	

}
