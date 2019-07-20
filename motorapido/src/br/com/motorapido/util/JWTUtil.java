package br.com.motorapido.util;


import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.FuncaoBO;
import br.com.motorapido.enums.ParametroEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JWTUtil {

	// private static String key = "OFICIAL_APP";

	public static final String TOKEN_HEADER = "Authentication";

	public static String create(String subject, String chave)  throws ExcecaoBanco {

		return Jwts.builder().setSubject(subject).signWith(SignatureAlgorithm.HS512,
				chave).compact();

	}

	public static Jws<Claims> decode(String token) throws ExcecaoBanco, ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException, ExcecaoNegocio {
		return Jwts.parser()
				.setSigningKey(FuncaoBO.getInstance().getParam(ParametroEnum.CHAVE_SEGURANCA))
				.parseClaimsJws(token);
	}

}

