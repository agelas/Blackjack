//Code by Mathias Insley
//Answer to Q2 is 6.
import java.util.ArrayList;

public class DeckTester_InsleyV2
{
   public static void main (String[] args)
   {
      String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"} ;
      String[] ranks = {"Ace","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King"};
      Card dealtCard;

      Deck cardDeck = new Deck(suits,ranks) ;

      System.out.println("Deck has " + cardDeck.size() + " cards.\n");

      while(cardDeck.size() > 0)
      {
         dealtCard = cardDeck.deal();             
         dealtCard.show();
         System.out.println("Deck now has " + cardDeck.size() + " cards.\n");
      }
      
      String[] suits2 ={};
      String[] ranks2 ={};
      Card dealtCard2;
      
      Deck cardDeck2 = new Deck(suits2, ranks2);
      
      System.out.println("Deck 2 has " + cardDeck2.size()+ " cards.\n");
      
      //isEmpty() method test
      System.out.println(cardDeck2.isEmpty());
      
      //deal method return null test
      dealtCard2 = cardDeck2.deal(); 
      if(dealtCard2!=null)
      {          
         dealtCard2.show();
         System.out.println("Deck now has " + cardDeck2.size() + " cards.\n");
      }
      else
      {  
         System.out.println("There are no cards in the deck");
      }
   }
}

class Card
{
   String suit;
   String rank;
   
   // Method to display the card 
   public void show ()
   {
         System.out.println(rank + " of " + suit) ;
   }
}

// Deck class is the entire deck of cards.
class Deck
{
   ArrayList<Card> myDeck = new ArrayList<Card>();
      
   // Constructor built from two arrays
   public Deck (String[] suit, String[] rank)
   {
      for (int i = 0; i < suit.length; i++)
      {
         for (int j = 0; j < rank.length; j++)
         {
            Card newCard = new Card();
            newCard.suit = suit[i];
            newCard.rank = rank[j];
            myDeck.add(newCard);
         }
      }
   }

   // size method returns number of cards in deck.
   int size ()
   {
      return myDeck.size();
   }

   // deal method returns a card from the top of the deck.
   Card deal ()
   {
      Card returnCard;
      if(isEmpty())
      {
         return null;
      }                 
      returnCard = myDeck.get(myDeck.size() - 1) ;
      myDeck.remove(myDeck.size() - 1) ;
      return returnCard ;
   }
   
   boolean isEmpty()
   {
      if (myDeck.size()==0)
      {
         return true;
      }
      return false;
   }
}

