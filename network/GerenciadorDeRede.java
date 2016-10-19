package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class GerenciadorDeRede extends Observable implements Observer {

	private Thread threadDeConexao;
	
	public GerenciadorDeRede(Observer observer) {
		addObserver(observer);
	}
	
	public void iniciaConexaoComoServidor(int porta) throws IOException {
		ReceptorDeConexoes receptor = new ReceptorDeConexoes(this, porta);
		threadDeConexao = new Thread(receptor);
		threadDeConexao.start();
	}
	
	public void iniciaConexaoComoCliente(String ipRemoto, int porta) {
		System.out.println("vai criar o socket com o ip " + ipRemoto + " e a porta " + porta);
		ConectadorDeCliente conectador = new ConectadorDeCliente(this, ipRemoto, porta);
		threadDeConexao = new Thread(conectador);
		//TODO: Avisar ao professor que aqui tinha um thread.run ao inves de um thread.start
		threadDeConexao.start();
	}
	
	public static InetAddress getIpLocal() {
		InetAddress ipLocal = null;
		try {
			ipLocal = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println("Falhou ao descobrir o próprio IP.");
		}
		return ipLocal;
	}

	@Override
	public void update(Observable o, Object arg) {
		Interlocutor interlocutor = (Interlocutor)arg;
		threadDeConexao.interrupt();
		if(interlocutor.getTipoInterlocutor() == TipoInterlocutor.SERVIDOR) {
			System.out.println("Matou a thread de conectar ao servidor");
		} else {
			System.out.println("Matou a thread de aguardar conexao");
		}
		
		System.out.println("Notificou o gerenciador de rede. agora vai dar set changed no gerenciador de rede");
		setChanged();
		System.out.println("Deu set changed no gerenciador. agora vai notificar o dialog");
		notifyObservers(interlocutor);
	}
}
