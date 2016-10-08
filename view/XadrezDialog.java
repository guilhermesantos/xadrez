package view;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import properties.Constantes;

public class XadrezDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6412765964258819121L;
	private JButton botao;
	private JLabel labelMensagem;
	
	public XadrezDialog(Window window, String title, boolean acaoPadraoEhFechar) {
		super(window, title);
		super.setLayout(new BorderLayout());
		super.setSize(200, 100);
		super.setLocation(Constantes.LARGURA_JANELA/2, Constantes.ALTURA_JANELA/2);
		
		botao = new JButton("Ok");
		if(acaoPadraoEhFechar) {
			fazBotaoFecharODialog();
		}

		labelMensagem = new JLabel();
		super.add(botao, BorderLayout.SOUTH);
	}

	private void fazBotaoFecharODialog() {
		botao.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				}
		);
	}
	
	public void setTextoBotao(String textoBotao) {
		botao.setText(textoBotao);
	}
	
	public void setTextoMensagem(String textoMensagem) {
		labelMensagem = new JLabel(textoMensagem);
		super.add(labelMensagem, BorderLayout.NORTH);
	}
	
	public JButton getButton() {
		return botao;
	}
	
	
}
