package br.com.motorapido.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Months;
import org.joda.time.Weeks;

import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaobanco.ExcecaoBancoConexao;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.dao.FabricaDAO;
import br.com.motorapido.entity.Parametro;
import br.com.motorapido.entity.ValorParametro;






public final class FuncoesUtil {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static SimpleDateFormat sdfHMS = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");
	public static DecimalFormat df2 = new DecimalFormat("#,###,###,##0.00");
	public static DecimalFormat df4 = new DecimalFormat("#,###,###,##0.0000");
	public static DecimalFormat df6 = new DecimalFormat("#,###,###,##0.000000");

	private static final DecimalFormat DF_NUM_OFICIO = new DecimalFormat("0000");

	public static Date removeTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static boolean datasIguais(Date data1, Date data2) {
		Date d1 = removeTime(data1);
		Date d2 = removeTime(data2);
		return d1.equals(d2);
	}
	
	public static List<String> getParams(String chave) throws ExcecaoBancoConexao, ExcecaoBanco {
		Parametro param = new Parametro();
		param.setChave(chave);
		ValorParametro valor = new ValorParametro();
		valor.setParametro(param);
		List<ValorParametro> listaParametros = FabricaDAO.getFabricaDAO().getPostgresValorParametroDAO()
				.findByExample(valor);
		if (listaParametros.size() > 0) {
			List<String> retorno = new ArrayList<String>();
			for (ValorParametro valorParametro : listaParametros) {
				retorno.add(valorParametro.getValor());
			}
			return retorno;
		}
		return null;
	}

	public static List<String> getParams(String chave, EntityManager em)
			throws ExcecaoBancoConexao, ExcecaoBanco {
		Parametro param = new Parametro();
		param.setChave(chave);
		ValorParametro valor = new ValorParametro();
		valor.setParametro(param);
		List<ValorParametro> listaParametros = FabricaDAO.getFabricaDAO().getPostgresValorParametroDAO()
				.findByExample(valor, em);
		if (listaParametros.size() > 0) {
			List<String> retorno = new ArrayList<String>();
			for (ValorParametro valorParametro : listaParametros) {
				retorno.add(valorParametro.getValor());
			}
			return retorno;
		}
		return null;
	}

	public static String getParam(String chave, EntityManager em)
			throws ExcecaoBancoConexao, ExcecaoBanco {
		try {
			final List<String> params = FuncoesUtil.getParams(chave, em);
			if (params.size() > 0)
				return params.get(0);
			return "";
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getParam(String chave) throws ExcecaoBancoConexao, ExcecaoBanco {
		try {
			final List<String> params = FuncoesUtil.getParams(chave);
			if (params.size() > 0)
				return params.get(0);
			return "";
		} catch (Exception ex) {
			return "";
		}
	}

	public static boolean isSameYear(Date... dates) {
		Calendar current = Calendar.getInstance();
		Calendar before = Calendar.getInstance();
		for (int i = 0; i < dates.length; i++) {
			current.setTime(dates[i]);
			if (i > 0
					&& (current.get(Calendar.YEAR) != before.get(Calendar.YEAR)))
				return false;

			before.setTime(dates[i]);
		}
		return true;
	}

	public static boolean isWeekend(Calendar date) {
		int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
		return (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY);
	}

	public static boolean isWeekend(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return isWeekend(c);
	}

	public static boolean isEspecificDay(Date date, int dayOfWeek) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return isEspecificDay(c, dayOfWeek);
	}

	public static boolean isEspecificDay(Calendar date, int dayOfWeek) {
		return date.get(Calendar.DAY_OF_WEEK) == dayOfWeek;
	}

	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static boolean isAfterDayOfWeek(Date current, Date after) {
		Calendar cCurrent = Calendar.getInstance();
		cCurrent.setTime(current);
		Calendar cAfter = Calendar.getInstance();
		cAfter.setTime(after);
		int dayOfWeekCurr = cCurrent.get(Calendar.DAY_OF_WEEK);
		int dayOfWeekAfter = cAfter.get(Calendar.DAY_OF_WEEK);
		if (++dayOfWeekCurr == dayOfWeekAfter)
			return true;
		else
			return false;
	}

	public static boolean isPreviousDay(Date day, Date previous) {
		return compareDay(day, previous, false);
	}

	public static boolean isNextDay(Date day, Date next) {
		return compareDay(day, next, true);
	}

	private static boolean compareDay(Date day, Date otherDay, boolean isNext) {
		Calendar cDay = Calendar.getInstance();
		cDay.setTime(day);
		Calendar cOtherDay = Calendar.getInstance();
		cOtherDay.setTime(otherDay);
		int amount = isNext ? -1 : 1;
		if (cDay.get(Calendar.DAY_OF_MONTH) == (cOtherDay
				.get(Calendar.DAY_OF_MONTH) + amount)
				&& cDay.get(Calendar.MONTH) == cOtherDay.get(Calendar.MONTH)
				&& cDay.get(Calendar.YEAR) == cDay.get(Calendar.YEAR))
			return true;
		else
			return false;
	}

	public static int obterQuantSemanasEntreDatas(Date dtInicial, Date dtFinal) {
		DateTime cDtInicial = new DateTime(dtInicial);
		DateTime cDtFinal = new DateTime(dtFinal);
		return Weeks.weeksBetween(
				cDtInicial.dayOfWeek().withMinimumValue().minusDays(1),
				cDtFinal.dayOfWeek().withMaximumValue().plusDays(1)).getWeeks();
	}

	public static int obterQuantMesesEntreDatas(Date dtInicial, Date dtFinal) {
		DateTime cDtInicial = new DateTime(dtInicial);
		DateTime cDtFinal = new DateTime(dtFinal);
		return Months.monthsBetween(cDtInicial.withDayOfMonth(1),
				cDtFinal.withDayOfMonth(1)).getMonths();
	}

	public static Date sendToNextFriday(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int amount = Calendar.FRIDAY - dayOfWeek;
		c.add(Calendar.DAY_OF_WEEK, amount);
		return c.getTime();
	}
	
	public static Float formatarFloat(float numero) throws ExcecaoNegocio{
		  String retorno = "";
		  DecimalFormat formatter = new DecimalFormat("#.00");
		  try{
		    retorno = formatter.format(numero);
		  }catch(Exception ex){
		   throw new ExcecaoNegocio("Erro ao formatar numero: " + ex);
		  }
		  return Float.parseFloat(retorno);
		}
	
	
	public static BigDecimal formatarBigDecimal(float numero) throws ExcecaoNegocio{
		  String retorno = "";
		  DecimalFormat formatter = new DecimalFormat("#.00");
		  try{
		    retorno = formatter.format(numero);
		  }catch(Exception ex){
		   throw new ExcecaoNegocio("Erro ao formatar numero: " + ex);
		  }
		  return BigDecimal.valueOf(Float.parseFloat(retorno));
		}

	public static Date sendToNextSunday(Date data) {
		Calendar c = Calendar.getInstance();
		c.setTime(data);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int amount = Calendar.SATURDAY - dayOfWeek;
		c.add(Calendar.DAY_OF_WEEK, ++amount);
		return c.getTime();
	}

	public static Date sendToLastSunday(Date data) {
		DateTime cDtInicial = new DateTime(data);
		return cDtInicial.withDayOfWeek(DateTimeConstants.SUNDAY).plusWeeks(-1)
				.toDate();
	}

	public static long diferencaDiasEntreDatas(Date dtMenor, Date dtMaior) {
		long dif = dtMaior.getTime() - dtMenor.getTime();

		return dif / (24 * 60 * 60 * 1000);
	}

	public static String removeAccents(String str) {
		str = Normalizer.normalize(str, Normalizer.Form.NFD);
		str = str.replaceAll("[^\\p{ASCII}]", "");
		return str.trim();

	}

	public static BigDecimal criarBigDecimal(double valor, int nrCasasDecimais) {
		BigDecimal tmp = new BigDecimal(valor, MathContext.UNLIMITED);
		return tmp.setScale(nrCasasDecimais, RoundingMode.HALF_UP);
	}

	public static boolean isMaior(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) == 1);
	}

	public static boolean isMaiorOuIgual(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) > -1);
	}

	public static boolean isMenor(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) == -1);
	}

	public static boolean isMenorOuIgual(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) < 1);
	}

	public static boolean isIgual(BigDecimal bg1, BigDecimal bg2) {
		return (bg1.compareTo(bg2) == 0);
	}

	public static String criptografarSenha(String senha) throws Exception{
		MessageDigest algorithm;
		try {
			algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
			String retorno = hexString.toString();
			return retorno;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Erro ao processar senha");
		}
		
	}
	
	public static String gerarSenha() {
		int qtdeMaximaCaracteres = 8;
		String[] caracteres = { "a", "1", "b", "2", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g",
				"h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B",
				"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
				"X", "Y", "Z" };

		StringBuilder senha = new StringBuilder();

		for (int i = 0; i < qtdeMaximaCaracteres; i++) {
			int posicao = (int) (Math.random() * caracteres.length);
			senha.append(caracteres[posicao]);
		}

		return senha.toString();
	}
	
	 public static boolean pontoEstaDentro(CoordenadaPontoUtil test, CoordenadaPontoUtil[] points) {
	      int i;
	      int j;
	      boolean result = false;
	      for (i = 0, j = points.length - 1; i < points.length; j = i++) {
	        if ((points[i].y > test.y) != (points[j].y > test.y) &&
	            (test.x < (points[j].x - points[i].x) * (test.y - points[i].y) / (points[j].y-points[i].y) + points[i].x)) {
	          result = !result;
	         }
	      }
	      return result;
	    }
	
	/*
	
	public static boolean onSegment(CoordenadaPontoUtil p, CoordenadaPontoUtil q, CoordenadaPontoUtil r)
    {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x)
                && q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;
        return false;
    }
 
    public static int orientation(CoordenadaPontoUtil p, CoordenadaPontoUtil q, CoordenadaPontoUtil r)
    {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);
 
        if (val == 0)
            return 0;
        return (val > 0) ? 1 : 2;
    }
 
    public static boolean doIntersect(CoordenadaPontoUtil p1, CoordenadaPontoUtil q1, CoordenadaPontoUtil p2, CoordenadaPontoUtil q2)
    {
 
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);
 
        if (o1 != o2 && o3 != o4)
            return true;
 
        if (o1 == 0 && onSegment(p1, p2, q1))
            return true;
 
        if (o2 == 0 && onSegment(p1, q2, q1))
            return true;
 
        if (o3 == 0 && onSegment(p2, p1, q2))
            return true;
 
        if (o4 == 0 && onSegment(p2, q1, q2))
            return true;
 
        return false;
    }
 
    public static boolean isInside(CoordenadaPontoUtil polygon[], int n, CoordenadaPontoUtil p)
    {
        int INF = 10000;
        if (n < 3)
            return false;
 
        CoordenadaPontoUtil extreme = new CoordenadaPontoUtil(INF, p.y);
 
        int count = 0, i = 0;
        do
        {
            int next = (i + 1) % n;
            if (doIntersect(polygon[i], polygon[next], p, extreme))
            {
                if (orientation(polygon[i], p, polygon[next]) == 0)
                    return onSegment(polygon[i], p, polygon[next]);
 
                count++;
            }
            i = next;
        } while (i != 0);
 
        return (count & 1) == 1 ? true : false;
    }*/
	
	
	
	
}
