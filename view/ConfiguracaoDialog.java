package view;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import runnable.ThreadAutoSave;

public class ConfiguracaoDialog extends JDialog {
	private static final long serialVersionUID = -3190253265492900407L;
	private JPanel painelConteudo;
	private JPanel painelBotoes;
	
	private JTextField campoAutoSave;
	private int periodoAutoSave;
	
	private JButton botaoSair;
	
	public ConfiguracaoDialog(Window window) {
		super(window, "Configurações");
		periodoAutoSave = 0;
		configuraDialog();
	}

	private void configuraDialog() {
		this.setSize(200, 150);
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		
		painelConteudo = constroiPainelDeDadosConfiguracao();
		this.add(painelConteudo, BorderLayout.CENTER);
		
		painelBotoes = constroiPainelBotoes();
		this.add(painelBotoes, BorderLayout.SOUTH);
	}
	
	private JPanel constroiPainelDeDadosConfiguracao() {
		JPanel painel = new JPanel();
		
		JLabel labelAutoSave = new JLabel("Periodo de auto save (segundos): ");
		painel.add(labelAutoSave);
		campoAutoSave = new JTextField(10);
		campoAutoSave.setText(Integer.toString(periodoAutoSave));
		painel.add(campoAutoSave);
		
		JLabel labelAviso = new JLabel("Periodo 0 desativará o autosave.");
		painel.add(labelAviso);
		return painel;
	}
	
	private JPanel constroiPainelBotoes() {
		JPanel painel = new JPanel();
		botaoSair = new JButton("Ok");
		
		ActionListener listenerQueFechaODialog = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(campoAutoSave.getText().isEmpty()) {
					periodoAutoSave = 0;
				} else {
					periodoAutoSave = Integer.parseInt(campoAutoSave.getText());
				}
				dispose();
				ThreadAutoSave.getInstance().setPeriodoAutoSave(periodoAutoSave);
			}
		};
		botaoSair.addActionListener(listenerQueFechaODialog);
		painel.add(botaoSair);
		return painel;
	}

	public int getPeriodoAutoSave() {
		return periodoAutoSave;
	}
	
}
