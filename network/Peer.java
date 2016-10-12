package network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import exceptions.ConexaoAindaNaoEstabelecidaException;

public class Peer implements Runnable {
	private int portaDeEscuta;
	private ServerSocket servidorLocal;
	private Socket clienteRemoto;
	private Socket conexaoAoServidorRemoto;
	private PrintStream escritorDaRede;
	private String nome;
	private InetAddress ipLocal;
	
	//Esta classe usa serversocket para leitura, socket para escrita
	public Peer(int portaDeEscuta, String nome) {
		this.nome = nome;
		this.portaDeEscuta = portaDeEscuta;
		try {
			this.ipLocal = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			System.out.println(nome + " falhou ao descobrir o próprio IP.");
		}
		estabeleceServidorLocal();
	}
	
	public void estabeleceServidorLocal() {
		try {
			servidorLocal = new ServerSocket(portaDeEscuta);
		} catch (IOException e) {
			System.out.println("Erro ao estabelecer o servidor no programa " + nome);
		}	
		new Thread(this).start();
	}
	
	public void conectaAoServidorRemoto(InetAddress ipRemoto, int portaDeEscrita) 
			throws UnknownHostException, IOException {
			conexaoAoServidorRemoto = new Socket(ipRemoto, portaDeEscrita);
			escritorDaRede = new PrintStream(conexaoAoServidorRemoto.getOutputStream());
	}
	
	public void escreveMensagem(String mensagem) throws ConexaoAindaNaoEstabelecidaException {
		if(escritorDaRede != null) {
			escritorDaRede.println(nome + " diz: " + mensagem);
		} else {
			throw new ConexaoAindaNaoEstabelecidaException();
		}
	}
	
	public void fechaConexaoAoServidorRemoto() {
		try {
			conexaoAoServidorRemoto.close();
		} catch (IOException e) {
			System.out.println("Erro ao fechar a conexao ao servidor remoto no " + nome);
		}
	}
	
	public void fechaServidorLocal() throws IOException {
		servidorLocal.close();
	}

	@Override
	public void run() {
		try {
			clienteRemoto = servidorLocal.accept();
		} catch (IOException e) {
			System.out.println("Erro ao esperar um cliente conectar ao servidor.");
		}
		System.out.println("Cliente conectado ao programa " + nome);
	}
	
	public int getPortaDeEscuta() {
		return portaDeEscuta;
	}
	
	public InetAddress getIpLocal() {
		return ipLocal;
	}
}
