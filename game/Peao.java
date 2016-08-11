package game;

import java.io.IOException;


import properties.Constantes;

public class Peao extends Peca {
	
	public Peao(Cor cor) {
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
}
