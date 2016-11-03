package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.Cor;
import game.Xadrez;
import network.Interlocutor;
import network.TipoInterlocutor;
import timer.TimerGrafico;

public class Janela extends JFrame {

	private static final long serialVersionUID = -419158925384719190L;
	
//Atributos da interface gr�fica
// ------------------------------------------------------//
	private Xadrez jogo;
	private GerenciadorInterfaceGraficaXadrez xadrezGrafico;
	private JPanel containerDosBotoes;
	private JButton botaoReiniciar;
	private JButton botaoSalvar;
	private JButton botaoMultiplayer;
	private JButton botaoCarregar;
	private Window janelaExterna;
	private TimerGrafico timerPartida;
	
//Atributos da rede
	private Interlocutor interlocutor;
// ------------------------------------------------------//

	public Janela(String titulo, int largura, int altura) {
		super(titulo);
		configuraJanela(largura, altura);

		jogo = new Xadrez();
		
		xadrezGrafico = new GerenciadorInterfaceGraficaXadrez(jogo);
		super.add(xadrezGrafico, BorderLayout.CENTER);
		super.add(Logger.getInstance(), BorderLayout.SOUTH);

		containerDosBotoes = criaContainerDosBotoes();
		super.add(containerDosBotoes, BorderLayout.NORTH);
	}
	
	private JPanel criaContainerDosBotoes() {
		JPanel containerDosBotoes = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		botaoReiniciar = criaBotaoReiniciar();
		containerDosBotoes.add(botaoReiniciar);
		
		botaoSalvar = criaBotaoSalvar();
		containerDosBotoes.add(botaoSalvar);
		
		botaoCarregar = criaBotaoCarregar();
		containerDosBotoes.add(botaoCarregar);
		
		botaoMultiplayer = criaBotaoMultiplayer();
		containerDosBotoes.add(botaoMultiplayer);
		
		timerPartida = new TimerGrafico("Dura��o do jogo: ", jogo.getTimerPartida());
		containerDosBotoes.add(timerPartida);
		
		return containerDosBotoes;
	}

	private void configuraJanela(int largura, int altura) {
		super.setSize(largura, (int)(altura*1.07));
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setLayout(new BorderLayout());
		super.setResizable(false);
		janelaExterna = SwingUtilities.getWindowAncestor(this);
	}
	
	private JButton criaBotaoReiniciar() {
		JButton botaoReiniciar = new JButton("Reiniciar");
		botaoReiniciar.setBackground(Color.WHITE);
		
		botaoReiniciar.addActionListener(
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jogo = new Xadrez();
				xadrezGrafico.substituiJogoEAtualizaGraficos(jogo);
				timerPartida.trocaTimerLogico(jogo.getTimerPartida());
			}
		});
		return botaoReiniciar;
	}
	
	private JButton criaBotaoSalvar() {
		JButton botaoSalvar = new JButton("Salvar");
		botaoSalvar.setBackground(Color.WHITE);
		
		botaoSalvar.addActionListener(
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					jogo.salvaJogo("jogo_salvo.dat");
					Logger.getInstance().logar("Jogo salvo.");
				} catch (FileNotFoundException e1) {
					System.out.println("Arquivo nao encontrado");
				} catch (IOException e1) {
					e1.printStackTrace();
					System.out.println("Erro ao criar o object output stream");
				}
			}
		});
		return botaoSalvar;
	}
	
	private JButton criaBotaoCarregar() {
		JButton botaoCarregar = new JButton("Carregar");
		botaoCarregar.setBackground(Color.WHITE);
		
		botaoCarregar.addActionListener(
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					jogo = jogo.carregaJogo("jogo_salvo.dat");
					xadrezGrafico.substituiJogoEAtualizaGraficos(jogo);
					timerPartida.trocaTimerLogico(jogo.getTimerPartida());
				} catch (FileNotFoundException e1) {
					Logger.getInstance().logar("Nao encontrou o arquivo jogo_salvo.dat");
				} catch (IOException e1) {
					Logger.getInstance().logar("IOException. :(");
				} catch (ClassNotFoundException e1) {
					Logger.getInstance().logar("Erro ao deserializar a inst�ncia de xadrez que estava salva em disco!");
				}
			}
		});
		return botaoCarregar;
	}
	
	private JButton criaBotaoMultiplayer() {
		JButton botaoMultiplayer = new JButton("Multiplayer");
		botaoMultiplayer.setBackground(Color.WHITE);
		NetworkDialog networkDialog = new NetworkDialog(janelaExterna);
		
		ActionListener listenerQueFazAbrirONetworkDialog = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				networkDialog.setVisible(true);
				interlocutor = networkDialog.getInterlocutor();
				if(interlocutor != null) {
					jogo = new Xadrez();
					xadrezGrafico.colocaOJogoEmRede(interlocutor);
					xadrezGrafico.substituiJogoEAtualizaGraficos(jogo);
					
					if(interlocutor.getTipoInterlocutor().equals(TipoInterlocutor.CLIENTE)) {
						System.out.println("Colocando o jogador local como branco");
						xadrezGrafico.setCorJogador(Cor.BRANCO);
					} else {
						System.out.println("Colocando o jogador local como preto");
						xadrezGrafico.setCorJogador(Cor.PRETO);
					}
					
					Logger.getInstance().logar("Conectado ao jogador " + interlocutor.getNome());
				}
			}
		};
		
		botaoMultiplayer.addActionListener(listenerQueFazAbrirONetworkDialog);
		return botaoMultiplayer;
	}
}
