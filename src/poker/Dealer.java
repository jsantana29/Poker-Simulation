/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import javax.swing.JOptionPane;

/**
 *
 * @author Jean
 */
public class Dealer {
    private static Stack<Card> deck = new Stack();
    private ArrayList<Card> flop;
    private ArrayList<Card> communityCards;
    private ArrayList<Card> totalValuePool;
    private ArrayList<Card> straight;
    private Card turn;
    private Card river;
    
    private static final int END_OF_POOL = 100;
    
    public Dealer() {
        flop = new ArrayList<>();
        communityCards = new ArrayList<>();
        totalValuePool = new ArrayList<>();
        straight = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Deck{" + "deck=" + deck + '}';
    }
    
    //Makes a card object and puts it inside the deck
    public void makeCard(String suit, int value){
         Card card = new Card(suit, value);
         fillDeck(card);
    }
    
    public void fillDeck(Card cardsInDeck){
        deck.push(cardsInDeck);
    }
    
    //Displays the entire deck
    public void displayDeck(){
        int fullDeckCount = deck.size();
        for(int i = 0; i<fullDeckCount;i++){
            System.out.println(Poker.cardName(deck.get(i).getValue())+" of "+deck.get(i).getSuit());              
        }
    }
    
    //Shuffles the entire deck
    public void shuffle(){
        Random r = new Random();
        Stack<Card> shuffleDeck = new Stack();
        int fullDeckCount = deck.size();
        
        for(int i = 0; i < fullDeckCount; i++){
            int randomCard = r.nextInt(deck.size());
            shuffleDeck.push(deck.get(randomCard));
            deck.remove(randomCard);           
        }
        deck = shuffleDeck;
    }
    
    //Draws a card from the top of the deck
    public Card drawCard(){
        Card drawnCard = deck.pop();
        return drawnCard;
    }
    
     //Draws the three cards that make up the flop
    public void setFlop(){
        for(int i = 1; i <= 3;i++){
            flop.add(drawCard());
        }
    }
    
    //Randomly draws the card that represents the turn from the top of the deck
    public void setTurn(){
        turn = drawCard();
    }
    
    //Randomly draws the card that represents the river from the top of the deck
    public void setRiver(){
        river = drawCard();
    }
    
    //Displays the flop
    public void displayFlop(){
        for(int i = 0; i < flop.size(); i++){
            System.out.println(Poker.cardName(flop.get(i).getValue())+" of "+flop.get(i).getSuit());
        }
    }
    
    public void displayTurn(){
        System.out.println(Poker.cardName(turn.getValue())+" of "+turn.getSuit());
    }
    
    public void displayRiver(){
        System.out.println(Poker.cardName(river.getValue())+" of "+river.getSuit());
    }
    
    //Gives the Ace card its proper value for this game
    public int checkAceValue(int value){
        if(value == 1){
            return value + 13;
        }
        return value;
    }
    
    //Pools together all of the community cards(flop, turn, and river)
    public void poolCommunityCards(ArrayList<Card>flop,Card turn, Card river){
        for(int i = 0; i<flop.size(); i++){
            communityCards.add(flop.get(i));
        }
        communityCards.add(turn);
        communityCards.add(river);
    }
    
    //Checks which player has the highest single card value
    public int checkHighCard(ArrayList<Card>hand1, ArrayList<Card>hand2){
        final int TOTAL_CARDS = 4;
        ArrayList<Integer> scorePool = new ArrayList<>();
        
        int hand1ScoreValue1 = checkAceValue(hand1.get(0).getValue());
        scorePool.add(hand1ScoreValue1);
        int hand1ScoreValue2 = checkAceValue(hand1.get(1).getValue());
        scorePool.add(hand1ScoreValue2);
        
        int hand2ScoreValue1 = checkAceValue(hand2.get(0).getValue());
        scorePool.add(hand2ScoreValue1);
        int hand2ScoreValue2 = checkAceValue(hand2.get(1).getValue());
        scorePool.add(hand2ScoreValue2);
        
        int highest = 0;
        int secondHighest = 0;
        
        for(int i = 0; i < scorePool.size(); i++){
            if(scorePool.get(i)> highest){
                highest = scorePool.get(i);
            }
        }
        
        scorePool.remove(scorePool.indexOf(highest));
        
        for(int i = 0; i < scorePool.size(); i++){
            if(scorePool.get(i)> secondHighest){
                secondHighest = scorePool.get(i);
            }
        }
        
        
        if(highest == hand1ScoreValue1 || highest == hand1ScoreValue2 && highest != hand1ScoreValue1 || highest != hand1ScoreValue2){
            return 1;
        }
        else if(highest == hand2ScoreValue1 || highest == hand2ScoreValue2 && highest != hand1ScoreValue1 || highest != hand1ScoreValue2){
            return 2;
        }
        else{
            if(secondHighest == hand1ScoreValue1 || secondHighest == hand1ScoreValue2 && secondHighest != hand2ScoreValue1 || secondHighest != hand2ScoreValue2){
                return 1;
            }
            else if(secondHighest == hand2ScoreValue1 || secondHighest == hand2ScoreValue2 && secondHighest != hand1ScoreValue1 || secondHighest != hand1ScoreValue2){
                return 2;
            }
            else{
                return 3;
            }
        }
    }
    
    //Checks to see if the player has pairs
    public void checkPairs(ArrayList<Card>hand, Player p){
        int pairCounter = 0; //Counts how many pairs have been formed using the player's hand
        int communityPair = 0; //Counts how many pairs have been formed using only the community pool
        int totalPairs = 0; // Counts the total amount of pairs that have been formed
        
        final int firstCard = 0;
        final int secondCard = 1;
        
        
        poolCommunityCards(flop,turn,river);
        
        
        pairCounter = checkHandPair(hand,p,pairCounter);
        
        if(pairCounter < 1){
            pairCounter = checkCombinedPair(hand.get(firstCard), p,pairCounter);
            pairCounter = checkCombinedPair(hand.get(secondCard), p,pairCounter);
        }
        
        communityPair = checkCommunityPair(p,pairCounter,communityPair);
        

        poolCommunityCards(flop,turn,river);
        totalPairs = calculateTotalPairs(pairCounter,communityPair);
        
        if(totalPairs > 0){
            if(totalPairs > 1){
                p.setTwoPairs(true);
            }
            else{
                p.setTwoPairs(false);
            }
            clearCommunityCards();
            p.setPairs(true);
        }
        else{            
            clearCommunityCards();
            p.setPairs(false);                  
        }
    }
    
    //Checks if the player already has a pair in their hand
    public int checkHandPair(ArrayList<Card>hand, Player p, int pairCounter){
        if(hand.get(0).getValue() == hand.get(1).getValue()){
            pairCounter++;
            if(hand.get(0).getValue() > p.getPairStrength()){
                p.setPairStrength(checkAceValue(hand.get(0).getValue()));
            }
            
        }
        return pairCounter;
    }
    
    //Checks if a card from the player's hand can form a pair with a card from the community pool
    public int checkCombinedPair(Card card, Player p, int handPairCounter){
        for(int i = 0; i < communityCards.size(); i++){
            if(card.getValue() == communityCards.get(i).getValue()){
                handPairCounter++;
                communityCards.remove(i);
                if(card.getValue() > p.getPairStrength()){
                    p.setPairStrength(checkAceValue(card.getValue()) );
                }
                break;
            }
            
        }
            
        return handPairCounter;
    }
    
    //Checks if there is already a pair inside the community pool
    public int checkCommunityPair(Player p, int handPairCounter, int communityPair){
        for(int i = 0; i < communityCards.size(); i++){
            Card check = communityCards.remove(i);
            for(int j = 0; j < communityCards.size();j++){
                if(check.getValue() == communityCards.get(j).getValue()){
                    communityPair++;
                    if(check.getValue()>p.getPairStrength() && handPairCounter > 0){
                        p.setPairStrength(checkAceValue(check.getValue()));
                    }
                    communityCards.remove(j);
                    break;
                }
            }
            
        }
        return communityPair;
    }
    
    //Counts all of the valid pairs the player has created using both his hand and the community pool
    public int calculateTotalPairs(int handPairCounter, int communityPair){
        if(handPairCounter > 0){
            return handPairCounter + communityPair;
        }
        else{
            return handPairCounter;
        }
    }
    
    //Checks if the player has formed three or more cards of the same value using their hand and the community pool
    public void checkOfKind(ArrayList<Card>hand, Player p){
        int kindCounter = 1;
        final int firstCard = 0;
        final int secondCard = 1;
        
        poolCommunityCards(flop,turn,river);
        
        kindCounter = checkHandKind(hand, p, kindCounter);
        
        if(kindCounter < 2){
            kindCounter = checkCombinedKind(hand.get(firstCard), p, kindCounter);
            kindCounter = checkCombinedKind(hand.get(secondCard), p, kindCounter);
        }
        else{
            kindCounter = checkCombinedKind(hand.get(firstCard), p, kindCounter);
        }
        
        if(kindCounter % 3 == 0){
            p.setThreeOfAKind(true);
            clearCommunityCards();
        }
        else if(kindCounter % 4 == 0){
            p.setFourOfAKind(true);
            clearCommunityCards();
        }
        else{
            p.setThreeOfAKind(false);
            p.setFourOfAKind(false);
            clearCommunityCards();
        }
           
    }
    
    //Checks if both cards in the player's hand have the same value
    public int checkHandKind(ArrayList<Card>hand, Player p, int kindCounter){
         if(hand.get(0).getValue() == hand.get(1).getValue()){
            kindCounter ++;
            
        }
         return kindCounter;
    }
    
    //Checks if a card from the player's hand has the same value as the ones in the community pool
    public int checkCombinedKind(Card card, Player p, int kindCounter){
        int ofAKind = kindCounter;
        
        if(ofAKind > 2){
            ofAKind = 1;
        }
        
        for(int i = 0; i < communityCards.size(); i++){
            if(card.getValue() == communityCards.get(i).getValue()){
                ofAKind++;
                if(card.getValue() > p.getKindStrength() && ofAKind >=3){
                    p.setKindStrength(checkAceValue(card.getValue()));
                }

            }
            
        }
       
        //If true, then no three or four of a kind was ever found. Else, it was found and the counter is updated
        if(ofAKind < 3){
            return kindCounter;
        }
        else{
            kindCounter = ofAKind;
            return kindCounter;
        }
        
    }
    
    //Checks if at least five cards from both the player's hand and the community pool have the same suit
    public void checkFlush(ArrayList<Card>hand, Player p){
        int flushCounter = 1;
        final int firstCard = 0;
        final int secondCard = 1;
        
        poolCommunityCards(flop,turn,river);
        
        flushCounter = checkHandFlush(hand, flushCounter);
        
        if(flushCounter < 2){
            flushCounter = checkCombinedFlush(hand.get(firstCard), p, flushCounter);
            flushCounter = checkCombinedFlush(hand.get(secondCard), p, flushCounter);
        }
        else{
            flushCounter = checkCombinedFlush(hand.get(firstCard), p, flushCounter);
        }
        
        if(flushCounter >= 5){
            p.setFlush(true);
            clearCommunityCards();
        }
        else{
            p.setFlush(false);
            clearCommunityCards();
        }    
        
    }
    
    //Checks if both cards from the player's hand have the same suit
    public int checkHandFlush(ArrayList<Card>hand, int flushCounter){
        if(hand.get(0).getSuit().equalsIgnoreCase(hand.get(1).getSuit())){
            flushCounter ++;
            
        }
         return flushCounter;
    }
    
    //Compares a card's suit from the player's hand to the entire communtiy pool     
    public int checkCombinedFlush(Card card, Player p, int flushCounter){
        int miniFlushCounter = flushCounter;
        
        if(miniFlushCounter > 2){
            miniFlushCounter = 1;
        }
        
               
        for(int i = 0; i < communityCards.size(); i++){
            if(card.getSuit().equalsIgnoreCase(communityCards.get(i).getSuit())){
                miniFlushCounter++;

            }
            
        }
       
        if(miniFlushCounter < 5){
            return flushCounter;
        }
        else{
            flushCounter = miniFlushCounter;
            return flushCounter;
        }
    }
    
    //Checks if at least five cards have consecutive card values(ex. 5,6,7,8,9)
    public void checkStraight(ArrayList<Card>hand, Player p){
        final int firstCard = 0;
        final int secondCard = 1;
        
        poolCommunityCards(flop,turn,river);
        
        if(!verifyStraightChance(hand.get(firstCard)) && !verifyStraightChance(hand.get(secondCard))){
            p.setStraight(false);
            clearCommunityCards();
            clearTotalValuePool();
        }
        else{
            p.setStraight(verifyStraight(hand,p));
            clearCommunityCards();
            clearTotalValuePool();
        }
        
        
    }
    
    //Checks if any card in the player's hand has a chance of forming a straight with the community pool
    public boolean verifyStraightChance(Card card){
        for(int i = 0; i < communityCards.size(); i++){
            if(card.getValue() + 1 == communityCards.get(i).getValue() || card.getValue() - 1 == communityCards.get(i).getValue()){
                return true;
            }
        }
        return false;
    }
    
    //Checks if the straight between the player's hand and the community pool is actually formed
    public boolean verifyStraight(ArrayList<Card>hand, Player p){
        int straightCounter = 0;
        
        fillTotalValuePool(hand);
        Collections.sort(totalValuePool);
        
        for(int i = 0; i < totalValuePool.size() - 1; i++){
            System.out.println(totalValuePool.get(i)+ " " + p.getName());
            if(totalValuePool.get(i).getValue() + 1 == totalValuePool.get(i + 1).getValue()){
                    straight.add(totalValuePool.get(i));
                    
                    if(i == totalValuePool.size() - 1){
                        straight.add(totalValuePool.get(i+1));
                        straightCounter++;
                    }
                    
                    straightCounter++;
                    
   
                }
            else{
                straightCounter = 0;
                clearStraight();
            }
        }
        
        
        if(straightCounter == 5){            
            return true;
        }
        else if(straightCounter == 6){
            straight.remove(0);
            return true;
        }
        else if(straightCounter == 7){
            straight.remove(0);
            straight.remove(1);
            return true;
        }
        else{
            return false;
        }
  
    }
    
    //Checks if a full house is formed(a pair with a three of a kind)
    public void checkFullHouse(Player p){
        if(p.isPairs() && p.isThreeOfAKind() && p.getPairStrength() != p.getKindStrength()){
            p.setFullHouse(true);
        }
        else{
            p.setFullHouse(false);
        }
    }
    
    public void checkStraightFlush(ArrayList<Card>hand,Player p){
        poolCommunityCards(flop,turn,river);
        int flushCounter = 0;
        
        
        if(straight.size() > 0){
             String lastSuit = straight.get(0).getSuit();
             
             for(int i = 0; i < straight.size(); i++){
                if(straight.get(i).getSuit().contains(lastSuit)){
                    flushCounter++;

                }
               
            }
             System.out.println(straight.size());
        }
        
    
            if(flushCounter >= 5){
                p.setStraightFlush(true);
                clearStraight();
            }
            else{
                p.setStraightFlush(false);
                clearStraight();
            }
        }
    
    
    
    //Pools together all the values in the player's hand with all the values inside the community pool
    public void fillTotalValuePool(ArrayList<Card>hand){
        for(int i = 0; i < hand.size(); i++){
            totalValuePool.add(hand.get(i));
        }
        
        for(int i = 0; i < communityCards.size(); i++){
            totalValuePool.add(communityCards.get(i));
        }
        

    }
    
    
    //Clears the entire deck
    public void clearDeck(){
        deck.clear();
    }
    
    //Clears the flop
    public void clearFlop(){
        flop.clear();
    }
    
    //Clears the entire commmunity pool
    public void clearCommunityCards(){
        communityCards.clear();
    }
    
    //Clears the entire total value pool
    public void clearTotalValuePool(){
        totalValuePool.clear();
    }
    
    
    public void clearStraight(){
        straight.clear();
    }

    
    
       
}
