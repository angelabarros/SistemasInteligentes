package agents;

import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class Incendiario extends Agent {

	private int fogo_posicao_x;
	private int fogo_posicao_y;
	
	
	@Override
	protected void setup() {
		// TODO Auto-generated method stub
		super.setup();
		
		this.addBehaviour(new Request(this, 20000));
	
		
	}
	
	
	private class Request extends TickerBehaviour{
		
		public Request(Agent a, long period) {
			super(a,period);
		}
		
		public void onTick() {
			
			//Posição aleatória para atear o fogo
			Random rand = new Random();
			int posX = rand.nextInt(100);
			int posY = rand.nextInt(100);
			
			posX = 10;
			posY = 10;
			
			//enviar ao quartel a posição do fogo
			AID receiver = new AID();
			AID receiver_interface = new AID();
			receiver.setLocalName("Quartel");
			receiver_interface.setLocalName("Interface");
			
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.setContent(posX + ","  + posY );
			msg.addReceiver(receiver);
			msg.addReceiver(receiver_interface);
			System.out.println("a enviar mensagem p/ quartel.....");
			myAgent.send(msg);
					
		}
	}

	
	
}
