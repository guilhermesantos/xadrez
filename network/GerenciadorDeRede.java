package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import exceptions.ConexaoAindaNaoEstabelecidaException;

public class GerenciadorDeRede extends Observable {
	private int porta;
	private String nome;

	private ServerSocket servidorLocal;
	private PrintStream escritorDaRede;
	private ObjectInputStream leitorDaRede;
	
	private Thread threadDeConexao;
	
	private Interlocutor interlocutor;
	
	//Esta classe usa serversocket para leitura, socket para escrita
	public GerenciadorDeRede(int porta, String nome) {
		this.nome = nome;
		this.porta = porta;
	}
	
	public void estabeleceServidorLocal(Observer observer) {
		try {
			servidorLocal = new ServerSocket(porta);
			servidorLocal.setReuseAddress(true);
			addObserver(observer);
		} catch (IOException e) {
			System.out.println("Erro ao estabelecer o servidor no programa " + nome);
		}	
		
		ReceptorDeConexoes receptor = new ReceptorDeConexoes();
		threadDeConexao = new Thread(receptor);
		threadDeConexao.start();
	}
	
	private class ReceptorDeConexoes implements Runnable {
		@Override
		public void run() {
			try {
				interlocutor = new Interlocutor(servidorLocal.accept());
				setChanged();
				notifyObservers(interlocutor);
			} catch (IOException e) {
				System.out.println("Conexao cancelada antes da conexão de um cliente.");
			}
		}
	}
	
	public void fechaServidorLocal() throws IOException {
		servidorLocal.close();
		if(leitorDaRede != null) {
			leitorDaRede.close();
		}
		if(escritorDaRede != null) {
			escritorDaRede.close();
		}
		if(threadDeConexao != null && threadDeConexao.isAlive()) {
			try {
				threadDeConexao.join(500);
			} catch (InterruptedException e) {
				System.out.println("Erro ao matar a thread que recebe conexões");
			}
		}
	}
	
	public void conectaAoServidorRemoto(InetAddress ipRemoto) 
			throws UnknownHostException, IOException {
			interlocutor = new Interlocutor(new Socket(ipRemoto, porta));
	}
	
	public void fechaConexaoAoServidorRemoto() {
		try {
			interlocutor.getConexao().close();
		} catch (IOException e) {
			System.out.println("Erro ao fechar a conexao ao servidor remoto no " + nome);
		}
	}
	
	public void escreveMensagem(String mensagem) throws ConexaoAindaNaoEstabelecidaException {
		if(escritorDaRede != null) {
			escritorDaRede.println(nome + " diz: " + mensagem);
		} else {
			throw new ConexaoAindaNaoEstabelecidaException();
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
}
