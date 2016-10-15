package network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

public class GerenciadorDeRede extends Observable {
	private int porta;
	private String nome;

	private ServerSocket servidorLocal;
	private Thread threadDeConexao;
	
	private Interlocutor interlocutor;
	
	public GerenciadorDeRede(Observer observer) {
		addObserver(observer);
	}
	
	public void iniciaConexaoComoServidor(int porta) throws IOException {
		servidorLocal = new ServerSocket(porta);
		servidorLocal.setReuseAddress(true);
		
		ReceptorDeConexoes receptor = new ReceptorDeConexoes();
		threadDeConexao = new Thread(receptor);
		threadDeConexao.start();
	}
	
	private class ReceptorDeConexoes implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println("Vai começar a escutar conexoes");
				Socket con = servidorLocal.accept();
				interlocutor = new Interlocutor(con);
				System.out.println("Recebeu conexao!");
				setChanged();
				notifyObservers(true);
			} catch (IOException e) {
				System.out.println("Conexao cancelada antes da conexão de um cliente.");
			}
		}
	}
	
	public void iniciaConexaoComoCliente(String ipRemoto, int porta) throws IOException {
		System.out.println("vai criar o socket com o ip " + ipRemoto + " e a porta " + porta);
		Socket con = new Socket(ipRemoto, porta);
		System.out.println("socket do cliente foi conectado? " + con.isConnected());
		System.out.println("criou o socket");
		interlocutor = new Interlocutor(con);
		System.out.println("Criou o interlocutor");
		setChanged();
		System.out.println("fez set changed do observable no cliente");
		notifyObservers(false);
	}
	
	
	public void fechaServidorLocal() throws IOException {
		servidorLocal.close();
		if(threadDeConexao != null && threadDeConexao.isAlive()) {
			try {
				threadDeConexao.join(500);
			} catch (InterruptedException e) {
				System.out.println("Erro ao matar a thread que recebe conexões");
			}
		}
	}
	
	private class EmissorDeConexoes implements Runnable {
		String ipRemoto;
		public EmissorDeConexoes(String ip) {
			ipRemoto = ip;
		}
		
		@Override
		public void run() {
			try {
				interlocutor = new Interlocutor(new Socket(ipRemoto, porta));
				System.out.println("Criou o socket!");
			} catch (IOException e) {
				System.out.println("Nao conseguiu conectar ao servidor");
			}
			setChanged();
			System.out.println("Deu changed e vai notificar o dialog");
			notifyObservers(false);
		}
		
	}
	

	public void fechaConexaoAoServidorRemoto() {
		try {
			interlocutor.getConexao().close();
		} catch (IOException e) {
			System.out.println("Erro ao fechar a conexao ao servidor remoto no " + nome);
		}
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
}
