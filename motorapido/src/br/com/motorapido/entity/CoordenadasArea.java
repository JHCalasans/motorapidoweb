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
@Table(name = CoordenadasArea.nomeTabela, schema = CoordenadasArea.esquema, catalog = "diego")
public class CoordenadasArea extends Entidade{

	private static final long serialVersionUID = -3495917736351931928L;
	
	public final static String esquema = "diego";
	public final static String nomeTabela = "coordenadas_area";
	
	@Id
	@Column(name = "cod_coordenadas_area", nullable = false)
	@SequenceGenerator(name = "coordenadas_area_cod_coordenadas_area_seq", sequenceName = "diego.coordenadas_area_cod_coordenadas_area_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "coordenadas_area_cod_coordenadas_area_seq")
	private Integer codigo;
	
	@Column(name = "latitude", nullable = false)
	private double latitude;

	@Column(name = "longitude", nullable = false)
	private double longitude;
	
	@Column(name = "ordem", nullable = false)
	private int ordem;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cod_area", nullable = false, referencedColumnName = "cod_area")
	private Area area;
	

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


	public double getLatitude() {
		return latitude;
	}


	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}


	public double getLongitude() {
		return longitude;
	}


	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


	public int getOrdem() {
		return ordem;
	}


	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}


	public Area getArea() {
		return area;
	}


	public void setArea(Area area) {
		this.area = area;
	}

}
