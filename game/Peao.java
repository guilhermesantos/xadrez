package game;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import properties.Constantes;

public class Peao extends Peca {
	private static final long serialVersionUID = 2628878934548282814L;
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

	public void setPrimeiroMovimento(boolean primeiroMovimento) {
		this.primeiroMovimento = primeiroMovimento;
	}
	
	@Override
	protected String getEnderecoImagem() {
		return cor.equals(Cor.PRETO) ? Constantes.ENDERECO_IMAGEM_PEAO_PRETO :
			 Constantes.ENDERECO_IMAGEM_PEAO_BRANCO;
	}

	@Override
	public List<Point> getMovimentosValidos(Tabuleiro tabuleiro, int linha, int coluna) {
		List<Point> listaDeMovimentosValidos = new ArrayList<Point>();
	
		listaDeMovimentosValidos.addAll(pesquisaSeDaPraCapturarPraEsquerda(tabuleiro, linha, coluna));
		listaDeMovimentosValidos.addAll(pesquisaSeDaPraCapturarPraDireita(tabuleiro, linha, coluna));
		listaDeMovimentosValidos.addAll(pesquisaSeDaPraAndarPraFrente(tabuleiro, linha, coluna));
		
		return listaDeMovimentosValidos;
	}
	
	private List<Point> pesquisaSeDaPraAndarPraFrente(Tabuleiro tabuleiro, int linha, int coluna) {
		List<Point> movimentoPraFrente = new ArrayList<Point>();
		if(cor.equals(Cor.BRANCO)) {
			if(peaoEhBrancoEPodeAndarPraFrente(tabuleiro, linha, coluna)) {
				movimentoPraFrente.add(new Point(linha-1, coluna));
				if(primeiroMovimento && peaoEhBrancoEPodeAndarPraFrente(tabuleiro, linha-1, coluna)) {
					movimentoPraFrente.add(new Point(linha-2, coluna));
				}
			}

		} else {
			if(peaoEhPretoEPodeAndarPraFrente(tabuleiro, linha, coluna)) {
				movimentoPraFrente.add(new Point(linha+1, coluna));
				if(primeiroMovimento && peaoEhPretoEPodeAndarPraFrente(tabuleiro, linha+1, coluna)) {
					movimentoPraFrente.add(new Point(linha+2, coluna));
				}
			}
		}
		return movimentoPraFrente;
	}
	
	private boolean peaoEhBrancoEPodeAndarPraFrente(Tabuleiro tabuleiro, int linha, int coluna) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha-1, coluna)) {
			if(tabuleiro.casaEstaVazia(linha-1, coluna)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean peaoEhPretoEPodeAndarPraFrente(Tabuleiro tabuleiro, int linha, int coluna) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha+1, coluna)) {
			if(tabuleiro.casaEstaVazia(linha+1, coluna)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean peaoEhBrancoEPodeCapturarPraEsquerda(Tabuleiro tabuleiro, int linha, int coluna) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha-1, coluna-1)) {
			if(!tabuleiro.casaEstaVazia(linha-1, coluna-1) && 
					tabuleiro.getPeca(linha-1, coluna-1).getCor().equals(Cor.PRETO)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean peaoEhPretoEPodeCapturarPraEsquerda(Tabuleiro tabuleiro, int linha, int coluna) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha+1, coluna-1)) {
			if(!tabuleiro.casaEstaVazia(linha+1, coluna-1) &&
					tabuleiro.getPeca(linha+1, coluna-1).getCor().equals(Cor.BRANCO)) {
				return true;
			}
		}
		return false;
	}
	
	private List<Point> pesquisaSeDaPraCapturarPraEsquerda(Tabuleiro tabuleiro, int linha, int coluna) {
		List<Point> movimentoDeCaptura = new ArrayList<Point>();
		
		if(cor.equals(Cor.BRANCO)) {
			if(peaoEhBrancoEPodeCapturarPraEsquerda(tabuleiro, linha, coluna)) {
				movimentoDeCaptura.add(new Point(linha-1, coluna-1));
			}
		} else {
			if(peaoEhPretoEPodeCapturarPraEsquerda(tabuleiro, linha, coluna)) {
				movimentoDeCaptura.add(new Point(linha+1, coluna-1));
			}
		}
		return movimentoDeCaptura;
	}
	
	private List<Point> pesquisaSeDaPraCapturarPraDireita(Tabuleiro tabuleiro, int linha, int coluna) {
		List<Point> movimentoDeCaptura = new ArrayList<Point>();

		if(cor.equals(Cor.BRANCO)) {
			if(peaoEhBrancoEPodeCapturarPraDireita(tabuleiro, linha, coluna)) {
				movimentoDeCaptura.add(new Point(linha-1, coluna+1));
			}
		} else {
			if(peaoEhPretoEPodeCapturarPraDireita(tabuleiro, linha, coluna)) {
				movimentoDeCaptura.add(new Point(linha+1, coluna+1));
			}
		}
		return movimentoDeCaptura;
	}
	
	private boolean peaoEhBrancoEPodeCapturarPraDireita(Tabuleiro tabuleiro, int linha, int coluna) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha-1, coluna+1)) {
			if(!tabuleiro.casaEstaVazia(linha-1, coluna+1)) {
				if(tabuleiro.getPeca(linha-1, coluna+1).getCor().equals(Cor.PRETO)) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean peaoEhPretoEPodeCapturarPraDireita(Tabuleiro tabuleiro, int linha, int coluna) {
		if(!tabuleiro.casaEstaForaDoTabuleiro(linha+1, coluna+1)) {
			if(!tabuleiro.casaEstaVazia(linha+1, coluna+1)) { 
				if(tabuleiro.getPeca(linha+1, coluna+1).getCor().equals(Cor.BRANCO)) {
					return true;
				}
			}
		}
		return false;
	}
}
