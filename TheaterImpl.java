// Import the required packages.
import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;

public class TheaterImpl extends UnicastRemoteObject implements TheaterInterface // Create class
{
   //Members of class
   private ArrayList<Seat> Seats=new ArrayList<Seat>(); //ArrayList with seats.
   private ArrayList<Client> Clients=new ArrayList<Client>(); //ArrayList with clients.
   private HashMap<String, ArrayList<TheaterListener>> holdonlist=new HashMap<String, ArrayList<TheaterListener>>(); //HashMap with list of waiting clients 
   
   public TheaterImpl() throws RemoteException  //Default Constructor
   {
	super();
        
        //Add seats to Arraylist
        Seats.add(new Seat("ΠΑ",200,50.00));
        Seats.add(new Seat("ΠΒ",300,40.00));
        Seats.add(new Seat("ΠΓ",500,30.00));
        Seats.add(new Seat("ΚΕ",100,25.00));
        Seats.add(new Seat("ΠΘ",50,20.00));
        
        int i;
        for(i=0;i<Seats.size();i++)
        {
              holdonlist.put(Seats.get(i).getType(),new ArrayList<TheaterListener>()); //Add node based on type of seats
        }
   }

   public String list(String hostname) throws RemoteException
   {
        String result="\nAvailable Seats\n";
        int i;
        for(i=0;i<Seats.size();i++) //Add the free seats of each type and their price to result.
        {
           result+="\t Type " +Seats.get(i).getType() +" has " +Seats.get(i).getFree_Seats() +" seats with price " +Seats.get(i).getPrice() +" euro each one.\n";
        }

        return result; //Return result
   }

   public String book(String hostname,String type,String number, String name) throws RemoteException
   {
       boolean found = false;  //flag if seat found with this customer's name
       int i;
       String answer = "";
     
       Seat seatchoice=selectSeat(type);   //Select seat by type.

       if(seatchoice==null) //Check if this type of seat exists
       {
          answer+="\nNo seat found with this type: " +type +".\n";
          return answer;
       }

       int num=Integer.parseInt(number); //Convert String to int
       
       if(num>seatchoice.getMax_Seats())  //Book more seats than the maximum number of seats.
       {
             answer+="\nClient " +name +", you passed the limit of max seats in theatre.\n";
             return answer;
       }

       else if(num>seatchoice.getFree_Seats()) //Book more seats than the number of available seats.
       {
          answer+="\nThere are no available seats!\n";
          return answer;
       }

       for(i=0;i<Clients.size();i++) //Check if client has already reserved seats with this name.
       {
          if(Clients.get(i).getFullname().equals(name))
          {
             found=true;
             Clients.get(i).addReservation(seatchoice,num);
             break;
          }
       }

       if(found==false) //New client
       {
           Clients.add(new Client(name)); //Add new client to ArrayList
           Clients.get(i).addReservation(seatchoice,num);  //Add client's reservations to ArrayList 
       }

       //Send how many seats with current type each client books and the price
       answer+="\n Client " +Clients.get(i).getFullname() +" has just booked " +number +" seats with type " +Clients.get(i).getSpecificReservation(type).getTypeofSeat() +" and price " +(num*seatchoice.getPrice()) +".\n";
       return answer;
   }

   public String guests(String hostname) throws RemoteException
   {
       String answer="";
       int k;

       if(Clients.isEmpty()) //Check if we have clients in ArrayList
       {
          answer+="\nNo clients\n";
       }
         
       else
       {
          answer+="\n There are " +Clients.size() +" clients.\n";
       }

       for(k=0;k<Clients.size();k++)
       {
           answer+=Clients.get(k).infoReservations();
       }

       return answer;
   }

   public String cancel(String hostname,String type,String number, String name) throws RemoteException
   {
        Seat seatchoice=selectSeat(type);   //Select seat by type.
        String answer="";
        int clientindex,checked,i,num;
    
        clientindex=-5;
        checked=-5;
        num=Integer.parseInt(number); //Convert String to int    
   
        if(seatchoice==null) //Check if this type of seat doesn't exist
        {
          answer+="\nThis type of seat does not exist.\n";
          return answer;
        }

        for(i=0;i<Clients.size();i++)
        {
            if(Clients.get(i).getFullname().equals(name))
            {
                checked=Clients.get(i).removeReservation(seatchoice,num); //Cancel seat.
               
                if(checked<-1)
                   break;
 
                //Send how many seats with current type each client cancels and the price
                answer+="\nClient " +Clients.get(i).getFullname() +" has just cancelled " +number +" seats with type " +Clients.get(i).getSpecificReservation(type).getTypeofSeat() +" and price " +(num*seatchoice.getPrice()) +".\n";
                clientindex=i;
                break;
            }
        }

        if(checked>=0) //Sucessful cancel
        {
           Clients.get(clientindex).getReservations().remove(checked); //Remove reservations
           
           if(Clients.get(clientindex).getReservations().isEmpty()) //No other seats are booked by client
           {
              Clients.remove(clientindex); //remove client
              return "\nClient " +name +" has been deleted due to the fact that he/she hasn't any reservations of seats.\n";
           }
        }
 
        else if(checked==-5) //No Client found in Clients ArrayList
        {
            return "\nThe client with name " +name +" is not found.\n";
        }

        else if(checked==-2)  //Client tries to cancel a number of seats that is more than a number of seats he/she booked 
        {
             return "\nThe number of cancelled seats is more than the number of booked seats.\n";
        }

        else if(checked==-3) //Cancel seats that you havent booked
        {
           return "\nThe client with name " +name +" hasn't booked type " +type +" of seat.\n";
        }

        answer+=Clients.get(clientindex).infoReservations(); // Add information of client to answer
        return answer;
   }

   public void addListener(String type, TheaterListener clnt) throws RemoteException //Add listener
   {
         holdonlist.get(type).add(clnt);
   }

   public void removeListener(String type, TheaterListener clnt) throws RemoteException //Remove listener
   {
         holdonlist.get(type).remove(clnt);
   }

   public Seat selectSeat(String type) throws RemoteException //Select seats by type
   {
       int j;
       for(j=0;j<Seats.size();j++) //Search the type of seat client needs
       {
           if(Seats.get(j).getType().equals(type))
              return Seats.get(j);
       }

       return null;
   }

   public void CallBack(String type) throws RemoteException
   {
         Seat seatchoice=selectSeat(type);   //Select seat by type.
         int i;
     
         for(i=0;i<holdonlist.get(type).size();i++)
         {
            if(holdonlist.get(type).get(i).getRequestedSeats()<=seatchoice.getFree_Seats()) //More available seats than the requests of clients in holdonlist.
            {
               holdonlist.get(type).get(i).seatsAvailable(type,seatchoice.getFree_Seats()); //Call client back
               removeListener(type,holdonlist.get(type).get(i));
               i--; //reduce the size of holdonlist
            }
         }
   }
}
