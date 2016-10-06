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

import exceptions.ClicouNoMeioDoNadaException;
import exceptions.MovimentoInvalidoException;
import exceptions.NaoHaMovimentosValidosException;
import exceptions.PecaNaoPertenceAoJogadorException;
import game.EstadoJogo;
import game.Xadrez;

public class XadrezGrafico extends JPanel implements Observer {
	
	private static final long serialVersionUID = -4661370508920536135L;

	private Xadrez jogo;
	private TabuleiroGrafico tabuleiroGrafico;
	private XadrezMouseListener listener;
	
	public XadrezGrafico() {
		super.setLayout(new BorderLayout());
		listener = new XadrezMouseListener();
		super.addMouseListener(listener);
		
		jogo = new Xadrez(this);

		tabuleiroGrafico = new TabuleiroGrafico(jogo);
		add(tabuleiroGrafico);
		tabuleiroGrafico.addMouseListener(listener);
	}
	
	@Override
	public void update(Observable o, Object arg) {
		FimDeJogoDialog dialog = new FimDeJogoDialog
				(SwingUtilities.getWindowAncestor(this), "Fim de jogo");

		if(arg.equals(EstadoJogo.TURNO_BRANCO)) {
			dialog.setText("Jogador branco venceu!");
			System.out.println("Jogador branco venceu!");
		} else {
			dialog.setText("Jogador preto venceu!");
			System.out.println("Jogador preto venceu!");
		}
		
		dialog.getButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jogo.iniciaNovoJogo();
				tabuleiroGrafico.atualizaPosicaoDasPecasNoTabuleiroGrafico();
				dialog.dispose();
			}
			
		});
		dialog.setVisible(true);
	}
	
	private class XadrezMouseListener implements MouseListener {
		public XadrezMouseListener() {
			System.out.println("blablabla");
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Point casaSelecionada = tabuleiroGrafico.converteCoordenadasEmCasaDoTabuleiro(e);
			int linhaCasaSelecionada = casaSelecionada.x;
			int colunaCasaSelecionada = casaSelecionada.y;
			
			if(tabuleiroGrafico.casaEstaDestacada(casaSelecionada)) {
				jogo.movePeca(jogo.getPosicaoUltimaPecaSelecionada().x, 
						jogo.getPosicaoUltimaPecaSelecionada().y, linhaCasaSelecionada, colunaCasaSelecionada);
				tabuleiroGrafico.atualizaPosicaoDasPecasNoTabuleiroGrafico();
			} else {
				//Clicou em outra peca ou clicou no meio do nada
				try {
					List<Point> movimentosValidos = jogo.selecionaPeca(linhaCasaSelecionada, colunaCasaSelecionada);
					tabuleiroGrafico.destacaCasas(movimentosValidos);

				} catch (ClicouNoMeioDoNadaException e1) {
					System.out.println("O jogador clicou no meio do nada.");
				} catch (PecaNaoPertenceAoJogadorException e2) {
					System.out.println("A peca selecionada nao pertence ao jogador!");
				} catch (NaoHaMovimentosValidosException e3) {
					System.out.println("Nao há movimentos válidos para a peça selecionada.");
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
