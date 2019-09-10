//Code by Mathias Insley

class Card
{
   private String rank;
   private String suit;
   private int pointValue;
   
   public String getRank()
   {
      return rank;
   }
   
   public int getValue()
   {
      return pointValue;
   }
   
   public String getSuit()
   {
      return suit;
   }
   
   public void setValue()
   {
      pointValue = 1;
   }
   
   public String toString()
   {
      return getRank() + " of " + getSuit() + "(point value = " + getValue()+ ")";
   }
   
   Card(String cardRank, String suit, int pointValue)
   {
      this.rank = cardRank;
      this.suit = suit;
      this.pointValue = pointValue;
   }
   
   Card()
   {
      rank = "Eight";
      suit = "Clubs";
      pointValue=8;
   }

}

public class CardTester_Insley
{
   public static void main(String[] args)
   {
      Card card1 = new Card("King", "Spades", 10);
      System.out.println(card1.toString());
      Card card2 = new Card("Nine", "Hearts", 9);
      System.out.println(card2.toString());
      Card card3 = new Card();
      System.out.println(card3.toString());
   }
}