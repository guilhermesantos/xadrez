package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Tabuleiro extends JPanel {
	
	private static final long serialVersionUID = -4661370508920536135L;
	private TabuleiroMouseListener mouseListener = new TabuleiroMouseListener();
	
	public Tabuleiro() {
		super.setLayout(new GridLayout(8, 8));
		super.addMouseListener(mouseListener);
		geraRepresentacaoTabuleiro();
	}
	
	public void geraRepresentacaoTabuleiro() {
		boolean corBranca = true;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				JButton casaTabuleiro = new JButton();
				if(corBranca) {
					casaTabuleiro.setBackground(Color.WHITE);
				} else {
					casaTabuleiro.setBackground(Color.BLACK);
				}
				casaTabuleiro.addMouseListener(mouseListener);
				this.add(casaTabuleiro);
				corBranca = !corBranca;
			}
			corBranca = !corBranca;
		}
		System.out.println("printando!");
	}
	
	private class TabuleiroMouseListener implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			System.out.println("mouse arrastado");
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("moveu o mouse");
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
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
	}
}
