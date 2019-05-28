package br.com.motorapido.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GoogleDirection implements Serializable{

	
	private static final long serialVersionUID = 2048792155233413041L;
	
	private Route[] routes;
	
	public GoogleDirection(){
		
	}

	public Route[] getRoutes() {
		return routes;
	}

	public void setRoutes(Route[] routes) {
		this.routes = routes;
	}
	

}
