public class Reservations //Declare class
{
   //Declare private members of class.
   private String typeofseat;
   private int bookamount;
   private double finalprice;

   //Declare Constructor
   public Reservations(Seat seat,int bkmnt)
   {
      typeofseat=seat.getType();
      bookamount=bkmnt;
      this.calculatePrice(seat.getPrice());
   }

   // Declare setters and getters.
   public void setTypeofSeat(String tps)
   {
      typeofseat=tps;
   }

   public void setBookamount(int bkmnt)
   {
        bookamount=bkmnt;
   }

   public void setFinalPrice(double fpr)
   {
        finalprice=fpr;
   }

   public String getTypeofSeat()
   {
      return typeofseat;
   }

   public int getBookamount()
   {
       return bookamount;
   }

   public double getFinalPrice()
   {
       return finalprice;
   }

   //Method of calculating price of booked seats
   public void calculatePrice(double pr)
   {
        finalprice=pr*bookamount;
   }
}
 
