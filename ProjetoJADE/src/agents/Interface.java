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


//Versão com Java Swing
//package agents;
//import jade.core.AID;
//import jade.core.Agent;
//import jade.core.behaviours.CyclicBehaviour;
//import jade.core.behaviours.OneShotBehaviour;
//import jade.core.behaviours.ParallelBehaviour;
//import jade.domain.DFService;
//import jade.domain.FIPAException;
//import jade.domain.FIPAAgentManagement.DFAgentDescription;
//import jade.domain.FIPAAgentManagement.ServiceDescription;
//import jade.lang.acl.ACLMessage;
//import jade.lang.acl.UnreadableException;
//import java.awt.Color;
//import java.awt.Graphics;
//import java.awt.Point;
//import java.io.IOException;
//import java.util.List;
//import java.util.Random;
//import java.util.ArrayList;
//import javax.swing.JFrame;
//import javax.swing.*;
//public class Interface extends Agent {
//	protected ArrayList<Point> sitios_gasolina = new ArrayList<>();
//	protected ArrayList<Point> sitios_water = new ArrayList<>();
//	protected java.util.ArrayList<Point> areas = new java.util.ArrayList<>();
//	Grid grid = new Grid();
//	protected void setup(){	
//		super.setup();
//		System.out.print("Starting Interface");
//		DFAgentDescription dfd = new DFAgentDescription();
//		dfd.setName(getAID());
//		ServiceDescription sd = new ServiceDescription();
//		sd.setType("Interface");
//		sd.setName(getLocalName());
//		dfd.addServices(sd);
//		try {
//			DFService.register(this, dfd);
//		}catch (FIPAException e) {
//			e.printStackTrace();
//		}
//		sitios_water.add(new Point(2,5));
//		sitios_water.add(new Point(10,10));
//		sitios_water.add(new Point(40,40));
//		sitios_water.add(new Point(37,14));
//		sitios_water.add(new Point(22,5));
//		sitios_water.add(new Point(39,25));
//		sitios_water.add(new Point(45,47));
//		sitios_water.add(new Point(16,49));
//		sitios_water.add(new Point(30,36));
//		sitios_water.add(new Point(10,42));		
//		sitios_gasolina.add(new Point(2,2));
//		sitios_gasolina.add(new Point(5,5));
//		sitios_gasolina.add(new Point(44,16));
//		sitios_gasolina.add(new Point(15,27));
//		sitios_gasolina.add(new Point(24,48));
//		sitios_gasolina.add(new Point(34,11));
//		sitios_gasolina.add(new Point(45,15));
//		sitios_gasolina.add(new Point(47,24));
//		sitios_gasolina.add(new Point(25,20));
//		sitios_gasolina.add(new Point(39,33));
//		for(int i=5; i<=15; i+=5) {
//			areas.add(new Point(i,i));
//			areas.add(new Point(i-1,i));
//		}
//		for(int i=33; i<=50; i+=5) {
//			areas.add(new Point(i,i));
//			areas.add(new Point(i-1,i));
//		}
//		for(int x=20; x<=30; x++) {
//			for(int y=20; y<=30; y++) {
//				areas.add(new Point(x,y));
//			}
//		}
//		//Desenhar a grelha do mapa		
//		grid.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
//		grid.setSitios_gasolina(sitios_gasolina);
//		grid.setSitios_water(sitios_water);
//		for (Point ponto: sitios_gasolina) {
//			grid.fillCell(ponto.x, ponto.y, Color.YELLOW);
//		}
//		for (Point ponto: sitios_water) {
//			grid.fillCell(ponto.x, ponto.y, Color.BLUE);
//		}
//		for (Point ponto: areas) {
//			grid.fillCell(ponto.x, ponto.y, Color.GRAY);
//		}
//		/*for(int i = 0; i < 10; i++) {
//			Random rand = new Random();
//			int posX = rand.nextInt(50);
//			int posY = rand.nextInt(50);
//			grid.fillCell(posX, posY, Color.GREEN);
//		}
//		for(int i=0; i<5; i++) {
//			Random rand = new Random();
//			int posX = rand.nextInt(50);
//			int posY = rand.nextInt(50);
//			grid.fillCell(posX, posY, Color.ORANGE);
//		}
//		for(int i=0; i<2; i++) {
//			Random rand = new Random();
//			int posX = rand.nextInt(50);
//			int posY = rand.nextInt(50);
//			grid.fillCell(posX, posY, Color.PINK);
//		}
//		grid.fillCell(15, 10, Color.RED);
//		grid.fillCell(40, 40, Color.RED);
//		grid.fillCell(34, 23, Color.RED);*/
//		this.addBehaviour(new Receiver());
//	}
//	protected void takeDown() {
//		System.out.println("+++Ending+++Interface+++");
//		try {
//			DFService.deregister(this);
//		}catch(FIPAException e) {
//			e.printStackTrace();
//		}
//		super.takeDown();
//	}
//	private class Receiver extends CyclicBehaviour{
//		private float xFogoAtivo, yFogoAtivo;
//		private int xAgente, yAgente;
//		private int res=0;
//		java.util.HashMap<String, Agente_Participativo> agentes_mapa = new java.util.HashMap<String, Agente_Participativo>();
//		public void action() {
//			ACLMessage msg = receive();
//			if(msg != null) {
//				if(msg.getPerformative() == ACLMessage.INFORM) {					
//					//receber coordenadas do fogo ativo
//					String[] coordenadas = msg.getContent().split(",");
//					xFogoAtivo = Integer.parseInt(coordenadas[0]);
//					yFogoAtivo = Integer.parseInt(coordenadas[1]);
//					grid.fillCell((int)xFogoAtivo, (int)yFogoAtivo, Color.RED);
//					//grid.
//					System.out.println("FOGO AQUI");
//					SwingUtilities.invokeLater(new Runnable() {
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//							grid.fillCell((int)xFogoAtivo, (int)yFogoAtivo, Color.RED);
//							grid.repaint();
//						}
//					});
//			} 	
//			/*else if(msg.getPerformative() == ACLMessage.REQUEST) {
//				try {
//					Custom_Message content =(Custom_Message) msg.getContentObject();
//					this.xAgente = content.getPosicaoX();
//					this.yAgente = content.getPosicaoY();
//					grid.fillCell(xAgente, yAgente);
//				} catch (UnreadableException e1) {
//					e1.printStackTrace();
//				}
//			}
//			else if(msg.getPerformative() == ACLMessage.CONFIRM) {
//				try {
//					Custom_Message content = (Custom_Message) msg.getContentObject();
//					this.xFogoAtivo = content.getPosicaoX();
//					this.yFogoAtivo = content.getPosicaoY();
//					grid.fillCell((int)xFogoAtivo, (int)yFogoAtivo);
//				} catch (UnreadableException e) {
//					e.printStackTrace();
//				}
//			}else {
//				block();
//			}	*/		
//		}
//	}
//}
//	public static class Grid extends JFrame {
//		private List<Point> fillCells;
//		private Color color;
//		protected ArrayList<Point> sitios_gasolina = new ArrayList<>();
//		protected ArrayList<Point> sitios_water = new ArrayList<>();
//		private Graphics grid;
//		public ArrayList<Point> getSitios_gasolina() {
//			return sitios_gasolina;
//		}
//		public void setSitios_gasolina(ArrayList<Point> sitios_gasolina) {
//			this.sitios_gasolina = sitios_gasolina;
//		}
//		public ArrayList<Point> getSitios_water() {
//			return sitios_water;
//		}
//		public void setSitios_water(ArrayList<Point> sitios_water) {
//			this.sitios_water = sitios_water;
//		}
//		public Color getColor() {
//			return color;
//		}
//		public void setColor(Color color) {
//			this.color = color;
//		}
//		public Grid() {
//            fillCells = new ArrayList<>(25);
//			setSize(800,900);
//			setVisible(true);			
//		}
//		public void paint(Graphics grid) {			
//			this.grid = grid;
//			for (int x = 10; x <= 500; x += 10) {
//				for (int y = 10; y <= 500; y += 10) {
//					grid.drawRect(x, 10 + y, 10, 10);
//				}
//			}		
//			for (Point fillCell : fillCells) {
//                int cellX = 10 + (fillCell.x * 10);
//                int cellY = 10 + (fillCell.y * 10);            		
//                grid.setColor(Color.RED);
//                grid.fillRect(cellX, cellY, 10, 10);
//            }			
//		}
//		public void fillCell(int x, int y, Color cor) {
////			this.setColor(cor);
//			grid.toString();
//			grid.setColor(cor);
////			fillCells.add(new Point(x, y));
//			int cellX = 10 + (x * 10);
//            int cellY = 10 + (y * 10);
//			grid.fillRect(cellX, cellY, 10, 10);
//			grid.setColor(Color.BLACK);
//			repaint();
//		}		
//	}
//}

