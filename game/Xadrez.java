package game;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import exceptions.ClicouNoMeioDoNadaException;
import exceptions.JogoJaAcabouException;
import exceptions.NaoEstaNaVezDoJogadorException;
import exceptions.NaoHaMovimentosValidosException;
import exceptions.PecaNaoPertenceAoJogadorException;
import timer.Tempo;

public class Xadrez implements Serializable, Cloneable {
	private static final long serialVersionUID = 4332058372731129426L;

	private EstadoJogo estadoJogo;
	private Point coordenadasPecaSelecionada;
	private List<Point> movimentosValidos;
	private Tabuleiro tabuleiro;
	private Cor corDoUltimoJogadorAAgir;
	private Tempo tempoPartida;
	private Tempo tempoTurno;
	
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
		corDoUltimoJogadorAAgir = Cor.PRETO;
		tempoPartida = new Tempo();
		tempoTurno = new Tempo();
	}
	
	public List<Point> selecionaPeca(Cor corJogador, Point coordenadas) throws PecaNaoPertenceAoJogadorException, 
	NaoHaMovimentosValidosException, ClicouNoMeioDoNadaException, 
	JogoJaAcabouException, NaoEstaNaVezDoJogadorException {
	
		if(jogoJaAcabou()) {
			throw new JogoJaAcabouException();
		}

		if(!estaNaVezDoJogador(corJogador)) {
			throw new NaoEstaNaVezDoJogadorException();
		}
		
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
	
	public void movePeca(Cor corJogador, Point coordenadasOrigem, Point coordenadasDestino) {
		Peca pecaDeslocada = tabuleiro.getPeca(coordenadasOrigem.x, coordenadasOrigem.y);
		Peca pecaDestino = tabuleiro.getPeca(coordenadasDestino.x, coordenadasDestino.y);
		
		tabuleiro.removePeca(coordenadasOrigem.x, coordenadasOrigem.y);
		tabuleiro.colocaPeca(coordenadasDestino.x, coordenadasDestino.y, pecaDeslocada);

		if(pecaDeslocada instanceof Peao) {
			((Peao)pecaDeslocada).setPrimeiroMovimento(false);
		}

		alternaTurno();
		
		boolean estaCapturandoUmaPeca = pecaDestino != null;

		if(estaCapturandoUmaPeca) {
			boolean pecaCapturadaEhORei = pecaDestino instanceof Rei;
			
			if(pecaCapturadaEhORei) {
				boolean ehVitoriaDasPecasBrancas = pecaDeslocada.getCor().equals(Cor.BRANCO);
				
				if(ehVitoriaDasPecasBrancas) {
					estadoJogo = EstadoJogo.VITORIA_BRANCO;
				} else {
					estadoJogo = EstadoJogo.VITORIA_PRETO;
				}
			}
		}
		corDoUltimoJogadorAAgir = corJogador;
		movimentosValidos.clear();
	}

	private void alternaTurno() {
		if(estadoJogo.equals(EstadoJogo.TURNO_BRANCO)) {
			estadoJogo = EstadoJogo.TURNO_PRETO;
		} else {
			estadoJogo = EstadoJogo.TURNO_BRANCO;
		}
	}
	
	public boolean estaNaVezDoJogador(Cor corJogador) {
		System.out.println("Cor do jogador que esta tentando se mover: " + corJogador);
		System.out.println("O turno é das pecas da cor: " + estadoJogo);
		return (corJogador.equals(Cor.BRANCO) && estadoJogo.equals(EstadoJogo.TURNO_BRANCO)) ||
				(corJogador.equals(Cor.PRETO) && estadoJogo.equals(EstadoJogo.TURNO_PRETO));
	}
	
	public boolean jogadorEPecaSaoDaMesmaCor(Cor corJogador, Point coordenadasPeca) {
		if(!tabuleiro.casaEstaVazia(coordenadasPeca.x, coordenadasPeca.y)) {
			Peca peca = tabuleiro.getPeca(coordenadasPeca.x, coordenadasPeca.y);
			return peca.getCor().equals(peca.getCor());
		}
		return false;
	}
	
	public boolean jogoJaAcabou() {
		return estadoJogo.equals(EstadoJogo.VITORIA_BRANCO) || estadoJogo.equals(EstadoJogo.VITORIA_PRETO);
	}
	
	public void salvaJogo(String nomeArquivo) throws FileNotFoundException, IOException {
		FileOutputStream fileOutput = new FileOutputStream(nomeArquivo);
		ObjectOutputStream objectOutput = new ObjectOutputStream(fileOutput);
		objectOutput.writeObject(this);
		objectOutput.flush();
		objectOutput.close();
	}
	
	public static Xadrez carregaJogo(String nomeArquivo) throws FileNotFoundException, IOException, ClassNotFoundException {		
		Xadrez jogoCarregado;
		FileInputStream fileInput = new FileInputStream(nomeArquivo);
		ObjectInputStream objectInput = new ObjectInputStream(fileInput);
		jogoCarregado = (Xadrez)objectInput.readObject();
		objectInput.close();
		
		return jogoCarregado;
	}
	
	public static void apagaJogosalvo(String nomeArquivo) throws FileNotFoundException {
		System.out.println("Apagando " + nomeArquivo);
		List<String> jogosEncontrados = buscaJogosSalvos();
		if(jogosEncontrados.contains(nomeArquivo)) {
			System.out.println("Encontrou" + nomeArquivo);
			new File("./"+nomeArquivo).delete();
		} else {
			throw new FileNotFoundException();
		}
	}
	
	public static List<String> buscaJogosSalvos() {
		List<String> jogosEncontrados = new ArrayList<String>();
		
		File arquivos[] = new File(".").listFiles();
		for(File arquivoEncontrado : arquivos) {
			if(arquivoEncontrado.getName().contains(".dat")) {
				System.out.println("Jogo encontrado: " + arquivoEncontrado.getName());
				jogosEncontrados.add(arquivoEncontrado.getName());
			}
		}
		return jogosEncontrados;
	}
	
	public static String[] buscaJogosSalvosComoArray() {
		List<String> jogosSalvos = buscaJogosSalvos();
		String jogosArray[] = new String[jogosSalvos.size()];
		for(int i = 0; i < jogosSalvos.size(); i++) {
			jogosArray[i] = jogosSalvos.get(i);
		}
		return jogosArray;
	}
	
	public Cor getCorDoUltimoJogadorAAgir() {
		return corDoUltimoJogadorAAgir;
	}

	public Tempo getTempoPartida() {
		return tempoPartida;
	}

	public void setTempoPartida(Tempo tempoPartida) {
		this.tempoPartida = tempoPartida;
	}

	public Tempo getTempoTurno() {
		return tempoTurno;
	}

	public void setTempoTurno(Tempo tempoTurno) {
		this.tempoTurno = tempoTurno;
	}

}
