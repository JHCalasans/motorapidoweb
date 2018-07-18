package br.com.motorapido.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.TipoPunicaoBO;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.entity.TipoPunicao;
import br.com.motorapido.util.ExcecoesUtil;

@FacesConverter(value = "tipoPunicaoConverter", forClass = Motorista.class)
public class TipoPunicaoConverter  implements Converter{

	private static Map<String, TipoPunicao> tiposPunicoes = new HashMap<String, TipoPunicao>();

	static {
		try {
			List<TipoPunicao> list = TipoPunicaoBO.getInstance().obterTiposPunicoes(null, "S");
			for (TipoPunicao tipoPunicao : list) {
				tiposPunicoes.put(tipoPunicao.getCodigo().toString(), tipoPunicao);
			}
		} catch (ExcecaoNegocio e) {
			ExcecoesUtil.TratarExcecao(e);
		}
	}

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return arg2 != null ? tiposPunicoes.get(arg2) : null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		return arg2 != null ? ((TipoPunicao) arg2).getCodigo().toString() : null;
	}


}
