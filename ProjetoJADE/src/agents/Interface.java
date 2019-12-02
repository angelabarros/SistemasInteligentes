package agents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;

public class Interface extends Agent {
	
	protected ArrayList<Point> sitios = new ArrayList<>();

	protected void setup(){
	}
	
	protected void takeDown() {
		System.out.println("+++Ending+++Interface+++");
		try {
			DFService.deregister(this);
			
		}catch(FIPAException e) {
			e.printStackTrace();
		}
		super.takeDown();
	}
	
	
	
	private class InformarPostosAbastecimento extends OneShotBehaviour{
		
		@Override
		public void action() {
			// TODO Auto-generated method stub
			 //sitios de abastecimento de combustivel
			Random rand = new Random();
			for(int i=0; i<2; i++) {
				Point p = new Point(rand.nextInt(100), rand.nextInt(100));
				System.out.println(p.x+"     .     "+p.y);
				sitios.add(p);
			}
			
			System.out.println("SIZE: " + sitios.size());
			
		} 
		
	}
}
