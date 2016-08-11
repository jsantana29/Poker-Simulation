/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poker;

import java.io.IOException;
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
        int runtimeCounter = 0;
        
        boolean running = true;
        
        while(running){
            cardGenerator();
            d.shuffle();
            d.displayDeck();
            System.out.println("_____________________________________");
        
            try{
                Player p1 = new Player();
                Player p2 = new Player();
                
                System.out.println("Player 1 hand:");
                p1.drawHand();            
                p1.displayHand();
            
                System.out.println("_____________________________________");
            
                System.out.println("Player 2 hand:");
                p2.drawHand();           
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
                
                if(d.checkHighCard(p1.getHand(), p2.getHand()) == 1){
                    System.out.println("Player 1 has the highest single card!");
                }
                else if(d.checkHighCard(p1.getHand(), p2.getHand()) == 2){
                    System.out.println("Player 2 has the highest single card!");
                }
                else{
                    System.out.println("It's a tie!");
                }
                
                d.checkPairs(p1.getHand(), p1);
                d.checkPairs(p2.getHand(), p2);
                
                d.checkOfKind(p1.getHand(), p1);
                d.checkOfKind(p2.getHand(), p2);
                
                d.checkFlush(p1.getHand(), p1);
                d.checkFlush(p2.getHand(), p2);
                
                d.checkStraight(p1.getHand(), p1);
                d.checkStraight(p2.getHand(), p2);
                
                d.checkFullHouse(p1);
                d.checkFullHouse(p2);
                
                System.out.println("Player 1: Full House: "+p1.isFullHouse()+", Straight: "+p1.isStraight()+", Flush: "+p1.isFlush()+", Four of a kind: "+p1.isFourOfAKind()+", Three of a kind: "+p1.isThreeOfAKind()+" , Two pair = "+p1.isTwoPairs()+", Pair = "+p1.isPairs());
                System.out.println("Player 2: Full House: "+p2.isFullHouse()+", Straight: "+p2.isStraight()+", Flush: "+p2.isFlush()+", Four of a kind: "+p2.isFourOfAKind()+", Three of a kind: "+p2.isThreeOfAKind()+" , Two pair = "+p2.isTwoPairs()+", Pair = "+p2.isPairs());
                
//                System.out.println(d.checkPairs(p1.getHand(),p1.getPairStrength(),p1.getName()));
//                System.out.println(d.checkPairs(p2.getHand(),p2.getPairStrength(),p2.getName()));
                
                //String option = JOptionPane.showInputDialog("Press any button to play again\nPress n to exit");
                
                
                if(p1.isFullHouse() || p2.isFullHouse()){
                    runtimeCounter++;
                    System.out.println(runtimeCounter);
                    running = false;
                }
                p1.clearHand();
                p2.clearHand();
                d.clearDeck();
                d.clearFlop();
                d.clearCommunityCards();
                runtimeCounter++;
  
            }
            catch(NullPointerException e){
                System.out.println(e);
            }
            
            
            
        }
        
    }
    
    public static String cardName(Integer value){
        if(value == 1){
            return "Ace";
        }
        else if(value == 11){
            return "Jack";
        }
        else if(value == 12){
            return "Queen";
        }
        else if(value == 13){
            return "King";
        }
        else{
            return value.toString();
        }
             
    }
    
    public static void cardGenerator(){
        Dealer d = new Dealer();
                      
        for(int s = 1; s <= 4;s++){
            for(int v = 1; v < 14; v++){
                if(s == 1){
                    String suit = "Hearts";
                    int value = v;
                    d.makeCard(suit, value);
                }
                else if(s == 2){
                    String suit = "Diamonds";
                    int value = v;
                    d.makeCard(suit, value);
                }
                else if(s == 3){
                    String suit = "Clubs";
                    int value = v;
                    d.makeCard(suit, value);
                }
                else if(s == 4){
                    String suit = "Spades";
                    int value = v;
                    d.makeCard(suit, value);
                }
            }
        }
    }
    
}
