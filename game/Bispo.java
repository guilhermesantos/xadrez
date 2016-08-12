package game;

import java.io.IOException;

import properties.Constantes;

public class Bispo extends Peca {
	
	public Bispo(Cor cor) {
		super.tipoPeca = TipoPeca.BISPO;
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
		return cor.equals(cor.PRETO) ? Constantes.ENDERECO_IMAGEM_BISPO_PRETO :
					 Constantes.ENDERECO_IMAGEM_BISPO_BRANCO;
	}

}
