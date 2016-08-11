package view;

import javax.swing.JFrame;

public class Janela extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -419158925384719190L;
	
	private XadrezPanel tabuleiroPanel;
	
	public Janela(String titulo, int largura, int altura) {
		super(titulo);
		super.setSize(largura, altura);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		tabuleiroPanel = new XadrezPanel();
		super.add(tabuleiroPanel);
		super.setVisible(true);
	}
}
