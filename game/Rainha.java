package game;

import java.io.IOException;

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

}
