package game;

import java.io.IOException;

import properties.Constantes;

public class Cavalo extends Peca {
	
	public Cavalo(Cor cor) {
		super.cor = cor;
		try {
			carregarImagem();
		} catch (IOException e) {
			System.out.println("Erro ao carregar cavalo.png");
		}
	}


	@Override
	protected String getEnderecoImagem() {
		return cor.equals(cor.PRETO) ? Constantes.ENDERECO_IMAGEM_CAVALO_PRETO :
			 Constantes.ENDERECO_IMAGEM_CAVALO_BRANCO;
	}

}
