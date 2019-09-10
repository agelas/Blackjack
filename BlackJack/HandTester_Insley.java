//Code by Mathias Insley

import java.util.ArrayList;

public class HandTester_Insley
{
   public static void main (String[] args)
   {
      String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"} ;
      String[] ranks = {"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
      int[] pointValue= {2,3,4,5,6,7,8,9,10,10,10,10,11};
      Card dealtCard;

      Deck cardDeck = new Deck(suits,ranks,pointValue) ;

      System.out.println("Deck has " + cardDeck.size() + " cards.\n");
      
      cardDeck.shuffle();
      
      System.out.println("Hand 1:");
      Hand hand1 = new Hand();
      while(!hand1.isBusted())
      {
         hand1.addCard(cardDeck.deal());
      }
      System.out.println(hand1.toString());
      
      System.out.println("Hand 2:");
      Hand hand2 = new Hand();
      while(!hand2.isBusted())
      {
         hand2.addCard(cardDeck.deal());
      }
      System.out.println(hand2.toString());
   }
}
class Card
{
   String suit;
   String rank;
   int pointValue;
      
   // Method to display a card's rank, suit, and point value (do not confuse with method printCards later)
   public void printCard()
   {
      System.out.println(rank + " of " + suit + "(Point Value = " + pointValue + ")") ;
   }
   
   String getCard()
   {
      return (rank + " of " + suit + "(Point Value = " + pointValue + ")") ;
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
   
   void printCards()
   {
      int a = myDeck.size()-1;
      while(a > 0)
      {
         Card current = myDeck.get(a);
         current.printCard();
         a--;
      }
   }
}

//Class Hand 
class Hand
{
   ArrayList<Card> myHand = new ArrayList<Card>();
   private int handValue;
   private int aces;
   
   //addCard method takes a card and adds it to the hand
   void addCard(Card newCard)
   {
      myHand.add(newCard);
   }
   
   //getValue returns the BlackJack value
   public int getValue()
   {
      int handValue = 0;
      int length = myHand.size();
      for(int i = 0; i<length;i++)
      {
         handValue += myHand.get(i).pointValue;
      }
      return handValue;
   }
   
   //isBusted returns true if value is over 21
   public boolean isBusted()
   {
      Card tempCard;
      
      if(getValue()<21)
      {
         return false;
      }
      
      
      
      for (int i =0;i<myHand.size();i++)
      {
         tempCard = myHand.get(i);
         
         if(tempCard.pointValue == 11)
         {
            tempCard.pointValue = 1;
            myHand.set(i, tempCard);
         }
      }
      return true;
   }
   
   public void showHand()
   {
      int length = myHand.size();
      for(int i = 0;i<length;i++)
      {
         myHand.get(i).printCard();
      }
   }
   
   //toString returns a string with all cards, 1 card per line
   public String toString()
   {
      String returnValue ="";
      int length = myHand.size();
      for(int i = 0;i<length;i++)
      {
         returnValue += myHand.get(i).getCard() + "\n";
      }
      return returnValue;
   }
}


