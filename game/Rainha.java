package game;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import properties.Constantes;

public class Rainha extends Peca {
	
	public Rainha(Cor cor) {
		super.cor = cor;
		try {
			carregarImagem();
		} catch (IOException e) {
			System.out.println("Erro ao carregar rainha.png");
		}
	}


	@Override
	protected String getEnderecoImagem() {
		return cor.equals(Cor.PRETO) ? Constantes.ENDERECO_IMAGEM_RAINHA_PRETA :
			 Constantes.ENDERECO_IMAGEM_RAINHA_BRANCA;
	}


	@Override
	public List<Point> getMovimentosValidos(Tabuleiro tabuleiro, int linha, int coluna) {
		List<Point> movimentosValidos = new ArrayList<Point>();
		
		getMovimentosValidosPraBaixo(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraCima(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireita(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerda(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaCima(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaBaixo(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaCima(tabuleiro, linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaBaixo(tabuleiro, linha, coluna, movimentosValidos);
		return movimentosValidos;
	}

	
	private void getMovimentosValidosPraDireita(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!tabuleiro.casaEstaForaDoTabuleiro(linha, coluna+i)) {
			if(tabuleiro.casaEstaVazia(linha, coluna+i)) {
				movimentosValidos.add(new Point(linha, coluna+i));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha, coluna+i)) {
					movimentosValidos.add(new Point(linha, coluna+i));
				}
				break;
			}
			i++;
		}
	}	

	private void getMovimentosValidosPraEsquerda(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!tabuleiro.casaEstaForaDoTabuleiro(linha, coluna-i)) {
			if(tabuleiro.casaEstaVazia(linha, coluna-i)) {
				movimentosValidos.add(new Point(linha, coluna-i));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha, coluna-i)) {
				movimentosValidos.add(new Point(linha, coluna-i));
				}
				break;
			}
			i++;
		}
	}
	
	private void getMovimentosValidosPraBaixo(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!tabuleiro.casaEstaForaDoTabuleiro(linha+i, coluna)) {
			if(tabuleiro.casaEstaVazia(linha+i, coluna)) {
				movimentosValidos.add(new Point(linha+i, coluna));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha+i, coluna, linha, coluna)) {
				movimentosValidos.add(new Point(linha+i, coluna));
				}
				break;
			}
			i++;
		}
	}
	
	private void getMovimentosValidosPraCima(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!tabuleiro.casaEstaForaDoTabuleiro(linha-i, coluna)) {
			if(tabuleiro.casaEstaVazia(linha-i, coluna)) {
				movimentosValidos.add(new Point(linha-i, coluna));
			} else {
				if(!tabuleiro.pecasSaoDaMesmaCor(linha, coluna, linha-i, coluna)) {
				movimentosValidos.add(new Point(linha-i, coluna));
				}
				break;
			}
			i++;
		}
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
	
	private List<Point> getMovimentosValidosPraEsquerdaBaixo(Tabuleiro tabuleiro, 
			int linha, int coluna, List<Point> movimentosValidos) {
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
