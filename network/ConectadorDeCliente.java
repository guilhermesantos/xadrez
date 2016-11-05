package network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class ConectadorDeCliente extends Observable implements Runnable {
	private String ipRemoto;
	private int portaRemota;
	
	public ConectadorDeCliente(Observer observer, String ip, int porta) {
		addObserver(observer);
		ipRemoto = ip;
		portaRemota = porta;
	}
	
	@Override
	public void run() {
		Socket con = null;
		Interlocutor interlocutor = null;
		ObjectInputStream entrada = null;
		ObjectOutputStream saida = null;
		
		try {
			con = new Socket(ipRemoto, portaRemota);
			System.out.println("Criou o socket do cliente!");
		} catch (IOException e) {
			System.out.println("Nao conseguiu conectar ao servidor");
		}
		
		try {
			entrada = new ObjectInputStream(new BufferedInputStream(con.getInputStream()));
		} catch (IOException e) {
			System.out.println("Nao conseguiu criar o input stream pro cliente");
		}
		System.out.println("Criou input stream do cliente");
		
		try {
			saida = new ObjectOutputStream(new BufferedOutputStream(con.getOutputStream()));
			saida.flush();
		} catch (IOException e) {
			System.out.println("Nao conseguiu criar o input stream pro servidor");
		}
		System.out.println("Criou output stream do cliente");
		
		interlocutor = new Interlocutor(con, entrada, saida, TipoInterlocutor.SERVIDOR);
		System.out.println("vai dar set changed por ter conseguido conectar no servidor (sou o cliente)");
		setChanged();
		System.out.println("Deu changed como cliente e vai notificar o gerenciador de rede");
		notifyObservers(interlocutor);
	}
}
