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
