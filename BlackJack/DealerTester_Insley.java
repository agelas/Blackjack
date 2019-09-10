//Code by Mathias Insley
//AList-A10 Dealer Class

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class DealerTester_Insley
{
   public static void main (String[] args)
   {
      String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"} ;
      String[] ranks = {"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
      int[] pointValue= {2,3,4,5,6,7,8,9,10,10,10,10,11};
      Card dealtCard;

      Deck cardDeck = new Deck(suits,ranks,pointValue) ;
      
      cardDeck.shuffle();
      
      Dealer dealer1 = new Dealer();
      
      dealer1.addDealerCard(cardDeck);
      
      System.out.println("Dealer 1 shows: ");
      dealer1.displayDealerHand();
      dealer1.addMoreDealerCard(cardDeck);
      
      System.out.print("Dealer's hand has a value of: ");
      dealer1.getDealerHandValue();
      
      System.out.println( "So it is " + dealer1.hitDealer() + " that he should get more cards" );
      
      System.out.println("Dealer has: ");
      dealer1.displayEndDealerHand();
      
      Dealer dealer2 = new Dealer();
      
      dealer2.addDealerCard(cardDeck);
      
      System.out.println("Dealer 2 shows: ");
      dealer2.displayDealerHand();
      dealer2.addMoreDealerCard(cardDeck);
      
      System.out.print("Dealer's hand has a value of: ");
      dealer2.getDealerHandValue();
      
      System.out.println( "So it is " + dealer2.hitDealer() + " that he should get more cards" );
      
      System.out.println("Dealer has: ");
      dealer2.displayEndDealerHand();
      
      Dealer dealer3 = new Dealer();
      
      dealer3.addDealerCard(cardDeck);
      
      System.out.println("Dealer 3 shows: ");
      dealer3.displayDealerHand();
      dealer3.addMoreDealerCard(cardDeck);
      
      System.out.print("Dealer's hand has a value of: ");
      dealer3.getDealerHandValue();
      
      System.out.println( "So it is " + dealer3.hitDealer() + " that he should get more cards" );
      
      System.out.println("Dealer has: ");
      dealer3.displayEndDealerHand();
       
   }
}

class Dealer
{
   private Hand dealerHand = new Hand();
   
   void displayDealerHand()
   {
      System.out.println(dealerHand.dealerString());
   }
   
   void displayEndDealerHand()
   {
      System.out.println(dealerHand.toString());
   }
   
   void addDealerCard(Card newCard)
   {
      dealerHand.addCard(newCard);
   }

   void addDealerCard(Deck myDeck)
   {
      addDealerCard(myDeck.deal());
      addDealerCard(myDeck.deal());   
   }
   
   void addMoreDealerCard(Deck myDeck)
   {
      while(hitDealer())
      {
         dealerHand.addCard(myDeck.deal());
      }    
   }
   
   public boolean hitDealer()
   { 
      dealerHand.isBusted(); 
      if(dealerHand.getValue()<17)
      {
         return true;
      }
      return false;
   }
   void getDealerHandValue()
   {
      System.out.println( dealerHand.getValue() );
   }
}

class Player
{
   private Hand playerHand = new Hand();
   
   /**
   Prints out cards in hand
   */
   void displayHand()
   {
      System.out.println(playerHand.toString());
   }
   
   /**
   Adds a card to hand
   */
   void addCard(Card newCard)
   {
      playerHand.addCard(newCard);
   }
   
   /**
   Adds 2 cards for initial set up of game
   */
   void deal (Deck myDeck)
   {
      addCard(myDeck.deal());
      addCard(myDeck.deal());
   }
   
   /**
   Reads user input
   */
   void getUserChoice(Deck myDeck)
   {
      /**
      Checks for blackjack, if it is then done
      */
      if(playerHand.getValue()==21)
      {
         System.out.println("Blackjack!");
         System.out.println("Your cards are:\n");
         displayHand();
         return ;
      }
         
      boolean busted = false ;
      
      Scanner scan = new Scanner(System.in);
      
      while(!playerHand.isBusted())
      {
         System.out.println("Your cards are:\n");
         displayHand();
         System.out.print("Point Value: ");
         getHandValue();
        
         if(playerHand.getValue()==21)
         {
            displayHand();
            System.out.println("You won!");
            return;
         }

         System.out.print("Do you want to hit <h> or Stand <s>?");
         String userInput = scan.nextLine();

         if(userInput.equals("h") | userInput.equals("H"))
         {
            addCard(myDeck.deal());
         }
         else
         {
            if(userInput.equals("s") | (userInput.equals("S")))
            {
               return;
            }
            System.out.print("Invalid input, try again");
         }       
      } 
      if(playerHand.isBusted())
      {
         displayHand();
         System.out.println("You busted!");
      } 
   }
   
   /**
   Prints out total point value of hand
   */
   void getHandValue()
   {
      System.out.println( playerHand.getValue() );
   }
}

class Card
{
   String suit;
   String rank;
   int pointValue;
      
   /**
   Method to display a card's rank, suit, and point value (do not confuse with method printCards later)
   */
   public void printCard()
   {
      System.out.println(rank + " of " + suit + "(Point Value = " + pointValue + ")") ;
   }
   
   String getCard()
   {
      return (rank + " of " + suit + "(Point Value = " + pointValue + ")") ;
   } 
}

/**
Deck class is the entire deck of cards.
*/
class Deck
{
   ArrayList<Card> myDeck = new ArrayList<Card>();

   /**
   Constructor built from two arrays
   */
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
   /**
   size method returns number of cards in deck.
   */
   int size ()
   {
      return myDeck.size();
   }

   /**
   deal method returns a card from the top of the deck.
   */
   Card deal ()
   {
      Card returnCard;                 
      returnCard = myDeck.get(myDeck.size() - 1) ;
      myDeck.remove(myDeck.size() - 1) ;
      return returnCard ;
   }
   
   /**
   shuffle method rearragnes cards in deck
   */
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

/**
Class Hand 
*/
class Hand
{
   ArrayList<Card> myHand = new ArrayList<Card>();
   private int handValue;
   private int aces;
   
   /**
   addCard method takes a card and adds it to the hand
   */
   void addCard(Card newCard)
   {
      myHand.add(newCard);
   }
   
   /**
   getValue returns the BlackJack value
   */
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
   
   /**
   isBusted returns true if value is over 21
   */
   public boolean isBusted()
   {
      Card tempCard;
      
      if(getValue()<=21)
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
            return false;
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
   
   /**
   toString returns a string with all cards, 1 card per line
   */
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
   
   public String dealerString()
   {
      String returnCard = "";
      int length = myHand.size()-1;
      for(int i = 0;i<length;i++)
      {
         returnCard += myHand.get(i).getCard() + "\n";
      }
      return returnCard;
   }
}