package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import exceptions.ClicouNoMeioDoNadaException;
import exceptions.MovimentoInvalidoException;
import exceptions.NaoHaMovimentosValidosException;
import exceptions.PecaNaoPertenceAoJogadorException;

public class Xadrez extends Observable {

	private EstadoJogo estadoJogo;

	private Point posicaoUltimaPecaSelecionada;
	
	private List<Point> movimentosValidos;

	public Point getPosicaoUltimaPecaSelecionada() {
		return posicaoUltimaPecaSelecionada;
	}

	public Xadrez(Observer observer) {
		this.addObserver(observer);
		iniciaNovoJogo();
	}

	public void iniciaNovoJogo() {
		Tabuleiro.getInstance().reinicializaTabuleiro();
		estadoJogo = EstadoJogo.TURNO_BRANCO;
		posicaoUltimaPecaSelecionada = null;
		movimentosValidos = new ArrayList<Point>();
	}

	public List<Point> selecionaPeca(int linha, int coluna) throws PecaNaoPertenceAoJogadorException, NaoHaMovimentosValidosException, ClicouNoMeioDoNadaException {

		if (Tabuleiro.getInstance().casaEstaVazia(linha, coluna)) {
			throw new ClicouNoMeioDoNadaException();
		}

		boolean pecaPertenceAoJogador = (Tabuleiro.getInstance().
				getPeca(linha, coluna).cor == Cor.BRANCO
				&& estadoJogo == EstadoJogo.TURNO_BRANCO)
				|| (Tabuleiro.getInstance().getPeca(linha, coluna).cor == Cor.PRETO
						&& estadoJogo == EstadoJogo.TURNO_PRETO);

		if (!pecaPertenceAoJogador) {
			System.out.println("Peca nao pertence ao jogador");
			throw new PecaNaoPertenceAoJogadorException();
		}
		movimentosValidos = Tabuleiro.getInstance().getPeca(linha, coluna).
				getMovimentosValidos(linha,	coluna);
		
		if (movimentosValidos.isEmpty()) {
			System.out.println("Nao ha movimentos validos pra essa peca");
			throw new NaoHaMovimentosValidosException();
		}
		
		posicaoUltimaPecaSelecionada = new Point(linha, coluna);
		
		return movimentosValidos;
	}
	
	public void movePeca(int linhaOrigem, int colunaOrigem, 
			int linhaDestino, int colunaDestino) {
		Peca pecaDeslocada = Tabuleiro.getInstance().getPeca(linhaOrigem, colunaOrigem);
		
		boolean estaCapturandoUmaPeca = !Tabuleiro.getInstance().casaEstaVazia(linhaDestino, colunaDestino);
		if(estaCapturandoUmaPeca) {
			
			boolean pecaCapturadaEhORei = Tabuleiro.getInstance().getPeca(linhaDestino, colunaDestino) instanceof Rei;
			if(pecaCapturadaEhORei) {
				setChanged();
				notifyObservers(estadoJogo);
			}
		}
		Tabuleiro.getInstance().removePeca(linhaOrigem, colunaOrigem);
		Tabuleiro.getInstance().colocaPeca(linhaDestino, colunaDestino, pecaDeslocada);

		if(pecaDeslocada instanceof Peao) {
			((Peao)pecaDeslocada).setPrimeiroMovimento(false);
		}
		
		if(estadoJogo.equals(EstadoJogo.TURNO_BRANCO)) {
			estadoJogo = EstadoJogo.TURNO_PRETO;
		} else {
			estadoJogo = EstadoJogo.TURNO_BRANCO;
		}
	}
}
