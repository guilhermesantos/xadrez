package network;

import game.Xadrez;

public class MensagemComJogo implements Mensagem {
	private static final long serialVersionUID = -6464436794031731665L;

	private Xadrez conteudo;

	public MensagemComJogo(Xadrez jogo) {
		this.conteudo = jogo;
	}
	
	@Override
	public Xadrez getConteudoMensagem() {
		return conteudo;
	}
}
