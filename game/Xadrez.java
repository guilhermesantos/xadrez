package game;

import exceptions.MovimentoInvalidoException;

public class Xadrez {
	
	private Tabuleiro tabuleiro;
	
	private EstadoJogo estadoJogo;
	
	public Xadrez() {
		iniciaNovoJogo();
	}
	
	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}
	
	public void iniciaNovoJogo() {
		tabuleiro = new Tabuleiro();
		estadoJogo = EstadoJogo.TURNO_BRANCO;
	}
	
	public void reiniciaJogo() {
		tabuleiro.reinicializaTabuleiro();
		estadoJogo = estadoJogo.TURNO_BRANCO;
	}
	
	public void movePeca(int linha, int coluna) throws MovimentoInvalidoException {

		if(tabuleiro.casaEstaVazia(linha, coluna)) {
			throw new MovimentoInvalidoException();
		} 

		boolean pecaPertenceAoJogador = 
				(tabuleiro.getPeca(linha, coluna).cor == Cor.BRANCO && estadoJogo == EstadoJogo.TURNO_BRANCO)
				|| (tabuleiro.getPeca(linha, coluna).cor == Cor.PRETO && estadoJogo == EstadoJogo.TURNO_PRETO);
		if(!pecaPertenceAoJogador) {
			throw new MovimentoInvalidoException();
		}
		
	}
}
