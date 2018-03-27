package blackJackPlayers;

import java.rmi.RemoteException;
import java.util.Vector;

public interface BlackJackDealer extends java.rmi.Remote {

	public boolean deal(Vector<BlackJackPlayer> tablePlayers) throws java.rmi.RemoteException;

	public void hit(BlackJackPlayer player) throws java.rmi.RemoteException;

	public void stand(BlackJackPlayer blackJackPlayer) throws java.rmi.RemoteException;

	public void demanderAnnoncement(Vector<BlackJackPlayer> tablePlayers) throws java.rmi.RemoteException;

	public void go(Vector<BlackJackPlayer> tablePlayers) throws java.rmi.RemoteException;

	public boolean isGameOver() throws java.rmi.RemoteException;

	public void say(String announcement) throws java.rmi.RemoteException;

	public void lancerPartie(Vector<BlackJackPlayer> tablePlayers) throws java.rmi.RemoteException;

	public void playDouble(Vector<BlackJackPlayer> tablePlayers, int numPlayer) throws RemoteException;

	public boolean canPlayerDouble(BlackJackPlayer player) throws RemoteException;

	public boolean acceptBetFrom(BlackJackPlayer player, int bet) throws java.rmi.RemoteException;
}
