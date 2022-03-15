package blackjack.domain.participant;

import blackjack.domain.card.Card;

public class Dealer extends Participant {

    private static final int DEALER_CARD_PIVOT = 17;
    private static final String DEALER_NAME = "딜러";

    public Dealer() {
        super.name = new Name(DEALER_NAME);
    }

    public boolean shouldReceive() {
        return hand.getScore() < DEALER_CARD_PIVOT;
    }

    public Card getOpenCard() {
        return hand.getCards().get(0);
    }
}