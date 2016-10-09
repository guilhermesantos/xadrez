package game;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import exceptions.ClicouNoMeioDoNadaException;
import exceptions.NaoHaMovimentosValidosException;
import exceptions.PecaNaoPertenceAoJogadorException;

public class Xadrez extends Observable implements Serializable {
	private static final long serialVersionUID = 4332058372731129426L;

	private EstadoJogo estadoJogo;
	private Point coordenadasPecaSelecionada;
	private List<Point> movimentosValidos;
	
	public EstadoJogo getEstadoJogo() {
		return estadoJogo;
	}

	public List<Point> getMovimentosValidos() {
		return movimentosValidos;
	}

	public Point getCoordenadasPecaSelecionada() {
		return coordenadasPecaSelecionada;
	}

	public Xadrez() {
		iniciaNovoJogo();
	}

	public void iniciaNovoJogo() {
		Tabuleiro.getInstance().reinicializaTabuleiro();
		estadoJogo = EstadoJogo.TURNO_BRANCO;
		coordenadasPecaSelecionada = null;
		movimentosValidos = new ArrayList<Point>();
	}

	public List<Point> selecionaPeca(Point coordenadas) throws PecaNaoPertenceAoJogadorException, NaoHaMovimentosValidosException, ClicouNoMeioDoNadaException {

		if (Tabuleiro.getInstance().casaEstaVazia(coordenadas.x, coordenadas.y)) {
			throw new ClicouNoMeioDoNadaException();
		}

		boolean pecaPertenceAoJogador = (Tabuleiro.getInstance().
				getPeca(coordenadas.x, coordenadas.y).cor == Cor.BRANCO
				&& estadoJogo == EstadoJogo.TURNO_BRANCO)
				|| (Tabuleiro.getInstance().getPeca(coordenadas.x, coordenadas.y).cor == Cor.PRETO
						&& estadoJogo == EstadoJogo.TURNO_PRETO);

		if (!pecaPertenceAoJogador) {
			throw new PecaNaoPertenceAoJogadorException();
		}
		movimentosValidos = Tabuleiro.getInstance().getPeca(coordenadas.x, coordenadas.y).
				getMovimentosValidos(coordenadas.x,	coordenadas.y);
		
		if (movimentosValidos.isEmpty()) {
			throw new NaoHaMovimentosValidosException();
		}
		
		coordenadasPecaSelecionada = new Point(coordenadas.x, coordenadas.y);
		
		return movimentosValidos;
	}
	
	public void movePeca(Point coordenadasOrigem, 
			Point coordenadasDestino) {
		Peca pecaDeslocada = Tabuleiro.getInstance().getPeca(coordenadasOrigem.x, coordenadasOrigem.y);
		
		boolean estaCapturandoUmaPeca = !Tabuleiro.getInstance().casaEstaVazia(coordenadasDestino.x, coordenadasDestino.y);
		if(estaCapturandoUmaPeca) {
			
			boolean pecaCapturadaEhORei = Tabuleiro.getInstance()
					.getPeca(coordenadasDestino.x, coordenadasDestino.y) instanceof Rei;
			if(pecaCapturadaEhORei) {
				setChanged();
				notifyObservers(estadoJogo);
			}
		}
		Tabuleiro.getInstance().removePeca(coordenadasOrigem.x, coordenadasOrigem.y);
		Tabuleiro.getInstance().colocaPeca(coordenadasDestino.x, coordenadasDestino.y, pecaDeslocada);

		if(pecaDeslocada instanceof Peao) {
			((Peao)pecaDeslocada).setPrimeiroMovimento(false);
		}
		movimentosValidos.clear();
		
		if(estadoJogo.equals(EstadoJogo.TURNO_BRANCO)) {
			estadoJogo = EstadoJogo.TURNO_PRETO;
		} else {
			estadoJogo = EstadoJogo.TURNO_BRANCO;
		}
	}
	
	public void salvaJogo(String nomeArquivo) throws FileNotFoundException, IOException {
		FileOutputStream fileOutput = new FileOutputStream(nomeArquivo);
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
		objectOutput.writeObject(this);
		objectOutput.flush();
		objectOutput.close();
	}
	
	public Xadrez carregaJogo() throws FileNotFoundException, IOException, ClassNotFoundException {
		Xadrez jogoCarregado;
		FileInputStream fileInput = new FileInputStream("jogo_salvo.dat");
		ObjectInputStream objectInput = new ObjectInputStream(fileInput);
		jogoCarregado = (Xadrez)objectInput.readObject();
		objectInput.close();
		return jogoCarregado;
	}

}
