//Import the required packages.
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.rmi.server.UnicastRemoteObject;

public class TheaterClient extends UnicastRemoteObject implements TheaterListener    //Create class
{
   private int requests;    // Number of seats each customer requests

   public TheaterClient() throws RemoteException //Default constructor
   {
      
   }

   public TheaterClient(int requests) throws RemoteException //Constructor with parameter requests
   {
      this.requests=requests;
   }

   //Getter
   public int getRequestedSeats() throws RemoteException
   {
        return requests;
   }

   public void seatsAvailable(String type, int num) throws RemoteException //Print number of available seats with this type
   {
          System.out.println("Type: " +type +", number of available seats: " +num);
          UnicastRemoteObject.unexportObject(this, true); // Remove Object.
   }

   public static void main(String[] args) throws InterruptedException//main method
   {
      try
      {
         //Lookup for the service to our own registry
         String url = "rmi://" +InetAddress.getLocalHost().getHostAddress() +":52369/TheaterInterfaceService"; 
         TheaterInterface th=(TheaterInterface) Naming.lookup(url);

         int arguments=args.length;   //Number of command-line arguments
         
	 if(arguments==0) // No command-line arguments
         {
           //Print results
	   System.out.println("Option unavailable!");
           System.out.println("You should try one of those options:");
           System.out.println("1) java TheaterClient list <hostname>");
           System.out.println("2) java TheaterClient book <hostname> <type> <number> <name>");
           System.out.println("3) java TheaterClient guests <hostname>");
           System.out.println("4) java TheaterClient cancel <hostname> <type> <number> <name>");
         }
        
         else
         {
               if(args[0].equals("list") && arguments==2)   // option list
               {
                   System.out.println(th.list(args[1]));  //Print result
               }

               else if(args[0].equals("book") && arguments==5)  //option book
               {
                   String str="";
                   
                   if(Integer.parseInt(args[3])>0)   // the number of seats must be positive in order to book
                   {
                      str+=th.book(args[1],args[2],args[3],args[4]); //Call book method
                      System.out.print(str); //Print result
                      
                      if(str.equals("\nThere are no available seats!\n"))   // In case there are unavailable seats of certain type
                      {
                          Scanner input = new Scanner(System.in);  //Scanner Object
                          boolean flag=true;

                          do
                          {                         
                          	System.out.println("\nDo you want to be in waiting list(yes/no)?");
                          	String ans=input.next();
				
				if(ans.equalsIgnoreCase("no"))
                          	{
                                   flag=false;
                          	}
                                
                                else if(ans.equalsIgnoreCase("yes"))
                                {
                                   flag=false;
			           TheaterClient thcl=new TheaterClient(Integer.parseInt(args[3]));
                                   th.addListener(args[2], thcl); //Add Listener
                                }
                                
                          }while(flag);
                      }
                   }
                   
               }

               else if(args[0].equals("guests") && arguments==2) //Option guests 
               {
                    System.out.println(th.guests(args[1])); //Print result
               }

               else if(args[0].equals("cancel") && arguments==5) //Option cancel
               {
                   String str="";
                   
                   if(Integer.parseInt(args[3])>0)   // the number of seats must be positive in order to cancel
                   {
                       str+=th.cancel(args[1],args[2],args[3],args[4]); //Call cancel method
                       System.out.print(str); //Print result

                       if (str.charAt(1) != 'T') //Check if second character is not 'T'
		       {
			   th.CallBack(args[2]);
		       }
                   }
               }
         }
      }
      catch (MalformedURLException e)
      {
	System.out.println("\nMalformedURLException:" + e); // Error message.
      }
      catch (RemoteException e)
      {
	System.out.println("\nRemoteException:" + e); // Error message.
      }
      catch (NotBoundException e)
      {
	System.out.println("\nNotBoundException:" + e); // Error message.
      }
      catch (java.lang.ArithmeticException e)
      {
	System.out.println("\nArithmeticException:" + e); // Error message.
      }
      catch (Exception aInE)
      {
            System.out.println(aInE); // Error message.
      }
   }
}
