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
		return cor.equals(cor.PRETO) ? Constantes.ENDERECO_IMAGEM_RAINHA_PRETA :
			 Constantes.ENDERECO_IMAGEM_RAINHA_BRANCA;
	}


	@Override
	public List<Point> getMovimentosValidos(int linha, int coluna) {
		List<Point> movimentosValidos = new ArrayList<Point>();
		
		getMovimentosValidosPraBaixo(linha, coluna, movimentosValidos);
		getMovimentosValidosPraCima(linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireita(linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerda(linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaCima(linha, coluna, movimentosValidos);
		getMovimentosValidosPraDireitaBaixo(linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaCima(linha, coluna, movimentosValidos);
		getMovimentosValidosPraEsquerdaBaixo(linha, coluna, movimentosValidos);
		return movimentosValidos;
	}

	
	private void getMovimentosValidosPraDireita(int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha, coluna+i)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha, coluna+i)) {
				movimentosValidos.add(new Point(linha, coluna+i));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha, coluna+i)) {
					movimentosValidos.add(new Point(linha, coluna+i));
				}
				break;
			}
			i++;
		}
	}	

	private void getMovimentosValidosPraEsquerda(int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha, coluna-i)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha, coluna-i)) {
				movimentosValidos.add(new Point(linha, coluna-i));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha, coluna-i)) {
				movimentosValidos.add(new Point(linha, coluna-i));
				}
				break;
			}
			i++;
		}
	}
	
	private void getMovimentosValidosPraBaixo(int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha+i, coluna)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha+i, coluna)) {
				movimentosValidos.add(new Point(linha+i, coluna));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha+i, coluna, linha, coluna)) {
				movimentosValidos.add(new Point(linha+i, coluna));
				}
				break;
			}
			i++;
		}
	}
	
	private void getMovimentosValidosPraCima(int linha, int coluna, List<Point> movimentosValidos) {
		int i = 1;
		while(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha-i, coluna)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha-i, coluna)) {
				movimentosValidos.add(new Point(linha-i, coluna));
			} else {
				if(!Tabuleiro.getInstance().pecasSaoDaMesmaCor(linha, coluna, linha-i, coluna)) {
				movimentosValidos.add(new Point(linha-i, coluna));
				}
				break;
			}
			i++;
		}
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
