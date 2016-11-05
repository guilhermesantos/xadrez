package timer;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import runnable.ThreadTimer;

public class TimerGrafico extends JPanel implements Observer {
	private static final long serialVersionUID = -2596813432724469961L;
	
	private JLabel labelTitulo;
	private JLabel labelTimer;
	private ThreadTimer threadTimer;
	
	public TimerGrafico(String titulo) {
		threadTimer = new ThreadTimer();
		configuraTimer(titulo);
	}
	
	public TimerGrafico(String titulo, Tempo tempo) {
		threadTimer = new ThreadTimer(tempo);
		configuraTimer(titulo);
	}
	
	private void configuraTimer(String titulo) {
		threadTimer.addObserver(this);
		new Thread(threadTimer).start();
		
		this.labelTitulo = new JLabel();
		this.add(labelTitulo);
		this.labelTitulo.setText(titulo);
		
		labelTimer = new JLabel();
		this.add(labelTimer);
		atualizaLabelTimer();
	}

	public void encerraTimer() {
		threadTimer.encerraTimer();
	}
	
	public void atualizaLabelTimer() {
		labelTimer.setText(threadTimer.getTempo().toString());
	}

	@Override
	public void update(Observable o, Object arg) {
		atualizaLabelTimer();
	}

	public void setTempo(Tempo tempo) {
		threadTimer.setTempo(tempo);
		atualizaLabelTimer();
	}

	public Tempo getTempo() {
		return threadTimer.getTempo();
	}
}
