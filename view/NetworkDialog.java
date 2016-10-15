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
import network.Interlocutor;

public class NetworkDialog extends JDialog implements Observer {
	private static final long serialVersionUID = 2274645232014160309L;

//Atributos da interface grafica
// ------------------------------------------------------//
	private JPanel painelDeEntradaDeDadosDaConexao;
	private PainelComMensagens painelConectando;
	private PainelComMensagens painelConectandoFalhou;
	private PainelComMensagens painelConectandoFuncionou;
	private PainelComMensagens painelAguardandoConexao;
	private PainelComMensagens painelAguardandoConexaoFalhou;
	private PainelComMensagens painelAguardandoConexaoFuncionou;
	
	private JPanel containerDosCamposDaConexao;
	private JPanel containerDosBotoesDaConexao;
	private JPanel containerDosRadioButtons;

	private JRadioButton radioHospedar;
	private JRadioButton radioConectar;
	
	private JTextField campoPortaConexao;
	private JTextField campoIPRemoto;
	private JTextField campoNomeJogador;

	private CardLayout layoutDoDialog;
	
//Atributos para conexao em rede	
// ------------------------------------------------------//
	private GerenciadorDeRede gerenciadorDeRede;
	private Interlocutor interlocutor;
	
	private static final int LARGURA_DIALOG = 200;
	private static final int ALTURA_DIALOG = 300;
	
	public NetworkDialog(Window window) {
		super(window, "Multiplayer");

		if(gerenciadorDeRede == null) {
			gerenciadorDeRede = new GerenciadorDeRede(this);
		}
		
		configuraDialog();
		
		criaPaineisDoCardLayout();
	}

	private void criaPaineisDoCardLayout() {
		painelDeEntradaDeDadosDaConexao = constroiPainelDeEntradaDeDadosDaConexao();
		super.add(painelDeEntradaDeDadosDaConexao, "painelDeEntradaDeDadosDaConexao");
		
		painelConectando = new PainelComMensagens("Conectando...");
		painelConectando.colocaActionListenerNoBotao(criaActionListenerQueCancelaConectando());
		super.add(painelConectando, "painelConectando");
		
		painelConectandoFalhou = new PainelComMensagens("Conexão falhou.", "Certeza", "que o servidor está pronto?");
		painelConectandoFalhou.colocaActionListenerNoBotao(criaActionListenerQueCancelaConectando());
		super.add(painelConectandoFalhou, "painelConectandoFalhou");
		
		painelConectandoFuncionou = new PainelComMensagens("Conectou ao servidor!");
		painelConectandoFuncionou.colocaActionListenerNoBotao(criaActionListenerQueFechaODialog());
		painelConectandoFuncionou.mudaTextoDoBotao("Fechar");
		super.add(painelConectandoFuncionou, "painelConectandoFuncionou");
		
		painelAguardandoConexao = new PainelComMensagens("Aguardando conexao...");
		painelAguardandoConexao.colocaActionListenerNoBotao(criaActionListenerQueCancelaAguardandoConexao());
		super.add(painelAguardandoConexao, "painelAguardandoConexao");
		
		painelAguardandoConexaoFalhou = new PainelComMensagens("Servidor falhou.","Certeza ", "que a porta está livre?");
		painelAguardandoConexaoFalhou.colocaActionListenerNoBotao(criaActionListenerQueCancelaAguardandoConexao());
		super.add(painelAguardandoConexaoFalhou, "painelAguardandoConexaoFalhou");
		
		painelAguardandoConexaoFuncionou = new PainelComMensagens("Cliente detectado!");
		painelAguardandoConexaoFuncionou.colocaActionListenerNoBotao(criaActionListenerQueFechaODialog());
		painelAguardandoConexaoFuncionou.mudaTextoDoBotao("Fechar");
		super.add(painelAguardandoConexaoFuncionou, "painelAguardandoConexaoFuncionou");
	}
	
	private void configuraDialog() {
		super.setSize(LARGURA_DIALOG, ALTURA_DIALOG);
		super.setLocation(50, 50);
		super.setResizable(false);
		super.setModal(true);
		layoutDoDialog = new CardLayout();
		super.setLayout(layoutDoDialog);
		this.interlocutor = null;
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
			JLabel labelPortaConexao = new JLabel("Porta para fazer conexão: ");
			JLabel labelIPRemoto = new JLabel("Ip de destino: ");
			JLabel labelIPLocal = new JLabel("IP local: " + GerenciadorDeRede.getIpLocal().getHostAddress());
			
			campoNomeJogador = new JTextField(10);
			campoIPRemoto = new JTextField(10);
			campoPortaConexao = new JTextField(10);

			containerCampos.add(labelNomeJogador);
			containerCampos.add(campoNomeJogador);
			
			containerCampos.add(labelPortaConexao);
			containerCampos.add(campoPortaConexao);

			containerCampos.add(labelIPRemoto);
			containerCampos.add(campoIPRemoto);
	
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
		
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				if(radioHospedar.isSelected()) {
					layoutDoDialog.show(getContentPane(), "painelAguardandoConexao");
					iniciaConexaoComoServidor();
					
				} else if(radioConectar.isSelected()) {
					System.out.println("Vai mudar para o painel que avisa que o cliente esta conectando");
					layoutDoDialog.show(getContentPane(), "painelConectando");
					System.out.println("Mudou o painel!");
					iniciaConexaoComoCliente();
				}
			}
		};
		return listener;
	}
	
	public void iniciaConexaoComoServidor() {
		try {
			gerenciadorDeRede.iniciaConexaoComoServidor(Integer.parseInt(campoPortaConexao.getText()));
		} catch (IOException e) {
			System.out.println("Aguardando conexao falhou!");
			layoutDoDialog.show(getContentPane(), "painelAguardandoConexaoFalhou");
		}
	}
	
	public void iniciaConexaoComoCliente() {
		System.out.println("Vai iniciar conexao como cliente");
		gerenciadorDeRede.iniciaConexaoComoCliente(campoIPRemoto.getText(), 
				Integer.parseInt(campoPortaConexao.getText()));
	}
	
	@Override
	//Invocado quando o cliente remoto conecta ao servidor local, ou o cliente local conecta ao 
	//servidor remoto
	public void update(Observable o, Object arg) {
		System.out.println("Network dialog foi notificado que a " 
	+ "conexão foi estabelecida. o arg eh: " + (boolean)arg);
		boolean conectouComoServidor = (boolean)arg;
		if(conectouComoServidor) {
			System.out.println("Conectou como servidor");
			layoutDoDialog.show(getContentPane(), "painelAguardandoConexaoFuncionou");
		} else {
			System.out.println("Conectou como cliente");
			layoutDoDialog.show(getContentPane(), "painelConectandoFuncionou");
		}
	}
	
	private ActionListener criaActionListenerQueCancelaAguardandoConexao() {
		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				/*try {
					gerenciadorDeRede.fechaServidorLocal();
				} catch (IOException e1) {
					e1.printStackTrace();
				}*/

				layoutDoDialog.show(getContentPane(), "painelDeEntradaDeDadosDaConexao");
			}
		};
		return listener;
	}
	
	private ActionListener criaActionListenerQueCancelaConectando() {
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
					campoIPRemoto.setEnabled(false);
					campoIPRemoto.setBackground(Color.LIGHT_GRAY);
				} else if(radioConectar.isSelected()) {
					campoIPRemoto.setEnabled(true);
					campoIPRemoto.setBackground(Color.WHITE);
				}
			}
		};
		return radioButtonListener;
	}

	public Interlocutor getInterlocutor() {
		return interlocutor;
	}
}
