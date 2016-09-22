package view;

import java.awt.FlowLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import properties.Constantes;

public class FimDeJogoDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6412765964258819121L;
	private JButton botao;
	private JLabel labelMensagem;
	
	public FimDeJogoDialog(Window window, String title) {
		super(window, title);
		super.setLayout(new FlowLayout());
		super.setSize(200, 100);
		super.setLocation(Constantes.LARGURA_JANELA/2, Constantes.ALTURA_JANELA/2);
		
		labelMensagem = new JLabel();
		botao = new JButton("Ok");
		super.add(botao);
	}
	
	public void setText(String textoMensagem) {
		labelMensagem = new JLabel(textoMensagem);
		labelMensagem.setText(textoMensagem);
		super.add(labelMensagem);
	}
	
	public JButton getButton() {
		return botao;
	}
}
