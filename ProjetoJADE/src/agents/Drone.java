package agents;

import java.util.Random;

import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Drone extends Agente_Participativo {
	
	protected void setup(){
		
		System.out.println("Iniciar agente drone!");
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
		sd.setType("agente");
		sd.setName(getLocalName());
		dfd.addServices(sd);
		
		try {
			DFService.register(this, dfd);
			
		}catch (FIPAException e) {
			e.printStackTrace();
		}
		
	}
}
