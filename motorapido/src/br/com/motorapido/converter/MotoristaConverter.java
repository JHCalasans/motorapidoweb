package br.com.motorapido.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.MotoristaBO;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.util.ExcecoesUtil;

@FacesConverter(value = "motoristaConverter", forClass = Motorista.class)
public class MotoristaConverter implements Converter{

		private static Map<String, Motorista> motoristas = new HashMap<String, Motorista>();

		static {
			try {
				List<Motorista> list = MotoristaBO.getInstance().obterMotoristasAtivos();
				for (Motorista motorista : list) {
					motoristas.put(motorista.getCodigo().toString(), motorista);
				}
			} catch (ExcecaoNegocio e) {
				ExcecoesUtil.TratarExcecao(e);
			}
		}

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
			return arg2 != null ? motoristas.get(arg2) : null;
		}

		@Override
		public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			return arg2 != null ? ((Motorista) arg2).getCodigo().toString() : null;
		}

	
}
