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
import jade.lang.acl.UnreadableException;
import jade.util.leap.ArrayList;
import jade.util.leap.HashMap;

import java.awt.Point;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;

public class Quartel extends Agent {

	int x = 0, y= 0;
	float total_combustivel_gasto = 0;
	int total_agua_gasta = 0;
	ArrayList tempos = new ArrayList();
	int fogos_apagados = 0;
	int fogos_zonas_importantes = 0;
	protected java.util.ArrayList<Point> areas = new java.util.ArrayList<>();
	
	protected void setup() {
		super.setup();
		
		System.out.print("Starting Quartel");
		
		//adicionar residencias
		for(int i=5; i<=15; i+=5) {
			areas.add(new Point(i,i));
			areas.add(new Point(i-1,i));
		}
		
		for(int i=33; i<=50; i+=5) {
			areas.add(new Point(i,i));
			areas.add(new Point(i-1,i));
		}
		
		for(int x=20; x<=30; x++) {
			for(int y=20; y<=30; y++) {
				areas.add(new Point(x,y));
			}
		}
		
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
		private String nome_agente;
		
		public EnviarAgente(AID agenteAID, int x, int y, String nome) {
			this.agente = agenteAID;
			System.out.println("O agente é.............................");
			this.x = x;
			this.y = y;
			this.nome_agente = nome;
		}
		
		
		
		public void action() {
			//vai mandar a mensagem para o agente
			ACLMessage msg = new ACLMessage(ACLMessage.CONFIRM);
			System.out.println("agente.getName: " + agente.getName() + " " + "agente.getLocalName: " + nome_agente);
			msg.setContent(x + "," + y);
			msg.addReceiver(agente);
			myAgent.send(msg);
		}
		
		
		
	}
	
	
	
	
	private class Receiver extends CyclicBehaviour{
		private float xFogoAtivo, yFogoAtivo;
		private int xAgente, yAgente;
		private String nome_agente, local_name;
		private int agentesProcessados = 0;
		private float minDistancia = 10000;
		private AID agenteMaisProximo, agente_aid;
		private String agenteMaisProximo_nome;
		private long time = 0;
		private int res=0;
		java.util.HashMap<String, Agente_Participativo> agentes_mapa = new java.util.HashMap<String, Agente_Participativo>();
		
		
		public void action() {
			ACLMessage msg = receive();
			if(msg != null) {
//				try {
//					Custom_Message content =(Custom_Message) msg.getContentObject();
//					System.out.println(content.getAid_agente());
//					
//				} catch (UnreadableException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				//mensagem vinda do incendiário
				if(msg.getPerformative() == ACLMessage.INFORM) {
					
					//receber coordenadas do fogo ativo
					String[] coordenadas = msg.getContent().split(",");
					xFogoAtivo = Integer.parseInt(coordenadas[0]);
					yFogoAtivo = Integer.parseInt(coordenadas[1]);
					
					System.out.println("Há um fogo ativo!!! x: " + xFogoAtivo + "y: " + yFogoAtivo);
					
					for (Point point : areas) {
						if(xFogoAtivo == point.x && yFogoAtivo == point.y) {
							fogos_zonas_importantes++;
							System.out.println("O fogo ativo encontra-se numa zona residencial");
							System.out.println("FOGO COM GRAVIDADE!");
						}
					}
					
					//chamar agentes participativos - drones
					DFAgentDescription template = new DFAgentDescription(); //duplicar e depois fazer merge
					ServiceDescription sd = new ServiceDescription();
					sd.setType("Drone");
					template.addServices(sd);
					
					//meter a contar um timer para o fogo ativo
					time = System.currentTimeMillis();
					
					//chamar agentes participativos - camiao
					DFAgentDescription template_camiao = new DFAgentDescription();
					ServiceDescription sd_camiao = new ServiceDescription();
					sd_camiao.setType("Camiao");
					template_camiao.addServices(sd_camiao);
					
					//chamar agentes participativos - aeronave
					DFAgentDescription template_aeronave = new DFAgentDescription();
					ServiceDescription sd_aeronave = new ServiceDescription();
					sd_aeronave.setType("Aeronave");
					template_aeronave.addServices(sd_aeronave);
					
					
					//resultado de todos os agentes participativos
					DFAgentDescription[] resultado_drones, resultado_camiao, resultado_aeronave;
					
					
					
					
					
					try {
						resultado_drones = DFService.search(myAgent, template);
						resultado_camiao = DFService.search(myAgent, template_camiao);
						resultado_aeronave = DFService.search(myAgent, template_aeronave);
						//resultado = resultado_drones;
						res = resultado_camiao.length + resultado_drones.length + resultado_aeronave.length;
						AID[] agentes;
						agentes = new AID[res];
						
						
						//nao é necessário o parallelbehaviour
						ParallelBehaviour pb = new ParallelBehaviour(myAgent, ParallelBehaviour.WHEN_ALL) {
							
							public int onEnd() {
								System.out.println("Todos os agentes participativos foram analizados");
								return super.onEnd();
							}
						
						};
						
						myAgent.addBehaviour(pb);
						
						
						
						System.out.println("resultado_dos_dois.length: " + res);
						
						//fazer calculo agente mais proximo
						for (Map.Entry<String, Agente_Participativo> entry : agentes_mapa.entrySet()) {
						    System.out.println(entry.getKey() + " = " + entry.getValue());
						}
						
						calcularAgenteMaisProximo(agentes_mapa.values(), xFogoAtivo, yFogoAtivo);
						
						if(agenteMaisProximo_nome != null) {
							System.out.println("a enviar o agente mais proximo ........" + agenteMaisProximo_nome);
							pb.addSubBehaviour(new EnviarAgente(agenteMaisProximo,(int) xFogoAtivo, (int)yFogoAtivo, agenteMaisProximo_nome));
							//limpar variáveis
							agenteMaisProximo = null;
							agentesProcessados = 0; 
							minDistancia = 10000;
						}
						
						
						
					}catch (FIPAException e) {
						e.printStackTrace();
					}
					
				
				
			//mensagem vinda do agente participativo para informar a localização
			} else if(msg.getPerformative() == ACLMessage.REQUEST) {
				//receber coordenadas do agente
				
				try {
					Custom_Message content =(Custom_Message) msg.getContentObject();
					this.agente_aid = content.getAid_agente();
					this.xAgente = content.getPosicaoX();
					this.yAgente = content.getPosicaoY();
					this.local_name = content.getLocalName_agente();
					
					System.out.println("NOME: " + this.local_name + " ||| PosX: " + this.xAgente + " & PosY: " + this.yAgente);
					
				} catch (UnreadableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//preencher hashmap com a key do fogo -- depois!!!!!
				Agente_Participativo agente_novo = new Agente_Participativo();
				agente_novo.setPosicaoX((int)xAgente);
				agente_novo.setAgente_nome(local_name);
				agente_novo.setAid_agente(agente_aid);
				
				
				agente_novo.setPosicaoY((int)yAgente);
				agentes_mapa.put(local_name, agente_novo);
				
				//agentes_mapa.toString();

				
			}else if(msg.getPerformative() == ACLMessage.CONFIRM) {
				System.out.println("FOGO. APAGADO. COM. SUCESSO.");
				this.xFogoAtivo = 0;
				this.yFogoAtivo = 0;
				long time_final = 0;
				fogos_apagados++;
//				time_final = System.currentTimeMillis();
//				long result = time_final - time;
				
				String[] mensagem = msg.getContent().split(",");
				String agua_gasta = mensagem[1].toString();
				String combustivel_gasto = mensagem[2].toString();
				String tipo_agente = mensagem[3].toString();
				
				total_agua_gasta += Integer.parseInt(agua_gasta);
				total_combustivel_gasto += Float.parseFloat(combustivel_gasto);
				
				double res;
				res = Math.random()*5000;
				if(tipo_agente.equalsIgnoreCase("Drone")) {
					res = res/4;
				}
				if(tipo_agente.equalsIgnoreCase("Aeronave")) {
					res = res/2;
				}
				
				System.out.println("TEMPO DURAÇÃO: " + res);
				tempos.add((float)res);
				System.out.println("TOTAL DOS RECURSOS GASTOS ATÉ AGORA:");
				System.out.println("Água: " + total_agua_gasta + " || Combustivel: " + total_combustivel_gasto);
				float media = 0, soma = 0;
				for (Object f :tempos.toList()) {
					soma += Float.parseFloat(f.toString());
				}
				media = soma / tempos.size();
				System.out.println("Média tempos: " + media);
				System.out.println("TOTAL FOGOS APAGADOS: " + fogos_apagados + "  ~ dos quais em zonas residenciais: " + fogos_zonas_importantes);
			}
				
			}else {
				block();
			}
			
		}
		
		//calc
		protected void calcularAgenteMaisProximo(Collection<Agente_Participativo>agentes_ativos, float x, float y) {
			float distancia = 0;
			
			//agentes_mapa;
			System.out.println("simulando coisas");
			for(Agente_Participativo agente : agentes_ativos) {
				distancia = (float) Math.sqrt((y - agente.getPosicaoY()) * (y - agente.getPosicaoY()) + (x - agente.getPosicaoX()) * (x - agente.getPosicaoX()));
				System.out.println(distancia);
				if(distancia < minDistancia) {
					minDistancia = distancia;
					agenteMaisProximo_nome = agente.getAgente_nome();
					agenteMaisProximo = agente.getAid_agente();
					
				}
			}
			agenteMaisProximo_nome.toString();
			System.out.println("agente + próximo: " + agenteMaisProximo_nome);
			
			//dividir isto em listas de agentes_participativos e ver o melhor de cada tipo
			//depois de ter o melhor, ter em conta a velocidade (tempo para chegar ao fogo)
			//depois ter em conta os recursos
			}
	}
}
