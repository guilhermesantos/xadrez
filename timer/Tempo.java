package timer;

import java.io.Serializable;

public class Tempo implements Serializable {
	private static final long serialVersionUID = 1843909182889908420L;

	private int segundos, minutos, horas;

	public Tempo() {
		segundos = minutos = horas = 0;
	}
	
	public Tempo(int segundos, int minutos, int horas) {
		this.segundos = segundos;
		this.minutos = minutos;
		this.horas = horas;
	}
	
	public void aumentaTempoEm1Segundo() {
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
	}
	
	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public int getHoras() {
		return horas;
	}

	public void setHoras(int horas) {
		this.horas = horas;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if(horas != 0) {
			builder.append(horas+":");
		}
		builder.append(minutos + ":" + segundos);
		return builder.toString();
	}
}
