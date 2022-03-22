package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;

import blackjack.domain.bet.BetMoney;
import blackjack.domain.card.Card;
import blackjack.domain.card.CardNumber;
import blackjack.domain.card.Deck;
import blackjack.domain.card.JustBlackjackDeck;
import blackjack.domain.card.JustTwoSpadeDeck;
import blackjack.domain.card.Suit;
import blackjack.domain.player.Dealer;
import blackjack.domain.player.Name;
import blackjack.domain.player.Participant;
import blackjack.domain.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OutComeTest {

    @Test
    @DisplayName("Outcome의 get을하면 결과를 문자열로 반환한다.")
    void get() {
        assertThat(Outcome.WIN.get()).isEqualTo("승");
        assertThat(Outcome.DRAW.get()).isEqualTo("무");
        assertThat(Outcome.LOSE.get()).isEqualTo("패");
    }

    @Test
    @DisplayName("Outcome의 matchAboutPlayer 메서드는 딜러와 플레이어가 모두 Bust라면 참가자가 패배했다고 판단한다.")
    void compare_all_bust() {
        Deck deck = new JustTwoSpadeDeck();
        Player dealer = new Dealer(deck);
        dealer.hit(Card.of(CardNumber.TEN, Suit.SPADE));
        dealer.hit(Card.of(CardNumber.TEN, Suit.HEART));

        Player player = new Participant(new Name("aki"), deck, new BetMoney(10));
        player.hit(Card.of(CardNumber.TEN, Suit.SPADE));
        player.hit(Card.of(CardNumber.TEN, Suit.DIAMOND));

        assertThat(Outcome.matchAboutPlayer((Dealer) dealer, player)).isEqualTo(Outcome.LOSE);
    }

    @Test
    @DisplayName("Outcome의 matchAboutPlayer 메서드는 플레이어가 Bust라면 무조건 참가자가 패배했다고 판단한다.")
    void compare_player_bust() {
        Deck deck = new JustTwoSpadeDeck();
        Player dealer = new Dealer(deck);
        dealer.hit(Card.of(CardNumber.SEVEN, Suit.CLUB));

        Player player = new Participant(new Name("aki"), deck, new BetMoney(10));
        player.hit(Card.of(CardNumber.TEN, Suit.SPADE));
        player.hit(Card.of(CardNumber.TEN, Suit.DIAMOND));

        assertThat(Outcome.matchAboutPlayer((Dealer) dealer, player)).isEqualTo(Outcome.LOSE);
    }

    @Test
    @DisplayName("Outcome의 matchAboutPlayer 메서드는 딜러만 Bust라면 무조건 참가자가 승리했다고 판단한다.")
    void compare_dealer_bust() {
        Deck deck = new JustTwoSpadeDeck();
        Player dealer = new Dealer(deck);
        dealer.hit(Card.of(CardNumber.TEN, Suit.CLUB));
        dealer.hit(Card.of(CardNumber.TEN, Suit.DIAMOND));

        Player player = new Participant(new Name("aki"), deck, new BetMoney(10));

        assertThat(Outcome.matchAboutPlayer((Dealer) dealer, player)).isEqualTo(Outcome.WIN);
    }

    @Test
    @DisplayName("Outcome의 matchAboutPlayer 메서드는 딜러가 블랙잭이고 플레이어가 블랙잭이 아니라면 참가자가 패배했다고 판단한다.")
    void compare_dealer_blackjack() {
        Deck blackjackDeck = new JustBlackjackDeck();
        Deck justTwoDeck = new JustTwoSpadeDeck();
        Player dealer = new Dealer(blackjackDeck);

        Player player = new Participant(new Name("aki"), justTwoDeck, new BetMoney(10));

        assertThat(Outcome.matchAboutPlayer((Dealer) dealer, player)).isEqualTo(Outcome.LOSE);
    }

    @Test
    @DisplayName("Outcome의 matchAboutPlayer 메서드는 플레이어가 블랙잭이고 딜러가 블랙잭이 아니라면 참가자가 승리했다고 판단한다.")
    void compare_player_blackjack() {
        Deck blackjackDeck = new JustBlackjackDeck();
        Deck justTwoDeck = new JustTwoSpadeDeck();

        Player dealer = new Dealer(justTwoDeck);

        Player player = new Participant(new Name("aki"), blackjackDeck, new BetMoney(10));

        assertThat(Outcome.matchAboutPlayer((Dealer) dealer, player)).isEqualTo(Outcome.WIN);
    }

    @Test
    @DisplayName("Outcome의 matchAboutPlayer 메서드는 딜러와 플레이어 모두 블랙잭이라면 무승부라고 판단한다.")
    void compare_player_and_dealer_blackjack() {
        Deck blackjackDeck = new JustBlackjackDeck();
        Player dealer = new Dealer(blackjackDeck);

        Player player = new Participant(new Name("aki"), blackjackDeck, new BetMoney(10));

        assertThat(Outcome.matchAboutPlayer((Dealer) dealer, player)).isEqualTo(Outcome.DRAW);
    }

    @Test
    @DisplayName("Outcome의 matchAboutPlayer 메서드는 딜러와 플레이어의 점수를 비교하여 승무패를 판단한다.")
    void compare_player_and_dealer() {
        Deck deck = new JustTwoSpadeDeck();
        Player dealer = new Dealer(deck);
        dealer.hit(Card.of(CardNumber.FIVE, Suit.CLUB));

        Player player = new Participant(new Name("aki"), deck, new BetMoney(10));

        assertThat(Outcome.matchAboutPlayer((Dealer) dealer, player)).isEqualTo(Outcome.LOSE);
    }
}