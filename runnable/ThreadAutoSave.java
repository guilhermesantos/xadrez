package runnable;

import java.io.IOException;

import game.Xadrez;

public class ThreadAutoSave implements Runnable {
	private static int periodo;
	private static Xadrez jogoASalvar;
	private static boolean autoSaveAtivado;

	public ThreadAutoSave() {
		periodo = 0;
		autoSaveAtivado = true;
	}
	
	public static ThreadAutoSave getInstance() {
		return ThreadAutoSaveInterna.instancia;
	}
	
	public static void setPeriodoAutoSave(int periodoAutoSave) {
		System.out.println("Configurando o periodo do auto save para " + periodoAutoSave);
		periodo = periodoAutoSave;
	}
	
	public static void setJogo(Xadrez jogo) {
		jogoASalvar = jogo;
	}
	
	public static void encerraAutoSave() {
		autoSaveAtivado = false;
	}
	
	public void run() {
		System.out.println("Começando a thread do autosave!");
		long tempoUltimaIteracao = System.currentTimeMillis();
		long tempoUltimoAutoSave = System.currentTimeMillis();
		while(autoSaveAtivado) {
			tempoUltimaIteracao = System.currentTimeMillis();
			if(periodo == 0) {
				System.out.println("Autosave esta com periodo zero, então nao vai salvar.");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				continue;
			}
			
			if(jogoASalvar == null) {
				System.out.println("Jogo a salvar esta nulo!");
			}
			
			if((tempoUltimaIteracao - tempoUltimoAutoSave)/1000 > periodo && jogoASalvar != null) {
				try {
					jogoASalvar.salvaJogo("autosave.dat");
				} catch (IOException e) {
					System.out.println("Erro de autosave");
					e.printStackTrace();
				}
				System.out.println("Jogo salvo com sucesso.");
				tempoUltimoAutoSave = System.currentTimeMillis();
			}
		}
	}
	
	private static class ThreadAutoSaveInterna {
		private static final ThreadAutoSave instancia = new ThreadAutoSave();
	}
}
