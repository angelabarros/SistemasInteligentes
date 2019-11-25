package agents;

import java.io.Serializable;

import jade.core.AID;

public class Custom_Message implements Serializable{
	
	private float posicaoX;
	private float posicaoY;
	private AID aid_agente;
	private String localName_agente;
	public float getPosicaoX() {
		return posicaoX;
	}
	public void setPosicaoX(float posicaoX) {
		this.posicaoX = posicaoX;
	}
	public float getPosicaoY() {
		return posicaoY;
	}
	public void setPosicaoY(float posicaoY) {
		this.posicaoY = posicaoY;
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
