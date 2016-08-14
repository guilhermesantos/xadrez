package game;

import java.awt.Point;
import java.io.IOException;
import java.util.List;

import properties.Constantes;

public class Torre extends Peca {
	
	public Torre(Cor cor) {
		super.cor = cor;
		try {
			carregarImagem();
		} catch (IOException e) {
			System.out.println("Erro ao carregar torre.png");
		}
	}

	@Override
	protected String getEnderecoImagem() {
		return cor.equals(cor.PRETO) ? Constantes.ENDERECO_IMAGEM_TORRE_PRETA :
			 Constantes.ENDERECO_IMAGEM_TORRE_BRANCA;
	}

	@Override
	public List<Point> getMovimentosValidos(int linha, int coluna) {
		// TODO Auto-generated method stub
		return null;
	}

}
