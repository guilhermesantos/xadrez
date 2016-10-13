package network;

import java.io.Serializable;

import game.Xadrez;

public class Mensagem implements Serializable {

	private static final long serialVersionUID = -3882436475149236772L;
	private Xadrez jogo;
	private String texto;
	
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public Xadrez getJogo() {
		return jogo;
	}
	public void setJogo(Xadrez jogo) {
		this.jogo = jogo;
	}
}
