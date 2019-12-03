package agents;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.omg.CORBA.PRIVATE_MEMBER;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class Agente_Participativo extends Agent {
	
	protected int posicaoX, posicaoY; 
	protected int capacidade_max_agua, velocidade;
	protected float capacidade_max_combustivel;
	protected int capacidade_agua_presente;
	protected float capacidade_combustivel_presente;
	protected float threshold_combustivel;
	protected int threshold_agua;
	protected boolean esta_a_andar;
	protected String agente_nome;
	protected AID aid_agente;
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

	Point p = new Point(50, 50);
	protected Point sitios_agua = p;
	
	Point q = new Point(60, 60);
	protected Point sitios_combustivel = q;
	
	
	public int getPosicaoX() {
		return posicaoX;
	}

	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}

	public int getPosicaoY() {
		return posicaoY;
	}

	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}

	public String getAgente_nome() {
		return agente_nome;
	}

	public void setAgente_nome(String agente_nome) {
		this.agente_nome = agente_nome;
	}

	public AID getAid_agente() {
		return aid_agente;
	}

	public void setAid_agente(AID aid_agente) {
		this.aid_agente = aid_agente;
	}

	//meter os agentes a arranjarem a posição ideal
	protected void setup(){
		
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
		
		
		/*
		 * System.out.println("Iniciar agente participativo"); super.setup();
		 * 
		 * //posições random (para já) {CAMIÃO} Random rand = new Random();
		 * this.posicaoX = rand.nextInt(100); this.posicaoY = rand.nextInt(100);
		 * this.velocidade = 1; this.capacidade_max_agua = 10;
		 * this.capacidade_max_combustivel = 10; this.capacidade_agua_presente = 10;
		 * this.capacidade_combustivel_presente = 10; this.threshold_combustivel = 5;
		 * this.threshold_agua = 2;
		 * 
		 * DFAgentDescription dfd = new DFAgentDescription(); dfd.setName(getAID());
		 * ServiceDescription sd = new ServiceDescription(); sd.setType("agente");
		 * sd.setName(getLocalName()); dfd.addServices(sd);
		 * 
		 * try { DFService.register(this, dfd);
		 * 
		 * }catch (FIPAException e) { e.printStackTrace(); }
		 * 
		 */
		
		
		
		
		this.addBehaviour(new Recursos(this, 5000));
		this.addBehaviour(new Request(this, 1000));
		//this.addBehaviour(new Receiver());
	}
	
	protected void takeDown() {
		System.out.println("Ending++++++++++++");
		try {
			DFService.deregister(this);
			
		}catch(FIPAException e) {
			e.printStackTrace();
		}
		super.takeDown();
	}
	
	
	protected class Recursos extends TickerBehaviour {
		private Agent ag;
		public Recursos(Agent a, long period) {
			super(a,period);
			ag = a;
		}
		
		
		public void onTick() {
			//calcular a disponibilidade dos recursos atuais
			
			if(!esta_a_andar){
		
				System.out.println( " ::agente:: " + ag.getLocalName() + " || Recursos atuais: água= " + capacidade_agua_presente + " " 
									+ "combustivel= " + capacidade_combustivel_presente );
				Boolean comb_recursos = temRecursosSuficientes_combustivel();
				Boolean agua_recursos = temRecursosSuficientes_agua();
				
				if(comb_recursos == false) {
					//calcular distancia dos postos de combustivel
					
					//deslocar para o posto mais próximo
					
					Point abastecimento = new Point(0,0);
					
					abastecimento = getAbastecimentoMaisProximo(posicaoX, posicaoY, sitios_gasolina);
					
					deslocar(abastecimento.x, abastecimento.y);
					if(abastecimento.x == posicaoX && abastecimento.y == posicaoY) { //chegou ao destino
						capacidade_combustivel_presente = capacidade_max_combustivel;
						System.out.println("Depósito de gasolina atestado!!! " + this.ag.getLocalName());
						//fazer um contador de recursos gastos.....
					}
					
				}
				if(agua_recursos == false) {
					deslocar(sitios_agua.x, sitios_agua.y);
					if(sitios_agua.x == posicaoX && sitios_agua.y == posicaoY) { //chegou ao destino
						capacidade_agua_presente = capacidade_max_agua;
						System.out.println("Depósito de água atestado!!!");
						//fazer um contador de recursos gastos.....
					}
				}
				
				
			}else {
				
			}
		}
		
		
	}
	
	protected class Request extends TickerBehaviour{ //apagar isto e fazer um oneshot a informar sempre que me mexer
		private Agent ag;
		public Request(Agent a, long period) {
			super(a,period);
			this.ag = a;
		}
		
		public void onTick() {
			//enviar ao quartel a posição do agente
			AID receiver = new AID();
			receiver.setLocalName("Quartel");
			
			Custom_Message cm = new Custom_Message();
			cm.setAid_agente(ag.getAID());
			cm.setLocalName_agente(ag.getLocalName());
			cm.setPosicaoX(posicaoX);
			cm.setPosicaoY(posicaoY);
			
			
			ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
//			msg.setContent(" " + posicaoX + ","  + posicaoY + "," + ag.getLocalName() + "," + ag.getAID()); // mais a outro informaçao (serializable)
			
			try {
				msg.setContentObject(cm);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			msg.addReceiver(receiver);
			myAgent.send(msg);

		}
		
		
	}
	
	
	
	protected class Receiver extends CyclicBehaviour {
		
		public void action() {
			//recebe mensagem para apagar fogo, assumindo que o agente tem combustível suficiente
			//if....recursos
			
			ACLMessage msg = receive();
			if( msg != null) {
				if(msg.getPerformative() == ACLMessage.CFP) { //verificar performative
					
					
				}
				else if(msg.getPerformative() == ACLMessage.CONFIRM) { //este é o agente + proximo e tem que ir apagar o fogo...
					System.out.println("TAS NO SITIO ERRADO");
					String[] informacao_recebida = msg.getContent().split(",");
					String xFogoAtivo = informacao_recebida[0];
					String yFogoAtivo = informacao_recebida[1];
					//deslocação até ao fogo
					deslocar(Integer.parseInt(xFogoAtivo),Integer.parseInt(yFogoAtivo));
					
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
	
	
	protected Point getAbastecimentoMaisProximo(float x_inicial, float y_inicial, ArrayList<Point> pontos) {
		
		int x=0;
		int y =0;
		float distance = 100000;
		float distance_aux = 0;
		Point selected = new Point(0, 0);
		System.out.println("A calcular coisas");
		for (Point point : pontos) {
			x = point.x;
			y = point.y;
			distance_aux = (float) Math.sqrt(((Math.pow((x - x_inicial), 2)) + (Math.pow((y - y_inicial), 2))));
			if(distance_aux < distance) {
				distance = distance_aux;
				selected.x = x;
				selected.y = y;
			}
			
		}
		return selected;
	
	}
	
	
	protected float calculaDistancia(float x_inicial, float x_dest, float y_inicial, float y_dest) {
		
		float distance = (float) Math.sqrt(((Math.pow((x_dest - x_inicial), 2)) + (Math.pow((y_dest - y_inicial), 2))));
		return distance;
	
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
		
		System.out.println("distancia percorrida:::::::::::::::::::::" + dist);
		
		float aux = dist/10;
		
		//considerando que se gasta 1 unidade de combustivel por cada 10 unidades de distância da matriz...
		System.out.println("GASTOUUUUUUUUUUUUUUUUUUUU:  " + aux);
		return (aux);
		
	}
	
	protected void deslocar(int x_pos, int y_pos) { //add tickerbehaviour para ver os pontos a deslocarem-se, a cada 1s 
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^andando^^^^^^^^^^^^^^^^^^^^^");
		this.esta_a_andar = true;
		this.posicaoX = x_pos;
		this.posicaoY = y_pos;
		
		
		
		
		this.esta_a_andar = !esta_a_andar;
		
	}
	
	//comunicação entre agentes para saber qual a melhor posição no mapa? (cooperação) [posterior]

	

	
	
		
}
