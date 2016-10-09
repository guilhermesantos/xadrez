package game;

public enum EstadoJogo {
	TURNO_PRETO("Turno das peças pretas."),
	TURNO_BRANCO("Turno das peças brancas."),
	XEQUE_BRANCO("Rei branco está em xeque!"),
	XEQUE_PRETO("Rei preto está em xeque!");
	
	private final String nome;
	
	private EstadoJogo(String s) {
		this.nome = s;
	}
	
	public String toString() {
		return this.nome;
	}
}
