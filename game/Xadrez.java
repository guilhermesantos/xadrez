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
import java.util.Observer;

import exceptions.ClicouNoMeioDoNadaException;
import exceptions.NaoHaMovimentosValidosException;
import exceptions.PecaNaoPertenceAoJogadorException;

public class Xadrez extends Observable implements Serializable {
	private static final long serialVersionUID = 4332058372731129426L;

	private EstadoJogo estadoJogo;
	private Point coordenadasPecaSelecionada;
	private List<Point> movimentosValidos;
	private Tabuleiro tabuleiro;
	
	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

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
		tabuleiro = new Tabuleiro();
		estadoJogo = EstadoJogo.TURNO_BRANCO;
		coordenadasPecaSelecionada = null;
		movimentosValidos = new ArrayList<Point>();
	}

	public Xadrez(Observer observer) {
		this();
		super.addObserver(observer);
	}

	public List<Point> selecionaPeca(Point coordenadas) throws PecaNaoPertenceAoJogadorException, NaoHaMovimentosValidosException, ClicouNoMeioDoNadaException {

		if (tabuleiro.casaEstaVazia(coordenadas.x, coordenadas.y)) {
			throw new ClicouNoMeioDoNadaException();
		}

		boolean pecaPertenceAoJogador = (tabuleiro.
				getPeca(coordenadas.x, coordenadas.y).cor == Cor.BRANCO
				&& estadoJogo == EstadoJogo.TURNO_BRANCO)
				|| (tabuleiro.getPeca(coordenadas.x, coordenadas.y).cor == Cor.PRETO
						&& estadoJogo == EstadoJogo.TURNO_PRETO);

		if (!pecaPertenceAoJogador) {
			throw new PecaNaoPertenceAoJogadorException();
		}

		movimentosValidos = tabuleiro.getPeca(coordenadas.x, coordenadas.y).
				getMovimentosValidos(tabuleiro, coordenadas.x,	coordenadas.y);
		
		if (movimentosValidos.isEmpty()) {
			throw new NaoHaMovimentosValidosException();
		}
		
		coordenadasPecaSelecionada = new Point(coordenadas.x, coordenadas.y);
		
		return movimentosValidos;
	}
	
	public void movePeca(Point coordenadasOrigem, 
			Point coordenadasDestino) {
		Peca pecaDeslocada = tabuleiro.getPeca(coordenadasOrigem.x, coordenadasOrigem.y);
		
		boolean estaCapturandoUmaPeca = !tabuleiro.casaEstaVazia(coordenadasDestino.x, coordenadasDestino.y);
		if(estaCapturandoUmaPeca) {
			
			boolean pecaCapturadaEhORei = tabuleiro
					.getPeca(coordenadasDestino.x, coordenadasDestino.y) instanceof Rei;
			
			if(pecaCapturadaEhORei) {
				setChanged();
				notifyObservers(estadoJogo);
			}
		}
		tabuleiro.removePeca(coordenadasOrigem.x, coordenadasOrigem.y);
		tabuleiro.colocaPeca(coordenadasDestino.x, coordenadasDestino.y, pecaDeslocada);

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
	
	public Xadrez carregaJogo(String nomeArquivo) throws FileNotFoundException, IOException, ClassNotFoundException {
		Xadrez jogoCarregado;
		FileInputStream fileInput = new FileInputStream("jogo_salvo.dat");
		ObjectInputStream objectInput = new ObjectInputStream(fileInput);
		jogoCarregado = (Xadrez)objectInput.readObject();
		objectInput.close();
		return jogoCarregado;
	}
}
