package casino;

import java.rmi.*;
import java.util.*;

import blackJackPlayers.*;

public class Casino {
	public Vector<BlackJackDealer> dealers = new Vector<BlackJackDealer>();
	public Vector<BlackJackPlayer> players = new Vector<BlackJackPlayer>();
	public Vector<Table> tables = new Vector<Table>();

	public Casino() {
		super();
	}

	public void recupererDealers() throws Exception {
		for (int i = 0; i < 10; i++) {
			BlackJackDealer dealer;
			dealer = (BlackJackDealer) Naming.lookup("Dealer" + i);
			dealers.add(dealer);

		}
	}

	public void recupererPlayers(int nbJoueurs) throws Exception {
		for (int i = 0; i < nbJoueurs; i++) {

			BlackJackPlayer player;
			player = (BlackJackPlayer) Naming.lookup("Player" + i);
			players.add(player);

		}
	}

	// créer une tables avec une taille
	public void hebergerTable(String createur, int tailleTable, int numTable) throws Exception {
		BlackJackDealer dealer = dealers.elementAt(numTable);
		Table table = new TableImpl(createur, tailleTable, dealer, numTable);
		tables.add(table);
	}

	// supprimer une tables
	public void suprimerTable(int nTable) {
		tables.removeElementAt(nTable);
		dealers.remove(nTable);
	}

	public static void main(String[] args) throws Exception {
		Scanner sc2;
		Casino casino = new Casino();
		casino.recupererDealers();
		casino.recupererPlayers(20);
		casino.hebergerTable("Casino", 6, 0);

		while (casino.players.size() > 0) {
			BlackJackPlayer player = casino.players.lastElement();
			sc2 = new Scanner(System.in);
			System.out.println("vous voulez choisir table (tape choisir) ou creer table (tape creer)");
			String reponse = sc2.nextLine();
			if (reponse.equals("creer")) {
				casino.tables = player.creerTable(casino.tables, casino.dealers);
			} else {
				player.choisirTable(casino.tables, casino.dealers);
			}
			casino.players.remove(player);

			for (int i = 0; i < casino.tables.size(); i++) {
				Table table = casino.tables.elementAt(i);
				table.hebergerPartie();
			}

		}

	}
}
