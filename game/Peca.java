package game;

import java.awt.Point;

public abstract class Peca {
	public Point posicao;

	public Point getPosicao() {
		return posicao;
	}

	public void setPosicao(Point posicao) {
		this.posicao = posicao;
	}
	
}
