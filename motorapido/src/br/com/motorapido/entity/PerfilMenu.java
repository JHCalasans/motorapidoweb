package br.com.motorapido.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.minhaLib.dao.Entidade;

@Entity
@Table(name = PerfilMenu.nomeTabela, schema = PerfilMenu.esquema, catalog = "diego")
public class PerfilMenu  extends Entidade{



	private static final long serialVersionUID = -6213685419195337230L;
	public final static String esquema = "diego";
	public final static String nomeTabela = "perfil_menu";
	
	
	@Id
	@Column(name = "cod_perfil_menu", nullable = false)
	@SequenceGenerator(name = "perfil_menu_cod_perfil_menu_seq", sequenceName = "diego.perfil_menu_cod_perfil_menu_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "perfil_menu_cod_perfil_menu_seq")
	private Integer codigo;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_perfil", nullable = false, referencedColumnName = "cod_perfil")
	private Perfil perfil;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cod_menu", nullable = false, referencedColumnName = "cod_menu")
	private Menu menu;
	
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

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

}
