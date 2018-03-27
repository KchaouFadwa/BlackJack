package blackJackPlayers;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import cards.PlayerCardHand;
import casino.*;

public class BlackJackPlayerImpl extends UnicastRemoteObject implements BlackJackPlayer {
	private static final long serialVersionUID = 1L;
	private String nom;
	private String annoncement = "";
	public PlayerCardHand hand;
	private int jetons = 100;
	private int bet = 0;
	private Scanner sc;
	private Scanner sc2;

	public BlackJackPlayerImpl(String name) throws RemoteException {
		super();
		setNom(name);

	}

	public int getJetons() throws RemoteException {
		return jetons;
	}

	public void setJetons(int jetons) throws RemoteException {
		this.jetons = jetons;
	}

	public boolean setBet(int bet) throws RemoteException {
		boolean betMade = false;

		if (bet <= (getJetons() + getBet())) {
			this.jetons += this.bet; // reset old bet
			this.bet = bet; // set new bet
			this.jetons -= bet; // update wallet
			betMade = true;
		}

		return betMade;
	}

	public int getBet() throws RemoteException {
		return this.bet;
	}

	public void clearBet() throws RemoteException {
		this.jetons += this.bet;
		this.bet = 0;
	}

	public boolean doubleBet() throws RemoteException {
		boolean betDoubled = false;

		if (setBet(getBet() * 2)) {
			betDoubled = true;
		}

		return betDoubled;
	}

	public void loses() throws RemoteException {
		this.bet = 0;
	}

	public void wins(int amount) throws RemoteException {
		this.jetons += amount;
		this.bet = 0;
	}

	public boolean betPlaced() throws RemoteException {
		return (getBet() >= 10) ? true : false;
	}

	public boolean isBankrupt() throws RemoteException {
		return (getJetons() < 1 && getBet() <= 0) ? true : false;
	}

	public boolean canDouble() throws RemoteException {
		boolean reponse = false;

		if (getBet() <= getJetons()) {
			reponse = true;
		}

		return reponse;
	}

	public void setHand(PlayerCardHand hand) throws RemoteException {
		this.hand = hand;
	}

	public String getNom() throws java.rmi.RemoteException {
		return nom;
	}

	public void setNom(String nom) throws java.rmi.RemoteException {
		this.nom = nom;
	}

	public void say(String announcement) throws RemoteException {
		annoncement = announcement;
		System.out.println(annoncement);
	}

	public PlayerCardHand getHand() throws java.rmi.RemoteException {
		return this.hand;
	}

	public Vector<Table> creerTable(Vector<Table> tables, Vector<BlackJackDealer> dealers) throws RemoteException {
		int tailleTable;
		System.out.println(this.getNom());
		sc = new Scanner(System.in);
		System.out.println("tapez la taille de la table <= 6");
		tailleTable = sc.nextInt();
		while (tailleTable > 6) {
			sc = new Scanner(System.in);
			System.out.println("tapez la taille de la table");
			tailleTable = sc.nextInt();
		}
		try {
			Table table;
			table = new TableImpl(this.getNom(), tailleTable, dealers.elementAt(tables.size()), tables.size());
			tables.add(table);
			table.ajouterJoueur(this);
			table.setTemporaire(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tables;

	}

	public void choisirTable(Vector<Table> tables, Vector<BlackJackDealer> dealers) throws RemoteException {
		Table table;
		System.out.println(this.getNom());
		System.out.println("La liste des tables");
		for (int i = 0; i < tables.size(); i++) {
			System.out.println(tables.elementAt(i).getName());
		}

		sc2 = new Scanner(System.in);
		System.out.println("choisir une table en tapant le num de table");
		int numTable = sc2.nextInt();
		table = tables.elementAt(numTable);
		if ((table.isGameOver() == true) && (table.isComplete() == false)) {
			table.ajouterJoueur(this);
		}
		if ((table.isGameOver() == false) && (table.isComplete() == false)) {
			System.out.println("Le " + this.getNom() + " est en attente");
		}

	}

	// le joueur quite la partie
	public void quiterPartie(int numTable, Vector<Table> tables) throws RemoteException {

		Table table = tables.elementAt(numTable);
		if (table.isGameOver()) {
			System.out.println("le " + this.getNom() + " quite la table n " + numTable);
			if (this.getNom().equals(table.getCreateur()) && table.isTemporaire()) {

				table.getTablePlayers().removeAllElements();
				tables.remove(table);
				System.out.println("La table " + numTable + " est détruite");
			} else {
				table.getTablePlayers().remove(this);
				System.out.println("le joueurs " + this.getNom() + " est éliminé");
			}

		}
	}

	public int placerBet() throws RemoteException {
		int betPlayer;
		System.out.println(this.getNom());
		sc = new Scanner(System.in);
		System.out.println("S'il vous plaît placer vos mises");
		betPlayer = sc.nextInt();
		return betPlayer;
	}

	public String repondre() throws RemoteException {
		System.out.println("tour " + this.getNom());
		sc = new Scanner(System.in);
		System.out.println("si vous voulez une carte tapez hit sinon stand :");
		String annoncement = sc.nextLine();
		return annoncement;
	}

	
	public void presentation() throws RemoteException {
		System.out.println(this.getNom());
		System.out.println("Nombre de jetons: "+this.getJetons());	
		
	}

	public static void main(String[] args) throws Exception {
		int r = 1011;
		for (int i = 0; i < 20; i++) {
			LocateRegistry.createRegistry(r);
			Naming.rebind("Player" + i, new BlackJackPlayerImpl("joueur" + i));
			r++;
		}
	}
}
