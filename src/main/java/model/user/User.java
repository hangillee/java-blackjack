package model.user;

import model.card.Card;
import model.card.Deck;

public class User {

    private final Name name;
    private final Hand hand;

    public User(final Name name) {
        this.name = name;
        this.hand = Hand.create();
    }

    public void receiveInitialCards(final Deck deck) {
        receiveCard(deck.pick());
        receiveCard(deck.pick());
    }

    public void receiveCard(final Card card) {
        this.hand.receiveCard(card);
    }

    public int calculateTotalValue() {
        return hand.getTotalValue();
    }

    public boolean isBlackJack() {
        return hand.isBlackJack();
    }

    public Name getName() {
        return this.name;
    }

    public Hand getHand() {
        return this.hand;
    }

    public int getCardTotalValue() {
        return hand.getTotalValue();
    }
}