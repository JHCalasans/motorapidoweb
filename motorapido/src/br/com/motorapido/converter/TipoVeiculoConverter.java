package br.com.motorapido.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.motorapido.bo.TipoVeiculoBO;
import br.com.motorapido.dao.FabricaDAO;
import br.com.motorapido.entity.TipoVeiculo;
import br.com.motorapido.util.ExcecoesUtil;

@FacesConverter(value = "tipoVeiculoConverter", forClass = TipoVeiculo.class)
public class TipoVeiculoConverter implements Converter {

	private static Map<String, TipoVeiculo> tiposVeiculos = new HashMap<String, TipoVeiculo>();

	static {
		try {
			List<TipoVeiculo> list = TipoVeiculoBO.getInstance().obterTiposVeiculosAtivos();
			for (TipoVeiculo tipoVeiculo : list) {
				tiposVeiculos.put(tipoVeiculo.getCodigo().toString(), tipoVeiculo);
			}
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2 != null ? tiposVeiculos.get(arg2) : null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2 != null ? ((TipoVeiculo) arg2).getCodigo().toString() : null;
	}

}
