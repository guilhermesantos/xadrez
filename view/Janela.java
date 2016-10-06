package view;

import javax.swing.JFrame;

public class Janela extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -419158925384719190L;
	
	private XadrezGrafico xadrezGrafico;

	public Janela(String titulo, int largura, int altura) {
		super(titulo);
		super.setSize(largura, altura);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		xadrezGrafico = new XadrezGrafico();
		super.add(xadrezGrafico);
		super.setVisible(true);
	}
}
