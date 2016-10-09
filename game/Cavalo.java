package game;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import properties.Constantes;

public class Cavalo extends Peca {
	private static final long serialVersionUID = 3031364078356413277L;

	public Cavalo(Cor cor) {
		super.cor = cor;
		try {
			carregarImagem();
		} catch (IOException e) {
			System.out.println("Erro ao carregar cavalo.png");
		}
	}


	@Override
	protected String getEnderecoImagem() {
		return cor.equals(Cor.PRETO) ? Constantes.ENDERECO_IMAGEM_CAVALO_PRETO :
			 Constantes.ENDERECO_IMAGEM_CAVALO_BRANCO;
	}


	@Override
	public List<Point> getMovimentosValidos(Tabuleiro tabuleiro, int linha, int coluna) {
		List<Point> movimentosValidos = new ArrayList<Point>();
		
		getMovimentoCimaCimaDireita(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentoCimaCimaEsquerda(tabuleiro, linha, coluna, movimentosValidos);
		
		getMovimentoBaixoBaixoDireita(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentoBaixoBaixoEsquerda(tabuleiro, linha, coluna, movimentosValidos);
		
		getMovimentoDireitaDireitaCima(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentoDireitaDireitaBaixo(tabuleiro, linha, coluna, movimentosValidos);
		
		getMovimentoEsquerdaEsquerdaCima(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentoEsquerdaEsquerdaBaixo(tabuleiro, linha, coluna, movimentosValidos);
		return movimentosValidos;
	}
	
	private boolean isMovimentoValido(Tabuleiro tabuleiro, int linha, int coluna, int linhaCandidato, int colunaCandidato) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linhaCandidato, colunaCandidato)) {
			if(tabuleiro.casaEstaVazia(linhaCandidato, colunaCandidato)) {
				return true;
			} else if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linhaCandidato, colunaCandidato)) {
				return true;
			}
		}
		return false;
	}

	private void getMovimentoCimaCimaDireita(Tabuleiro tabuleiro, int linha, 
			int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(tabuleiro, linha, coluna, linha-2, coluna+1)) {
			movimentosValidos.add(new Point(linha-2, coluna+1));
		}
	}
	
	private void getMovimentoCimaCimaEsquerda(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(tabuleiro, linha, coluna, linha-2, coluna-1)) {
			movimentosValidos.add(new Point(linha-2, coluna-1));
		}
	}
	
	private void getMovimentoBaixoBaixoDireita(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(tabuleiro, linha, coluna, linha+2, coluna+1)) {
			movimentosValidos.add(new Point(linha+2, coluna+1));
		}
	}
	
	private void getMovimentoBaixoBaixoEsquerda(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(tabuleiro, linha, coluna, linha+2, coluna-1)) {
			movimentosValidos.add(new Point(linha+2, coluna-1));
		}
	}

	private void getMovimentoDireitaDireitaCima(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(tabuleiro, linha, coluna, linha-1, coluna+2)) {
			movimentosValidos.add(new Point(linha-1, coluna+2));
		}
	}
	
	private void getMovimentoDireitaDireitaBaixo(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(tabuleiro, linha, coluna, linha+1, coluna+2)) {
			movimentosValidos.add(new Point(linha+1, coluna+2));
		}
	}
	
	private void getMovimentoEsquerdaEsquerdaCima(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(tabuleiro, linha, coluna, linha-1, coluna-2)) {
			movimentosValidos.add(new Point(linha-1, coluna-2));
		}
	}
	
	private void getMovimentoEsquerdaEsquerdaBaixo(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(tabuleiro, linha, coluna, linha+1, coluna-2)) {
			movimentosValidos.add(new Point(linha+1, coluna-2));
		}
	}
}
