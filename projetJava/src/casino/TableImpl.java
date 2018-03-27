package casino;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Vector;
import blackJackPlayers.*;

public class TableImpl extends UnicastRemoteObject implements Table {

	private static final long serialVersionUID = 1L;
	public BlackJackDealer dealer;
	public Vector<BlackJackPlayer> tablePlayers = new Vector<BlackJackPlayer>(6);
	private int tailleTable;
	private int numTable;
	// true -> table temporaire
	private boolean isTemporaire;
	// true -> jeu partie terminé
	private boolean gameOver = true;
	// temps d'attente pour lancer la partie
	private int tempsAttente;
	// le nom de createur de la table
	private String createur;
	private boolean complete = false;

	// constructeur de classe Table
	public TableImpl(String createur, int tailleTable, BlackJackDealer dealer, int numTable) throws RemoteException {
		this.createur = createur;
		this.tailleTable = tailleTable;
		this.dealer = dealer;
		this.numTable = numTable;
		if (this.createur.equals("Casino")) {
			isTemporaire = false;
		} else {
			isTemporaire = true;
		}
	}

	// heberger Partie
	public void hebergerPartie() throws Exception {
		tempsAttente = 10000;
		if (tablePlayers.size() >= 2 && !this.isComplete()) {
			System.out.println("la " + this.getName() + " est incompléte");
			this.setComplete(false);
			try {
				Thread.sleep(tempsAttente);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dealer.lancerPartie(tablePlayers);
			this.gameOver=dealer.isGameOver();
		}
		if (this.isComplete()) {
			System.out.println("la " + this.getName() + " est compléte");
			dealer.lancerPartie(tablePlayers);
			this.gameOver=dealer.isGameOver();
		}
		

	}

	@Override
	public String getName() throws RemoteException {
		return "Table" + numTable;
	}

	public void ajouterJoueur(BlackJackPlayer player) throws RemoteException {
		tablePlayers.add(player);
	}

	public boolean isGameOver() throws RemoteException {
		return gameOver;
	}

	public void setGameOver(boolean gameIsOver) {
		this.gameOver = gameIsOver;
	}

	public int getTailleTable() throws RemoteException {
		return tailleTable;
	}

	public void setTailleTable(int tailleTable) {
		this.tailleTable = tailleTable;
	}

	public int getNumTable() {
		return numTable;
	}

	public void setNumTable(int numTable) {
		this.numTable = numTable;
	}

	public int getTempsAttente() {
		return tempsAttente;
	}

	public void setTempsAttente(int tempsAttente) {
		this.tempsAttente = tempsAttente;
	}

	public boolean isComplete() throws RemoteException {
		if (tablePlayers.size() < tailleTable) {
			this.complete = false;
		}
		if (tablePlayers.size() == tailleTable) {
			this.complete = true;
		}
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public Vector<BlackJackPlayer> getTablePlayers() throws RemoteException {
		return tablePlayers;
	}

	public void setTablePlayers(Vector<BlackJackPlayer> tablePlayers) {
		this.tablePlayers = tablePlayers;
	}

	public String getCreateur() throws RemoteException {
		return createur;
	}

	public void setCreateur(String createur) {
		this.createur = createur;
	}

	public boolean isTemporaire() throws RemoteException {
		return isTemporaire;
	}

	public void setTemporaire(boolean isTemporaire) throws RemoteException {
		this.isTemporaire = isTemporaire;
	}

}
