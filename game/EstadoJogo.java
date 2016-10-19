package game;

public enum EstadoJogo {
	TURNO_BRANCO("Turno das pe�as brancas."),
	TURNO_PRETO("Turno das pe�as pretas."),
	XEQUE_BRANCO("Rei branco est� em xeque!"),
	XEQUE_PRETO("Rei preto est� em xeque!"),
	VITORIA_BRANCO("Vit�ria das pe�as brancas!"),
	VITORIA_PRETO("Vit�ria das pe�as pretas");

	private final String nome;
	
	private EstadoJogo(String s) {
		this.nome = s;
	}
	
	public String toString() {
		return this.nome;
	}
}
