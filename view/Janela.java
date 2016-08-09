package view;


import javax.swing.JFrame;

public class Janela extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -419158925384719190L;
	
	private JanelaMouseAdapter janelaMouseAdapter;
	
	public Janela(String titulo, int largura, int altura) {
		super(titulo);
		super.setSize(largura, altura);
		super.addMouseListener(janelaMouseAdapter);
		super.addMouseMotionListener(janelaMouseAdapter);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

}
