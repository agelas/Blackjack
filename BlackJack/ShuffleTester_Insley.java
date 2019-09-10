//Code by Mathias Insley

import java.util.ArrayList;

public class ShuffleTester_Insley
{
   public static void main (String[] args)
   {
      String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"} ;
      String[] ranks = {"Ace","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King"};
      int[] pointValue= {1,2,3,4,5,6,7,8,9,10,10,10,10};
      Card dealtCard;

      Deck cardDeck = new Deck(suits,ranks,pointValue) ;

      System.out.println("Deck has " + cardDeck.size() + " cards.\n");
      
      cardDeck.shuffle();

      while(cardDeck.size() > 0)
      {
         dealtCard = cardDeck.deal();             
         dealtCard.printCards();
         //System.out.println("Deck now has " + cardDeck.size() + " cards.\n");
      }
   }
}
class Card
{
   String suit;
   String rank;
   int pointValue;
      
   // Method to display the card 
   public void printCards()
   {
         System.out.println(rank + " of " + suit + "(Point Value = " + pointValue + ")") ;
   }
}

// Deck class is the entire deck of cards.
class Deck
{
   ArrayList<Card> myDeck = new ArrayList<Card>();

   // Constructor built from two arrays
   public Deck (String[] suit, String[] rank, int[] pointValue)          
   {
      for (int i = 0; i < suit.length; i++)
      {
         for (int j = 0; j < rank.length; j++)
         {
            Card newCard = new Card();
            newCard.suit = suit[i];
            newCard.rank = rank[j];
            newCard.pointValue = pointValue[j];
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
      returnCard = myDeck.get(myDeck.size() - 1) ;
      myDeck.remove(myDeck.size() - 1) ;
      return returnCard ;
   }
   
   //shuffle method rearragnes cards in deck
   void shuffle()
   {
      Card shuffleCard;
      for(int k=51;k>0;k--)
      {
         int j = (int)(Math.random()*k);
         Card temp = myDeck.get(j); 
         myDeck.set(j, myDeck.get(k));
         myDeck.set(k,temp); 
      }
   }
}


