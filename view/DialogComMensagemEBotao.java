package view;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import properties.Constantes;

public class DialogComMensagemEBotao extends JDialog {
	private static final long serialVersionUID = -6412765964258819121L;
	private JButton botao;
	private JLabel labelMensagem;
	
	public DialogComMensagemEBotao(Window window, String tituloDaJanela) {
		super(window, tituloDaJanela);
		configuraDialog();
		criaComponentes();
	}
	
	public DialogComMensagemEBotao(Window window, String tituloDaJanela, String mensagem, int largura, int altura) { 
		super(window, tituloDaJanela);
		configuraDialog(largura, altura);
		criaComponentes();
		labelMensagem.setText(mensagem);
		super.add(labelMensagem, BorderLayout.NORTH);
	}
	
	public DialogComMensagemEBotao(Window window, String tituloDaJanela, String mensagem) {
		super(window, tituloDaJanela);
		configuraDialog();
		criaComponentes();
		labelMensagem.setText(mensagem);
		super.add(labelMensagem, BorderLayout.NORTH);
	}
	
	public DialogComMensagemEBotao(Window window, String tituloDaJanela, String mensagem, String mensagemNoBotao) {
		super(window, tituloDaJanela);
		configuraDialog();
		criaComponentes();
		labelMensagem.setText(mensagem);
		botao.setText(mensagemNoBotao);
	}
	
	public DialogComMensagemEBotao(Window window, String tituloDaJanela, ActionListener acaoDoBotao) {
		super(window, tituloDaJanela);
		configuraDialog();
		criaComponentes();
		botao.addActionListener(acaoDoBotao);
	}
	
	private void criaComponentes() {
		botao = new JButton("Ok");
		botao.addActionListener(criaActionListenerQueFechaODialog());

		labelMensagem = new JLabel();
		super.add(botao, BorderLayout.SOUTH);
	}
	
	private void configuraDialog(int largura, int altura) {
		System.out.println("Esta colocando o tamanho no dialog!");
		super.setSize(largura, altura);
		super.setLayout(new BorderLayout());
		super.setSize(200, 100);
		super.setLocation(Constantes.LARGURA_JANELA/2, Constantes.ALTURA_JANELA/2);
	}
	
	private void configuraDialog() {
		configuraDialog(200, 200);
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
