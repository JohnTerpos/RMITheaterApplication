// Import the required packages
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class TheaterServer //Create class
{
    
      public TheaterServer()  //Default Constructor
      {
         try
         {
            TheaterImpl th=new TheaterImpl(); // Create a new Implementation.
            
	    // Binding the remote object (stub) in our own registry
            LocateRegistry.createRegistry(52369);
            String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + ":52369/TheaterInterfaceService";  
             
            Naming.rebind(url, th);

         }
         catch(Exception aInE)
         {
              System.out.println("Error: " +aInE);  //Error message
         }           
      }

      public static void main(String[] args)
      {
         //Create the Remote Object
         TheaterServer server=new TheaterServer();
	 
      }
}
