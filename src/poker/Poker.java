/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poker;

import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Jean
 */
public class Poker {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Dealer d = new Dealer();
        

        

        cardGenerator();
        Player p1 = new Player();
        Player p2 = new Player();

        String handEditorOption = JOptionPane.showInputDialog(null, "Would you like to go into editor mode?");

        if (handEditorOption.equalsIgnoreCase("y")) {
            editorMode(p1,p2,d);
        }
        else{
            simulationMode(p1,p2,d);
        }

        

    }
    
    public static void simulationMode(Player p1, Player p2, Dealer d){
        int runtimeCounter = 0;
        boolean running = true;
        
        while (running) {
            
            d.shuffle();
            d.displayDeck();
            System.out.println("_____________________________________");

            try {               
                    p1.drawCard();
                    p2.drawCard();

                    p1.drawCard();
                    p2.drawCard();

                    System.out.println("Player 1 hand:");
                    p1.displayHand();

                    System.out.println("_____________________________________");

                    System.out.println("Player 2 hand:");
                    p2.displayHand();

                    System.out.println("_____________________________________");

                    System.out.println("");
                    d.setFlop();
                    System.out.println("Here is the flop!");
                    d.displayFlop();

                    System.out.println("");

                    d.setTurn();
                    System.out.println("The Turn!");
                    d.displayTurn();

                    System.out.println("");

                    d.setRiver();
                    System.out.println("The River!");

                    d.displayRiver();
                    System.out.println("_____________________________________");
                    System.out.println("");
                

                if (d.checkHighCard(p1.getHand(), p2.getHand()) == 1) {
                    System.out.println("Player 1 has the highest single card!");
                } else if (d.checkHighCard(p1.getHand(), p2.getHand()) == 2) {
                    System.out.println("Player 2 has the highest single card!");
                } else {
                    System.out.println("It's a tie!");
                }

                d.checkPairs(p1.getHand(), p1);
                d.checkPairs(p2.getHand(), p2);
                //System.out.println("1");

                d.checkOfKind(p1.getHand(), p1);
                d.checkOfKind(p2.getHand(), p2);
                //System.out.println("1");

                d.checkFlush(p1.getHand(), p1);
                d.checkFlush(p2.getHand(), p2);
                //System.out.println("1");

                d.checkStraight(p1.getHand(), p1);
                d.checkStraight(p2.getHand(), p2);
                //System.out.println("1");

                d.checkFullHouse(p1);
                d.checkFullHouse(p2);
                //System.out.println("1");

                System.out.println("Player 1: " + p1.isRoyalFlush() + ", Straight Flush: " + p1.isStraightFlush() + ", Full House: " + p1.isFullHouse() + ", Straight: " + p1.isStraight() + ", Flush: " + p1.isFlush() + ", Four of a kind: " + p1.isFourOfAKind() + ", Three of a kind: " + p1.isThreeOfAKind() + " , Two pair = " + p1.isTwoPairs() + ", Pair = " + p1.isPairs());
                System.out.println("Player 2: " + p2.isRoyalFlush() + ", Straight Flush: " + p2.isStraightFlush() + ", Full House: " + p2.isFullHouse() + ", Straight: " + p2.isStraight() + ", Flush: " + p2.isFlush() + ", Four of a kind: " + p2.isFourOfAKind() + ", Three of a kind: " + p2.isThreeOfAKind() + " , Two pair = " + p2.isTwoPairs() + ", Pair = " + p2.isPairs());

                if (p1.isStraightFlush()||p2.isStraightFlush()) {
                    runtimeCounter++;
                    running = false;
                }

                d.returnHand(p1);
                d.returnHand(p2);
                d.returnCommunityCards();
                System.out.println(d.getDeck().size());
                runtimeCounter++;

            } catch (NullPointerException e) {
                System.out.println(e);
            }

            System.out.println(runtimeCounter);

        }
    }
    
    public static void editorMode(Player p1, Player p2, Dealer d){
        d.pickCards(p1);
            d.pickCards(p2);

            System.out.println("Player 1 hand:");
            p1.displayHand();

            System.out.println("_____________________________________");

            System.out.println("Player 2 hand:");
            p2.displayHand();

            System.out.println("_____________________________________");

            d.pickCommunity();

            System.out.println("Here is the flop!");
            d.displayFlop();

            System.out.println("");

            System.out.println("The Turn!");
            d.displayTurn();

            System.out.println("");

            System.out.println("The River!");
            d.displayRiver();

            System.out.println("_____________________________________");
            System.out.println("");
            
            if (d.checkHighCard(p1.getHand(), p2.getHand()) == 1) {
                    System.out.println("Player 1 has the highest single card!");
                } else if (d.checkHighCard(p1.getHand(), p2.getHand()) == 2) {
                    System.out.println("Player 2 has the highest single card!");
                } else {
                    System.out.println("It's a tie!");
                }

                d.checkPairs(p1.getHand(), p1);
                d.checkPairs(p2.getHand(), p2);
                //System.out.println("1");

                d.checkOfKind(p1.getHand(), p1);
                d.checkOfKind(p2.getHand(), p2);
                //System.out.println("1");

                d.checkFlush(p1.getHand(), p1);
                d.checkFlush(p2.getHand(), p2);
                //System.out.println("1");

                d.checkStraight(p1.getHand(), p1);
                d.checkStraight(p2.getHand(), p2);
                //System.out.println("1");

                d.checkFullHouse(p1);
                d.checkFullHouse(p2);
                //System.out.println("1");

                System.out.println("Player 1: " + p1.isRoyalFlush() + ", Straight Flush: " + p1.isStraightFlush() + ", Full House: " + p1.isFullHouse() + ", Straight: " + p1.isStraight() + ", Flush: " + p1.isFlush() + ", Four of a kind: " + p1.isFourOfAKind() + ", Three of a kind: " + p1.isThreeOfAKind() + " , Two pair = " + p1.isTwoPairs() + ", Pair = " + p1.isPairs());
                System.out.println("Player 2: " + p2.isRoyalFlush() + ", Straight Flush: " + p2.isStraightFlush() + ", Full House: " + p2.isFullHouse() + ", Straight: " + p2.isStraight() + ", Flush: " + p2.isFlush() + ", Four of a kind: " + p2.isFourOfAKind() + ", Three of a kind: " + p2.isThreeOfAKind() + " , Two pair = " + p2.isTwoPairs() + ", Pair = " + p2.isPairs());

                d.returnHand(p1);
                d.returnHand(p2);
                d.returnCommunityCards();
                System.out.println(d.getDeck().size());
    }

    //Gives each royal card their respective name
    public static String cardName(Integer value) {
        if (value == 14) {
            return "Ace";
        } else if (value == 11) {
            return "Jack";
        } else if (value == 12) {
            return "Queen";
        } else if (value == 13) {
            return "King";
        } else {
            return value.toString();
        }

    }

    //Generates all 52 cards and sends them to the Dealer to stack them in the deck
    public static void cardGenerator() {
        Dealer d = new Dealer();

        for (int s = 1; s <= 4; s++) {
            for (int v = 2; v < 15; v++) {
                if (s == 1) {
                    String suit = "Hearts";
                    int value = v;
                    d.makeCard(suit, value);
                } else if (s == 2) {
                    String suit = "Diamonds";
                    int value = v;
                    d.makeCard(suit, value);
                } else if (s == 3) {
                    String suit = "Clubs";
                    int value = v;
                    d.makeCard(suit, value);
                } else if (s == 4) {
                    String suit = "Spades";
                    int value = v;
                    d.makeCard(suit, value);
                }
            }
        }
    }

}
