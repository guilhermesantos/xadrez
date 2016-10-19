package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class NomeJogadorLabel extends JLabel {
	private static final long serialVersionUID = 8521197791547252683L;

	public NomeJogadorLabel(String nome) {
		super(nome);
		super.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		super.setForeground(Color.BLUE);
	}
}
