package br.com.motorapido.converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.CaracteristicaBO;
import br.com.motorapido.entity.Caracteristica;
import br.com.motorapido.entity.Motorista;
import br.com.motorapido.util.ExcecoesUtil;

@FacesConverter(value = "caracteristicaConverter", forClass = Motorista.class)
public class CaracteristicaConverter implements Converter{

		private static Map<String, Caracteristica> caracteristicas = new HashMap<String, Caracteristica>();

		static {
			try {
				List<Caracteristica> list = CaracteristicaBO.getInstance().obterCaracteristicas(null, "S");
				for (Caracteristica caracteristica : list) {
					caracteristicas.put(caracteristica.getCodigo().toString(), caracteristica);
				}
			} catch (ExcecaoNegocio e) {
				ExcecoesUtil.TratarExcecao(e);
			}
		}

		@Override
		public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
			return arg2 != null ? caracteristicas.get(arg2) : null;
		}

		@Override
		public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
			return arg2 != null ? ((Caracteristica) arg2).getCodigo().toString() : null;
		}

	
}
