package game;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import properties.Constantes;

public class Bispo extends Peca {
	
	public Bispo(Cor cor) {
		super.cor = cor;
		try {
			carregarImagem();
		} catch (IOException e) {
			System.out.println("Erro ao carregar bispo.png");
			System.out.println("asdasdasda");
		}
	}


	@Override
	protected String getEnderecoImagem() {
		return cor.equals(Cor.PRETO) ? Constantes.ENDERECO_IMAGEM_BISPO_PRETO :
					 Constantes.ENDERECO_IMAGEM_BISPO_BRANCO;
	}


	@Override
	public List<Point> getMovimentosValidos(Tabuleiro tabuleiro, int linha, int coluna) {
		List<Point> movimentosValidos = new ArrayList<Point>();
		
		getMovimentosValidosPraDireitaBaixo(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaCima(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaBaixo(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaCima(tabuleiro, linha, coluna, movimentosValidos);
		
		return movimentosValidos;
	}
	
	private List<Point> getMovimentosValidosPraDireitaBaixo(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!tabuleiro.casaEstaForaDoTabuleiro(linha+i, coluna+i)) {
			if(tabuleiro.casaEstaVazia(linha+i, coluna+i)) {
				movimentosValidos.add(new Point(linha+i, coluna+i));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha+i, coluna+i)) {
					movimentosValidos.add(new Point(linha+i, coluna+i));
				}
				break;
			}
			i++;
		}
		return movimentosValidos;
	}

	private List<Point> getMovimentosValidosPraDireitaCima(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!tabuleiro.casaEstaForaDoTabuleiro(linha-i, coluna+i)) {
			if(tabuleiro.casaEstaVazia(linha-i, coluna+i)) {
				movimentosValidos.add(new Point(linha-i, coluna+i));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha-i, coluna+i)) {
					movimentosValidos.add(new Point(linha-i, coluna+i));
				}
				break;
			}
			i++;
		}		
		return movimentosValidos;
	}
	
	private List<Point> getMovimentosValidosPraEsquerdaBaixo(Tabuleiro tabuleiro, int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!tabuleiro.casaEstaForaDoTabuleiro(linha+i, coluna-i)) {
			if(tabuleiro.casaEstaVazia(linha+i, coluna-i)) {
				movimentosValidos.add(new Point(linha+i, coluna-i));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha+i, coluna-i)) {
					movimentosValidos.add(new Point(linha+i, coluna-i));
				}
				break;
			}
			i++;
		}
		return movimentosValidos;
	}
	
	private List<Point> getMovimentosValidosPraEsquerdaCima(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!tabuleiro.casaEstaForaDoTabuleiro(linha-i, coluna-i)) {
			if(tabuleiro.casaEstaVazia(linha-i, coluna-i)) {
				movimentosValidos.add(new Point(linha-i, coluna-i));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha-i, coluna-i)) {
					movimentosValidos.add(new Point(linha-i, coluna-i));
				}
				break;
			}
			i++;
		}
		return movimentosValidos;
	}
}
