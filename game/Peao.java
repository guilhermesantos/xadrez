package game;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import properties.Constantes;

public class Peao extends Peca {
	boolean primeiroMovimento;
	
	public Peao(Cor cor) {
		primeiroMovimento = true;
		super.cor = cor;
		try {
			carregarImagem();
		} catch (IOException e) {
			System.out.println("Erro ao carregar peao.png");
		}
	}

	
	@Override
	protected String getEnderecoImagem() {
		return cor.equals(cor.PRETO) ? Constantes.ENDERECO_IMAGEM_PEAO_PRETO :
			 Constantes.ENDERECO_IMAGEM_PEAO_BRANCO;
	}

	@Override
	public List<Point> getMovimentosValidos(int linha, int coluna) {
		List<Point> listaDeMovimentosValidos = new ArrayList<Point>();
	
		listaDeMovimentosValidos.addAll(pesquisaSeDaPraCapturarPraEsquerda(linha, coluna));
		listaDeMovimentosValidos.addAll(pesquisaSeDaPraCapturarPraDireita(linha, coluna));
		listaDeMovimentosValidos.addAll(pesquisaSeDaPraAndarPraFrente(linha, coluna));
		
		return listaDeMovimentosValidos;
	}
	
	private List<Point> pesquisaSeDaPraAndarPraFrente(int linha, int coluna) {
		List<Point> movimentoPraFrente = new ArrayList<Point>();
		if(cor.equals(cor.BRANCO)) {
			if(peaoEhBrancoEPodeAndarPraFrente(linha, coluna)) {
				movimentoPraFrente.add(new Point(linha-1, coluna));
				if(primeiroMovimento && peaoEhBrancoEPodeAndarPraFrente(linha-1, coluna)) {
					movimentoPraFrente.add(new Point(linha-2, coluna));
				}
			}

		} else {
			if(peaoEhPretoEPodeAndarPraFrente(linha, coluna)) {
				movimentoPraFrente.add(new Point(linha+1, coluna));
				if(primeiroMovimento && peaoEhPretoEPodeAndarPraFrente(linha+1, coluna)) {
					movimentoPraFrente.add(new Point(linha+2, coluna));
				}
			}
		}
		return movimentoPraFrente;
	}
	
	private boolean peaoEhBrancoEPodeAndarPraFrente(int linha, int coluna) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha-1, coluna)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha-1, coluna)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean peaoEhPretoEPodeAndarPraFrente(int linha, int coluna) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha+1, coluna)) {
			if(Tabuleiro.getInstance().casaEstaVazia(linha+1, coluna)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean peaoEhBrancoEPodeCapturarPraEsquerda(int linha, int coluna) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha-1, coluna-1)) {
			if(!Tabuleiro.getInstance().casaEstaVazia(linha-1, coluna-1) && 
					Tabuleiro.getInstance().getPeca(linha-1, coluna-1).getCor().equals(Cor.PRETO)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean peaoEhPretoEPodeCapturarPraEsquerda(int linha, int coluna) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha+1, coluna-1)) {
			if(!Tabuleiro.getInstance().casaEstaVazia(linha+1, coluna-1) &&
					Tabuleiro.getInstance().getPeca(linha+1, coluna-1).getCor().equals(Cor.BRANCO)) {
				return true;
			}
		}
		return false;
	}
	
	private List<Point> pesquisaSeDaPraCapturarPraEsquerda(int linha, int coluna) {
		List<Point> movimentoDeCaptura = new ArrayList<Point>();
		
		if(cor.equals(Cor.BRANCO)) {
			if(peaoEhBrancoEPodeCapturarPraEsquerda(linha, coluna)) {
				movimentoDeCaptura.add(new Point(linha-1, coluna-1));
			}
		} else {
			if(peaoEhPretoEPodeCapturarPraEsquerda(linha, coluna)) {
				movimentoDeCaptura.add(new Point(linha+1, coluna-1));
			}
		}
		return movimentoDeCaptura;
	}
	
	private List<Point> pesquisaSeDaPraCapturarPraDireita(int linha, int coluna) {
		List<Point> movimentoDeCaptura = new ArrayList<Point>();

		if(cor.equals(Cor.BRANCO)) {
			if(peaoEhBrancoEPodeCapturarPraDireita(linha, coluna)) {
				movimentoDeCaptura.add(new Point(linha-1, coluna+1));
			}
		} else {
			System.out.println("Pesquisa p/ direita. Peao eh preto");
			if(peaoEhPretoEPodeCapturarPraDireita(linha, coluna)) {
				movimentoDeCaptura.add(new Point(linha+1, coluna+1));
			}
		}
		return movimentoDeCaptura;
	}
	
	private boolean peaoEhBrancoEPodeCapturarPraDireita(int linha, int coluna) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha-1, coluna+1)) {
			if(!Tabuleiro.getInstance().casaEstaVazia(linha-1, coluna+1)) {
				if(Tabuleiro.getInstance().getPeca(linha-1, coluna+1).getCor().equals(Cor.PRETO)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean peaoEhPretoEPodeCapturarPraDireita(int linha, int coluna) {
		if(!Tabuleiro.getInstance().casaEstaForaDoTabuleiro(linha+1, coluna+1)) {
			System.out.println("casa esta dentro do tabuleiro");
			if(!Tabuleiro.getInstance().casaEstaVazia(linha+1, coluna+1)) { 
				if(Tabuleiro.getInstance().getPeca(linha+1, coluna+1).getCor().equals(Cor.BRANCO)) {
					System.out.println("achou uma peca branca em "+linha+1+" "+coluna+1);
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void movePeca() {
		primeiroMovimento = false;
	}
	
}
