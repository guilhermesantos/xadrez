package view;

import javax.swing.JTextField;

public class Logger extends JTextField {
	private static final long serialVersionUID = -7679534926254695936L;
	
	public static Logger getInstance() {
		return LoggerSingletonHolder.logger;
	}
	
	public void logar(String texto) {
		setText(null);
		setText(texto);
	}
	
	private static class LoggerSingletonHolder {
		private static final Logger logger = new Logger();
	}
}
