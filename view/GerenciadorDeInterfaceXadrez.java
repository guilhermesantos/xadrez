package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import exceptions.MovimentoInvalidoException;
import game.Tabuleiro;
import game.Xadrez;

public class GerenciadorDeInterfaceXadrez extends JPanel {
	
	private static final long serialVersionUID = -4661370508920536135L;

	Xadrez jogo;
	private XadrezMouseListener listener;
	private Tabuleiro tabuleiro;
	private JPanel[][] representacaoTabuleiro;
	
	public GerenciadorDeInterfaceXadrez() {
		super.setLayout(new GridLayout(8, 8));
		
		listener = new XadrezMouseListener();
		super.addMouseListener(listener);
		super.addMouseMotionListener(listener);

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
				JLabel imagem = new JLabel();
				if(corBranca) {
					representacaoTabuleiro[i][j].setBackground(Color.WHITE);
				} else {
					representacaoTabuleiro[i][j].setBackground(Color.GRAY);
				}
				representacaoTabuleiro[i][j].add(imagem);
				this.add(representacaoTabuleiro[i][j]);
				corBranca = !corBranca;
			}
			corBranca = !corBranca;
		}
	}
	
	private void atualizaDesenhoDasPecasNoTabuleiro() {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				JLabel imagemPeca = (JLabel)representacaoTabuleiro[i][j].getComponent(0);
				
				if(tabuleiro.casaEstaVazia(i, j)) {
					imagemPeca.setIcon(null);
				} else {
					imagemPeca = new JLabel(tabuleiro.getPeca(i, j).getImagem());
					representacaoTabuleiro[i][j].add(imagemPeca);
				}
			}
		}
	}
	
	private Point converteCoordenadasEmCasaDoTabuleiro(MouseEvent e) {
		Component compClicado = e.getComponent().getComponentAt(e.getX(), e.getY());

		int xExtremoEsquerdo = compClicado.getX();
		int yExtremoEsquerdo = compClicado.getY();
		System.out.println("x e y do extremo esquerdo: " + xExtremoEsquerdo + " " + yExtremoEsquerdo);
	
		int larguraComponente = compClicado.getWidth();
		int alturaComponente = compClicado.getHeight();
		System.out.println("Largura e altura do componente: " + larguraComponente + " " + alturaComponente);
		
		int xPeca = (int) Math.floor(yExtremoEsquerdo/alturaComponente);
		System.out.println("Numero horizontal da casa: " + xPeca);
		
		int yPeca = (int) Math.floor(xExtremoEsquerdo/larguraComponente);
		System.out.println("Numero vertical da casa: " + yPeca);
		
		Point coordenadasPecaSelecionada = new Point(xPeca, yPeca);
		return coordenadasPecaSelecionada;
	}
	
	private class XadrezMouseListener implements MouseListener, MouseMotionListener {

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
			Point coordenadasCasaSelecionada = converteCoordenadasEmCasaDoTabuleiro(e);
			int xCasa = (int)coordenadasCasaSelecionada.getX();
			int yCasa = (int)coordenadasCasaSelecionada.getY();
			
			try {
				List<Point> movimentosValidos = jogo.selecionaPeca(xCasa, yCasa);
				representacaoTabuleiro[xCasa][yCasa].setBackground(Color.RED);
				for(Point movimento : movimentosValidos) {
					representacaoTabuleiro[(int) movimento.getX()][(int) movimento.getY()].setBackground(Color.BLUE);
				}
			} catch (MovimentoInvalidoException e1) {
				System.out.println("Movimento invalido!");
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			converteCoordenadasEmCasaDoTabuleiro(e);
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
