package agents;

import java.io.Serializable;

import jade.core.AID;

public class Custom_Message implements Serializable{
	
	private int posicaoX;
	private int posicaoY;
	private AID aid_agente;
	private String localName_agente;
	
	
	public int getPosicaoX() {
		return posicaoX;
	}

	public int getPosicaoY() {
		return posicaoY;
	}

	public void setPosicaoY(int posicaoY) {
		this.posicaoY = posicaoY;
	}

	public void setPosicaoX(int posicaoX) {
		this.posicaoX = posicaoX;
	}

	public AID getAid_agente() {
		return aid_agente;
	}
	public void setAid_agente(AID aid_agente) {
		this.aid_agente = aid_agente;
	}
	public String getLocalName_agente() {
		return localName_agente;
	}
	public void setLocalName_agente(String localName_agente) {
		this.localName_agente = localName_agente;
	}
	
	
	
}
