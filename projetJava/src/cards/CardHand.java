package cards;

import java.util.*;

public abstract class CardHand extends Vector<Card> {

	private static final long serialVersionUID = 1L;

	// constructeur
	public CardHand() {
		super();
	}

	// Retirez toutes les cartes
	public void clear() {
		super.clear();
	}

	// total des cartes
	public int getTotal() {
		int total = 0;

		for (Card eachCard : this) {
			total += eachCard.getValue();
		}

		return total;
	}

	// V�rifie si la main de la carte perd ou non.
	public boolean isperd() {
		return (getTotal() > 21) ? true : false;
	}

	// V�rifie si la main a un Blackjack �gal � 21 et seulement deux cartes
	public boolean hasBlackjack() {
		return (getTotal() == 21 && this.size() == 2) ? true : false;
	}

	// repr�sentation des cartes en main
	public String toString() {
		return super.toString() + " (" + getTotal() + ")";
	}
}
