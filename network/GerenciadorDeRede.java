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
	private Socket computadorRemoto;
	private PrintStream escritorDaRede;
	private ObjectInputStream leitorDaRede;

	private boolean conectado;
	
	//Esta classe usa serversocket para leitura, socket para escrita
	public GerenciadorDeRede(int porta, String nome) {
		this.nome = nome;
		this.porta = porta;
		conectado = false;
	}
	
	public void estabeleceServidorLocal(Observer observer) {
		try {
			servidorLocal = new ServerSocket(porta);
			addObserver(observer);
		} catch (IOException e) {
			System.out.println("Erro ao estabelecer o servidor no programa " + nome);
		}	
		ReceptorDeConexoes receptor = new ReceptorDeConexoes();
		new Thread(receptor).start();
	}
	
	public void fechaServidorLocal() throws IOException {
		servidorLocal.close();
		conectado = false;
	}
	
	public void conectaAoServidorRemoto(InetAddress ipRemoto) 
			throws UnknownHostException, IOException {
			computadorRemoto = new Socket(ipRemoto, porta);
			leitorDaRede = new ObjectInputStream(computadorRemoto.getInputStream());
			escritorDaRede = new PrintStream(computadorRemoto.getOutputStream());
	}
	
	public void fechaConexaoAoServidorRemoto() {
		try {
			computadorRemoto.close();
			conectado = false;
		} catch (IOException e) {
			System.out.println("Erro ao fechar a conexao ao servidor remoto no " + nome);
		}
	}
	
	public Object leObjeto() throws ConexaoAindaNaoEstabelecidaException {
		if(leitorDaRede != null) {
			try {
				return leitorDaRede.readObject();
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Erro ao recuperar objeto da rede");
			}
		} else {
			throw new ConexaoAindaNaoEstabelecidaException();
		}
		return null;
	}
	
	public void escreveObjeto(Object objeto) throws ConexaoAindaNaoEstabelecidaException {
		if(escritorDaRede != null) {
			escritorDaRede.print(objeto);
		} else {
			throw new ConexaoAindaNaoEstabelecidaException();
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

	public boolean isConectado() {
		return conectado;
	}
	
	private class ReceptorDeConexoes implements Runnable {
		@Override
		public void run() {
			try {
				computadorRemoto = servidorLocal.accept();
				leitorDaRede = new ObjectInputStream(computadorRemoto.getInputStream());
				escritorDaRede = new PrintStream(computadorRemoto.getOutputStream());
				setChanged();
				notifyObservers();
				conectado = true;
			} catch (IOException e) {
				System.out.println("Erro ao esperar um cliente conectar ao servidor.");
			}
		}
	}
		
}
