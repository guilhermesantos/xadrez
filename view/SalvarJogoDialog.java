package view;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.Xadrez;

public class SalvarJogoDialog extends JDialog {
	private static final long serialVersionUID = 7859175891348144808L;
	private JPanel painelNomeJogo;
	private JTextField campoNomeJogo;
	
	private JPanel painelBotoes;
	private JButton botaoSair;
	private JButton botaoSalvar;
	
	private Xadrez jogoSalvo;
	
	public SalvarJogoDialog(Window window, Xadrez jogo) {
		super(window, "Salvar jogo");
		jogoSalvo = jogo;
		configuraDialog();
	}
	
	public void configuraDialog() {
		this.setSize(200, 150);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		constroiPainelNomeJogo();
		constroiPainelBotoes();
	}
	
	public void constroiPainelNomeJogo() {
		painelNomeJogo = new JPanel();
		
		JLabel labelNomeJogo = new JLabel("Nome do jogo: ");
		campoNomeJogo = new JTextField(10);
		
		painelNomeJogo.add(labelNomeJogo);
		painelNomeJogo.add(campoNomeJogo);
		
		this.add(painelNomeJogo, BorderLayout.CENTER);
	}
	
	public void constroiPainelBotoes() {
		botaoSair = new JButton("Sair");
		botaoSair.addActionListener(criaActionListenerQueFechaODialog());
		
		botaoSalvar = new JButton("Salvar");
		botaoSalvar.addActionListener(criaActionListenerQueSalvaJogo());
		
		painelBotoes = new JPanel();
		painelBotoes.add(botaoSalvar);
		painelBotoes.add(botaoSair);
		this.add(painelBotoes, BorderLayout.SOUTH);
	}

	
	private ActionListener criaActionListenerQueSalvaJogo() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					jogoSalvo.salvaJogo(campoNomeJogo.getText().concat(".dat"));
				} catch (FileNotFoundException e1) {
					System.out.println("Falhou ao salvar o jogo. Nao conseguiu criar/encontrar o arquivo");
					e1.printStackTrace();
				} catch (IOException e1) {
					System.out.println("Falhou ao salvar o jogo. Erro de IO");
					e1.printStackTrace();
				}
				dispose();
			}
		};
		return listener;
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

	public Xadrez getJogoCarregado() {
		return jogoSalvo;
	}

	public void setJogoCarregado(Xadrez jogoCarregado) {
		this.jogoSalvo = jogoCarregado;
	}	
}
