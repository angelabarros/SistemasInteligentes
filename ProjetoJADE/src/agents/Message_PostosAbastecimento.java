package agents;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public class Message_PostosAbastecimento implements Serializable{
	
	 private ArrayList<Point> sitios = new ArrayList<>();
	 
	 
	 private Point p;


	public ArrayList<Point> getSitios() {
		return sitios;
	}


	public void setSitios(ArrayList<Point> sitios) {
		this.sitios = sitios;
	}


	public Point getP() {
		return p;
	}


	public void setP(Point p) {
		this.p = p;
	}


	public Message_PostosAbastecimento() {
		super();
	}
	 
	
	public void addPosto(Point p) {
		this.sitios.add(p);
	}
	 
	 
	 
	
}
