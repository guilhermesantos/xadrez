package view;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import game.Xadrez;

public class CarregarJogoDialog extends JDialog {
	private static final long serialVersionUID = -7554118507387709893L;
	private JScrollPane painelListaDeJogos;
	private JList<String> listaVisualDeJogos;
	private DefaultListModel<String> listaLogicaDeJogos;
	
	private JPanel painelBotoes;
	private JButton botaoCarregar;
	private JButton botaoApagar;
	private JButton botaoSair;
	
	private Xadrez jogoCarregado;
	private Janela janela;
	
	public CarregarJogoDialog(Window window, Janela janela) {
		super(window, "Carregar jogo");
		this.janela = janela;
		configuraDialog();
	}
	
	public void configuraDialog() {
		this.setSize(300, 300);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		
		constroiListaDeJogos();
		constroiPainelBotoes();
	}
	
	private void constroiListaDeJogos() {
		listaLogicaDeJogos = new DefaultListModel<>();
		List<String >jogosEncontrados = Xadrez.buscaJogosSalvos();
		for(String jogo: jogosEncontrados) {
			listaLogicaDeJogos.addElement(jogo);
		}
		
		listaVisualDeJogos = new JList(listaLogicaDeJogos);
		
		listaVisualDeJogos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaVisualDeJogos.setLayoutOrientation(JList.VERTICAL);
		listaVisualDeJogos.setVisibleRowCount(-1);
		
		painelListaDeJogos = new JScrollPane(listaVisualDeJogos);
		this.add(painelListaDeJogos, BorderLayout.CENTER);
	}
	
	private void constroiPainelBotoes() {
		botaoCarregar = new JButton("Carregar");
		botaoCarregar.addActionListener(criaActionListenerQueCarregaJogo());

		botaoApagar = new JButton("Apagar");
		botaoApagar.addActionListener(criaActionListenerQueApagaJogo());
		
		botaoSair = new JButton("Sair");
		botaoSair.addActionListener(criaActionListenerQueFechaODialog());
		
		painelBotoes = new JPanel();
		painelBotoes.add(botaoCarregar);
		painelBotoes.add(botaoApagar);
		painelBotoes.add(botaoSair);
		this.add(painelBotoes, BorderLayout.SOUTH);
	}

	private ActionListener criaActionListenerQueApagaJogo() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!listaVisualDeJogos.isSelectionEmpty()) {
					try {
						Xadrez.apagaJogosalvo(listaVisualDeJogos.getSelectedValue());
					} catch (FileNotFoundException e1) {
						System.out.println("Erro ao apagar jogo salvo.");
						e1.printStackTrace();
					}
					listaLogicaDeJogos.remove(listaVisualDeJogos.getSelectedIndex());
				}
			}
		};
		return listener;
	}

	private ActionListener criaActionListenerQueCarregaJogo() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(!listaVisualDeJogos.isSelectionEmpty()) {
						jogoCarregado = Xadrez.carregaJogo(listaVisualDeJogos.getSelectedValue());
						janela.callbackCarregaJogo(jogoCarregado);
						Logger.getInstance().logar("Jogo "+listaVisualDeJogos.getSelectedValue()+" carregado com sucesso.");
						dispose();
					}
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
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
		return jogoCarregado;
	}

	public void setJogoCarregado(Xadrez jogoCarregado) {
		this.jogoCarregado = jogoCarregado;
	}	
}
