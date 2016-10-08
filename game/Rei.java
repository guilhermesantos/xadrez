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
	public List<Point> getMovimentosValidos(int linha, int coluna) {
		List<Point> movimentosValidos = new ArrayList<Point>();
		
		getMovimentosValidosPraDireita(linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerda(linha, coluna, movimentosValidos);
		getMovimentosValidosPraCima(linha, coluna, movimentosValidos);
		getMovimentosValidosPraBaixo(linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaCima(linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaBaixo(linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaCima(linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaBaixo(linha, coluna, movimentosValidos);
		return movimentosValidos;
	}
	
	private void getMovimentosValidosPraDireita(int linha, int coluna, List<Point> movimentosValidos) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha, coluna+1)){
			if(Tabuleiro.getInstance().casaEstaVazia(linha, coluna+1)) {
				movimentosValidos.add(new Point(linha, coluna+1));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha, coluna+1)) {
					movimentosValidos.add(new Point(linha, coluna+1));
				}
			}
		}
	}	

	private void getMovimentosValidosPraEsquerda(int linha, int coluna, List<Point> movimentosValidos) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha, coluna-1)){
			if(Tabuleiro.getInstance().casaEstaVazia(linha, coluna-1)) {
				movimentosValidos.add(new Point(linha, coluna-1));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha, coluna-1)) {
					movimentosValidos.add(new Point(linha, coluna-1));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraBaixo(int linha, int coluna, List<Point> movimentosValidos) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha+1, coluna)){
			if(Tabuleiro.getInstance().casaEstaVazia(linha+1, coluna)) {
				movimentosValidos.add(new Point(linha+1, coluna));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha+1, coluna)) {
					movimentosValidos.add(new Point(linha+1, coluna));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraCima(int linha, int coluna, List<Point> movimentosValidos) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha-1, coluna)){
			if(Tabuleiro.getInstance().casaEstaVazia(linha-1, coluna)) {
				movimentosValidos.add(new Point(linha-1, coluna));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha-1, coluna)) {
					movimentosValidos.add(new Point(linha-1, coluna));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraDireitaCima(int linha, int coluna, List<Point> movimentosValidos) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha-1, coluna+1)){
			if(Tabuleiro.getInstance().casaEstaVazia(linha-1, coluna+1)) {
				movimentosValidos.add(new Point(linha-1, coluna+1));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha-1, coluna+1)) {
					movimentosValidos.add(new Point(linha-1, coluna+1));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraDireitaBaixo(int linha, int coluna, List<Point> movimentosValidos) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha+1, coluna+1)){
			if(Tabuleiro.getInstance().casaEstaVazia(linha+1, coluna+1)) {
				movimentosValidos.add(new Point(linha+1, coluna+1));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha+1, coluna+1)) {
					movimentosValidos.add(new Point(linha+1, coluna+1));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraEsquerdaCima(int linha, int coluna, List<Point> movimentosValidos) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha-1, coluna-1)){
			if(Tabuleiro.getInstance().casaEstaVazia(linha-1, coluna-1)) {
				movimentosValidos.add(new Point(linha-1, coluna-1));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha-1, coluna-1)) {
					movimentosValidos.add(new Point(linha-1, coluna-1));
				}
			}
		}
	}
	
	private void getMovimentosValidosPraEsquerdaBaixo(int linha, int coluna, List<Point> movimentosValidos) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha+1, coluna-1)){
			if(Tabuleiro.getInstance().casaEstaVazia(linha+1, coluna-1)) {
				movimentosValidos.add(new Point(linha+1, coluna-1));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha+1, coluna-1)) {
					movimentosValidos.add(new Point(linha+1, coluna-1));
				}
			}
		}
	}
}
