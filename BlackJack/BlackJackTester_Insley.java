/*
   Code by Mathias Insley
   AList-A13 BlackJack Game
**/

import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/*
   Tester class  
**/
public class BlackJackTester_Insley
{
   public static void main (String[] args)
   {
      BlackJackGame myGame = new BlackJackGame();
      myGame.playGame();     
   }
}

/*
   The BlackJackGame object
**/
class BlackJackGame
{
   Deck cardDeck;      // Global pointer to the card deck arraylist
   Player[] players;   // Global pointer to array of players
   Dealer dealer1;     // Global pointer to the dealer
   int numPlayers;     //Global pointer to number of players
   
   String[] suits = {"Diamonds", "Clubs", "Hearts", "Spades"} ;
   String[] ranks = {"Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King","Ace"};
   int[] pointValue= {2,3,4,5,6,7,8,9,10,10,10,10,11};
   
   /*
      Constructor gets the players, creates the dealer and builds the card deck.
   **/
   BlackJackGame ()
   {
      System.out.println("Welcome to BlackJack!!!\nThis game brought to you by courtesy of Mathias Insley");

      /*
         Get player names
      **/
      String[] playerNames = getPlayerNames() ;
      numPlayers = playerNames.length;
      
      /*
         Place players into an array
      **/
      players = new Player[numPlayers];      
      for (int i=0; i<numPlayers; i++)
      {
         players[i] = new Player(playerNames[i]);
      }

      /*
         Create a new deck of cards & shuffle them
      **/  
      cardDeck = new Deck(suits,ranks,pointValue) ;       
      
      /*
         Create a dealer
      **/
      dealer1 = new Dealer();   
   }
   
   /*
      The primary game driver. Collects user input (hit or stay) and 
      finishes off the dealer's hand at the end. Then calls on methods from 
      player class to see who wins, ties, or loses.
      @return is void
      @param takes nothing
   **/
   public void playGame()
   {    
      boolean keepPlaying = true;
      int totalHands = 0;
      int totalDealerWins=0;
      
      while(keepPlaying)
      {  
         totalHands++;
         if (cardDeck.size()< 26)
         {
            System.out.println("Creating new deck");
            cardDeck.newDeck(suits, ranks, pointValue);
         }
         
         //deal the first card to players
         for (int i = 0; i < numPlayers; i++)
         {
            players[i].deal(cardDeck);
         }
         // first card to dealer
         dealer1.addDealerCard(cardDeck);
         
         //deal the second card to players
         for (int i = 0; i < numPlayers; i++)
         {
            players[i].deal(cardDeck);
         }
         // second card to dealer
         dealer1.addDealerCard(cardDeck);
         
         System.out.println("\n Dealer shows: ");
         dealer1.displayDealerHand();
         
         //Play out player's hands
         for(int i=0;i<numPlayers;i++)
         {
            System.out.print("\n" + players[i].getPlayerName()+"'s");
            players[i].getUserChoice(cardDeck);
         }
         
         dealer1.addMoreDealerCard(cardDeck);
         dealer1.displayEndDealerHand();
         
         //Need to add code to resolve who wins and loses, tally results in class variables.
         int dealerWins=0;
         for(int i=0;i<numPlayers;i++)
         {
            dealerWins += players[i].determineWinner(dealer1.getDealerHandValue());  
         }
         totalDealerWins+=dealerWins;
         System.out.println("Dealer won " + dealerWins + " times");
         
         //re-initialize hands for next game
         for(int i=0;i<numPlayers;i++)
         {
            players[i].newHand();
         }
         dealer1.newHand();
     
         //decides if you keep going
         keepPlaying = playAgain();
      }
      
      System.out.println("\nGames Statistics: ");
      System.out.println("Total number of hands played: " + totalHands);
      System.out.println("Dealer won " + totalDealerWins + " times");
      for(int i=0;i<numPlayers;i++)
      {
         System.out.println("Player " + players[i].getPlayerName() + " won " + players[i].getWins() + " times");
      }
   }
   
   /*
      Collects players names
      @return is a string
      @param is nothing
   **/
   String[] getPlayerNames()
   {
      int numPlayers;
            
      Scanner userInput = new Scanner(System.in);
   
      System.out.print("Enter number of players: ");
      numPlayers = userInput.nextInt();
      userInput.nextLine() ;              // Flush the line buffer
   
      String[] players = new String[numPlayers];

      for (int i=0; i<numPlayers; i++)
      {
         System.out.print("Enter name of player " + (i+1) + ": ");
         players[i] = userInput.nextLine();
      }
            
      return players;  
   }
   
   /*
      Utility code to display the players.
      @return is void
      no @param
   **/
   void listPlayerNames ()
   {
      for (int i=0; i<players.length; i++)
      {
         System.out.println("Player " + (i+1) + " " + players[i].getPlayerName());
      }
   }
   /*
      @return is a boolean based on user input
      no @param
   **/
   boolean playAgain()
   {
      String response = "";
      Scanner userInput2 = new Scanner(System.in);
      boolean goodInput = false;
      while (!goodInput)
      {
         System.out.print("Would you like to play again? <y/n>: ");
         response = userInput2.nextLine();
         if (response.equals("n") | response.equals("N") | response.equals("y") | response.equals("Y"))
         {
            goodInput = true;
         } 
      }
      if (response.equals("n") | response.equals("N"))
      {
         return false;
      }
      else
      {
         return true;
      }
   }   
}

/*
   Dealer class manages the dealer's cards
   @author Mathias Insley
**/
class Dealer
{
   private Hand dealerHand = new Hand();
   
   void displayDealerHand()
   {
      System.out.println(dealerHand.dealerString(false));
   }
   
   /*
      @return is a void
      no @param
   **/
   void displayEndDealerHand()
   {
      System.out.println("Dealer's final hand: ");
      System.out.println(dealerHand.toString());
      System.out.println("\nPoint Value: " + getDealerHandValue() +"\n");
   }
   
   /*
    @return is void
    @param is a card
   **/
   void addDealerCard(Card newCard)
   {
      dealerHand.addCard(newCard);
   }
   
   /*
    @return is void
    @param is a deck, and deals a card from the deck into dealer's hand
   **/
   void addDealerCard(Deck myDeck)
   {
      addDealerCard(myDeck.deal());
           
   }
   
   /*
      @return is void
      @param is a deck. Method determines if dealer gets
      another card based on whether or not hitDealer() method is true
   **/
   void addMoreDealerCard(Deck myDeck)
   {
      System.out.println("\nPlaying out Dealer...\nDealers cards:");
      System.out.println(dealerHand.dealerString(true));

      while(hitDealer())
      {
         dealerHand.addCard(myDeck.deal());
      }    
   }
   
   /*
      Determines if dealer needs more cards based on if dealer's hand
      is busted. If not, then checks if point value is less than 17. If
      it is, then adds another card.
      @return is a boolean
      no @param
   **/
   public boolean hitDealer()
   { 
      dealerHand.isBusted(); 
      if(dealerHand.getValue()<17)
      {
         return true;
      }
      return false;
   }
   
   void displayDealerHandValue()
   {
      System.out.println( dealerHand.getValue() );
   }
   
   int getDealerHandValue()
   {
      return dealerHand.getValue();
   }
   
   void newHand()
   {
      dealerHand.newHand();
   }
}

/*
   Handles player specific functions: name, hand they hold, whether they want to hit or stand
   @author Mathias Insley
**/
class Player
{
   private Hand playerHand = new Hand();
   private String playerName;
   private int winTally=0;
   
   /*
      Constructor which takes a name input as @param
   **/
   Player (String name)
   {
      playerName = name;
   }
   
   /*
      @return is player's name
      no @param 
   **/
   String getPlayerName()
   {
      return playerName;
   }
   
   void newHand()
   {
      playerHand.newHand();
   }
   
   /*
      Prints out cards in hand
      @return is void
      no @param
   **/
   void displayHand()
   {
      System.out.println(playerHand.toString());
   }
   
   /*
      Adds a card to hand
      @return is void
      @param is a card
   **/
   void addCard(Card newCard)
   {
      playerHand.addCard(newCard);
   }
   
   /*
      Adds cards for initial set up of game
      @param is a deck
      @return is void
   **/
   void deal (Deck myDeck)
   {
      addCard(myDeck.deal());
   }
   
   /*
      Reads user input to see whether or not to add cards per player's wishes
      and also checks hand to see for blackjack and if hand is busted. 
      @return is void
      @param is the card deck since this method will deal cards.     
   **/
   void getUserChoice(Deck myDeck)
   {
      /*
         Checks for blackjack, if it is then done
      **/
      if(playerHand.getValue()==21)
      {
         System.out.println("cards are:\n");
         displayHand();
         System.out.println("Blackjack!");
         return ;
      }
         
      boolean busted = false ;
      
      Scanner scan = new Scanner(System.in);
      
      while(!playerHand.isBusted())
      {
         System.out.print(" cards are:\n");
         displayHand();
         System.out.print("Point Value: ");
         getHandValue();
        
         if(playerHand.getValue()==21)
         {
            displayHand();
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
         System.out.println(playerName + " busted with " + playerHand.getValue() + "!\n");
      } 
   }
   
   /*
      Prints out total point value of hand
   **/
   void getHandValue()
   {
      System.out.println( playerHand.getValue() );
   }
   
   /*
      Determines the status of the player (bust, win, lose tie)
      @return is an int which will be used to tally dealer's wins.
      @param are the dealerPoints because players are playing against dealer.
   **/
   int determineWinner(int dealerPoints)
   {  
      //Need to look at several cases. First we see if we busted, which means we loose
      if(playerHand.getValue()>21)
      {
         System.out.println("Player " + playerName + " busted with " + playerHand.getValue() );
         return 1;
      }
      
      //Second, if dealer busts, then player wins
      if(dealerPoints>21)
      {
         System.out.println("Player " + playerName + " wins with " + playerHand.getValue());
         winTally++;
         return 0;
      }
      
      //Third, if player hand beats dealer
      if(playerHand.getValue() > dealerPoints)
      {
         System.out.println("Player " + playerName + " wins with " + playerHand.getValue()); 
         winTally++;
         return 0;
      }
      
      //Fourth, if there is a tie, then dealer wins
      if(playerHand.getValue()==dealerPoints)
      {
         System.out.println("Player " + playerName + " ties with " + playerHand.getValue());
         return 0;
      }
      
      //Fifth, if player hand is less than dealer
      if(playerHand.getValue()< dealerPoints)
      {
         System.out.println("Player " + playerName + " loses with " + playerHand.getValue());
         return 1;
      }
      return 0;
   }
     
   int getWins()
   {
      return winTally;
   }
}

/*
   class Card handles all the card specific functions like printing them and
   retrieving them.
   @author Mathias Insley
**/
class Card
{
   String suit;
   String rank;
   int pointValue;
      
   /*
      Method to display a card's rank, suit, and point value (do not confuse with method printCards later)
   **/
   public void printCard()
   {
      System.out.println(rank + " of " + suit + "(Point Value = " + pointValue + ")") ;
   }
   
   String getCard()
   {
      return (rank + " of " + suit + "(Point Value = " + pointValue + ")") ;
   } 
}

/*
   Deck class is the entire deck of cards and handles the creation of the deck.
   @author Mathias Insley
**/
class Deck
{
   ArrayList<Card> myDeck = new ArrayList<Card>();

   /*
      Constructor built from three arrays to make cards
      @param are three arrays
      @return is a deck
   **/
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
      shuffle();
   }
   /* 
      Method to reshuffle deck
      @param are three arrays like above to make a new deck
      @return is a deck
   **/
   public void newDeck (String[] suit, String[] rank, int[] pointValue)          
   {
      myDeck.clear();
      
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
      shuffle();
   }
   /*
      Size method returns number of cards in deck.
   **/
   int size()
   {
      return myDeck.size();
   }

   /*
      Deal method returns a card from the top of the deck.
      @return is a card
      @param is void
   **/
   Card deal()
   {   
      Card returnCard;                 
      returnCard = myDeck.get(myDeck.size() - 1) ;
      myDeck.remove(myDeck.size() - 1) ;
      return returnCard ;
   }
   
   /*
      Shuffle method rearragnes cards in deck
      @return is void
      no @param
   **/
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
   
   /*
      Method prints all the cards in the deck
      @return is void
      no @param
   **/
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

/*
   Class Hand which controls all the methods which operate on a player's hand.
   @author Mathias Insley  
**/
class Hand
{
   ArrayList<Card> myHand = new ArrayList<Card>();
   private int handValue;
   private int aces;
   
   /*
      addCard method takes a card and adds it to the hand
      @return is void
   **/
   void addCard(Card newCard)
   {
      myHand.add(newCard);
   }
   
   /*
      getValue returns the BlackJack value
      @return is an int which represents the total value of the hand
   **/
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
   
   /*
      isBusted returns true if value is over 21
      @return is a boolean
   **/
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
   
   /*
      Shows the cards in the hand
      @ return is void
   **/
   public void showHand()
   {
      int length = myHand.size();
      for(int i = 0;i<length;i++)
      {
         myHand.get(i).printCard();
      }
   }
   
   /*
      toString returns a string with all cards, 1 card per line
      @return is a string
      no @param
   **/
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
   
   /*
      dealerString returns a string specific to the dealer, meaning
      the first card is not shown, or is the HOLD CARD
      @return is a string
      @param is the boolean showAll, if showAll is true then it shows all cards
   **/
   public String dealerString(boolean showAll)
   {
      String returnCard = "";
      int length = myHand.size();
      for(int i = 0;i<length;i++)
      {
         if ((i > 0) | showAll)
         {
            returnCard += myHand.get(i).getCard() + "\n";
         }
         else
         {
            returnCard += "***HOLD CARD***\n";
         }
      }
      return returnCard;
   }
   
   void newHand()
   {
      myHand.clear();
   }
   
}