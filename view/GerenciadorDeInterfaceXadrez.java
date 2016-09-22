package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import exceptions.MovimentoInvalidoException;
import game.EstadoJogo;
import game.Tabuleiro;
import game.Xadrez;

public class GerenciadorDeInterfaceXadrez extends JPanel implements Observer {
	
	private static final long serialVersionUID = -4661370508920536135L;

	private Xadrez jogo;
	private XadrezMouseListener listener;
	private JPanel[][] representacaoTabuleiro;
	private List<Point> casasDestacadas;
	
	public GerenciadorDeInterfaceXadrez() {
		super.setLayout(new GridLayout(8, 8));
		listener = new XadrezMouseListener();
		super.addMouseListener(listener);
		super.addMouseMotionListener(listener);
		
		jogo = new Xadrez(this);

		criaNovoTabuleiro();
		atualizaPecasNoTabuleiro();
		//Gambiarra. arrumar
		//limpaCasasDestacadasSeHouver();
	}
	
	private void criaNovoTabuleiro() {
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

				JLabel iconeDaPeca = new JLabel();
				representacaoTabuleiro[i][j].add(iconeDaPeca);
				corBranca = !corBranca;
			}
			corBranca = !corBranca;
		}

	}
	
	private void atualizaPecasNoTabuleiro() {
		Tabuleiro.getInstance().exibeTabuleiroNoConsole();
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				JLabel containerDoIconeDaPeca = (JLabel)representacaoTabuleiro[i][j].getComponent(0);
				
				if(Tabuleiro.getInstance().casaEstaVazia(i, j)) {
					containerDoIconeDaPeca.setIcon(null);
				} else {
					containerDoIconeDaPeca.setIcon(Tabuleiro.getInstance().getPeca(i, j).getImagem());
				}
			}
		}
		super.paintAll(super.getGraphics());
		//Gambiarra. arrumar
		limpaCasasDestacadasSeHouver();
	}
	
	private void destacaMovimentosValidos(List<Point> movimentosValidos) {
		limpaCasasDestacadasSeHouver(); 
		
		for(Point movimentoValido : movimentosValidos) {
			representacaoTabuleiro[movimentoValido.x][movimentoValido.y].setBackground(Color.RED);
		}
		casasDestacadas.addAll(movimentosValidos);
	}

	private void limpaCasasDestacadasSeHouver() {
		if(casasDestacadas != null && !casasDestacadas.isEmpty()) {
				for(Point casaDestacada : casasDestacadas) {
					if(casaDestacada.x % 2 == 0) {
						if(casaDestacada.y % 2 == 0) {
							pintaFundoDeBranco(casaDestacada.x, casaDestacada.y);
						} else {
							pintaFundoDePreto(casaDestacada.x, casaDestacada.y);
						}
					} else {
						if(casaDestacada.y % 2 == 0) {
							pintaFundoDePreto(casaDestacada.x, casaDestacada.y);
						} else {
							pintaFundoDeBranco(casaDestacada.x, casaDestacada.y);
						}
					}
				}
		}
		casasDestacadas = new ArrayList<Point>();
	}

	private void pintaFundoDePreto(int linha, int coluna) {
		representacaoTabuleiro[linha][coluna].setBackground(Color.GRAY);
	}

	private void pintaFundoDeBranco(int linha, int coluna) {
		representacaoTabuleiro[linha][coluna].setBackground(Color.WHITE);
	}
	
	private Point converteCoordenadasEmCasaDoTabuleiro(MouseEvent e) {
		Component compClicado = e.getComponent().getComponentAt(e.getX(), e.getY());

		int xExtremoEsquerdo = compClicado.getX();
		int yExtremoEsquerdo = compClicado.getY();
	
		int larguraComponente = compClicado.getWidth();
		int alturaComponente = compClicado.getHeight();
		
		int xPeca = (int) Math.floor(yExtremoEsquerdo/alturaComponente);
		int yPeca = (int) Math.floor(xExtremoEsquerdo/larguraComponente);
		
		Point coordenadasPecaSelecionada = new Point(xPeca, yPeca);
		return coordenadasPecaSelecionada;
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
				atualizaPecasNoTabuleiro();
				//limpaCasasDestacadasSeHouver();
				System.out.println("Action performed!");
				dialog.dispose();
			}
			
		});
		dialog.setVisible(true);
	}
	
	private class XadrezMouseListener implements MouseListener, MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			//System.out.println("esta em cima do elemento "+e.getComponent().toString());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			Point casaSelecionada = converteCoordenadasEmCasaDoTabuleiro(e);
			int linhaCasaSelecionada = casaSelecionada.x;
			int colunaCasaSelecionada = casaSelecionada.y;
			
			if(casasDestacadas.contains(casaSelecionada)) {
				jogo.movePeca(jogo.getPosicaoUltimaPecaSelecionada().x, 
						jogo.getPosicaoUltimaPecaSelecionada().y, linhaCasaSelecionada, colunaCasaSelecionada);
				atualizaPecasNoTabuleiro();
				//Gambiarra. arrumar
				//limpaCasasDestacadasSeHouver();
			} else {
				//Clicou em outra peca ou clicou no meio do nada
				try {
					System.out.println("Selecionando nova peca");
					//Clicou em outra peca
					List<Point> movimentosValidos = jogo.selecionaPeca(linhaCasaSelecionada, colunaCasaSelecionada);
					destacaMovimentosValidos(movimentosValidos);

				} catch (MovimentoInvalidoException e1) {
					//Clicou no meio do nada
					System.out.println("Movimento invalido!");
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			converteCoordenadasEmCasaDoTabuleiro(e);
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
