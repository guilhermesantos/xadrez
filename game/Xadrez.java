package game;

import java.awt.Point;
import java.util.List;

import exceptions.MovimentoInvalidoException;

public class Xadrez {

	private EstadoJogo estadoJogo;

	private Peca pecaSelecionada;
	
	private Point posicaoUltimaPecaSelecionada;

	public Point getPosicaoUltimaPecaSelecionada() {
		return posicaoUltimaPecaSelecionada;
	}

	public Peca getPecaSelecionada() {
		return pecaSelecionada;
	}

	public Xadrez() {
		iniciaNovoJogo();
	}

	public void iniciaNovoJogo() {
		estadoJogo = EstadoJogo.TURNO_PRETO;
		pecaSelecionada = null;
		posicaoUltimaPecaSelecionada = null;
	}

	public void reiniciaJogo() {
		Tabuleiro.getInstance().reinicializaTabuleiro();
		estadoJogo = estadoJogo.TURNO_BRANCO;
	}

	public List<Point> selecionaPeca(int linha, int coluna) throws MovimentoInvalidoException {

		if (Tabuleiro.getInstance().casaEstaVazia(linha, coluna)) {
			throw new MovimentoInvalidoException();
		}

		boolean pecaPertenceAoJogador = (Tabuleiro.getInstance().
				getPeca(linha, coluna).cor == Cor.BRANCO
				&& estadoJogo == EstadoJogo.TURNO_BRANCO)
				|| (Tabuleiro.getInstance().getPeca(linha, coluna).cor == Cor.PRETO
						&& estadoJogo == EstadoJogo.TURNO_PRETO);

		if (!pecaPertenceAoJogador) {
			System.out.println("Peca nao pertence ao jogador");
			throw new MovimentoInvalidoException();
		}
		List<Point> movimentosValidos = Tabuleiro.getInstance().getPeca(linha, coluna).
				getMovimentosValidos(linha,	coluna);
		
		if (movimentosValidos.isEmpty()) {
			System.out.println("Nao ha movimentos validos pra essa peca");
			throw new MovimentoInvalidoException();
		}
		
		pecaSelecionada = Tabuleiro.getInstance().getPeca(linha, coluna);
		posicaoUltimaPecaSelecionada = new Point(linha, coluna);
		
		return movimentosValidos;
	}
	
	public void movePeca(int linhaOrigem, int colunaOrigem, 
			int linhaDestino, int colunaDestino) {
		Peca pecaDeslocada = Tabuleiro.getInstance().getPeca(linhaOrigem, colunaOrigem);
		Tabuleiro.getInstance().removePeca(linhaOrigem, colunaOrigem);
		Tabuleiro.getInstance().colocaPeca(linhaDestino, colunaDestino, pecaDeslocada);
		//Gambiarra. Arrumar
		pecaDeslocada.movePeca();
		if(estadoJogo.equals(EstadoJogo.TURNO_BRANCO)) {
			estadoJogo = EstadoJogo.TURNO_PRETO;
		} else {
			estadoJogo = EstadoJogo.TURNO_BRANCO;
		}
	}
}
