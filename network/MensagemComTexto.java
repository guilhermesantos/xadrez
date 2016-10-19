package network;

public class MensagemComTexto implements Mensagem {
	private static final long serialVersionUID = 2949316336817235931L;
	
	private String conteudoMensagem;
	
	public MensagemComTexto(String mensagem) {
		this.conteudoMensagem = mensagem;
	}
	
	@Override
	public String getConteudoMensagem() {
		return conteudoMensagem;
	}
}
