package br.com.motorapido.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import javax.el.ELResolver;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public final class FacesUtil {

	private FacesUtil() {
	}

	public final static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public static Object eval(String str) {
		FacesContext context = FacesContext.getCurrentInstance();
		ELResolver resolver = context.getApplication().getELResolver();
		return resolver.getValue(context.getELContext(), null, str);
	}

	public final static void redirecionar(String fromOutcome, String toOutcome, boolean facesRedirect,
			List<String> mensagens) {
		if (mensagens != null) {
			for (String msg : mensagens) {
				getFacesContext().addMessage(null, new FacesMessage(msg));
			}
			getFacesContext().getExternalContext().getFlash().setKeepMessages(true);
		}
		getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), fromOutcome,
				toOutcome + (facesRedirect ? "?faces-redirect=true" : ""));
	}

	public final static void redirecionarComViewParams(String fromOutcome, String toOutcome, boolean facesRedirect) {
		getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), fromOutcome,
				toOutcome
						+ (facesRedirect ? "?faces-redirect=true&includeViewParams=true" : "?includeViewParams=true"));

	}

	public final static String getApplicationUri() {
		try {
			FacesContext ctxt = FacesContext.getCurrentInstance();
			ExternalContext ext = ctxt.getExternalContext();
			URI uri = new URI(ext.getRequestScheme(), null, ext.getRequestServerName(), ext.getRequestServerPort(),
					ext.getRequestContextPath(), null, null);
			return uri.toASCIIString();
		} catch (URISyntaxException e) {
			throw new FacesException(e);
		}
	}

	public static Map<String, Object> getSessionMap() {
		if (FacesContext.getCurrentInstance() != null
				&& FacesContext.getCurrentInstance().getExternalContext() != null) {
			return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		}
		return null;
	}

	public static boolean isCNPJ(String CNPJ) {
		// considera-se erro CNPJ's formados por uma sequencia de numeros iguais
		if (CNPJ.equals("00000000000000") || CNPJ.equals("11111111111111") || CNPJ.equals("22222222222222")
				|| CNPJ.equals("33333333333333") || CNPJ.equals("44444444444444") || CNPJ.equals("55555555555555")
				|| CNPJ.equals("66666666666666") || CNPJ.equals("77777777777777") || CNPJ.equals("88888888888888")
				|| CNPJ.equals("99999999999999") || (CNPJ.length() != 14))
			return (false);

		char dig13, dig14;
		int sm, i, r, num, peso;

		// "try" - protege o c�digo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 11; i >= 0; i--) {
				// converte o i-�simo caractere do CNPJ em um n�mero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posi��o de '0' na tabela ASCII)
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig13 = '0';
			else
				dig13 = (char) ((11 - r) + 48);

			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 2;
			for (i = 12; i >= 0; i--) {
				num = (int) (CNPJ.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso + 1;
				if (peso == 10)
					peso = 2;
			}

			r = sm % 11;
			if ((r == 0) || (r == 1))
				dig14 = '0';
			else
				dig14 = (char) ((11 - r) + 48);

			// Verifica se os d�gitos calculados conferem com os d�gitos
			// informados.
			if ((dig13 == CNPJ.charAt(12)) && (dig14 == CNPJ.charAt(13)))
				return (true);
			else
				return (false);
		} catch (InputMismatchException erro) {
			return (false);
		}
	}

}
