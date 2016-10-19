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
	private Thread threadEscutadoraDeMensagens;
	private TipoInterlocutor tipoInterlocutor;
	
	public Interlocutor(Socket conexao, ObjectInputStream entrada, 
			ObjectOutputStream saida, TipoInterlocutor tipo) {
		super();
		this.conexao = conexao;
		this.entrada = entrada;
		this.saida = saida;
		this.tipoInterlocutor = tipo;
		this.nome = null;
		this.mensagemLida = null;
	}
	public void comecaAEscutarMensagens() {
		System.out.println("Vai ligar a thread escutadora");
		EscutadorDeMensagens escutador = new EscutadorDeMensagens();
		threadEscutadoraDeMensagens = new Thread(escutador);
		//TODO: Avisar pro professor que aqui tambem tinha um thread.run ao inves de um thread.start
		threadEscutadoraDeMensagens.start();
		System.out.println("Colocou a thread escutadora para funcionar");
	}
	
	public void mataInterlocutor() {
		try {
			threadEscutadoraDeMensagens.join(500);
		} catch (InterruptedException e) {
			System.out.println("Erro ao matar a thread escutadora do interlocutor");
			e.printStackTrace();
		}
		
		try {
			conexao.close();
		} catch (IOException e) {
			System.out.println("Erro ao matar a conexao ao interlocutor");
			e.printStackTrace();
		}
	}

	public void escreveMensagem(Mensagem mensagem) {
		try {
			saida.writeObject(mensagem);
			saida.flush();
		} catch (IOException e) {
			System.out.println("Erro ao enviar mensagem para o interlocutor");
		}
	}

	
	public void paraDeEscutarMensagens() {
		try {
			threadEscutadoraDeMensagens.join(500);
		} catch (InterruptedException e) {
			System.out.println("Erro ao interromper a thread escutadora de mensagens");
			e.printStackTrace();
		}
	}
	
	private class EscutadorDeMensagens implements Runnable {
		@Override
		public void run() {
			System.out.println("ligou a thread escutadora!");
			try {
				while( (mensagemLida = (Mensagem)entrada.readObject()) != null) {
					if(nome == null) {
						System.out.println("Mensagem recebida: " + (String)mensagemLida.getConteudoMensagem());
						setNome((String)mensagemLida.getConteudoMensagem());
					}
					setChanged();
					notifyObservers(mensagemLida);
				}
			} catch (ClassNotFoundException e) {
				System.out.println("Nao conseguiu converter o objeto recebido em Mensagem");
			} catch (IOException e) {
				System.out.println("Nao recebeu nada.");
			}
		}
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		System.out.println("O interlocutor desse cara aqui chama " + nome);
		this.nome = nome;
	}

	public TipoInterlocutor getTipoInterlocutor() {
		return tipoInterlocutor;
	}

	public Socket getConexao() {
		return conexao;
	}
	
	public Mensagem getMensagemLida() {
		return mensagemLida;
	}
}
