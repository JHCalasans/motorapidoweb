package br.com.motorapido.filtro;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.minhaLib.excecao.excecaobanco.ExcecaoBanco;
import br.com.minhaLib.excecao.excecaonegocio.ExcecaoNegocio;
import br.com.motorapido.bo.FuncaoBO;
import br.com.motorapido.enums.ParametroEnum;
import br.com.motorapido.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureException;

public class JWTFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse res = (HttpServletResponse) servletResponse;

		
		try {
			String resul = FuncaoBO.getInstance().getParam(ParametroEnum.SERVICOS_LIBERADOS_SEGURANCA);
			List<String> servicos = Arrays.asList(resul.split(";"));
			for(String servico : servicos){
				if((req.getRequestURI().contains("/"+servico))){
					filterChain.doFilter(servletRequest, servletResponse);
					return;
				}
			}
			/*if (req.getRequestURI().endsWith("/login") || req.getRequestURI().endsWith("/enviarMensagem") 
					|| req.getRequestURI().endsWith("/cadastrar")) {
				filterChain.doFilter(servletRequest, servletResponse);
				return;
			}*/
		} catch (ExcecaoNegocio e) {			
			res.setContentType("aplication/json");
			res.setStatus(500);
			res.getWriter().write(e.getMessage());
		}
		

		String token = req.getHeader(JWTUtil.TOKEN_HEADER);

		if (token == null || token.trim().isEmpty()) {
			res.setContentType("aplication/json");
			res.getWriter().write("Token de segurança não encontrado.");
			res.setStatus(401);
			return;
		}

		try {
			Jws<Claims> parser = JWTUtil.decode(token);
			filterChain.doFilter(servletRequest, servletResponse);
		} catch (SignatureException e) {
			res.setContentType("aplication/json");
			res.getWriter().write("Usuário não autorizado para esta funcionalidade.");
			res.setStatus(401);
		} catch (ExcecaoBanco e) {
			res.setContentType("aplication/json");
			res.setStatus(500);
			res.getWriter().write(e.getMessage());
		}catch(Exception e){
			res.setContentType("aplication/json");
			res.setStatus(500);
			res.getWriter().write("Falha ao decriptografar chave de serviços");
		}

	}

	@Override
	public void destroy() {		
	}

}
