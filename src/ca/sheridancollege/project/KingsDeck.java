/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;
import java.util.ArrayList;
import java.util.Collections;
/**
 *
 * @author arora
 */
public class KingsDeck extends GroupOfCards {

    public KingsDeck() {
        // Initialize with 52 cards.
        super(52);
        cards = new ArrayList<>();
        initializeDeck();
    }
    
    private void initializeDeck() {
        String[] suits = {"Hearts", "Spades", "Clubs", "Diamonds"};
        String[] ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new KingsCard(rank, suit));
            }
        }
    }
    
    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }
}
