package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Interlocutor {
	private Socket conexao;
	private String nome;
	private ObjectInputStream entrada;
	private PrintStream saida;
	
	public Interlocutor(Socket conexao) {
		super();
		this.conexao = conexao;
		try {
			entrada = new ObjectInputStream(conexao.getInputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar canal de entrada");
		}
		try {
			saida = new PrintStream(conexao.getOutputStream());
		} catch (IOException e) {
			System.out.println("Erro ao criar canal de saida");
		}
	}
	
	public Socket getConexao() {
		return conexao;
	}

	public void setConexao(Socket conexao) {
		this.conexao = conexao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public PrintStream getSaida() {
		return saida;
	}
	
	public void escreveMensagem(Object mensagem) {
		saida.print(mensagem);
	}
	
	private class EscutadorDeMensagens implements Runnable {
		private long delta;
		private long marcacaoAnterior;
		@Override
		public void run() {
			long marcacaoAtual;
			while(true) {
				marcacaoAtual = System.nanoTime();
				delta = marcacaoAtual - marcacaoAnterior;
				if(delta < 1e9) {
					
				}
			}
		}
	}
}
