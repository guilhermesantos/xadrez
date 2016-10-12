package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import network.GerenciadorDeRede;

public class NetworkDialog extends JDialog implements Observer {
	private static final long serialVersionUID = 2274645232014160309L;

//Atributos da interface grafica
// ------------------------------------------------------//
	private JPanel painelDeEntradaDeDadosDaConexao;
	private JPanel painelConectando;
	private JPanel painelAguardandoConexao;
	
	private JPanel containerDosCamposDaConexao;
	private JPanel containerDosBotoesDaConexao;
	private JPanel containerDosRadioButtons;

	private JRadioButton radioHospedar;
	private JRadioButton radioConectar;
	
	private JTextField campoPortaConexao;
	private JTextField campoIPDestino;
	private JTextField campoNomeJogador;

	private CardLayout layoutDoDialog;
	
//Atributos para conexao em rede	
// ------------------------------------------------------//
	private GerenciadorDeRede gerenciadorDeRede;
	
	private static final int LARGURA_DIALOG = 200;
	private static final int ALTURA_DIALOG = 300;
	
	public NetworkDialog(Window window) {
		super(window, "Multiplayer");
		configuraDialog();
		
		painelDeEntradaDeDadosDaConexao = constroiPainelDeEntradaDeDadosDaConexao();
		super.add(painelDeEntradaDeDadosDaConexao, "painelDeEntradaDeDadosDaConexao");
		
		painelConectando = constroiPainelConectando();
		super.add(painelConectando, "painelConectando");
		
		painelAguardandoConexao = constroiPainelAguardandoConexao();
		super.add(painelAguardandoConexao, "painelAguardandoConexao");
	}
	
	private void configuraDialog() {
		super.setSize(LARGURA_DIALOG, ALTURA_DIALOG);
		super.setLocation(50, 50);
		super.setResizable(false);
		super.setModal(true);
		layoutDoDialog = new CardLayout();
		super.setLayout(layoutDoDialog);
	}
	
	private JPanel constroiPainelAguardandoConexao() {
		JPanel painelAguardandoConexao = new JPanel(new BorderLayout());

		JLabel labelAguardandoConexao = new JLabel("            Aguardando conexao...");
		painelAguardandoConexao.add(labelAguardandoConexao, BorderLayout.CENTER);
		
		JButton botaoCancelar = new JButton("Cancelar");
		botaoCancelar.addActionListener(criaActionListenerQueCancelaAguardandoConexao());
		painelAguardandoConexao.add(botaoCancelar, BorderLayout.SOUTH);
		return painelAguardandoConexao;
	}
	
	private JPanel constroiPainelConectando() {
		JPanel painelConectando = new JPanel(new BorderLayout());

		JLabel labelConectando = new JLabel("                    Conectando...");
		painelConectando.add(labelConectando, BorderLayout.CENTER);
		
		JButton botaoCancelar = new JButton("Cancelar");
		botaoCancelar.addActionListener(criaActionListenerQueCancelaConexao());
		painelConectando.add(botaoCancelar, BorderLayout.SOUTH);
		
		return painelConectando;
	}
	
	private JPanel constroiPainelDeEntradaDeDadosDaConexao() {
		JPanel painelDeDados = new JPanel(new BorderLayout());
		
		containerDosCamposDaConexao = constroiContainerDosCamposDaConexao();
		painelDeDados.add(containerDosCamposDaConexao, BorderLayout.CENTER);

		containerDosRadioButtons = constroiContainerDeRadioButtons();
		painelDeDados.add(containerDosRadioButtons, BorderLayout.NORTH);
		
		containerDosBotoesDaConexao = constroiContainerDosBotoesDaConexao();
		painelDeDados.add(containerDosBotoesDaConexao, BorderLayout.SOUTH);
		
		return painelDeDados;
	}
	
	private JPanel constroiContainerDosCamposDaConexao() {
			
			JPanel containerCampos = new JPanel(new FlowLayout());
	
			JLabel labelNomeJogador = new JLabel("Nome do jogador: ");
			campoNomeJogador = new JTextField(10);
			
			JLabel labelIPDestino = new JLabel("Ip de destino: ");
			campoIPDestino = new JTextField(10);
	
			JLabel labelPortaConexao = new JLabel("Porta para fazer conexão: ");
			campoPortaConexao = new JTextField(10);
			
			JLabel labelIPLocal = null;
			labelIPLocal = new JLabel("IP local: " + GerenciadorDeRede.getIpLocal().getHostAddress());

			containerCampos.add(labelNomeJogador);
			containerCampos.add(campoNomeJogador);
			
			containerCampos.add(labelPortaConexao);
			containerCampos.add(campoPortaConexao);

			containerCampos.add(labelIPDestino);
			containerCampos.add(campoIPDestino);
	
			containerCampos.add(labelIPLocal);
			
			return containerCampos;
	}	
	
	private JPanel constroiContainerDeRadioButtons() {
		radioHospedar = new JRadioButton("Hospedar jogo", false);
		radioConectar = new JRadioButton("Conectar a  um jogo", false);
		
		ItemListener radioButtonListener = criaRadioButtonListener();
		radioHospedar.addItemListener(radioButtonListener);
		radioConectar.addItemListener(radioButtonListener);

		ButtonGroup radioGroup = new ButtonGroup();
		radioGroup.add(radioHospedar);
		radioGroup.add(radioConectar);
		
		JPanel painelDeRadioButtons = new JPanel(new BorderLayout());
		painelDeRadioButtons.add(radioHospedar, BorderLayout.NORTH);
		painelDeRadioButtons.add(radioConectar, BorderLayout.CENTER);
		
		radioGroup.setSelected(radioHospedar.getModel(), true);
		
		return painelDeRadioButtons;
	}

	
	
	private JPanel constroiContainerDosBotoesDaConexao() {
		JPanel containerDosBotoes = new JPanel(new FlowLayout());
		
		JButton botaoCancelar = new JButton("Cancelar");
		botaoCancelar.addActionListener(criaActionListenerQueFechaODialog());
		containerDosBotoes.add(botaoCancelar);
		
		JButton botaoConectar = new JButton("Conectar");
		botaoConectar.addActionListener(criaActionListenerQueRealizaConexao());
		containerDosBotoes.add(botaoConectar);
		
		return containerDosBotoes;
	}
	
	private ActionListener criaActionListenerQueFechaODialog() {
		ActionListener listener = new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
		return listener;
	}
	
	private ActionListener criaActionListenerQueRealizaConexao() {
		NetworkDialog gambiarra = this;
		
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				gerenciadorDeRede = new GerenciadorDeRede(Integer
						.parseInt(campoPortaConexao.getText()), campoNomeJogador.getText());

				if(radioHospedar.isSelected()) {
					layoutDoDialog.show(getContentPane(), "painelAguardandoConexao");
					gerenciadorDeRede.estabeleceServidorLocal(gambiarra);
					
				} else if(radioConectar.isSelected()) {
					layoutDoDialog.show(getContentPane(), "painelConectando");
				}
				
			}
		};
		return listener;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		try {
			gerenciadorDeRede.fechaServidorLocal();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ActionListener criaActionListenerQueCancelaConexao() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layoutDoDialog.show(getContentPane(), "painelDeEntradaDeDadosDaConexao");
			}
		};
		return listener;	
	}
	
	private ActionListener criaActionListenerQueCancelaAguardandoConexao() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layoutDoDialog.show(getContentPane(), "painelDeEntradaDeDadosDaConexao");
			}
		};
		return listener;
	}
	
	private ItemListener criaRadioButtonListener() {
		ItemListener radioButtonListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(radioHospedar.isSelected()) {
					campoIPDestino.setEnabled(false);
					campoIPDestino.setBackground(Color.LIGHT_GRAY);
				} else if(radioConectar.isSelected()) {
					campoIPDestino.setEnabled(true);
					campoIPDestino.setBackground(Color.WHITE);
				}
			}
		};
		return radioButtonListener;
	}
}
