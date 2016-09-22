package game;

import java.awt.Point;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.swing.ImageIcon;


public abstract class Peca {
	protected Cor cor;
	
	protected ImageIcon imagem;

	public Point posicao;
	
	public Cor getCor() {
		return cor;
	}
	
	public Point getPosicao() {
		return posicao;
	}

	public void setPosicao(Point posicao) {
		this.posicao = posicao;
	}
	
	public ImageIcon getImagem() {
		return imagem;
	}
	
	public abstract List<Point> getMovimentosValidos(int linha, int coluna);
	
	protected abstract String getEnderecoImagem();
	
	public ImageIcon carregarImagem() throws IOException {
		URL urlImagem = this.getClass().getResource(getEnderecoImagem());
		System.out.println("endereco imagem: " + getEnderecoImagem());
		System.out.println("cor da peca: " + getCor().toString());
		if(urlImagem == null) {
			System.out.println("Nao conseguiu carregar imagem");
			throw new IOException();
		} else {
			imagem = new ImageIcon(urlImagem);
			return imagem;
		}
	}
}
