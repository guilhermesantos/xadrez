package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PainelComMensagens extends JPanel {
	private static final long serialVersionUID = 2795030233102130660L;
	
	private JPanel messageContainer;
	private String vetorDeMensagens[];

	private JButton botaoCancelar;
	
	public PainelComMensagens(String... mensagens) {
		super.setLayout(new BorderLayout());
		
		botaoCancelar = new JButton("Cancelar");
		messageContainer = new JPanel(new FlowLayout());
		vetorDeMensagens = mensagens;
		
		super.add(botaoCancelar, BorderLayout.SOUTH);
		super.add(messageContainer, BorderLayout.CENTER);
		colocaMensagensNoPainel();
	}
	
	private void colocaMensagensNoPainel() {
		for(String mensagem : vetorDeMensagens) {
			JLabel label = new JLabel(mensagem);
			messageContainer.add(label);
		}
	}

	public void colocaActionListenerNoBotao(ActionListener listener) {
		botaoCancelar.addActionListener(listener);
	}
}
