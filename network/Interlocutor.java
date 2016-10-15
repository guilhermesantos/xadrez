package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

public class Interlocutor extends Observable {
	private Socket conexao;
	private String nome;
	private ObjectInputStream entrada;
	private ObjectOutputStream saida;
	private Mensagem mensagemLida;
	
	public Interlocutor(Socket conexao, ObjectInputStream entrada, ObjectOutputStream saida) {
		super();
		this.conexao = conexao;
		//EscutadorDeMensagens escutador = new EscutadorDeMensagens();
		//new Thread(escutador).run();
	}
	
	public Socket getConexao() {
		return conexao;
	}

	public void escreveMensagem(Mensagem mensagem) throws IOException {
		saida.writeObject(mensagem);
		saida.flush();
	}
	
	public Mensagem getMensagem() {
		return mensagemLida;
	}
	
	private class EscutadorDeMensagens implements Runnable {
		private long delta;
		private long marcacaoAnterior;
		@Override
		public void run() {
			long marcacaoAtual = System.currentTimeMillis();
			while(true) {
				marcacaoAtual = System.currentTimeMillis();
				delta = marcacaoAtual - marcacaoAnterior;
				if(delta < 1e3) {
					System.out.println("Tentando ler!");
					try {
						System.out.println("Tentando ler mensagem");
						mensagemLida = (Mensagem)entrada.readObject();
						setChanged();
						notifyObservers(mensagemLida);
					} catch (ClassNotFoundException e) {
					} catch (IOException e) {
						System.out.println("Nao recebeu nada.");
					}
				}
				marcacaoAnterior = marcacaoAtual;
			}
		}
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
