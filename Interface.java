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

import agents.Interface.Grid;

import javax.swing.*;

public class Interface extends Agent {
	

	protected ArrayList<Point> sitios_gasolina = new ArrayList<>();
	protected ArrayList<Point> sitios_water = new ArrayList<>();
	Grid grid = new Grid();
	
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
		
		//Desenhar a grelha do mapa
		
		grid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		grid.setColor(0);
		grid.setSitios_gasolina(sitios_gasolina);
		grid.setSitios_water(sitios_water);
//		grid.fillCell(0, 10);
//		grid.fillCell(5, 20);
//		grid.fillCell(10, 30);
//		grid.fillCell(15, 40);
//		grid.fillCell(20, 50);
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
	
	
	
	public static class Grid extends JFrame {
		private List<Point> fillCells;
		private int color;
		protected ArrayList<Point> sitios_gasolina = new ArrayList<>();
		protected ArrayList<Point> sitios_water = new ArrayList<>();
		
		
		
		
		public ArrayList<Point> getSitios_gasolina() {
			return sitios_gasolina;
		}

		public void setSitios_gasolina(ArrayList<Point> sitios_gasolina) {
			this.sitios_gasolina = sitios_gasolina;
		}

		public ArrayList<Point> getSitios_water() {
			return sitios_water;
		}

		public void setSitios_water(ArrayList<Point> sitios_water) {
			this.sitios_water = sitios_water;
		}

		public int getColor() {
			return color;
		}

		public void setColor(int color) {
			this.color = color;
		}

		public Grid() {
            fillCells = new ArrayList<>(25);
			setSize(1200,1050);
			setVisible(true);
			
		}
		
		public void paint(Graphics g) {
			
			for (int x = 20; x <= 700; x += 20) {
				for (int y = 20; y <= 700; y += 20) {
					g.drawRect(x, 20 + y, 20, 20);
				}
			}		
			for (Point fillCell : this.sitios_gasolina) {
                int cellX = 20 +(fillCell.x * 20);
                int cellY = 40 +(fillCell.y * 20);
                
        		switch(color) {
        		case 0://fogo
        			g.setColor(Color.RED);
        			break;
        		case 1://agentes
        			g.setColor(Color.YELLOW);
        			break;
        		case 2://postos de abastecimento
        			g.setColor(Color.BLUE);
        			break;
        		default:
        			g.setColor(Color.RED);
        			break;
        		}
                g.fillRect(cellX, cellY, 20, 20);
            }
		}
				
		public void fillCell(int x, int y) {
			fillCells.add(new Point(x, y));
			repaint();
		}
	}
}
