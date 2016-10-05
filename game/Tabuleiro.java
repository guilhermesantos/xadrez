package game;

public class Tabuleiro {
	private Peca[][] casas;
	
	private static class TabuleiroSingletonHolder {
		private static final Tabuleiro tabuleiro = new Tabuleiro();
	}
	
	public static Tabuleiro getInstance() {
		return TabuleiroSingletonHolder.tabuleiro;
	}
	private Tabuleiro() {
		casas = new Peca[8][8];
		reinicializaTabuleiro();
	}

	public void reinicializaTabuleiro() {
		for(int i = 0; i < 8; i++) {
			casas[1][i] = new Peao(Cor.PRETO);
			casas[6][i] = new Peao(Cor.BRANCO);
		}
		
		for(int i=2; i < 6; i++) {
			for(int j = 0; j < 8; j++) {
				casas[i][j] = null;
			}
		}
		
		casas[0][0] = new Torre(Cor.PRETO);
		casas[0][7] = new Torre(Cor.PRETO);
		casas[7][0] = new Torre(Cor.BRANCO);
		casas[7][7] = new Torre(Cor.BRANCO);
		
		casas[0][1] = new Cavalo(Cor.PRETO);
		casas[0][6] = new Cavalo(Cor.PRETO);
		casas[7][1] = new Cavalo(Cor.BRANCO);
		casas[7][6] = new Cavalo(Cor.BRANCO);
		
		casas[0][2] = new Bispo(Cor.PRETO);
		casas[0][5] = new Bispo(Cor.PRETO);
		casas[7][2] = new Bispo(Cor.BRANCO);
		casas[7][5] = new Bispo(Cor.BRANCO);
		
		casas[0][3] = new Rainha(Cor.PRETO);
		casas[7][3] = new Rainha(Cor.BRANCO);
		
		casas[0][4] = new Rei(Cor.PRETO);
		casas[7][4] = new Rei(Cor.BRANCO);
	}
	
	public boolean casaEstaVazia(int linha, int coluna) {
		return casas[linha][coluna] == null ? true : false;
	}
	
	public boolean casaEstaForaDoTabuleiro(int linha, int coluna) {
		if((linha < 0 || linha > 7) || (coluna < 0 || coluna > 7)) {
			//System.out.println("A CASA TA FORA DO TABULEIRO");
		}
		return (linha < 0 || linha > 7) || (coluna < 0 || coluna > 7);
	}
	
	public boolean pecasSaoDaMesmaCor(int linhaPeca1, int colunaPeca1, int linhaPeca2,
			int colunaPeca2) {
		return getPeca(linhaPeca1, colunaPeca1).getCor().equals(
				getPeca(linhaPeca2, colunaPeca2).getCor());
	}
		
	public Peca getPeca(int linha, int coluna) {
		return casas[linha][coluna];
	}
	
	public void removePeca(int linha, int coluna) {
		casas[linha][coluna] = null;
	}
	
	public void colocaPeca(int linha, int coluna, Peca peca) {
		casas[linha][coluna] = peca;
	}
	
	public void exibeTabuleiroNoConsole() {
		for(int i=0; i < 8; i++) {
			for(int j=0; j<8; j++) {
				if(casaEstaVazia(i, j)) {
					System.out.print("[0]");
				} else {
					System.out.print("[1]");
				}
			}
			System.out.println("");
		}
	}
}
