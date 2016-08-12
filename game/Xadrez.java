package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import exceptions.MovimentoInvalidoException;

public class Xadrez {

	private Tabuleiro tabuleiro;

	private EstadoJogo estadoJogo;

	private Peca pecaSelecionada;
	
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

	public List<Point> selecionaPeca(int linha, int coluna) throws MovimentoInvalidoException {

		if (tabuleiro.casaEstaVazia(linha, coluna)) {
			throw new MovimentoInvalidoException();
		}

		boolean pecaPertenceAoJogador = (tabuleiro.getPeca(linha, coluna).cor == Cor.BRANCO
				&& estadoJogo == EstadoJogo.TURNO_BRANCO)
				|| (tabuleiro.getPeca(linha, coluna).cor == Cor.PRETO && estadoJogo == EstadoJogo.TURNO_PRETO);

		if (!pecaPertenceAoJogador) {
			System.out.println("Peca nao pertence ao jogador");
			throw new MovimentoInvalidoException();
		}
		List<Point> movimentosValidos = calculaMovimentosValidosDaPeca(linha, coluna);
		if (movimentosValidos.isEmpty()) {
			System.out.println("Nao ha movimentos validos pra essa peca");
			throw new MovimentoInvalidoException();
		}
		return movimentosValidos;
	}

	public void movePeca(int linhaOrigem, int colunaOrigem, int linhaDestino, int colunaDestino) {

	}

	private List<Point> calculaMovimentosValidosDaPeca(int linha, int coluna) {
		
		Peca peca = tabuleiro.getPeca(linha, coluna);
		System.out.println("Tipo da peca selecionada: " + peca.getTipoPeca());
		if (peca.getTipoPeca() == TipoPeca.PEAO) {
			System.out.println("Peca selecionada eh um peao");
			return calculaMovimentosValidosDoPeao(linha, coluna);
		} else if (peca.getTipoPeca() == TipoPeca.TORRE) {
			return calculaMovimentosValidosDaTorre(linha, coluna);
		} else if (peca.getTipoPeca() == TipoPeca.CAVALO) {
			return calculaMovimentosValidosDoCavalo(linha, coluna);
		} else if (peca.getTipoPeca() == TipoPeca.BISPO) {
			return calculaMovimentosValidosDoBispo(linha, coluna);
		} else if (peca.getTipoPeca() == TipoPeca.REI) {
			return calculaMovimentosValidosDoRei(linha, coluna);
		} else if (peca.getTipoPeca() == TipoPeca.RAINHA) {
			return calculaMovimentosValidosDaRainha(linha, coluna);
		}
		List<Point> movimentosValidos = new ArrayList<Point>();
		return movimentosValidos;
	}
	
	private List<Point> calculaMovimentosValidosDoPeao(int linha, int coluna) {
		List<Point> movimentosValidos = new ArrayList<Point>();
		Peca peca = tabuleiro.getPeca(linha, coluna);
		if(peca.getCor() == Cor.BRANCO) {
			for(int i=1; i <= 2; i++) {
				if(tabuleiro.getPeca(linha-i, coluna) == null) {
					movimentosValidos.add(new Point(linha-i, coluna));
				}
			}
		} else {
			for(int i=1; i <= 2; i++) {
				if(tabuleiro.getPeca(linha+i, coluna) == null) {
					movimentosValidos.add(new Point(linha+i, coluna));
				}
			}
		}
		return movimentosValidos;
	}
	private boolean jaTemPecaOcupandoEspaco(int linha, int coluna) {
		Peca peca = tabuleiro.getPeca(linha, coluna);
		return peca != null;
	}

	private List<Point> calculaMovimentosValidosDaTorre(int linha, int coluna) {
		for(int i=0; i < 8; i++) {
			
		}
		return null;
	}

	private List<Point> calculaMovimentosValidosDoCavalo(int linha, int coluna) {
		return null;
	}

	private List<Point> calculaMovimentosValidosDoBispo(int linha, int coluna) {
		return null;
	}

	private List<Point> calculaMovimentosValidosDoRei(int linha, int coluna) {
		return null;
	}

	private List<Point> calculaMovimentosValidosDaRainha(int linha, int coluna) {
		return null;
	}
}
