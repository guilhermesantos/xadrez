package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;
import java.util.Observer;

public class ReceptorDeConexoes extends Observable implements Runnable {
	int portaLocal;
	
	public ReceptorDeConexoes(Observer observer, int porta) {
		addObserver(observer);
		portaLocal = porta;
	}
	@Override
	public void run() {
		ServerSocket servidor = null;
		Interlocutor interlocutor = null;
		try {
			servidor = new ServerSocket(portaLocal);
		} catch (IOException e1) {
			System.out.println("Erro instanciando servidor local");
		}
		try {
			servidor.setReuseAddress(true);
		} catch (SocketException e1) {
			System.out.println("Erro na hora de colocar o set reuse address no servidor");
		}
		
		System.out.println("Vai começar a escutar conexoes");
		Socket con = null;
		try {
			con = servidor.accept();
		} catch (IOException e) {
			System.out.println("Nao recebeu nenhuma conexao");
		}
		
		ObjectOutputStream saida = null;
		try {
			saida = new ObjectOutputStream(con.getOutputStream());
			saida.flush();
		} catch (IOException e) {
			System.out.println("Erro ao criar o canal de saida do servidor local");
		}
		
		ObjectInputStream entrada = null;
		try {
			entrada = new ObjectInputStream(con.getInputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar o canal de entrada do servidor local");
		}
		interlocutor = new Interlocutor(con, entrada, saida);
		System.out.println("Recebeu conexao!");
		setChanged();
		notifyObservers(true);
	}
}