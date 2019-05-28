package br.com.motorapido.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Route implements Serializable{


	private static final long serialVersionUID = -4454314009294241928L;

	
	private PolylineOverView overview_polyline;
	
	public Route(){
		
	}
	
	
	public PolylineOverView getOverview_polyline() {
		return overview_polyline;
	}


	public void setOverview_polyline(PolylineOverView overview_polyline) {
		this.overview_polyline = overview_polyline;
	}

	@XmlRootElement
	public class PolylineOverView
    {
        private String points;

        public PolylineOverView(){
        	
        }
        
		public String getPoints() {
			return points;
		}

		public void setPoints(String points) {
			this.points = points;
		}
        
        
        
    }
	
}
