package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Xadrez;

public class TabuleiroGrafico extends JPanel {
	private static final long serialVersionUID = -6253571512577610354L;
	private JPanel [][]casas;
	private List<Point> casasDestacadas;
	
	public TabuleiroGrafico(Xadrez jogo) {
		super.setLayout(new GridLayout(8, 8));
		constroiEColoreAsCasas();
		atualizaTabuleiroGrafico(jogo);
	}
	
	private void constroiEColoreAsCasas() {
		casas = new JPanel[8][8];
		boolean corBranca = true;

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				casas[i][j] = new JPanel();
				if(corBranca) {
					casas[i][j].setBackground(Color.WHITE);
				} else {
					casas[i][j].setBackground(Color.GRAY);
				}
				JLabel iconeDaPeca = new JLabel();
				casas[i][j].add(iconeDaPeca);
				super.add(casas[i][j]);
				corBranca = !corBranca;
			}
			corBranca = !corBranca;
		}	
	}
	
	public void atualizaTabuleiroGrafico(Xadrez jogo) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				JLabel containerDoIconeDaPeca = (JLabel)casas[i][j].getComponent(0);
				
				if(jogo.getTabuleiro().casaEstaVazia(i, j)) {
					containerDoIconeDaPeca.setIcon(null);
				} else {
					containerDoIconeDaPeca.setIcon(jogo.getTabuleiro().getPeca(i, j).getImagem());
				}
			}
		}
		super.paintAll(super.getGraphics());
		limpaCasasDestacadasSeHouver();
	}
	
	public void destacaCasas(List<Point> movimentosValidos) {
		limpaCasasDestacadasSeHouver(); 
		
		for(Point movimentoValido : movimentosValidos) {
			casas[movimentoValido.x][movimentoValido.y].setBackground(Color.YELLOW);
		}
		casasDestacadas.addAll(movimentosValidos);
	}
	
	public boolean casaEstaDestacada(Point casa) {
		return casasDestacadas.contains(casa);
	}
	
	public List<Point> getCasasDestacadas() {
		return casasDestacadas;
	}
	
	public void limpaCasasDestacadasSeHouver() {
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
	
	public Point converteCoordenadasEmCasaDoTabuleiro(MouseEvent e) {
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
	
	private void pintaFundoDePreto(int linha, int coluna) {
		casas[linha][coluna].setBackground(Color.GRAY);
	}

	private void pintaFundoDeBranco(int linha, int coluna) {
		casas[linha][coluna].setBackground(Color.WHITE);
	}
}
