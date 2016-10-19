package network;

import java.io.Serializable;

public interface Mensagem extends Serializable {
	public abstract Object getConteudoMensagem();
}
