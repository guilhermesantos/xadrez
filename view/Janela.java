package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.Cor;
import game.Xadrez;
import network.Interlocutor;
import network.TipoInterlocutor;
import runnable.ThreadAutoSave;
import timer.TimerGrafico;

public class Janela extends JFrame {

	private static final long serialVersionUID = -419158925384719190L;
	
//Atributos da interface gráfica
// ------------------------------------------------------//
	private Xadrez jogo;
	private GerenciadorInterfaceGraficaXadrez xadrezGrafico;
	private JPanel containerDosBotoes;
	private JButton botaoReiniciar;
	private JButton botaoSalvar;
	private JButton botaoMultiplayer;
	private JButton botaoCarregar;
	private Window janelaExterna;
	private static TimerGrafico timerPartida;
	private static TimerGrafico timerTurno;
	private JButton botaoConfigurar;
	
//Atributos da rede
	private Interlocutor interlocutor;
// ------------------------------------------------------//

	public Janela(String titulo, int largura, int altura) {
		super(titulo);
		configuraJanela(largura, altura);

		jogo = new Xadrez();

		ThreadAutoSave.getInstance().setJogo(jogo);
		new Thread(ThreadAutoSave.getInstance()).start();
		
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
		
		botaoConfigurar = criaBotaoConfigurar();
		containerDosBotoes.add(botaoConfigurar);
		
		botaoMultiplayer = criaBotaoMultiplayer();
		containerDosBotoes.add(botaoMultiplayer);
		
		timerPartida = new TimerGrafico("Duração da partida: ");
		containerDosBotoes.add(timerPartida);
		
		timerTurno = new TimerGrafico("Duração do turno: ");
		containerDosBotoes.add(timerTurno);
		
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
				timerPartida.setTempo(jogo.getTempoPartida());
				timerTurno.setTempo(jogo.getTempoTurno());
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
				SalvarJogoDialog salvarJogoDialog = new SalvarJogoDialog(
						janelaExterna, jogo);
				jogo.setTempoPartida(timerPartida.getTempo());
				jogo.setTempoTurno(timerTurno.getTempo());
				salvarJogoDialog.setVisible(true);
			}
		});
		return botaoSalvar;
	}
	
	private JButton criaBotaoCarregar() {
		JButton botaoCarregar = new JButton("Carregar");
		botaoCarregar.setBackground(Color.WHITE);
		
		Janela gamb = this;
		botaoCarregar.addActionListener(
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CarregarJogoDialog carregarJogoDialog = 
						new CarregarJogoDialog(janelaExterna, gamb);
				carregarJogoDialog.setVisible(true);
			}
		});
		return botaoCarregar;
	}
	
	public void callbackCarregaJogo(Xadrez jogoCarregado) {
		this.jogo = jogoCarregado;
		timerPartida.setTempo(jogo.getTempoPartida());
		timerTurno.setTempo(jogo.getTempoTurno());
		xadrezGrafico.substituiJogoEAtualizaGraficos(jogo);
	}
	
	private JButton criaBotaoConfigurar() {
		JButton botaoConfigurar = new JButton("Configurar");
		ConfiguracaoDialog configuracaoDialog = new ConfiguracaoDialog(janelaExterna);
		
		ActionListener listenerQueFazAbrirOConfiguracaoDialog = 
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						configuracaoDialog.setVisible(true);
					}
		};
		botaoConfigurar.addActionListener(listenerQueFazAbrirOConfiguracaoDialog);
		botaoConfigurar.setBackground(Color.WHITE);
		return botaoConfigurar;
	}
	
	private JButton criaBotaoMultiplayer() {
		JButton botaoMultiplayer = new JButton("Jogo em rede");
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
					botaoSalvar.setEnabled(false);
					botaoCarregar.setEnabled(false);
					botaoConfigurar.setEnabled(false);
					timerPartida.setVisible(false);
					timerPartida.encerraTimer();
					timerTurno.setVisible(false);
					timerTurno.encerraTimer();
					ThreadAutoSave.encerraAutoSave();
				}
			}
		};
		
		botaoMultiplayer.addActionListener(listenerQueFazAbrirONetworkDialog);
		return botaoMultiplayer;
	}

	public static TimerGrafico getTimerPartida() {
		return timerPartida;
	}

	public static void setTimerPartida(TimerGrafico timerPartida) {
		Janela.timerPartida = timerPartida;
	}

	public static TimerGrafico getTimerTurno() {
		return timerTurno;
	}

	public static void setTimerTurno(TimerGrafico timerTurno) {
		Janela.timerTurno = timerTurno;
	}
}
