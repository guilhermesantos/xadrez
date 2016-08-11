package game;

import java.io.IOException;

import properties.Constantes;

public class Bispo extends Peca {
	
	public Bispo(Cor cor) {
		super.cor = cor;
		try {
			carregarImagem();
		} catch (IOException e) {
			System.out.println("Erro ao carregar bispo.png");
		}
	}


	@Override
	protected String getEnderecoImagem() {
		return cor.equals(cor.PRETO) ? Constantes.ENDERECO_IMAGEM_BISPO_PRETO :
					 Constantes.ENDERECO_IMAGEM_BISPO_BRANCO;
	}

}
