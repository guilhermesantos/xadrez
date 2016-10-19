package game;

public enum EstadoJogo {
	TURNO_BRANCO("Turno das peças brancas."),
	TURNO_PRETO("Turno das peças pretas."),
	XEQUE_BRANCO("Rei branco está em xeque!"),
	XEQUE_PRETO("Rei preto está em xeque!"),
	VITORIA_BRANCO("Vitória das peças brancas!"),
	VITORIA_PRETO("Vitória das peças pretas");

	private final String nome;
	
	private EstadoJogo(String s) {
		this.nome = s;
	}
	
	public String toString() {
		return this.nome;
	}
}
