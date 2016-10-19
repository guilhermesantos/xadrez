package game;

public enum Cor {
	PRETO,
	BRANCO;
	
	public Cor alternaCor() {
		if(this.equals(Cor.PRETO)) {
			return BRANCO;
		} else {
			return PRETO;
		}
	}
}
