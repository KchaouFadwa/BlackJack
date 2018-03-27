package casino;

import java.rmi.RemoteException;
import java.util.Vector;

import blackJackPlayers.*;


public interface Table extends java.rmi.Remote {

	public void hebergerPartie() throws Exception;
	public void ajouterJoueur(BlackJackPlayer player)throws RemoteException;
	public String getName()throws RemoteException;
	public boolean isGameOver()throws RemoteException;
	public boolean isComplete() throws RemoteException;
	public String getCreateur()throws RemoteException;
	public boolean isTemporaire()throws RemoteException;
	public void setTemporaire(boolean isTemporaire) throws RemoteException;
	public Vector<BlackJackPlayer> getTablePlayers()throws RemoteException;
	public int getTailleTable()throws RemoteException;

}
