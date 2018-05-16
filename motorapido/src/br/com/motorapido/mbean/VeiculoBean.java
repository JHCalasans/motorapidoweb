package br.com.motorapido.mbean;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.minhaLib.util.excecao.MsgUtil;
import br.com.motorapido.bo.FabricanteBO;
import br.com.motorapido.bo.ModeloBO;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.bo.TipoVeiculoBO;
import br.com.motorapido.bo.VeiculoBO;
import br.com.motorapido.entity.Fabricante;
import br.com.motorapido.entity.Modelo;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.TipoVeiculo;
import br.com.motorapido.entity.Veiculo;
import br.com.motorapido.util.ExcecoesUtil;
import br.com.motorapido.util.UtilDownload;

@SuppressWarnings("deprecation")
@ManagedBean(name = "veiculoBean")
@ViewScoped
public class VeiculoBean extends SimpleController {

	private static final long serialVersionUID = 9114494411844662958L;

	private Veiculo veiculo;

	private TipoVeiculo tipoVeiculo;

	private Fabricante fabricante;

	private Modelo modelo;

	private String cor;

	private List<Modelo> listaModelos;

	private List<Fabricante> listaFabricantes;

	private List<TipoVeiculo> listaTiposVeiculos;

	private UploadedFile documento;

	private List<Veiculo> listaVeiculosMotorista;

	private Motorista motorista;

	private Veiculo veiculoExcluir;
	
	@ManagedProperty(value = "#{uploadedFileBean}")
	private UploadedFileBean fileUploadBean;
	
	@PostConstruct
	public void carregar() {
		if (getFacesContext().isPostback()) {
			return;
		}
		try {
			String codMotoStr = (String) getRequestParam("codMotorista");
			// String teste =
			// FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap().get("referer");
			if (codMotoStr != null) {
				Integer codMotorista = Integer.valueOf(codMotoStr);
				carregarMotorista(codMotorista);
				atualizaCampos();
			}
			// if(getFacesContext().getViewRoot().getViewId().endsWith("cadastrarVeiculo.xhtml")){

			// }

		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	private void atualizaCampos() throws ExcecaoNegocio {
		veiculo = new Veiculo();
		documento = null;

		listaTiposVeiculos = TipoVeiculoBO.getInstance().obterTiposVeiculosAtivos();
		if (tipoVeiculo == null)
			tipoVeiculo = listaTiposVeiculos.get(0);
		atualizaTipoVeiculo();
		
		listaVeiculosMotorista = VeiculoBO.getInstance().obterVeiculosPorMotorista(motorista.getCodigo());

	}
	
	public void abrirExcluir(Veiculo param){
		veiculoExcluir = param;
		enviarJavascript("PF('dlConfirmDelete').show();");
	}
	
	public void excluirVeiculo(){
		try {
			VeiculoBO.getInstance().excluirVeiculo(veiculoExcluir);		
			atualizaCampos();
			addMsg(FacesMessage.SEVERITY_INFO, "Veículo excluído com sucesso.");
			veiculoExcluir = null;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	public void downloadDocumentos(Veiculo veiculo)
	{
		try {			
			UtilDownload.download(veiculo.getDocumento(), "Documentos do veículo de placa "+ veiculo.getPlaca() +".pdf",
					UtilDownload.MIMETYPE_OCTETSTREAM,
					UtilDownload.CONTENT_DISPOSITION_ATTACHMENT);
		} catch (Exception e)
		{
			ExcecoesUtil.TratarExcecao(e);
		}
	}
	
	private boolean validaChassi(){
		try {
			Veiculo retorno = VeiculoBO.getInstance().obterVeiculosPorChassi(veiculo.getChassi().toUpperCase());
			if (retorno != null ) {
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Chassi já cadastrado na base de dados!.", "");
				return false;
			}
			return true;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return false;
		}
	}
	
	private boolean validaPlaca(){
		try {			
			Veiculo retorno = VeiculoBO.getInstance().obterVeiculosPorPlaca(veiculo.getPlaca().toUpperCase());
			if (retorno != null ) {
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Placa já cadastrada na base de dados!.", "");
				return false;
			}
			return true;
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
			return false;
		}
	}

	private void carregarMotorista(Integer codMotorista) {
		try {
			setMotorista(MotoristaBO.getInstance().obterMotoristaPorCodigo(codMotorista));
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void documentoUploadAction(FileUploadEvent event) {
		setDocumento(event.getFile());
	}

	public void atualizaTipoVeiculo() {
		try {
			listaFabricantes = FabricanteBO.getInstance().obterFabricantesPorTipoVeiculo(tipoVeiculo.getCodigo());
			if (listaFabricantes == null || listaFabricantes.size() == 0) {
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Nenhum fabricante para este tipo de veículo!.", "");
				return;
			}
			fabricante = listaFabricantes.get(0);
			listaModelos = ModeloBO.getInstance().obterModeloPorFabricanteETipoVeiculo(tipoVeiculo.getCodigo(),
					fabricante.getCodigo());
			if (listaModelos == null || listaModelos.size() == 0) {
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Nenhum modelo para este fabricante!.", "");
				return;
			}
			modelo = listaModelos.get(0);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void atualizaFabricante() {
		try {
			listaModelos = ModeloBO.getInstance().obterModeloPorFabricanteETipoVeiculo(tipoVeiculo.getCodigo(),
					fabricante.getCodigo());
			if (listaModelos == null || listaModelos.size() == 0) {
				MsgUtil.updateMessage(FacesMessage.SEVERITY_ERROR, "Nnehum modelo para este fabricante!.", "");
				return;
			}
			modelo = listaModelos.get(0);
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	public void salvarVeiculo() {
		
		if (documento == null) {
			addMsg(FacesMessage.SEVERITY_ERROR, "Documento não Anexado.");
			return;
		}
		if (!validaChassi())
			return;		
		if (!validaPlaca())
			return;	
		try {
			veiculo.setDocumento(documento.getContents());
			veiculo.setMotorista(motorista);
			veiculo.setModelo(modelo);
			VeiculoBO.getInstance().salvarVeiculo(veiculo);
			addMsg(FacesMessage.SEVERITY_INFO, "Veículo cadastrado com sucesso.");
			atualizaCampos();

		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);

		}

	}

	@Override
	public String salvoSucesso() {
		return null;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public List<Fabricante> getListaFabricantes() {
		return listaFabricantes;
	}

	public void setListaFabricantes(List<Fabricante> listaFabricantes) {
		this.listaFabricantes = listaFabricantes;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public List<TipoVeiculo> getListaTiposVeiculos() {
		return listaTiposVeiculos;
	}

	public void setListaTiposVeiculos(List<TipoVeiculo> listaTiposVeiculos) {
		this.listaTiposVeiculos = listaTiposVeiculos;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	public List<Modelo> getListaModelos() {
		return listaModelos;
	}

	public void setListaModelos(List<Modelo> listaModelos) {
		this.listaModelos = listaModelos;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public UploadedFile getDocumento() {
		return documento;
	}

	public void setDocumento(UploadedFile documento) {
		this.documento = documento;
	}

	public List<Veiculo> getListaVeiculosMotorista() {
		return listaVeiculosMotorista;
	}

	public void setListaVeiculosMotorista(List<Veiculo> listaVeiculosMotorista) {
		this.listaVeiculosMotorista = listaVeiculosMotorista;
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}

	public Veiculo getVeiculoExcluir() {
		return veiculoExcluir;
	}

	public void setVeiculoExcluir(Veiculo veiculoExcluir) {
		this.veiculoExcluir = veiculoExcluir;
	}

	public UploadedFileBean getFileUploadBean() {
		return fileUploadBean;
	}

	public void setFileUploadBean(UploadedFileBean fileUploadBean) {
		this.fileUploadBean = fileUploadBean;
	}

}
