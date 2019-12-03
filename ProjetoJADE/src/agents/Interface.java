package agents;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Interface extends Agent {
	
	protected ArrayList<Point> sitios = new ArrayList<>();
	protected Message_PostosAbastecimento mySitios = new Message_PostosAbastecimento();

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
		
		this.addBehaviour(new InformarPostosAbastecimento());
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
				mySitios.addPosto(p);
			}
			
			System.out.println("SIZE: " + sitios.size());
			ACLMessage mensagem = new ACLMessage(ACLMessage.CFP);
			try {
				mensagem.setContentObject(mySitios);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			AID receiver_drone = new AID();
			receiver_drone.setLocalName("Drone");
			AID receiver_camiao = new AID();
			receiver_camiao.setLocalName("Camiao");
			
			//set receivers
			mensagem.addReceiver(receiver_camiao);
			mensagem.addReceiver(receiver_drone);
			myAgent.send(mensagem);
			
		} 
		
	}
}
