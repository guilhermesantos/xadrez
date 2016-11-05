package runnable;

import java.util.Observable;

import timer.Tempo;

public class ThreadTimer extends Observable implements Runnable {
	private Tempo tempo;
	private boolean rodando;
	
	public ThreadTimer() {
		this.tempo = new Tempo();
	}
	
	public ThreadTimer(Tempo tempo) {
		this.tempo = tempo;
	}
	
	public void encerraTimer() {
		this.rodando = false;
	}
	
	@Override
	public void run() {
		rodando = true;
		while(rodando) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			tempo.aumentaTempoEm1Segundo();
			setChanged();
			notifyObservers();
		}
		System.out.println("MATANDO TIMER!");
	}

	public Tempo getTempo() {
		return tempo;
	}

	public void setTempo(Tempo tempo) {
		this.tempo = tempo;
	}
}
