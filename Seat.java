public class Seat   //Declare class
{
   //Declare members of class
   private String type;
   private int free_seats;
   private int max_seats;
   private double price;

   //Declare Constructor
   public Seat(String tp,int seats,double pr)
   {
        type=tp;
        free_seats=seats;
        max_seats=seats;
        price=pr;
   }

   //Declare setters and getters
   public void set_Type(String tp)
   {
       type=tp;
   }

   public void setFree_seats(int frseats)
   {
       free_seats=frseats;
   }

   public void setMax_seats(int mxseats)
   {
       max_seats=mxseats;
   }

   public void setPrice(double pr)
   {
        price=pr;
   }

   public String getType()
   {
        return type;
   }

   public int getFree_Seats()
   {
        return free_seats;
   }

   public int getMax_Seats()
   {
        return max_seats;
   }

   public double getPrice()
   {
       return price;
   }
}
