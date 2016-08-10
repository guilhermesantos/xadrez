package view;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class Janela extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -419158925384719190L;
	
	private Tabuleiro tabuleiro;
	
	public Janela(String titulo, int largura, int altura) {
		super(titulo);
		super.setSize(largura, altura);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		super.setLayout(new GridLayout(0, 2));
		tabuleiro = new Tabuleiro();
		super.add(tabuleiro);
		super.setVisible(true);
	}
}
