package game;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import properties.Constantes;

public class Cavalo extends Peca {
	
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
	public List<Point> getMovimentosValidos(int linha, int coluna) {
		List<Point> movimentosValidos = new ArrayList<Point>();
		
		getMovimentoCimaCimaDireita(linha, coluna, movimentosValidos);
		getMovimentoCimaCimaEsquerda(linha, coluna, movimentosValidos);
		
		getMovimentoBaixoBaixoDireita(linha, coluna, movimentosValidos);
		getMovimentoBaixoBaixoEsquerda(linha, coluna, movimentosValidos);
		
		getMovimentoDireitaDireitaCima(linha, coluna, movimentosValidos);
		getMovimentoDireitaDireitaBaixo(linha, coluna, movimentosValidos);
		
		getMovimentoEsquerdaEsquerdaCima(linha, coluna, movimentosValidos);
		getMovimentoEsquerdaEsquerdaBaixo(linha, coluna, movimentosValidos);
		return movimentosValidos;
	}
	
	private boolean isMovimentoValido(int linha, int coluna, int linhaCandidato, int colunaCandidato) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linhaCandidato, colunaCandidato)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linhaCandidato, colunaCandidato)) {
				return true;
			} else if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linhaCandidato, colunaCandidato)) {
				return true;
			}
		}
		return false;
	}

	private void getMovimentoCimaCimaDireita(int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(linha, coluna, linha-2, coluna+1)) {
			movimentosValidos.add(new Point(linha-2, coluna+1));
		}
	}
	
	private void getMovimentoCimaCimaEsquerda(int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(linha, coluna, linha-2, coluna-1)) {
			movimentosValidos.add(new Point(linha-2, coluna-1));
		}
	}
	
	private void getMovimentoBaixoBaixoDireita(int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(linha, coluna, linha+2, coluna+1)) {
			movimentosValidos.add(new Point(linha+2, coluna+1));
		}
	}
	
	private void getMovimentoBaixoBaixoEsquerda(int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(linha, coluna, linha+2, coluna-1)) {
			movimentosValidos.add(new Point(linha+2, coluna-1));
		}
	}

	private void getMovimentoDireitaDireitaCima(int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(linha, coluna, linha-1, coluna+2)) {
			movimentosValidos.add(new Point(linha-1, coluna+2));
		}
	}
	
	private void getMovimentoDireitaDireitaBaixo(int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(linha, coluna, linha+1, coluna+2)) {
			movimentosValidos.add(new Point(linha+1, coluna+2));
		}
	}
	
	private void getMovimentoEsquerdaEsquerdaCima(int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(linha, coluna, linha-1, coluna-2)) {
			movimentosValidos.add(new Point(linha-1, coluna-2));
		}
	}
	
	private void getMovimentoEsquerdaEsquerdaBaixo(int linha, int coluna, List<Point> movimentosValidos) {
		if(isMovimentoValido(linha, coluna, linha+1, coluna-2)) {
			movimentosValidos.add(new Point(linha+1, coluna-2));
		}
	}
}
