package br.com.motorapido.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.motorapido.bo.FabricanteBO;
import br.com.motorapido.entity.Fabricante;
import br.com.motorapido.util.ExcecoesUtil;

@FacesConverter(value = "fabricanteConverter", forClass = Fabricante.class)
public class FabricanteConverter implements Converter {

	private static Map<String, Fabricante> fabricantes = new HashMap<String, Fabricante>();

	static {
		try {
			List<Fabricante> list = FabricanteBO.getInstance().obterFabricantesAtivos();
			for (Fabricante fabricante : list) {
				fabricantes.put(fabricante.getCodigo().toString(), fabricante);
			}
		} catch (Exception e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2 != null ? fabricantes.get(arg2) : null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2 != null ? ((Fabricante) arg2).getCodigo().toString() : null;
	}

}
