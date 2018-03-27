package blackJackPlayers;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.util.Vector;

import cards.*;

public class BlackJackDealerImpl extends UnicastRemoteObject implements BlackJackDealer {

	private static final long serialVersionUID = 1L;

	BlackJackPlayer dealer = new BlackJackPlayerImpl("Banque");
	String annoncement;

	// Si le croupier a distribué les deux premières cartes.
	private boolean firstDeal = true;

	// La valeur que doit avoir le croupier
	public static final int DEALER_STANDS_ON = 17;

	// la partie est terminée ou non
	public boolean gameOver = true;
	// la face visible ou non
	public boolean cardsFaceUp = false;
	public static final int CARD_PACKS = 2;
	private Deck deck = new Deck(CARD_PACKS);
	private boolean playerCanDouble = true;

	public BlackJackDealerImpl() throws RemoteException {
		super();
	}

	public BlackJackDealerImpl(String nom) throws RemoteException {
		super();
		this.dealer.setNom(nom);
	}

	@Override
	public boolean deal(Vector<BlackJackPlayer> tablePlayers) throws RemoteException {
		boolean cardsDealt = false;
		int betPlayer;
		BlackJackPlayer player;
		deck.shuffle();
		if (firstDeal) {
			gameOver = false;
			cardsFaceUp = false;
			for (int i = 0; i < tablePlayers.size(); i++) {
				player = tablePlayers.elementAt(i);
				betPlayer = player.placerBet();
				this.acceptBetFrom(player, betPlayer);
				if (player.betPlaced() && !player.isBankrupt()) {
					player.setHand(new PlayerCardHand());
				}
			}

			dealer.setHand(new PlayerCardHand());

			say("le croupier est en train de distribuer les deux premières cartes "
					+ "pour les joueurs et pour lui meme");
			for (int j = 0; j < 2; j++) {
				for (int i = 0; i < tablePlayers.size(); i++) {
					player = tablePlayers.elementAt(i);
					player.getHand().add(deck.deal());
					say(deck.deal().toString() + " pour " + player.getNom());
					if (player.getHand().hasBlackjack()) {
						say("Blackjack!");
						go(tablePlayers);
					}
				}

				dealer.getHand().add(deck.deal());

			}
			say(deck.deal().toString() + " pour " + dealer.getNom());

			cardsDealt = true;
			firstDeal = false;
		}
		return cardsDealt;
	}

	@Override
	public void hit(BlackJackPlayer player) throws RemoteException {
		say(player.getNom() + " dit hit");

		player.getHand().add(deck.deal());

		playerCanDouble = false;

		if (player.getHand().isperd()) {
			say(player.getNom() + " perd. il perd " + player.getBet());
			player.loses();
			gameOver = true;
		}

	}

	@Override
	public void stand(BlackJackPlayer player) throws RemoteException {
		say(player.getNom() + " dit stand ");
	}

	// tour de dealer et determiner le gagant
	public void go(Vector<BlackJackPlayer> tablePlayers) throws RemoteException {
		cardsFaceUp = true;

		if (!dealer.getHand().hasBlackjack()) {
			while (dealer.getHand().getTotal() < DEALER_STANDS_ON) {
				dealer.getHand().add(deck.deal());
				say(dealer.getNom() + " hit");
			}

			if (dealer.getHand().isperd()) {
				say(this.dealer.getNom() + " perd avec un total :"+dealer.getHand().getTotal());
			} else {
				say(dealer.getNom() + " stand en " + dealer.getHand().getTotal());
			}
		} else {
			say(dealer.getNom() + " a BLACKJACK!");
		}

		for (int i = 0; i < tablePlayers.size(); i++) {
			BlackJackPlayer player;
			player = tablePlayers.elementAt(i);
			if (dealer.getHand().hasBlackjack() && player.getHand().hasBlackjack()) {
				say("Egalité!");
				say("Hand "+ player.getNom() +" : "+player.getHand().getTotal());
				player.clearBet();

			} else if (player.getHand().hasBlackjack()) {
				say(player.getNom() + " gagne avec Blackjack €" + player.getBet());
				player.wins(player.getBet() *2);

			} else if (dealer.getHand().hasBlackjack()) {
				say(dealer.getNom()+" a un Blackjack. " + player.getNom() + " perd € " + player.getBet());
				player.loses();

			} else if (dealer.getHand().isperd()) {
				say(dealer.getNom()+" perd  " + player.getNom() + " gagne € " + player.getBet());
				player.wins(player.getBet() * 2);

			} else if (player.getHand().getTotal() == dealer.getHand().getTotal()) {
				say("Egalité");
				player.clearBet();

			} else if (player.getHand().getTotal() < dealer.getHand().getTotal()) {
				say(player.getNom() + " perd € " + player.getBet());
				player.loses();

			} else if (player.getHand().getTotal() > dealer.getHand().getTotal()) {
				say(player.getNom() + " gagne € " + player.getBet());
				
				player.wins(player.getBet() * 2);

			}
			player.presentation();

		}
		gameOver = true;
		if (gameOver) {
			say("la jeu est terminée");
		}
	}

	@Override
	public void demanderAnnoncement(Vector<BlackJackPlayer> tablePlayers) throws RemoteException {
		int taille = tablePlayers.size();
		while (taille > 0) {
			for (int i = 0; i < taille; i++) {
				BlackJackPlayer player = tablePlayers.elementAt(i);
				annoncement = player.repondre();
				if (annoncement.equals("hit")) {
					this.hit(tablePlayers.elementAt(i));
				} else {
					this.stand(tablePlayers.elementAt(i));
					taille--;
				}

			}
		}

	}

	// lancer partie
	public void lancerPartie(Vector<BlackJackPlayer> tablePlayers) throws java.rmi.RemoteException {
		BlackJackPlayer player;
		System.out.println("Dealer est pret");

		for (int i = 0; i < tablePlayers.size(); i++) {

			player = tablePlayers.elementAt(i);
			System.out.println(player.getNom() + " est pret" + " ");
		}
		this.deal(tablePlayers);
		this.demanderAnnoncement(tablePlayers);
		this.go(tablePlayers);
		gameOver = true;
	}

	@Override
	public void say(String announcement) throws RemoteException {
		annoncement = announcement;
		System.out.println(annoncement);

	}

	/**
	 * Le joueur voudrait placer des mises jusqu'à doubler de son original et avoir
	 * le dealer lui donne une carte de plus.
	 */
	public void playDouble(Vector<BlackJackPlayer> tablePlayers, int numPlayer) throws RemoteException {
		BlackJackPlayer player = tablePlayers.elementAt(numPlayer);
		if (player.doubleBet() && playerCanDouble) {
			player.getHand().add(deck.deal());
			say(player.getNom() + " joue double");

			if (player.getHand().isperd()) {
				say(player.getNom() + " perd. il perd €" + player.getBet());
				player.loses();
				gameOver = true;
			} else {
				go(tablePlayers);
			}
		} else {
			say(player.getNom() + ", tu peux pas doubler. Pas assez de jetons.");
		}
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int cardsLeftInPack() {
		return deck.size();
	}

	public void newDeck() {
		deck = new Deck(CARD_PACKS);
	}

	public boolean canPlayerDouble(BlackJackPlayer player) throws RemoteException {
		return (playerCanDouble && player.canDouble()) ? true : false;
	}

	public boolean acceptBetFrom(BlackJackPlayer player, int bet) throws java.rmi.RemoteException {
		boolean betSet = player.setBet(bet);
		if (player.betPlaced()) {
			say("Merci pour votre mises € " + player.getBet());
		} else {
			say("svp placez vos mises");
		}
		return (betSet) ? true : false;
	}

	public boolean isGameOver() throws RemoteException {
		return gameOver;
	}

	public boolean areCardsFaceUp() {
		return cardsFaceUp;
	}

	public static void main(String[] args) throws Exception {
		int registre = 1099;
		for (int i = 0; i < 10; i++) {
			LocateRegistry.createRegistry(registre);
			Naming.rebind("Dealer" + i, new BlackJackDealerImpl());
			registre++;
		}
	}

}
