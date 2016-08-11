package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Tabuleiro;
import game.Xadrez;

public class XadrezPanel extends JPanel {
	
	private static final long serialVersionUID = -4661370508920536135L;
	private TabuleiroMouseListener mouseListener = new TabuleiroMouseListener();
	
	Xadrez jogo;
	private Tabuleiro tabuleiro;
	private JPanel[][] representacaoTabuleiro;
	
	public XadrezPanel() {
		super.setLayout(new GridLayout(8, 8));
		super.addMouseListener(mouseListener);
		super.addMouseMotionListener(mouseListener);
	
		jogo = new Xadrez();
		jogo.iniciaNovoJogo();
		tabuleiro = jogo.getTabuleiro();
		
		criaRepresentacaoTabuleiro();
		atualizaDesenhoDasPecasNoTabuleiro();
	}
	
	private void criaRepresentacaoTabuleiro() {
		representacaoTabuleiro = new JPanel[8][8];
		boolean corBranca = true;

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				representacaoTabuleiro[i][j] = new JPanel();
				if(corBranca) {
					representacaoTabuleiro[i][j].setBackground(Color.WHITE);
				} else {
					representacaoTabuleiro[i][j].setBackground(Color.GRAY);
				}
				this.add(representacaoTabuleiro[i][j]);
				corBranca = !corBranca;
			}
			corBranca = !corBranca;
		}
	}
	
	private void atualizaDesenhoDasPecasNoTabuleiro() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				JLabel imagem = new JLabel();
				if(tabuleiro.casaEstaVazia(i, j)) {
					continue;
				} else {
					JLabel imagemPeca = new JLabel(tabuleiro.getPeca(i, j).getImagem());
					representacaoTabuleiro[i][j].add(imagemPeca);
				}
			}
		}
	}
		
	private class TabuleiroMouseListener implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("mouse arrastado");
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			//System.out.println("esta em cima do elemento "+e.getComponent().toString());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			System.out.println("pressionou o mouse");
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			System.out.println("soltou o mouse");
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			System.out.println("mouse entrou");
		}

		@Override
		public void mouseExited(MouseEvent e) {
			System.out.println("mouse saiu");
		}
	}
}
