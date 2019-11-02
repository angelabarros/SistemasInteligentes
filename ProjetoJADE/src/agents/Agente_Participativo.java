package agents;

import java.util.Random;

import org.omg.CORBA.PRIVATE_MEMBER;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Agente_Participativo extends Agent {
	
	private float posicaoX, posicaoY; 
	private int capacidade_max_agua, velocidade;
	private int capacidade_max_combustivel;
	private int capacidade_agua_presente;
	private int capacidade_combustivel_presente;
	private int threshold_combustivel;
	private int threshold_agua;
	private boolean esta_a_andar;
	
	
	protected void setup(){
		
		System.out.println("Iniciar agente participativo");
		super.setup();
		
		//posições random (para já) {CAMIÃO}
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
		sd.setType("agente");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
			
		}catch (FIPAException e) {
			e.printStackTrace();
		}
		
		
		
		this.addBehaviour(new Recursos(this, 5000));
		this.addBehaviour(new Receiver());
	}
	
	
	private class Recursos extends TickerBehaviour {
		
		public Recursos(Agent a, long period) {
			super(a,period);
		}
		
		public void onTick() {
			//calcular a disponibilidade dos recursos atuais
			
			if(!esta_a_andar){
		
				System.out.println("Recursos atuais: água= " + capacidade_agua_presente + " " 
									+ "combustivel= " + capacidade_combustivel_presente);
				Boolean comb_recursos = temRecursosSuficientes_combustivel();	//esta verificação deve ficar num tickerbehaviour? ou num cyclicbehaviour?
				
				if(comb_recursos == false) {
					//como chamar a função de abastecimento?
					//pode ser aqui? dentro do onTick?
					//criação de um behaviour (simplebehaviour) dentro de outro behaviour?
				}
			}else {
				
			}
		}
		
		
	}
	
	private class Request extends SimpleBehaviour{
		
		public void action() {
			//enviar ao quartel a posição do agente
			AID receiver = new AID();
			receiver.setLocalName("Quartel");
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			msg.setContent(" " + posicaoX + ","  + posicaoY );
			msg.addReceiver(receiver);
			myAgent.send(msg);

		}
		
		public boolean done() {
			return true;
		}
		
	}
	
	
	
	private class Receiver extends CyclicBehaviour {
		
		public void action() {
			//recebe mensagem para apagar fogo, assumindo que o agente tem combustível suficiente
			//if....recursos
			
			ACLMessage msg = receive();
			if( msg != null) {
				if(msg.getPerformative() == ACLMessage.CFP) { //verificar performative
					
					
				}
				else if(msg.getPerformative() == ACLMessage.CONFIRM) { //este é o agente + proximo e tem que ir apagar o fogo...
					String[] informacao_recebida = msg.getContent().split(",");
					String xFogoAtivo = informacao_recebida[0];
					String yFogoAtivo = informacao_recebida[1];
					//deslocação até ao fogo
					deslocar(Float.parseFloat(xFogoAtivo),Float.parseFloat(yFogoAtivo));
					
					//apagar o fogo
					if(Float.parseFloat(xFogoAtivo) == posicaoX && Float.parseFloat(yFogoAtivo) == posicaoY) { //garantir que o agente chegou ao incendio
						//atualizar os recursos
						capacidade_agua_presente--;
						float valor_gasto = calculaRecursos(Float.parseFloat(xFogoAtivo), Float.parseFloat(yFogoAtivo));
						capacidade_combustivel_presente -= valor_gasto;
						
					}
					
					//enviar informação ao quartel de que o fogo está apagado
					AID receiver = new AID();
					receiver.setLocalName("Quartel");
					
					ACLMessage mensagem_sucesso = new ACLMessage(ACLMessage.CONFIRM);
					mensagem_sucesso.setContent("sucesso");
					mensagem_sucesso.addReceiver(receiver);
					myAgent.send(mensagem_sucesso);
				}
				
			}
			
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	protected float calculaDistancia(float x_inicial, float x_dest, float y_inicial, float y_dest) {
		
		return (float) Math.sqrt((y_dest - y_inicial) * (y_dest - y_inicial) + (x_dest - x_inicial) * (x_dest - x_inicial));
	
	}
	
	
	
	protected boolean temRecursosSuficientes_combustivel() {
		
		if(this.capacidade_combustivel_presente <= this.threshold_combustivel) {
			return false;
		}else {
			return true;
		}
		
	}
	
	//verificar se existe agua suficiente
	protected boolean temRecursosSuficientes_agua() {
		
		if(this.capacidade_agua_presente <= this.threshold_agua) {
			return false;
		}else {
			return true;
		}
		
	}
	
	
	//verificar o combustivel gasto
	protected float calculaRecursos(float X, float Y) {
		
		float dist = this.calculaDistancia(this.posicaoX, X, this.posicaoY, Y);
		
		//considerando que se gasta 1 unidade de combustivel por cada 10 unidades de distância da matriz...
		
		return dist/10;
		
	}
	
	protected void deslocar(float x_pos, float y_pos) {
		this.esta_a_andar = true;
		this.posicaoX = x_pos;
		this.posicaoY = y_pos;
		this.esta_a_andar = !esta_a_andar;
		
	}
	
	

	
	
	
	
	
		
}
