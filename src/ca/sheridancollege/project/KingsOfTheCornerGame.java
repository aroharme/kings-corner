/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.sheridancollege.project;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author arora
 */
public class KingsOfTheCornerGame extends Game {
    
    private KingsDeck deck;
    private ArrayList<ArrayList<Card>> playPiles;
    private ArrayList<Card> cornerPiles;          
    private ArrayList<Card> drawPile;
    
    private int currentPlayerIndex;
    private Scanner scanner = new Scanner(System.in);
    private int roundCount = 1;
    
    public KingsOfTheCornerGame(String name) {
        super(name);
        // Create 4 players.
        ArrayList<Player> players = new ArrayList<>();
        players.add(new KingsPlayer("Player 1"));
        players.add(new KingsPlayer("Player 2"));
        
        setPlayers(players);
    }
    
    @Override
    public void play() {
        boolean gameWon = false;
        while (!gameWon) {
            System.out.println("***** Starting Round " + roundCount + " *****");
            startRound();
            gameWon = playRound();
            
            if (!gameWon) {
                if (roundCount == 1) {
                    System.out.println("Round tied. Starting a new round...");
                    roundCount++;
                } else {
                    gameWon = true;
                    declareWinner();
                }
            }
        }
    }
    
    private void startRound() {
        deck = new KingsDeck();
        deck.shuffle();
        
        // Set up corner piles for Kings.
        cornerPiles = new ArrayList<>();
        ArrayList<Card> tempDeck = new ArrayList<>(deck.getCards());
        for (Card card : tempDeck) {
            KingsCard kCard = (KingsCard) card;
            if (kCard.getRank().equals("King")) {
                cornerPiles.add(kCard);
                deck.getCards().remove(card);
                if (cornerPiles.size() == 4) {
                    break;
                }
            }
        }
        
        playPiles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Card> pile = new ArrayList<>();
            if (!deck.getCards().isEmpty()) {
                pile.add(deck.getCards().remove(0));
            }
            playPiles.add(pile);
        }
        
        drawPile = deck.getCards();
        
        for (Player p : getPlayers()) {
            KingsPlayer kp = (KingsPlayer) p;
            kp.getHand().clear();
            for (int j = 0; j < 7; j++) {
                if (!drawPile.isEmpty()) {
                    kp.addCard(drawPile.remove(0));
                }
            }
        }
        
        currentPlayerIndex = new Random().nextInt(getPlayers().size());
    }
    
    private boolean playRound() {
        boolean roundWon = false;
        while (!roundWon) {
            KingsPlayer currentPlayer = (KingsPlayer) getPlayers().get(currentPlayerIndex);
            System.out.println("\n" + currentPlayer.getName() + "'s turn.");
            displayPlayPiles();
            currentPlayer.displayHand();
            
            if (hasValidMove(currentPlayer)) {
                boolean moveMade = false;
                while (!moveMade) {
                    int cardChoice = Integer.parseInt(currentPlayer.getInput("Select a card number to play: ")) - 1;
                    int pileChoice = Integer.parseInt(currentPlayer.getInput("Select a play pile (1-4): ")) - 1;
                    
                    if (cardChoice < 0 || cardChoice >= currentPlayer.getHand().size() ||
                        pileChoice < 0 || pileChoice >= playPiles.size()) {
                        System.out.println("Invalid selection. Try again.");
                        continue;
                    }
                    
                    Card chosenCard = currentPlayer.getHand().get(cardChoice);
                    
                    if (isValidMove(chosenCard, playPiles.get(pileChoice))) {
                        playPiles.get(pileChoice).add(chosenCard);
                        currentPlayer.removeCard(chosenCard);
                        System.out.println("Played " + chosenCard.toString() + " on play pile " + (pileChoice + 1));
                        moveMade = true;
                        
                        if (currentPlayer.getHand().isEmpty()) {
                            System.out.println(currentPlayer.getName() + " wins the round!");
                            return true;
                        }
                    } else {
                        System.out.println("Invalid move according to the rules. Try again.");
                    }
                }
            } else {
                if (!drawPile.isEmpty()) {
                    String drawInput = currentPlayer.getInput("No valid moves. Draw a card? (YES/NO): ");
                    if (drawInput.equalsIgnoreCase("YES")) {
                        Card drawnCard = drawPile.remove(0);
                        System.out.println("You drew: " + drawnCard.toString());
                        boolean playedDrawn = false;
                        for (int i = 0; i < playPiles.size(); i++) {
                            if (isValidMove(drawnCard, playPiles.get(i))) {
                                playPiles.get(i).add(drawnCard);
                                playedDrawn = true;
                                System.out.println("Played drawn card on play pile " + (i + 1));
                                break;
                            }
                        }
                        if (!playedDrawn) {
                            System.out.println("Drawn card is not playable.");
                            String ct = currentPlayer.getInput("Change turn? (YES required): ");
                        }
                    } else {
                        System.out.println("Turn is being changed.");
                    }
                } else {
                    System.out.println("Draw pile is empty and no valid moves exist. Changing turn.");
                }
            }
            
            currentPlayerIndex = (currentPlayerIndex + 1) % getPlayers().size();
            
            if (drawPile.isEmpty() && !hasAnyValidMoveForAllPlayers()) {
                System.out.println("No valid moves available for any player.");
                return false;
            }
        }
        return roundWon;
    }
    
    private void displayPlayPiles() {
        System.out.println("Play Piles:");
        for (int i = 0; i < playPiles.size(); i++) {
            ArrayList<Card> pile = playPiles.get(i);
            Card topCard = pile.get(pile.size() - 1);
            System.out.println("Pile " + (i + 1) + " (Top card: " + topCard.toString() + ")");
        }
        System.out.println("Corner Piles (Kings):");
        for (int i = 0; i < cornerPiles.size(); i++) {
            System.out.println("Corner " + (i + 1) + ": " + cornerPiles.get(i).toString());
        }
    }
    
    /**
     * Valid move if the card is exactly one rank lower than the top card of the pile 
     * and is of the opposite color.
     */
    private boolean isValidMove(Card card, ArrayList<Card> pile) {
        if (pile.isEmpty()) return false;
        KingsCard topCard = (KingsCard) pile.get(pile.size() - 1);
        KingsCard playingCard = (KingsCard) card;
        if (playingCard.getRankValue() == topCard.getRankValue() - 1) {
            if (!playingCard.getColor().equalsIgnoreCase(topCard.getColor())) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasValidMove(KingsPlayer player) {
        for (Card card : player.getHand()) {
            for (ArrayList<Card> pile : playPiles) {
                if (isValidMove(card, pile)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean hasAnyValidMoveForAllPlayers() {
        for (Player p : getPlayers()) {
            if (hasValidMove((KingsPlayer) p)) {
                return true;
            }
        }
        return false;
    }

    
    @Override
    public void declareWinner() {
        int minCards = Integer.MAX_VALUE;
        String winner = "";
        for (Player p : getPlayers()) {
            int handSize = ((KingsPlayer) p).getHand().size();
            System.out.println(p.getName() + " has " + handSize + " cards.");
            if (handSize < minCards) {
                minCards = handSize;
                winner = p.getName();
            }
        }
        System.out.println("After tie-break, the winner is " + winner + "!");
    }
    
    public static void main(String[] args) {
        KingsOfTheCornerGame game = new KingsOfTheCornerGame("Kings of the Corner");
        game.play();
    }
}
