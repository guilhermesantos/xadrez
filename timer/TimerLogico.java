package timer;

import java.io.Serializable;
import java.util.Observable;

public class TimerLogico extends Observable implements Serializable {
	private static final long serialVersionUID = -41704464819883724L;
	private RunnableTimer runnableTimer;
	private long segundos, minutos, horas;

	public TimerLogico() {
		runnableTimer = new RunnableTimer();
		zeraTimerLogico();
	}
	
	public void iniciaTimer() {
		runnableTimer = new RunnableTimer();
		new Thread(runnableTimer).start();
	}
	
	public void zeraTimerLogico() {
		segundos = 0;
		minutos = 0;
		horas = 0;
	}
	
	public String getTempoFormatado() {
		StringBuilder sb = new StringBuilder();
		
		if(horas > 0) {
			sb.append(horas + ":");
		}
		
		sb.append(minutos + ":" + segundos);
		return sb.toString();
	}
	
	public void pausaTimerLogico() {
		try {
			runnableTimer.wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void encerraTimer() {
		runnableTimer.rodando = false;
	}
	
	public void continuaTimerLogico() {
		if(runnableTimer.isRodando()) {
			runnableTimer.notify();
		}
	}
	
	private class RunnableTimer implements Runnable, Serializable {
		private static final long serialVersionUID = -4071561770327384301L;
		private boolean rodando;
		
		public RunnableTimer() {
			rodando = false;
		}
		
		@Override
		public void run() {
			rodando = true;
			long marcacaoTempo1 = System.currentTimeMillis();
			long marcacaoTempo2 = System.currentTimeMillis();
			while(rodando) {
				marcacaoTempo2 = System.currentTimeMillis();
				if(marcacaoTempo2 - marcacaoTempo1 >= 1000) {
					segundos++;
					if(segundos == 60) {
						segundos = 0;
						minutos++;
						if(minutos == 60) {
							minutos = 0;
							horas++;
							if(horas == 24) {
								segundos = minutos = horas = 0;
							}
						}
					}
					marcacaoTempo1 = System.currentTimeMillis();
					setChanged();
					notifyObservers();
				}
			}
		}

		public boolean isRodando() {
			return rodando;
		}
	}
}
