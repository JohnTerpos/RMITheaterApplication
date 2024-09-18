//import the required packages
import java.util.ArrayList;

public class Client // Declare class
{
   //Declare private members of class.
   private String fullname;
   private ArrayList<Reservations> reservations = new ArrayList<Reservations>(); 

   //Declare Constructor
   public Client(String fnm)
   {
       fullname=fnm;  
   }

   //Declare setters and getters
   public void setFullname(String fnm)
   {
      fullname=fnm;
   }

   public void setReservations(ArrayList<Reservations> rsvs)
   {
      reservations=rsvs;
   }

   public String getFullname()
   {
       return fullname;
   }

   public ArrayList<Reservations> getReservations()
   {
       return reservations;
   }

   public Reservations getSpecificReservation(String type)  //Method of finding specific reservation
   {
     int i;

     for (i = 0; i < reservations.size(); i++) 
     {
	if ((reservations.get(i).getTypeofSeat()).equals(type)) // Check if you found this type of reservation.
	{
	   break;
	}
     }

     return reservations.get(i); // Return the reservation if found.
   }

   public void addReservation(Seat seat, int bookamount) // Add reservation.
   {
      boolean found = false;
      int i;
      
      for(i=0;i<reservations.size();i++)
      {
          if ((reservations.get(i).getTypeofSeat()).equals(seat.getType())) //Check if this type of seat is already reserved
	  {
		reservations.get(i).setBookamount(reservations.get(i).getBookamount() + bookamount); //update bookamount
		reservations.get(i).calculatePrice(seat.getPrice());  //update price
		found = true;
		break; // Stop Searching.
	  }
          
      }
     
      if(found==false) //New client
      {
          reservations.add(new Reservations(seat,bookamount));  //Add the reservation to ArrayList.
      }
 
      seat.setFree_seats(seat.getFree_Seats()-bookamount); //Update free seats.
   }

  public int removeReservation (Seat seat, int amountcancel) //Remove reservation
  {
     boolean found = false;
     int i,pos;
 
     pos=-1;
     for(i=0;i<reservations.size();i++)
     {
          if ((reservations.get(i).getTypeofSeat()).equals(seat.getType())) //Check if this type of seat is already reserved
	  {
	       if(amountcancel>reservations.get(i).getBookamount())	// Check if clients cancel more reservations than he booked
               {
                   return -2;
               } 
	
               reservations.get(i).setBookamount(reservations.get(i).getBookamount()-amountcancel); //update bookamount
	       reservations.get(i).calculatePrice(seat.getPrice()); //update price
               found=true;
                   
               if (reservations.get(i).getBookamount() == 0) // If not rooms left.
	       {
		   pos = i; // Remove certain index.
	       }  
	       break; // Stop Searching.
	  }
          
     }
  
     if (!found) // If client tries to cancel and  hasn't booked any seats.
     {
	return -3;
     }

     seat.setFree_seats(seat.getFree_Seats()+amountcancel); //Update free seats.
     return pos;
  }

  public double calculateFinalPrice()  // Calculate final price
  {
    double final_sum=0;
    int i;
    
    for(i=0;i<reservations.size();i++)
    {
        final_sum+=reservations.get(i).getFinalPrice();
    }

    return final_sum;
  }

  public String infoReservations() //Client's reservations
  {
     int i;
     String response=this.fullname +" has booked:\n";
    
     for(i=0;i<reservations.size();i++)
     {
        response+="\t" +reservations.get(i).getBookamount() +" seats with type " +reservations.get(i).getTypeofSeat() +" and price " +reservations.get(i).getFinalPrice() +" euros.\n";
     }

     response+="Total price: " +this.calculateFinalPrice() +" euros.\n";
     return response;  //Return client's reservations 
  }
}
