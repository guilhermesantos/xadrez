package game;

public class Tabuleiro {
	Peca[][] casas;
	
	public Tabuleiro() {
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
		
	public Peca getPeca(int linha, int coluna) {
		return casas[linha][coluna];
	}
}
