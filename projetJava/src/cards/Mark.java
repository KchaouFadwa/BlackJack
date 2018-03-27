package cards;

public class Mark {

	private String nom; // nom de la marque

	// constructeur
	public Mark(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	// representation de la marque
	public String toString() {
		return getNom();
	}
}