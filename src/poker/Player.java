/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poker;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Jean
 */
public class Player {
    
    //The player's hand, the limit of the hand, and the dealer object
    private ArrayList<Card> hand;
    private static final int HAND_LIMIT = 2;
    private Dealer d = new Dealer();
    
    //Stuff I haven't implemented yet
    private float cash;
    private int winPrecedence;
    private int pairStrength;
    private int kindStrength;
    
    //Determines the name of each player that is generated
    private String name;
    private static int number = 1;
    
    //Flags for hand rankings
    private boolean highCard;
    private boolean pairs;
    private boolean twoPairs;
    private boolean threeOfAKind;
    private boolean fourOfAKind;
    private boolean flush;
    private boolean straight;
    private boolean fullHouse;
    private boolean straightFlush;
    private boolean royalFlush;
    
    //Counters
    private int highCounter;
    private int pairCounter;
    private int twoPairCounter;
    private int threeOfAKindCounter;
    private int fourOfAKindCounter;
    private int flushCounter;
    private int straightCounter;
    private int fullHouseCounter;
    private int straightFlushCounter;
    private int royalFlushCounter;
    
    //Constructor
    public Player(){
        this.cash = 500;
        this.winPrecedence = 0;
        this.pairStrength = 0;
        this.kindStrength = 0;
        this.name = "Player "+number;
        this.number++;
        
        this.highCounter = 0;
        this.pairCounter = 0;
        this.twoPairCounter = 0;
        this.threeOfAKindCounter = 0;
        this.fourOfAKindCounter = 0;
        this.flushCounter = 0;
        this.straightCounter = 0;
        this.fullHouseCounter = 0;
        this.straightFlushCounter = 0;
        this.royalFlushCounter = 0;
        
        hand = new ArrayList<>();
    }

    
    //Whole bunch of accessors and mutators for the instance variables
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public int getHighCounter() {
        return highCounter;
    }

    public void setHighCounter(int highCounter) {
        this.highCounter = highCounter;
    }

    public int getPairCounter() {
        return pairCounter;
    }

    public void setPairCounter(int pairCounter) {
        this.pairCounter = pairCounter;
    }

    public int getTwoPairCounter() {
        return twoPairCounter;
    }

    public void setTwoPairCounter(int twoPairCounter) {
        this.twoPairCounter = twoPairCounter;
    }

    public int getThreeOfAKindCounter() {
        return threeOfAKindCounter;
    }

    public void setThreeOfAKindCounter(int threeOfAKindCounter) {
        this.threeOfAKindCounter = threeOfAKindCounter;
    }

    public int getFourOfAKindCounter() {
        return fourOfAKindCounter;
    }

    public void setFourOfAKindCounter(int fourOfAKindCounter) {
        this.fourOfAKindCounter = fourOfAKindCounter;
    }

    public int getFlushCounter() {
        return flushCounter;
    }

    public void setFlushCounter(int flushCounter) {
        this.flushCounter = flushCounter;
    }

    public int getStraightCounter() {
        return straightCounter;
    }

    public void setStraightCounter(int straightCounter) {
        this.straightCounter = straightCounter;
    }

    public int getFullHouseCounter() {
        return fullHouseCounter;
    }

    public void setFullHouseCounter(int fullHouseCounter) {
        this.fullHouseCounter = fullHouseCounter;
    }

    public int getStraightFlushCounter() {
        return straightFlushCounter;
    }

    public void setStraightFlushCounter(int straightFlushCounter) {
        this.straightFlushCounter = straightFlushCounter;
    }

    public int getRoyalFlushCounter() {
        return royalFlushCounter;
    }

    public void setRoyalFlushCounter(int royalFlushCounter) {
        this.royalFlushCounter = royalFlushCounter;
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
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isHighCard(){
        return highCard;
    }
    
    public void setHighCard(boolean result){
        highCard = result;
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

    public boolean isRoyalFlush() {
        return royalFlush;
    }

    public void setRoyalFlush(boolean royalFlush) {
        this.royalFlush = royalFlush;
    }
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
 
    
    public void drawCard(){
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
    
    public void addToCounters(){
        if(highCard){
            gotHighCard();
        }
        
        if(pairs){
            gotPair();
        }
        
        if(twoPairs){
            gotTwoPair();
        }
        
        if(threeOfAKind){
            gotThree();
        }
        
        if(fourOfAKind){
            gotFour();
        }
        
        if(flush){
            gotFlush();
        }
        
        if(straight){
            gotStraight();
        }
        
        if(fullHouse){
            gotFullHouse();
        }
        
        if(straightFlush){
            gotStraightFlush();
        }
        
        if(royalFlush){
            gotRoyalFlush();
        }
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void clearHand(){
        hand.clear();
    }

    public void takeCard(Card c){
        hand.add(c);
    }
    
    
    //Counters that keep track of all the hand ranks each player won in a simulation
    public void gotHighCard(){
        highCounter++;
    }
    
    public void gotPair(){
        pairCounter++;
    }
    
    public void gotTwoPair(){
        twoPairCounter++;
    }
    
    public void gotThree(){
        threeOfAKindCounter++;
    }
    
    public void gotFour(){
        fourOfAKindCounter++;
    }
    
    public void gotFlush(){
        flushCounter++;
    }
    
    public void gotStraight(){
        straightCounter++;
    }
    
    public void gotFullHouse(){
        fullHouseCounter++;
    }
    
    public void gotStraightFlush(){
        straightFlushCounter++;
    }
    
    public void gotRoyalFlush(){
        royalFlushCounter++;
    }
    
    public void resetCounters(){
        this.highCounter = 0;
        this.pairCounter = 0;
        this.twoPairCounter = 0;
        this.threeOfAKindCounter = 0;
        this.fourOfAKindCounter = 0;
        this.flushCounter = 0;
        this.straightCounter = 0;
        this.fullHouseCounter = 0;
        this.straightFlushCounter = 0;
        this.royalFlushCounter = 0;
    }
    
    public void displayPlayerStats(){
        JOptionPane.showMessageDialog(null, name+" got the following:\n" +highCounter+" high card wins\n" +pairCounter+ " pair wins\n"
        +twoPairCounter+ " two pair wins\n" +threeOfAKindCounter+ " three of a kind wins\n" +fourOfAKindCounter+ " four of a kind wins\n"
        +flushCounter+ " flush wins\n" +straightCounter+ " straight wins\n" +fullHouseCounter+ " full house wins\n" 
        +straightFlushCounter+ " straight flush wins\n" +royalFlushCounter+ " royal flush wins");
    }
    
    
    
    
    
    
    
    

   
    
    
    

    
    
    
    
    
    
    
    
}
