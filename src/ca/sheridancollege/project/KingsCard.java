/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;

/**
 *
 * @author arora
 */
public class KingsCard extends Card {
    private String suit;
    private String rank;
    private String color; // "Red" for Hearts/Diamonds, "Black" for Clubs/Spades

    public KingsCard(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
        // Set color based on suit
        if (suit.equalsIgnoreCase("Hearts") || suit.equalsIgnoreCase("Diamonds")) {
            this.color = "Red";
        } else {
            this.color = "Black";
        }
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public String getColor() {
        return color;
    }

    /**
     * Converts the rank to a numerical value.
     * Ace is 1, King is 13.
     */
    public int getRankValue() {
        switch (rank) {
            case "Ace":    return 1;
            case "2":      return 2;
            case "3":      return 3;
            case "4":      return 4;
            case "5":      return 5;
            case "6":      return 6;
            case "7":      return 7;
            case "8":      return 8;
            case "9":      return 9;
            case "10":     return 10;
            case "Jack":   return 11;
            case "Queen":  return 12;
            case "King":   return 13;
            default:       return 0;
        }
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}