package runnable;

import java.net.UnknownHostException;

import properties.Constantes;
import view.Janela;

public class Main {
	
	public static void main(String[] args) throws UnknownHostException {
		Janela janela = new Janela("Xadrez", Constantes.LARGURA_JANELA, Constantes.ALTURA_JANELA);
		janela.setVisible(true);
	}
}
