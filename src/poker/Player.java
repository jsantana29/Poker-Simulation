/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poker;

import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public class Player {
    private ArrayList<Card> hand;
    private static final int HAND_LIMIT = 2;
    private Dealer d = new Dealer();
    
    private float cash;
    private int winPrecedence;
    private int pairStrength;
    private int kindStrength;
    private String name;
    private static int number = 1;
    
    private boolean pairs;
    private boolean twoPairs;
    private boolean threeOfAKind;
    private boolean fourOfAKind;
    private boolean flush;
    private boolean straight;
    private boolean fullHouse;
    private boolean straightFlush;
    
    public Player(){
        this.cash = 500;
        this.winPrecedence = 0;
        this.pairStrength = 0;
        this.kindStrength = 0;
        this.name = "Player "+number;
        this.number++;
        hand = new ArrayList<>();
    }
    
    public void drawHand(){
        try{
            Card playerCards = d.drawCard();
            hand.add(playerCards);  
        }
        catch(NullPointerException e){
            System.out.println(e);
        }        
    }
    
   //Displays the player's hand
    public void displayHand(){
        System.out.println(Poker.cardName(hand.get(0).getValue())+" of "+hand.get(0).getSuit());
        System.out.println(Poker.cardName(hand.get(1).getValue())+" of "+hand.get(1).getSuit());
        
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getPairStrength() {
        return pairStrength;
    }

    public void setPairStrength(int pairStrength) {
        this.pairStrength = pairStrength;
    }

    public int getKindStrength() {
        return kindStrength;
    }

    public void setKindStrength(int kindStrength) {
        this.kindStrength = kindStrength;
    }
    
    
    public void clearHand(){
        hand.clear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPairs() {
        return pairs;
    }

    public void setPairs(boolean pairs) {
        this.pairs = pairs;
    }

    public boolean isTwoPairs() {
        return twoPairs;
    }

    public void setTwoPairs(boolean twoPairs) {
        this.twoPairs = twoPairs;
    }

    public boolean isThreeOfAKind() {
        return threeOfAKind;
    }

    public void setThreeOfAKind(boolean threeOfAKind) {
        this.threeOfAKind = threeOfAKind;
    }

    public boolean isFourOfAKind() {
        return fourOfAKind;
    }

    public void setFourOfAKind(boolean fourOfAKind) {
        this.fourOfAKind = fourOfAKind;
    }

    public boolean isFlush() {
        return flush;
    }

    public void setFlush(boolean flush) {
        this.flush = flush;
    }

    public boolean isStraight() {
        return straight;
    }

    public void setStraight(boolean straight) {
        this.straight = straight;
    }

    public boolean isFullHouse() {
        return fullHouse;
    }

    public void setFullHouse(boolean fullHouse) {
        this.fullHouse = fullHouse;
    }

    public boolean isStraightFlush() {
        return straightFlush;
    }

    public void setStraightFlush(boolean straightFlush) {
        this.straightFlush = straightFlush;
    }
    
    
    
    
    
    

   
    
    
    

    
    
    
    
    
    
    
    
}
