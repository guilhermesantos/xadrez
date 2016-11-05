package view;

import javax.swing.JTextArea;

public class Logger extends JTextArea {
	private static final long serialVersionUID = -7679534926254695936L;
	
	public static Logger getInstance() {
		return LoggerSingletonHolder.logger;
	}
	
	public void logar(String texto) {
		append(texto+"\n");
	}
	
	private static class LoggerSingletonHolder {
		private static final Logger logger = new Logger();
	}
}
