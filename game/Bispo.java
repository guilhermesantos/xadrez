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
	public List<Point> getMovimentosValidos(int linha, int coluna) {
		List<Point> movimentosValidos = new ArrayList<Point>();
		
		getMovimentosValidosPraDireitaBaixo(linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaCima(linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaBaixo(linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaCima(linha, coluna, movimentosValidos);
		
		return movimentosValidos;
	}
	
	private List<Point> getMovimentosValidosPraDireitaBaixo(int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha+i, coluna+i)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha+i, coluna+i)) {
				movimentosValidos.add(new Point(linha+i, coluna+i));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha+i, coluna+i)) {
					movimentosValidos.add(new Point(linha+i, coluna+i));
				}
				break;
			}
			i++;
		}
		return movimentosValidos;
	}

	private List<Point> getMovimentosValidosPraDireitaCima(int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha-i, coluna+i)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha-i, coluna+i)) {
				movimentosValidos.add(new Point(linha-i, coluna+i));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha-i, coluna+i)) {
					movimentosValidos.add(new Point(linha-i, coluna+i));
				}
				break;
			}
			i++;
		}		
		return movimentosValidos;
	}
	
	private List<Point> getMovimentosValidosPraEsquerdaBaixo(int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha+i, coluna-i)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha+i, coluna-i)) {
				movimentosValidos.add(new Point(linha+i, coluna-i));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha+i, coluna-i)) {
					movimentosValidos.add(new Point(linha+i, coluna-i));
				}
				break;
			}
			i++;
		}
		return movimentosValidos;
	}
	
	private List<Point> getMovimentosValidosPraEsquerdaCima(int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha-i, coluna-i)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha-i, coluna-i)) {
				movimentosValidos.add(new Point(linha-i, coluna-i));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha-i, coluna-i)) {
					movimentosValidos.add(new Point(linha-i, coluna-i));
				}
				break;
			}
			i++;
		}
		return movimentosValidos;
	}
}
