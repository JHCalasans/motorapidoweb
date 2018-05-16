package br.com.motorapido.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.motorapido.bo.ModeloBO;
import br.com.motorapido.entity.Modelo;
import br.com.motorapido.util.ExcecoesUtil;

@FacesConverter(value = "modeloConverter", forClass = Modelo.class)
public class ModeloConverter implements Converter {

	private static Map<String, Modelo> modelos = new HashMap<String, Modelo>();

	static {
		try {
			List<Modelo> list = ModeloBO.getInstance().obterModelosAtivos();
			for (Modelo modelo : list) {
				modelos.put(modelo.getCodigo().toString(), modelo);
			}
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2 != null ? modelos.get(arg2) : null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2 != null ? ((Modelo) arg2).getCodigo().toString() : null;
	}
}
