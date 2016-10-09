package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import game.Xadrez;

public class Janela extends JFrame {

	private static final long serialVersionUID = -419158925384719190L;
	
	private Xadrez jogo;
	private XadrezGrafico xadrezGrafico;
	private JButton botaoReiniciar;
	private JButton botaoSalvar;
	private JButton botaoMultiplayer;
	private JButton botaoCarregar;

	private Window janelaExterna;

	public Janela(String titulo, int largura, int altura) {
		super(titulo);
		super.setSize(largura, (int)(altura*1.07));
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		BorderLayout layout = new BorderLayout();
		super.setLayout(layout);
		janelaExterna = SwingUtilities.getWindowAncestor(this);
		
		JPanel container = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		botaoReiniciar = criaBotaoReiniciar();
		container.add(botaoReiniciar);
		
		botaoSalvar = criaBotaoSalvar();
		container.add(botaoSalvar);
		
		botaoCarregar = criaBotaoCarregar();
		container.add(botaoCarregar);
		
		botaoMultiplayer = criaBotaoMultiplayer();
		container.add(botaoMultiplayer);
		super.add(container, BorderLayout.NORTH);
		
		
		jogo = new Xadrez();
		xadrezGrafico = new XadrezGrafico(jogo);
		jogo.addObserver(xadrezGrafico);
		super.add(xadrezGrafico, BorderLayout.CENTER);
		
		super.add(Logger.getInstance(), BorderLayout.SOUTH);
	}
	
	private JButton criaBotaoReiniciar() {
		JButton botaoReiniciar = new JButton("Reiniciar");
		botaoReiniciar.setBackground(Color.WHITE);
		
		botaoReiniciar.addActionListener(
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jogo.iniciaNovoJogo();
				xadrezGrafico.atualizaRepresentacaoGrafica();
			}
		});
		return botaoReiniciar;
	}
	
	private JButton criaBotaoSalvar() {
		JButton botaoSalvar = new JButton("Salvar");
		botaoSalvar.setBackground(Color.WHITE);
		
		botaoSalvar.addActionListener(
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					jogo.salvaJogo("jogo_salvo.dat");
					//XadrezDialog dialog = new XadrezDialog(janelaExterna, "Salvar", "Jogo salvo.");
					//dialog.setVisible(true);
					Logger.getInstance().logar("Jogo salvo.");
				} catch (FileNotFoundException e1) {
					System.out.println("Arquivo nao encontrado");
				} catch (IOException e1) {
					System.out.println("Erro ao criar o object output stream");
				}
			}
		});
		return botaoSalvar;
	}
	
	private JButton criaBotaoCarregar() {
		JButton botaoCarregar = new JButton("Carregar");
		botaoCarregar.setBackground(Color.WHITE);
		
		botaoCarregar.addActionListener(
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					jogo = jogo.carregaJogo();
					xadrezGrafico.setJogo(jogo);
					xadrezGrafico.atualizaRepresentacaoGrafica();
				} catch (FileNotFoundException e1) {
					Logger.getInstance().logar("Nao encontrou o arquivo jogo_salvo.dat");
				} catch (IOException e1) {
					Logger.getInstance().logar("IOException. :(");
				} catch (ClassNotFoundException e1) {
					Logger.getInstance().logar("Erro ao deserializar a instância de xadrez que estava salva em disco!");
				}
			
			}
		});
		return botaoCarregar;
	}
	
	private JButton criaBotaoMultiplayer() {
		JButton botaoMultiplayer = new JButton("Multiplayer");
		botaoMultiplayer.setBackground(Color.WHITE);
		
		botaoMultiplayer.addActionListener(
		new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				XadrezDialog dialog = new XadrezDialog(janelaExterna, 
						"Multiplayer", "Aguardando conexão", "Cancelar");
				dialog.setVisible(true);
			}
		});
		return botaoMultiplayer;
	}


}
