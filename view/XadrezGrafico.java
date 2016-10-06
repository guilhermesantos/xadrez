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
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Point coordenadasCasaSelecionada = tabuleiroGrafico.converteCoordenadasEmCasaDoTabuleiro(e);

			boolean casaSelecionadaEhUmMovimentoValido = jogo.getMovimentosValidos().contains(coordenadasCasaSelecionada);
			if(casaSelecionadaEhUmMovimentoValido) {
				jogo.movePeca(jogo.getCoordenadasPecaSelecionada(), coordenadasCasaSelecionada);
				tabuleiroGrafico.atualizaPosicaoDasPecasNoTabuleiroGrafico();
			} else {
				//Clicou em outra peca ou clicou no meio do nada
				try {
					List<Point> movimentosValidos = jogo.selecionaPeca(coordenadasCasaSelecionada);
					tabuleiroGrafico.destacaCasas(movimentosValidos);

				} catch (ClicouNoMeioDoNadaException e1) {
					System.out.println("O jogador clicou no meio do nada.");
				} catch (PecaNaoPertenceAoJogadorException e2) {
					System.out.println("A peca selecionada nao pertence ao jogador.");
				} catch (NaoHaMovimentosValidosException e3) {
					System.out.println("Nao h� movimentos v�lidos para a pe�a selecionada.");
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
