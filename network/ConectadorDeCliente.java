package network;

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
			saida = new ObjectOutputStream(con.getOutputStream());
			saida.flush();
		} catch (IOException e) {
			System.out.println("Nao conseguiu criar o input stream pro servidor");
		}
		try {
			entrada = new ObjectInputStream(con.getInputStream());
		} catch (IOException e) {
			System.out.println("Nao conseguiu criar o input stream pro cliente");
		}
		interlocutor = new Interlocutor(con, entrada, saida);
		System.out.println("Conseguiu conectar como cliente");
		setChanged();
		System.out.println("Deu changed como cliente e vai notificar o dialog");
		notifyObservers(false);
	}
}
