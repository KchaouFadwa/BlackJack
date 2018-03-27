package cards;

public class Face {
	private String nom; // nom de la carte
	private int valeur; // valeur de la carte

	private final int MAX_AS = 11;
	private final int MIN_AS = 1;

	// constructeur qui crée un objet Face basé sur un code Int.
	public Face(int face) {
		switch (face) {
		case 1:
			setNom("As");
			setValeur(11);
			break;
		case 2:
			setNom("Deux");
			setValeur(2);
			break;
		case 3:
			setNom("Trois");
			setValeur(3);
			break;
		case 4:
			setNom("Quatre");
			setValeur(4);
			break;
		case 5:
			setNom("Cinq");
			setValeur(5);
			break;
		case 6:
			setNom("Six");
			setValeur(6);
			break;
		case 7:
			setNom("Sept");
			setValeur(7);
			break;
		case 8:
			setNom("Huit");
			setValeur(8);
			break;
		case 9:
			setNom("Neuf");
			setValeur(9);
			break;
		case 10:
			setNom("Dix");
			setValeur(10);
			break;
		case 11:
			setNom("Valet");
			setValeur(10);
			break;
		case 12:
			setNom("Dame");
			setValeur(10);
			break;
		case 13:
			setNom("Roi");
			setValeur(10);
			break;
		default:
			break;
		}
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getValeur() {
		return valeur;
	}

	public void setValeur(int valeur) {
		this.valeur = valeur;
	}

	// methode qui teste si la carte est un As ou non
	public boolean isAce() {
		return (nom.equals("As")) ? true : false;
	}

	// methode qui teste si la valeur de As est 1 ou non
	public boolean isLowAce() {
		return (nom.equals("As") && getValeur() == MIN_AS) ? true : false;
	}

	// methode qui change la valeur As en 1
	public void switchAce() {
		if (isAce()) {
			if (getValeur() == MAX_AS) {
				setValeur(MIN_AS);
			}
		}
	}

	// representation de la valeur de carte.
	public String toString() {
		return getNom();
	}
}
