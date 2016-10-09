package view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import exceptions.BotaoNaoTemListenerException;
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
		
		tabuleiroGrafico = new TabuleiroGrafico();
		tabuleiroGrafico.addMouseListener(listener);
		add(tabuleiroGrafico);
	}
	
	public void atualizaRepresentacaoGrafica() {
		tabuleiroGrafico.atualizaTabuleiroGrafico();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		XadrezDialog dialog = new XadrezDialog
				(SwingUtilities.getWindowAncestor(this), "Fim de jogo");

		ActionListener fazBotaoReiniciarOJogo = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jogo.iniciaNovoJogo();
				tabuleiroGrafico.atualizaTabuleiroGrafico();
				dialog.dispose();
			}
		};

		if(arg.equals(EstadoJogo.TURNO_BRANCO)) {
			dialog.setTextoMensagem("Jogador branco venceu!");
			System.out.println("Jogador branco venceu!");
		} else if(arg.equals(EstadoJogo.TURNO_PRETO)){
			dialog.setTextoMensagem("Jogador preto venceu!");
			System.out.println("Jogador preto venceu!");
		}

		try {
			dialog.substituiActionListenerDoBotao(fazBotaoReiniciarOJogo);
		} catch (BotaoNaoTemListenerException e1) {
			System.out.println("Problema ao substituir o action listener do botão");
		}
		
		dialog.setVisible(true);
	}
	
	public void setJogo(Xadrez jogo) {
		this.jogo = jogo;
	}
	
	private class XadrezMouseListener implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Point coordenadasCasaSelecionada = tabuleiroGrafico.converteCoordenadasEmCasaDoTabuleiro(e);
			
			boolean casaSelecionadaEhUmMovimentoValido = jogo.getMovimentosValidos().contains(coordenadasCasaSelecionada);
			if(casaSelecionadaEhUmMovimentoValido) {
				jogo.movePeca(jogo.getCoordenadasPecaSelecionada(), coordenadasCasaSelecionada);
				tabuleiroGrafico.atualizaTabuleiroGrafico();
				Logger.getInstance().logar(jogo.getEstadoJogo().toString());
			} else {
				//Clicou em outra peca ou clicou no meio do nada
				try {
					List<Point> movimentosValidos = jogo.selecionaPeca(coordenadasCasaSelecionada);
					tabuleiroGrafico.destacaCasas(movimentosValidos);

				} catch (ClicouNoMeioDoNadaException e1) {
					Logger.getInstance().logar("O jogador clicou no meio do nada.");
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
