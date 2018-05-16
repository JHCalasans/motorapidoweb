package br.com.motorapido.util;


import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.motorapido.enums.ParametroEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {

	// private static String key = "OFICIAL_APP";

	public static final String TOKEN_HEADER = "Authentication";

	public static String create(String subject, String chave)  throws ExcecaoBanco {

		return Jwts.builder().setSubject(subject).signWith(SignatureAlgorithm.HS512,
				chave).compact();

	}

	public static Jws<Claims> decode(String token) throws ExcecaoBanco {
		return Jwts.parser()
				.setSigningKey(FuncoesUtil.getParam(ParametroEnum.CHAVE_SEGURANCA.getCodigo()))
				.parseClaimsJws(token);
	}

}

