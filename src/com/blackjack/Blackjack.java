package com.blackjack;
import java.util.Scanner;
public class Blackjack {
    public static void main(String[] args) {

        System.out.println("Welcome to Blackjack!");
        // Start the game . Initialize constructor to set the player scores to zero.
        Game blackjack = new Game();


        // Create the playing deck.

           Deck playingDeck = new Deck();
           playingDeck.createFullDeck();
        // System.out.println(playingDeck);

        // Shuffle the playing Deck
           playingDeck.shuffleDeck();
        // System.out.println(playingDeck);

        // Create hands for the player and the dealer cards - hands are created from methods that are made in the deck class
        Deck playerHand = new Deck();
        Deck dealerHand = new Deck();


        // Set starting player money

        Scanner input = new Scanner(System.in);
        System.out.println("Hello!! How much $ are you playing today?");
        double playerMoney = input.nextDouble();

        // Game loops
        int roundCount = 0; // Variable to calculate the number of rounds the game continues till the player runs out of money.
        while (playerMoney > 0){
            roundCount++; // Increment the variable to reflect new round.


            System.out.println("You have $" + playerMoney + "\nHow much would you like to bet?");
            double playerBet = input.nextDouble();

            if(playerBet > playerMoney){
                System.out.println("You don't have sufficient balance to place the bet. You only have $" + playerMoney);
                break;
            }
            boolean endRound = false;

            // Start the game. Deal cards to the player.
            //  Player gets two cards


            playerHand.draw(playingDeck);
            playerHand.draw(playingDeck);

            // Dealer gets two cards

            dealerHand.draw(playingDeck);
            dealerHand.draw(playingDeck);

            while (true){

                // Display Player hand and card value
                System.out.println("-----------Player Hand------------------");
                System.out.println(playerHand);
                System.out.println("Player's card deck value: " + playerHand.cardsValue());

                // Display Dealer hand and card value
                System.out.println("-------------Dealer Hand-------------------");

                System.out.println(dealerHand.getCard(0).toString() + " [Hidden]");

                // Check for BlackJack
                // Check first if player has blackjack
                if(playerHand.cardsValue() == 21 && !endRound){
                    System.out.println("Player has BlackJack!!");

                    if(dealerHand.cardsValue() == 21){
                        System.out.println("Dealer has BlackJack!!");
                        System.out.println("Both player and dealer has BlackJack. PUSH");
                        blackjack.setPushes(1); // Update the player score to increment pushes to one.
                    }else{
                        // Player has blackJack, he wins 1.5 times the bet money.
                        playerMoney += (1.5 * playerBet);
                        System.out.println("Player won this round!!");
                    }
                    blackjack.setWins(1); // Update the player score to increment wins to one.
                    endRound = true;
                    break;
                }

                // Check first if Dealer has blackjack
                if(dealerHand.cardsValue() == 21 && !endRound){
                    System.out.println("Dealer has BlackJack!!");

                   if(playerHand.cardsValue() == 21){
                        System.out.println("Player has BlackJack!!");

                        System.out.println("Both player and dealer has BlackJack. PUSH");
                        blackjack.setPushes(1);
                    }else{
                        playerMoney -= playerBet; // Player loses the bet amount.
                        System.out.println("Dealer Won this round!!");
                        blackjack.setLosses(1);
                    }
                    endRound = true;
                    break;
                }

                // Ask player what they want to do? Hit or Stand.

                System.out.println("Would you like to Hit (1) or Stay (2)? ");
                System.out.println("Press 1 to Hit or Press 2 to Stay");
                int response = input.nextInt();

                // Player Hit
                if(response == 1){
                    // Draw another card
                    playerHand.draw(playingDeck);
                    System.out.println("Player draw: " +  playerHand.getCard(playerHand.deckSize()-1).toString());

                    if(playerHand.cardsValue() > 21){
                        System.out.println("Player Busted. Currently valued at: " + playerHand.cardsValue());
                        System.out.println("Dealer Wins!!");
                        playerMoney -= playerBet; // Player loses the bet amount
                        blackjack.setLosses(1);
                        endRound = true;
                        break;
                    }
                }

                // Player stand
                if(response == 2){
                    break;
                }

            }

            // Display dealers Card

            System.out.println("-------Dealer Cards---------- \n" + dealerHand);

            // If dealer's card value is < 17, dealer should draw card.
            while ((dealerHand.cardsValue() < 17) && !endRound){
                System.out.println("Dealer's card value is less than 17, Dealer hit.");
                dealerHand.draw(playingDeck);
                System.out.println("Dealer Draws: " + dealerHand.getCard(dealerHand.deckSize()-1).toString());
            }
            // Total value of the dealer hands,

            System.out.println("Total value of cards in dealer's hand: " + dealerHand.cardsValue());

            // Is Dealer busted

            if((dealerHand.cardsValue() > 21) && !endRound){
                System.out.println("Dealer busted!! Player won!");
                blackjack.setWins(1); // update playerWins

                // If dealer gets busted, player gets twice the bet amount.
                playerMoney += (2 * playerBet);
                endRound = true;
            }

            // Check tie
            if((playerHand.cardsValue() == dealerHand.cardsValue()) && !endRound){
                System.out.println("It's a tie");
                System.out.println("PUSH");
                blackjack.setPushes(1);
                endRound = true;
            }

            // Check to see if dealer card value is more than player.


            if(dealerHand.cardsValue() < playerHand.cardsValue() && !endRound){
                System.out.println("Player won!!");
                // If player card value is greater than the dealer, the player gets twice the bet amount.
                playerMoney += (2 * playerBet);
                blackjack.setWins(1);
                endRound = true;
            }

            if(dealerHand.cardsValue()> playerHand.cardsValue() && !endRound) {
                System.out.println("Dealer won!!");
                blackjack.setLosses(1);
                // If dealer card value is greater than player card value, the player loses the bet amount.
                playerMoney -= playerBet;

            }

            // Move both player & dealer cards back to the playing deck.
            playerHand.moveAllToDeck(playingDeck);
            dealerHand.moveAllToDeck(playingDeck);
            // Shuffle the deck .
            playingDeck.shuffleDeck();
            System.out.println("End of the Round " + roundCount + "!!");

            // Display the player's wins, losses and pushes.

            System.out.println("Wins: " + blackjack.getWins());
            System.out.println("Losses: " + blackjack.getLosses());
            System.out.println("Tie " + blackjack.getPushes());
            System.out.println("Player remaining amount $" + playerMoney);
            System.out.println("Do you want to play another round? \n Press 1 for YES \n Press 2 for No");
            int res = input.nextInt();
            if(res==1){
                continue;
            }else if(res == 2){
                break;
            }else{
                System.out.println("Invalid choice. See you again later.");
                break;
            }

        }

        System.out.println("Game Over!! Try again later!!");

    }
}