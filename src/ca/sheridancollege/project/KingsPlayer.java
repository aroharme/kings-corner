/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;
import java.util.ArrayList;
import java.util.Scanner;
/**
 *
 * @author arora
 */
public class KingsPlayer extends Player {
    private ArrayList<Card> hand;
    private Scanner scanner = new Scanner(System.in);  // for console input

    public KingsPlayer(String name) {
        super(name);
        hand = new ArrayList<>();
    }
    
    public void addCard(Card card) {
        hand.add(card);
    }
    
    public void removeCard(Card card) {
        hand.remove(card);
    }
    
    public ArrayList<Card> getHand() {
        return hand;
    }
    
    @Override
    public void play() {
        // Turn logic is handled in the game class.
    }
    
    // Utility method to get user input.
    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
    
    // Display the player's hand.
    public void displayHand() {
        System.out.println("Your hand:");
        for (int i = 0; i < hand.size(); i++) {
            System.out.println((i + 1) + ". " + hand.get(i).toString());
        }
    }
}

