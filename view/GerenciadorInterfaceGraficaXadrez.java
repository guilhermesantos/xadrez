package view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import exceptions.ClicouNoMeioDoNadaException;
import exceptions.JogoJaAcabouException;
import exceptions.NaoEstaNaVezDoJogadorException;
import exceptions.NaoHaMovimentosValidosException;
import exceptions.PecaNaoPertenceAoJogadorException;
import game.Cor;
import game.EstadoJogo;
import game.Xadrez;
import network.Interlocutor;
import network.MensagemComJogo;

public class GerenciadorInterfaceGraficaXadrez extends JPanel implements Observer {
	
	private static final long serialVersionUID = -4661370508920536135L;

	private Xadrez jogo;
	private TabuleiroGrafico tabuleiroGrafico;
	private Interlocutor interlocutor;
	private XadrezMouseListener listener;
	private Cor corJogador;

	public GerenciadorInterfaceGraficaXadrez(Xadrez jogo, Interlocutor interlocutor) {
		this(jogo);
		this.interlocutor = interlocutor;
	}
	
	public GerenciadorInterfaceGraficaXadrez(Xadrez jogo) {
		super.setLayout(new BorderLayout());
		listener = new XadrezMouseListener();
		super.addMouseListener(listener);
		
		tabuleiroGrafico = new TabuleiroGrafico();
		tabuleiroGrafico.addMouseListener(listener);

		substituiJogoEAtualizaGraficos(jogo);
		Logger.getInstance().logar(jogo.getEstadoJogo().toString());
		
		interlocutor = null;
		
		corJogador = Cor.BRANCO;
		super.add(tabuleiroGrafico);
	}
	
	public boolean jogoEstaEmRede() {
		return interlocutor != null;
	}
	
	public void substituiJogoEAtualizaGraficos(Xadrez jogo) {
		this.jogo = jogo;
		this.corJogador = jogo.getCorDoUltimoJogadorAAgir().alternaCor();
		tabuleiroGrafico.atualizaTabuleiroGraficoInteiro(jogo);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("Recebendo o estado do jogo");
		MensagemComJogo mensagem = (MensagemComJogo)arg;
		substituiJogoEAtualizaGraficos(mensagem.getConteudoMensagem());
		System.out.println("Recebeu o estado do jogo e restaurou o estado do jogo deste lado");
	}
	
	private void exibeODialogDeFimDeJogo() {
		DialogComMensagemEBotao dialogDeFimDeJogo = new DialogComMensagemEBotao
				(SwingUtilities.getWindowAncestor(this), "Fim de jogo");
		
		ActionListener listenerQueFazOBotaoReiniciarOJogo = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Xadrez novoJogo = new Xadrez();
				substituiJogoEAtualizaGraficos(novoJogo);
				corJogador = Cor.BRANCO;
				if(jogoEstaEmRede()) {
					interlocutor.escreveMensagem(new MensagemComJogo(jogo));
				}
				dialogDeFimDeJogo.dispose();
			}
		};
		
		dialogDeFimDeJogo.substituiActionListenerDoBotao(listenerQueFazOBotaoReiniciarOJogo);
		
		if(jogo.getEstadoJogo().equals(EstadoJogo.VITORIA_BRANCO)) {
			dialogDeFimDeJogo.setTextoMensagem("Pecas brancas venceram!");
		} else if(jogo.getEstadoJogo().equals(EstadoJogo.VITORIA_PRETO)){
			dialogDeFimDeJogo.setTextoMensagem("Pecas pretas venceram!");
		}
		
		dialogDeFimDeJogo.setVisible(true);
	}
	
	public void setCorJogador(Cor corJogador) {
		this.corJogador = corJogador;
	}

	private class XadrezMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			Point coordenadasCasaClicada = tabuleiroGrafico.converteCoordenadasEmCasaDoTabuleiro(e);
			
			boolean casaClicadaEhUmMovimentoValido = jogo.getMovimentosValidos()
					.contains(coordenadasCasaClicada);
			
			if(casaClicadaEhUmMovimentoValido) {
				jogo.movePeca(corJogador, jogo.getCoordenadasPecaSelecionada(), coordenadasCasaClicada);
				tabuleiroGrafico.atualizaTabuleiroGraficoInteiro(jogo);
				
				if(jogo.jogoJaAcabou()) {
					exibeODialogDeFimDeJogo();
				}
				Logger.getInstance().logar(jogo.getEstadoJogo().toString());
				
				if(jogoEstaEmRede()) {
					System.out.println("Jogo está em rede");
					interlocutor.escreveMensagem(new MensagemComJogo(jogo));
				} else {
					corJogador = corJogador.alternaCor();
				}
			} else {
				try {
					jogo.selecionaPeca(corJogador, coordenadasCasaClicada);
					tabuleiroGrafico.atualizaTabuleiroGraficoInteiro(jogo);
					
				} catch (ClicouNoMeioDoNadaException e1) {
					jogo.getMovimentosValidos().clear();
					tabuleiroGrafico.atualizaTabuleiroGraficoInteiro(jogo);
				} catch (PecaNaoPertenceAoJogadorException e2) {
					Logger.getInstance().logar("A peca selecionada nao pertence ao jogador.");
				} catch (NaoHaMovimentosValidosException e3) {
					Logger.getInstance().logar("Nao há movimentos válidos para a peça selecionada.");
				} catch (JogoJaAcabouException e1) {
					Logger.getInstance().logar("O jogo já terminou. Não há mais movimentos válidos.");
				} catch (NaoEstaNaVezDoJogadorException e1) {
					Logger.getInstance().logar("Não é a sua vez. Por favor, aguarde o movimento do outro jogador.");
				} 
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}
	}

	public Interlocutor getInterlocutor() {
		return interlocutor;
	}

	public void colocaOJogoEmRede(Interlocutor interlocutor) {
		interlocutor.addObserver(this);
		this.interlocutor = interlocutor;
	}
}
