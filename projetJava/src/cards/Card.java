package cards;

public class Card {
	private Mark marque; // type de la carte
	private Face face; // Face de la carte(nom + valeur de carte)
	private int code; // Numéro de carte

	public Card(Face face, Mark suit, int code) {
		setFace(face);
		setMarque(suit);
		setCode(code);
	}

	public Mark getMarque() {
		return marque;
	}

	public void setMarque(Mark marque) {
		this.marque = marque;
	}

	public Face getFace() {
		return face;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	// Renvoie la valeur d'une carte
	public int getValue() {
		return this.getFace().getValeur();
	}

	// Représentation de la carte à jouer.
	public String toString() {
		return getFace() + " de " + getMarque();
	}
}