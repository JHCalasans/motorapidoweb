package br.com.motorapido.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = Menu.nomeTabela, schema = Menu.esquema, catalog = "diego")
@NamedQueries(value = { 
		@NamedQuery(name = "Menu.obterMenuNaoVinculadosAoPerfil", query = "select me from Menu me where me.codigo not in (select pm.menu.codigo from PerfilMenu pm where pm.perfil.codigo = :codPerfil)")
		})
public class Menu extends Entidade{



	private static final long serialVersionUID = -386621683545416264L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "menu";
	
	
	
	@Id
	@Column(name = "cod_menu", nullable = false)
	@SequenceGenerator(name = "menu_cod_menu_seq", sequenceName = "diego.menu_cod_menu_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_cod_menu_seq")
	private Integer codigo;
	
	@Column(name = "nome", nullable = false, length = 100)
	private String nome;
	
	@Column(name = "url", nullable = false)
	private String url;

	
	@Override
	public Serializable getIdentificador() {
		
		return getCodigo();
	}


	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}
}
