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
import exceptions.NaoHaMovimentosValidosException;
import exceptions.PecaNaoPertenceAoJogadorException;
import game.EstadoJogo;
import game.Xadrez;

public class XadrezGrafico extends JPanel implements Observer {
	
	private static final long serialVersionUID = -4661370508920536135L;

	private Xadrez jogo;

	private TabuleiroGrafico tabuleiroGrafico;
	private XadrezMouseListener listener;

	public XadrezGrafico(Xadrez jogo) {
		super.setLayout(new BorderLayout());
		listener = new XadrezMouseListener();
		super.addMouseListener(listener);
		
		this.jogo = jogo;
		Logger.getInstance().logar(jogo.getEstadoJogo().toString());
		
		tabuleiroGrafico = new TabuleiroGrafico(jogo);
		tabuleiroGrafico.addMouseListener(listener);
		super.add(tabuleiroGrafico);
	}
	
	public void substituiJogoEAtualizaGraficos(Xadrez jogo) {
		this.jogo = jogo;
		tabuleiroGrafico.atualizaTabuleiroGraficoInteiro(jogo);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		DialogComMensagemEBotao dialogDeFimDeJogo = new DialogComMensagemEBotao
				(SwingUtilities.getWindowAncestor(this), "Fim de jogo");
		XadrezGrafico gambiarra = this;
		
		ActionListener listenerQueFazOBotaoReiniciarOJogo = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Xadrez novoJogo = new Xadrez(gambiarra);
				substituiJogoEAtualizaGraficos(novoJogo);
				dialogDeFimDeJogo.dispose();
			}
		};
		
		dialogDeFimDeJogo.substituiActionListenerDoBotao(listenerQueFazOBotaoReiniciarOJogo);

		if(arg.equals(EstadoJogo.TURNO_BRANCO)) {
			dialogDeFimDeJogo.setTextoMensagem("Pecas brancas venceram!");
		} else if(arg.equals(EstadoJogo.TURNO_PRETO)){
			dialogDeFimDeJogo.setTextoMensagem("Pecas pretas venceram!");
		}
		
		dialogDeFimDeJogo.setVisible(true);
	}
	
	private class XadrezMouseListener implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Point coordenadasCasaClicada = tabuleiroGrafico.converteCoordenadasEmCasaDoTabuleiro(e);
			
			boolean casaClicadaEhUmMovimentoValido = jogo.getMovimentosValidos()
					.contains(coordenadasCasaClicada);
			
			if(casaClicadaEhUmMovimentoValido) {
				jogo.movePeca(jogo.getCoordenadasPecaSelecionada(), coordenadasCasaClicada);
				tabuleiroGrafico.atualizaTabuleiroGraficoInteiro(jogo);;
				Logger.getInstance().logar(jogo.getEstadoJogo().toString());
			
			} else {
				//Clicou em outra peca ou clicou no meio do nada
				try {
					
					jogo.selecionaPeca(coordenadasCasaClicada);
					tabuleiroGrafico.atualizaTabuleiroGraficoInteiro(jogo);

				} catch (ClicouNoMeioDoNadaException e1) {
					
					//Logger.getInstance().logar("O jogador clicou no meio do nada.");
					jogo.getMovimentosValidos().clear();
					tabuleiroGrafico.atualizaTabuleiroGraficoInteiro(jogo);
					
				} catch (PecaNaoPertenceAoJogadorException e2) {
					Logger.getInstance().logar("A peca selecionada nao pertence ao jogador.");
				} catch (NaoHaMovimentosValidosException e3) {
					Logger.getInstance().logar("Nao há movimentos válidos para a peça selecionada.");
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
}
