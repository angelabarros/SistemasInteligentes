package agents;

import java.util.Random;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

//meter a info numa classe auxiliar, importar a classe e começar a utilizar as funçoes.

public class Drone extends Agente_Participativo {
	
	protected void setup(){
		
		System.out.println("Iniciar agente drone!" + this.getLocalName());
		super.setup();
		
		
		//posições random {drone}
		Random rand = new Random();
		this.posicaoX = rand.nextInt(100);
		this.posicaoY = rand.nextInt(100);
		this.velocidade = 1;
		this.capacidade_max_agua = 10;
		this.capacidade_max_combustivel = 10;
		this.capacidade_agua_presente = 10;
		this.capacidade_combustivel_presente = 10;
		this.threshold_combustivel = 5;
		this.threshold_agua = 2;
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Drone");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
			
		}catch (FIPAException e) {
			e.printStackTrace();
		}
		
		this.addBehaviour(new Receiver2());
		
	}
	
protected class Receiver2 extends CyclicBehaviour {
		private int agua_gasta = 1;
		public void action() {
			//recebe mensagem para apagar fogo, assumindo que o agente tem combustível suficiente
			//if....recursos
			
			ACLMessage msg = receive();
			if( msg != null) {
				
				if(msg.getPerformative() == ACLMessage.CFP) { //verificar performative
					
					
				}
				else if(msg.getPerformative() == ACLMessage.CONFIRM) { //este é o agente + proximo e tem que ir apagar o fogo...
					System.out.println("MENSAGEM RECEBIDA: " + msg.getContent());
					
					String[] informacao_recebida = msg.getContent().split(",");
					String xFogoAtivo = informacao_recebida[0];
					String yFogoAtivo = informacao_recebida[1];
					//deslocação até ao fogo
					
					float valor_a_gastar = calculaRecursos(Float.parseFloat(xFogoAtivo), Float.parseFloat(yFogoAtivo));
					System.out.println("o valor a gastar é ------------------------------ " + valor_a_gastar);
					
					if((capacidade_combustivel_presente - valor_a_gastar) < threshold_combustivel) {
						//copiar código
						
					}
					
					int aux_x = (int) Float.parseFloat(xFogoAtivo);
					int aux_y = (int) Float.parseFloat(yFogoAtivo);
					deslocar(aux_x,aux_y);
			 		
					//apagar o fogo
					if(Float.parseFloat(xFogoAtivo) == posicaoX && Float.parseFloat(yFogoAtivo) == posicaoY) { //garantir que o agente chegou ao incendio
						//atualizar os recursos
						capacidade_agua_presente -= agua_gasta;
						capacidade_combustivel_presente -= valor_a_gastar;
						yFogoAtivo = "0.0";
						xFogoAtivo = "0.0";
						
					}
					
					
					//enviar informação ao quartel de que o fogo está apagado
					AID receiver = new AID();
					receiver.setLocalName("Quartel");
					
					ACLMessage mensagem_sucesso = new ACLMessage(ACLMessage.CONFIRM);
					mensagem_sucesso.setContent("sucesso" + "," + agua_gasta + "," + valor_a_gastar);
					mensagem_sucesso.addReceiver(receiver);
					myAgent.send(mensagem_sucesso);
				}
				
			}
			
			
		}
		
		
	}
	
}
