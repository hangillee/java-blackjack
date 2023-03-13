package blackjack.domain.gameplayer;

import blackjack.domain.card.Card;
import blackjack.domain.card.Cards;

public abstract class BlackJackParticipant implements User {

    private static final int BLACKJACK_SIZE = 2;

    protected final Cards cards;
    protected final Score blackJackScore = Score.of(21);

    public BlackJackParticipant(Cards cards) {
        this.cards = cards;
    }

    public boolean isBurst() {
        return calculateScore().isGreaterThan(blackJackScore);
    }

    public boolean isBlackJack() {
        return cards.getSize() == BLACKJACK_SIZE && calculateScore().isEqualTo(blackJackScore);
    }

    @Override
    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public Score calculateScore() {
        return Score.from(cards);
    }
}