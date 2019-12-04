package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.JFrame;


public class Interface extends Agent {
	

	protected ArrayList<Point> sitios_gasolina = new ArrayList<>();
	protected ArrayList<Point> sitios_water = new ArrayList<>();

	
	protected void setup(){
		
		System.out.print("Starting Interface");
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Interface");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		
		try {
			
			DFService.register(this, dfd);
			
		}catch (FIPAException e) {
			e.printStackTrace();
		}
		
		//10 pontos de água
		sitios_water.add(new Point(5,10));
		sitios_water.add(new Point(20,20));
		sitios_water.add(new Point(80,80));
		sitios_water.add(new Point(74,28));
		sitios_water.add(new Point(45,10));
		sitios_water.add(new Point(78,50));
		sitios_water.add(new Point(90,95));
		sitios_water.add(new Point(33, 99));
		sitios_water.add(new Point(60,73));
		sitios_water.add(new Point(20, 84));		
		
		//10 pontos de gasolina
		sitios_gasolina.add(new Point(0,0));
		sitios_gasolina.add(new Point(5,5));
//		sitios_gasolina.add(new Point(88,33));
//		sitios_gasolina.add(new Point(30,55));
//		sitios_gasolina.add(new Point(49,97));
//		sitios_gasolina.add(new Point(68,22));
//		sitios_gasolina.add(new Point(70,30));
//		sitios_gasolina.add(new Point(95,48));
//		sitios_gasolina.add(new Point(50, 40));
//		sitios_gasolina.add(new Point(79, 66));

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
	
	
	
}
