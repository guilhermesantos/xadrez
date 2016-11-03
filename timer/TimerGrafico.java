package timer;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerGrafico extends JPanel implements Observer {
	private static final long serialVersionUID = -2596813432724469961L;
	
	private TimerLogico timerLogico;
	private JLabel labelTitulo;
	private JLabel labelTimer;
	
	public TimerGrafico(String titulo) {
		timerLogico = new TimerLogico();
		configuraTimer(titulo);
	}
	
	public TimerGrafico(String titulo, TimerLogico timer) {
		this.timerLogico = timer;
		configuraTimer(titulo);
	}
	
	private void configuraTimer(String titulo) {
		timerLogico.addObserver(this);

		this.labelTitulo = new JLabel();
		this.add(labelTitulo);
		this.labelTitulo.setText(titulo);
		
		labelTimer = new JLabel();
		this.add(labelTimer);
		atualizaLabelTimer();
	}
	
	public void iniciaTimerVisual() {
		timerLogico.iniciaTimer();
	}
	
	public void pausaTimerVisual() {
		timerLogico.pausaTimerLogico();
	}
	
	public void continuaTimerVisual() {
		timerLogico.continuaTimerLogico();
	}
	
	public void zeraTimerVisual() {
		timerLogico.zeraTimerLogico();
		atualizaLabelTimer();
	}

	public void atualizaLabelTimer() {
		labelTimer.setText(timerLogico.getTempoFormatado());
	}

	@Override
	public void update(Observable o, Object arg) {
		atualizaLabelTimer();
	}

	public TimerLogico getTimerLogico() {
		return timerLogico;
	}

	public void trocaTimerLogico(TimerLogico timerLogico) {
		System.out.println("Trocando timer logico");
		this.timerLogico = timerLogico;
		this.timerLogico.addObserver(this);
		System.out.println("trocou timer logico");
		atualizaLabelTimer();
		System.out.println("atualizou label do timer");
	}
}
