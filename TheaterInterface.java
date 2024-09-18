// Import the required packages.
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TheaterInterface extends Remote // Create interface
{
  //Specify methods used by Client.
  public String list(String hostname) throws RemoteException;

  public String book(String hostname,String type,String number, String name) throws RemoteException;

  public String guests(String hostname) throws RemoteException;

  public String cancel(String hostname,String type,String number, String name) throws RemoteException;

  public void addListener(String type, TheaterListener clnt) throws RemoteException;

  public void removeListener(String type, TheaterListener clnt) throws RemoteException;

  public Seat selectSeat(String type) throws RemoteException;

  public void CallBack(String type) throws RemoteException;
}
