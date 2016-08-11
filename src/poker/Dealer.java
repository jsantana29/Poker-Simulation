/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poker;

import java.util.ArrayList;
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
    private ArrayList<Integer> totalValuePool;
    private Card turn;
    private Card river;
    
    private static final int END_OF_POOL = 100;
    
    public Dealer() {
        flop = new ArrayList<>();
        communityCards = new ArrayList<>();
        totalValuePool = new ArrayList<>();
    }
    
    @Override
    public String toString() {
        return "Deck{" + "deck=" + deck + '}';
    }
    
    public void makeCard(String suit, int value){
         Card card = new Card(suit, value);
         fillDeck(card);
    }
    
    public void fillDeck(Card cardsInDeck){
        deck.push(cardsInDeck);
    }
    
    public void displayDeck(){
        int fullDeckCount = deck.size();
        for(int i = 0; i<fullDeckCount;i++){
            System.out.println(Poker.cardName(deck.get(i).getValue())+" of "+deck.get(i).getSuit());              
        }
    }
    
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
    
    public Card drawCard(){
        Card drawnCard = deck.pop();
        return drawnCard;
    }
    
     
    public void setFlop(){
        for(int i = 1; i <= 3;i++){
            flop.add(drawCard());
        }
    }
    
    public void setTurn(){
        turn = drawCard();
    }
    
    public void setRiver(){
        river = drawCard();
    }
    
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
    
    public int checkAceValue(int value){
        if(value == 1){
            return value + 13;
        }
        return value;
    }
    
    public void poolCommunityCards(ArrayList<Card>flop,Card turn, Card river){
        for(int i = 0; i<flop.size(); i++){
            communityCards.add(flop.get(i));
        }
        communityCards.add(turn);
        communityCards.add(river);
    }
    
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
        
        for(int i = 0; i < TOTAL_CARDS; i++){
            if(scorePool.get(i)> highest){
                secondHighest = highest;
                highest = scorePool.get(i);
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
    
    public void checkPairs(ArrayList<Card>hand, Player p){
        int pairCounter = 0;
        int communityPair = 0;
        int totalPairs = 0;
        
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
    
    public int checkHandPair(ArrayList<Card>hand, Player p, int pairCounter){
        if(hand.get(0).getValue() == hand.get(1).getValue()){
            pairCounter++;
            if(hand.get(0).getValue() > p.getPairStrength()){
                p.setPairStrength(checkAceValue(hand.get(0).getValue()));
            }
            
        }
        return pairCounter;
    }
    
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
    
    public int calculateTotalPairs(int handPairCounter, int communityPair){
        if(handPairCounter > 0){
            return handPairCounter + communityPair;
        }
        else{
            return handPairCounter;
        }
    }
    
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
    
    public int checkHandKind(ArrayList<Card>hand, Player p, int kindCounter){
         if(hand.get(0).getValue() == hand.get(1).getValue()){
            kindCounter ++;
            
        }
         return kindCounter;
    }
    
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
       
        if(ofAKind < 3){
            return kindCounter;
        }
        else{
            kindCounter = ofAKind;
            return kindCounter;
        }
        
    }
    
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
    
    public int checkHandFlush(ArrayList<Card>hand, int flushCounter){
        if(hand.get(0).getSuit().equalsIgnoreCase(hand.get(1).getSuit())){
            flushCounter ++;
            
        }
         return flushCounter;
    }
    
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
    
    public boolean verifyStraightChance(Card card){
        for(int i = 0; i < communityCards.size(); i++){
            if(card.getValue() + 1 == communityCards.get(i).getValue() || card.getValue() - 1 == communityCards.get(i).getValue()){
                return true;
            }
        }
        return false;
    }
    
    public boolean verifyStraight(ArrayList<Card>hand, Player p){
        int straightCounter = 0;
        
        fillTotalPool(hand);
        totalValuePool.sort(null);
        
        for(int i = 0; i < totalValuePool.size(); i++){
            if(totalValuePool.get(i) == END_OF_POOL){
                break;
            }
            
            if(totalValuePool.get(i) + 1 == totalValuePool.get(i + 1)){
                    straightCounter++;
                }
            else if(totalValuePool.get(i+1) == END_OF_POOL){
                    straightCounter = straightCounter;
                }
            else{
                straightCounter = 0;
            }
        }
        
        if(straightCounter >= 4){
            return true;
        }
        else{
            return false;
        }
  
    }
    
    public void checkFullHouse(Player p){
        if(p.isPairs() && p.isThreeOfAKind() && p.getPairStrength() != p.getKindStrength()){
            p.setFullHouse(true);
        }
        else{
            p.setFullHouse(false);
        }
    }
    
    public void fillTotalPool(ArrayList<Card>hand){
        for(int i = 0; i < hand.size(); i++){
            totalValuePool.add(hand.get(i).getValue());
        }
        
        for(int i = 0; i < communityCards.size(); i++){
            totalValuePool.add(communityCards.get(i).getValue());
        }
        
        totalValuePool.add(END_OF_POOL);
    }
    
    
    
    
    
    
    public void clearDeck(){
        deck.clear();
    }
    
    public void clearFlop(){
        flop.clear();
    }
    
    public void clearCommunityCards(){
        communityCards.clear();
    }
    
    public void clearTotalValuePool(){
        totalValuePool.clear();
    }

    
    
       
}
