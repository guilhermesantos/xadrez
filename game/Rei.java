package game;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import properties.Constantes;

public class Rei extends Peca {
	
	public Rei(Cor cor) {
		super.cor = cor;
		try {
			carregarImagem();
		} catch (IOException e) {
			System.out.println("Erro ao carregar rei.png");
		}
	}

	
	@Override
	protected String getEnderecoImagem() {
		return cor.equals(Cor.PRETO) ? Constantes.ENDERECO_IMAGEM_REI_PRETO :
			 Constantes.ENDERECO_IMAGEM_REI_BRANCO;
	}


	@Override
	public List<Point> getMovimentosValidos(Tabuleiro tabuleiro, int linha, int coluna) {
		List<Point> movimentosValidos = new ArrayList<Point>();
		
		getMovimentosValidosPraDireita(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerda(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraCima(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraBaixo(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaCima(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaBaixo(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaCima(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaBaixo(tabuleiro, linha, coluna, movimentosValidos);
		return movimentosValidos;
	}
	
	private void getMovimentosValidosPraDireita(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha, coluna+1)){
			if(tabuleiro.casaEstaVazia(linha, coluna+1)) {
				movimentosValidos.add(new Point(linha, coluna+1));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha, coluna+1)) {
					movimentosValidos.add(new Point(linha, coluna+1));
				}
			}
		}
	}	

	private void getMovimentosValidosPraEsquerda(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha, coluna-1)){
			if(tabuleiro.casaEstaVazia(linha, coluna-1)) {
				movimentosValidos.add(new Point(linha, coluna-1));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha, coluna-1)) {
					movimentosValidos.add(new Point(linha, coluna-1));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraBaixo(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha+1, coluna)){
			if(tabuleiro.casaEstaVazia(linha+1, coluna)) {
				movimentosValidos.add(new Point(linha+1, coluna));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha+1, coluna)) {
					movimentosValidos.add(new Point(linha+1, coluna));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraCima(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha-1, coluna)){
			if(tabuleiro.casaEstaVazia(linha-1, coluna)) {
				movimentosValidos.add(new Point(linha-1, coluna));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha-1, coluna)) {
					movimentosValidos.add(new Point(linha-1, coluna));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraDireitaCima(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha-1, coluna+1)){
			if(tabuleiro.casaEstaVazia(linha-1, coluna+1)) {
				movimentosValidos.add(new Point(linha-1, coluna+1));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha-1, coluna+1)) {
					movimentosValidos.add(new Point(linha-1, coluna+1));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraDireitaBaixo(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha+1, coluna+1)){
			if(tabuleiro.casaEstaVazia(linha+1, coluna+1)) {
				movimentosValidos.add(new Point(linha+1, coluna+1));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha+1, coluna+1)) {
					movimentosValidos.add(new Point(linha+1, coluna+1));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraEsquerdaCima(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha-1, coluna-1)){
			if(tabuleiro.casaEstaVazia(linha-1, coluna-1)) {
				movimentosValidos.add(new Point(linha-1, coluna-1));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha-1, coluna-1)) {
					movimentosValidos.add(new Point(linha-1, coluna-1));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraEsquerdaBaixo(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha+1, coluna-1)){
			if(tabuleiro.casaEstaVazia(linha+1, coluna-1)) {
				movimentosValidos.add(new Point(linha+1, coluna-1));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha+1, coluna-1)) {
					movimentosValidos.add(new Point(linha+1, coluna-1));
				}
			}
		}
	}
}
