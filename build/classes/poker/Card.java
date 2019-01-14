/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package poker;

/**
 *
 * @author Jean
 */
public class Card implements Comparable {
    private String suit;
    private int value;

    public Card(String suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    @Override
    public String toString() {
        return "CardsInDeck{" + "suit=" + suit + ", value=" + value + '}';
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public int compareTo(Object t) {
        int compareValue = ((Card)t).getValue();
        return this.value-compareValue;
    }

    
    
    
    
    
    
}
