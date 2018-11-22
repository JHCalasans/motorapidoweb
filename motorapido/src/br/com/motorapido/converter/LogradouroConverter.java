package br.com.motorapido.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.LogradouroBO;
import br.com.motorapido.entity.Logradouro;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.util.ExcecoesUtil;

@FacesConverter(value = "logradouroConverter", forClass = Motorista.class)
public class LogradouroConverter  implements Converter{

	private static Map<String, Logradouro> logradouros = new HashMap<String, Logradouro>();

	static {
		try {
			List<Logradouro> list = LogradouroBO.getInstance().obterLogradourosPorEstados("SE");
			for (Logradouro logradouro : list) {
				logradouros.put(logradouro.getCodigo().toString(), logradouro);
			}
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2 != null ? logradouros.get(arg2) : null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2 != null  ? ((Logradouro) arg2).getCodigo() == null ? null : ((Logradouro) arg2).getCodigo().toString() : null;
	}


}
