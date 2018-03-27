package blackJackPlayers;

import java.util.Vector;
import cards.PlayerCardHand;
import casino.Table;

public interface BlackJackPlayer extends java.rmi.Remote {

	public void say(String announcement) throws java.rmi.RemoteException;

	public void setHand(PlayerCardHand hand) throws java.rmi.RemoteException;

	public String getNom() throws java.rmi.RemoteException;

	public void setNom(String nom) throws java.rmi.RemoteException;

	public PlayerCardHand getHand() throws java.rmi.RemoteException;

	public Vector<Table> creerTable(Vector<Table> tables, Vector<BlackJackDealer> dealers)
			throws java.rmi.RemoteException;

	public void choisirTable(Vector<Table> tables, Vector<BlackJackDealer> dealers) throws java.rmi.RemoteException;

	public void quiterPartie(int numTable, Vector<Table> tables) throws java.rmi.RemoteException;

	public int getJetons() throws java.rmi.RemoteException;

	public void presentation() throws java.rmi.RemoteException;

	public String repondre() throws java.rmi.RemoteException;

	public void setJetons(int jetons) throws java.rmi.RemoteException;

	public boolean setBet(int bet) throws java.rmi.RemoteException;

	public int getBet() throws java.rmi.RemoteException;

	public void clearBet() throws java.rmi.RemoteException;

	public boolean doubleBet() throws java.rmi.RemoteException;

	public void loses() throws java.rmi.RemoteException;

	public void wins(int amount) throws java.rmi.RemoteException;

	public boolean betPlaced() throws java.rmi.RemoteException;

	public boolean isBankrupt() throws java.rmi.RemoteException;

	public boolean canDouble() throws java.rmi.RemoteException;

	public int placerBet() throws java.rmi.RemoteException;
}
