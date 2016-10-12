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
	private static final long serialVersionUID = -6412765964258819121L;
	private JButton botao;
	private JLabel labelMensagem;
	
	public XadrezDialog(Window window, String tituloDaJanela) {
		super(window, tituloDaJanela);

		super.setLayout(new BorderLayout());
		super.setSize(200, 100);
		super.setLocation(Constantes.LARGURA_JANELA/2, Constantes.ALTURA_JANELA/2);
		
		botao = new JButton("Ok");
		botao.addActionListener(criaActionListenerQueFechaODialog());

		labelMensagem = new JLabel();
		super.add(botao, BorderLayout.SOUTH);
	}
	
	public XadrezDialog(Window window, String tituloDaJanela, String mensagem) {
		this(window, tituloDaJanela);
		labelMensagem.setText(mensagem);
		super.add(labelMensagem, BorderLayout.NORTH);
	}
	
	public XadrezDialog(Window window, String tituloDaJanela, String mensagem, String mensagemNoBotao) {
		this(window, tituloDaJanela, mensagem);
		botao.setText(mensagemNoBotao);
	}
	
	public XadrezDialog(Window window, String tituloDaJanela, ActionListener acaoDoBotao) {
		this(window, tituloDaJanela);
		botao.addActionListener(acaoDoBotao);
	}
	
	private ActionListener criaActionListenerQueFechaODialog() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
		return listener;
	}
	
	public void setTextoBotao(String textoBotao) {
		botao.setText(textoBotao);
	}
	
	public void setTextoMensagem(String textoMensagem) {
		labelMensagem = new JLabel(textoMensagem);
		super.add(labelMensagem, BorderLayout.NORTH);
	}
	
	public void substituiActionListenerDoBotao(ActionListener novoListener) {
		botao.removeActionListener(botao.getActionListeners()[0]);
		botao.addActionListener(novoListener);
	}
}
