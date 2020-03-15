package blackjack.domain.deck;

import blackjack.domain.card.Card;
import blackjack.domain.card.CardFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DeckTest {

    @Test
    @DisplayName("카드 꺼내기")
    void pick() {
        List<Card> cards = CardFactory.generate();
        Deck deck = new Deck(cards);
        assertThat(deck.pick()).isInstanceOf(Card.class);
    }

    @Test
    @DisplayName("카드 꺼내기")
    void pickThrowException() {
        List<Card> cards = CardFactory.generate();
        Deck deck = new Deck(cards);
        for (int i = 0; i < 52; i++) {
            deck.pick();
        }
        assertThatThrownBy(() -> deck.pick())
                .isInstanceOf(NullPointerException.class)
                .hasMessage("카드를 모두 사용하셨습니다.");
    }
}