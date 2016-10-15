package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class GerenciadorDeRede extends Observable implements Observer {

	private Thread threadDeConexao;
	
	private Interlocutor interlocutor;
	
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
		threadDeConexao.run();
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

	public Interlocutor getInterlocutor() {
		return interlocutor;
	}

	@Override
	public void update(Observable o, Object arg) {
		try {
			threadDeConexao.join(500);
		} catch (InterruptedException e) {
			System.out.println("Erro ao matar a thread de conexão");
		}
		boolean ehServidor = (boolean)arg;
		System.out.println("O arg que chegou no gerenciador de rede eh: " + ehServidor);
		if(ehServidor) {
			System.out.println("CHEGOU COMO SERVIDOR");
			setChanged();
			notifyObservers(true);
			ReceptorDeConexoes receptor = (ReceptorDeConexoes)o;
		} else {
			System.out.println("CHEGOU COMO CLIENTE");
			setChanged();
			notifyObservers(false);
			ConectadorDeCliente conectador = (ConectadorDeCliente)o;
		}
	}
}
