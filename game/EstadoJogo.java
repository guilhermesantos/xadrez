package game;

public enum EstadoJogo {
	TURNO_PRETO("Turno das pe�as pretas."),
	TURNO_BRANCO("Turno das pe�as brancas."),
	XEQUE_BRANCO("Rei branco est� em xeque!"),
	XEQUE_PRETO("Rei preto est� em xeque!");
	
	private final String nome;
	
	private EstadoJogo(String s) {
		this.nome = s;
	}
	
	public String toString() {
		return this.nome;
	}
}
