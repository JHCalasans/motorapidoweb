package br.com.motorapido.util;

import java.util.Date;

public class Chat implements Comparable<Chat>{
	
	
	 private Date dataCriacao;
	
	 private String mensagem;
	
	 private String autor;

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	@Override
	public int compareTo(Chat o) {		
		return getDataCriacao().compareTo(o.getDataCriacao());
	}

}
