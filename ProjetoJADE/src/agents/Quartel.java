package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;

public class Quartel extends Agent {

	float x = 0, y= 0;
	
	protected void setup() {
		super.setup();
		
		System.out.print("Starting Quartel");
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Quartel");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		
		try {
			
			DFService.register(this, dfd);
			
		}catch (FIPAException e) {
			e.printStackTrace();
		}
		
		
		this.addBehaviour(new Receiver());
		//this.addBehaviour(new EnviarAgente(this.getAID(),x,y));
	}
	
	
	protected void takeDown() {
		super.takeDown();
	}
	
	
	private class EnviarAgente extends OneShotBehaviour{ 
		
		private float x,y;
		private AID agente;
		
		public EnviarAgente(AID agenteAID, float x, float y) {
			this.agente = agenteAID;
			System.out.println("O agente é.............................");
			this.x = x;
			this.y = y;
		}
		
		
		
		public void action() {
			//vai mandar a mensagem para o agente
			ACLMessage msg = new ACLMessage(ACLMessage.CONFIRM);
			System.out.println("agente.getName: " + agente.getName() + " " + "agente.getLocalName: " + agente.getLocalName());
			msg.setContent(x + "," + y);
			msg.addReceiver(agente);
			myAgent.send(msg);
		}
		
		
		
	}
	
	
	
	
	private class Receiver extends CyclicBehaviour{
		private float xFogoAtivo, yFogoAtivo, xAgente, yAgente;			
		private int agentesProcessados = 0;
		private float minDistancia = 10000;
		private AID agenteMaisProximo;
		
		
		public void action() {
			ACLMessage msg = receive();
			if(msg != null) {
				//mensagem vinda do incendiário
				if(msg.getPerformative() == ACLMessage.INFORM) { //verificar
					
					//receber coordenadas do fogo ativo
					String[] coordenadas = msg.getContent().split(",");
					xFogoAtivo = Float.parseFloat(coordenadas[0]);
					yFogoAtivo = Float.parseFloat(coordenadas[1]);
					
					System.out.println("Há um fogo ativo!!! x: " + xFogoAtivo + "y: " + yFogoAtivo);
					
					//chamar agentes participativos
					DFAgentDescription template = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("Drone");
					template.addServices(sd);
					
					//meter a contar um timer para o fogo ativo
					
					
					//resultado de todos os agentes participativos
					DFAgentDescription[] resultado;
					
					try {
						resultado = DFService.search(myAgent, template);
						AID[] agentes;
						agentes = new AID[resultado.length];
						
						
						//nao é necessário o parallelbehaviour
						ParallelBehaviour pb = new ParallelBehaviour(myAgent, ParallelBehaviour.WHEN_ALL) {
							
							public int onEnd() {
								System.out.println("Todos os agentes participativos foram analizados");
								return super.onEnd();
							}
						
						};
						
						myAgent.addBehaviour(pb);
						
						
						
						System.out.println("resultado.length: " + resultado.length);
						
						
						for (int i = 0; i < resultado.length; i++) {
							agentes[i] = resultado[i].getName();
							pb.addSubBehaviour(new EnviarAgente(agentes[i], xFogoAtivo, yFogoAtivo));
						}
						
						
					}catch (FIPAException e) {
						e.printStackTrace();
					}
					
				
				
			//mensagem vinda do agente participativo para informar a localização
			} else if(msg.getPerformative() == ACLMessage.REQUEST) {
				//receber coordenadas do agente
				String[] coordenadas = msg.getContent().split(",");
				xAgente = Float.parseFloat(coordenadas[0]);
				yAgente = Float.parseFloat(coordenadas[1]);
				//preencher o array
				//calculo do agente + proximo (retorna o agente + proximo)
				agentesProcessados++;
				
				float distancia = (float) Math.sqrt((yFogoAtivo - yAgente) * (yFogoAtivo - yAgente) + (xFogoAtivo - xAgente) * (xFogoAtivo - xAgente));
				if(distancia < minDistancia) {
					minDistancia = distancia;
					agenteMaisProximo = msg.getSender();
				}
				
				System.out.println("17 coisas");
				if(agentesProcessados == 17 ) { //ver isto depois....
					//momento de escolha do agente para apagar fogo
					ACLMessage mensagem = new ACLMessage(ACLMessage.CONFIRM);
					mensagem.addReceiver(agenteMaisProximo);
					mensagem.setContent(xFogoAtivo + "," + yFogoAtivo);
					myAgent.send(mensagem);
					agentesProcessados = 0;
					minDistancia = 10000;
					agenteMaisProximo = null;
				}
				
				
				
			}else if(msg.getPerformative() == ACLMessage.CONFIRM) {
				System.out.println("FOGO. APAGADO. COM. SUCESSO.");
			}
				
			}else {
				block();
			}
			
		}
	}
}
