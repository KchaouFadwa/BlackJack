package cards;

import java.util.*;

public class CardPack extends Stack<Card> {

	private static final long serialVersionUID = 1L;

	public static final int CARDS_IN_PACK = 52; // Nombre de la carte dans un pack

	// constructeur
	public CardPack() {
		super();

		final String[] marques = { "Cœur", "Carreau", "Trèfle", "Pique" };

		int cardCode = 1;

		for (String marque : marques) // 13 cartes de chaque marque
		{
			for (int i = 1; i < 14; i++) {
				this.push(new Card(new Face(i), new Mark(marque), cardCode));
				cardCode++;
			}
		}
	}
}