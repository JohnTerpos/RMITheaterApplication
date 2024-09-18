//Import the required packages
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TheaterListener extends Remote //Create interface
{
  //Specify methods used by server
  public void seatsAvailable(String type, int num) throws RemoteException;

  public int getRequestedSeats() throws RemoteException;
}
