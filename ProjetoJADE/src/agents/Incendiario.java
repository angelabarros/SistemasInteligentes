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
		
		this.addBehaviour(new Request(this, 10000));
	
		
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
			
			fogo_posicao_x = posX;
			fogo_posicao_y = posY;
			
			//enviar ao quartel a posição do fogo
			AID receiver = new AID();
			receiver.setLocalName("Quartel");
			//meter aqui o AID do interface
			
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.setContent(" " + posX + ","  + posY );
			msg.addReceiver(receiver);
			System.out.println("a enviar mensagem p/ quartel.....");
			myAgent.send(msg);
			
			
			//enviar à interface a posição do fogo
//			AID receiver2 = new AID();
//			receiver2.setLocalName("interface");
//			
//			ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM);
//			msg2.setContent("PosiçãoX: " + posX + " " + "PosiçãoY: " + posY );
//			msg2.addReceiver(receiver2);
//			myAgent.send(msg2);
			
			
			
		}
	}

	
	
}
