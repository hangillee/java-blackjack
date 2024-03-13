package view;

import controller.dto.dealer.DealerHand;
import controller.dto.gamer.GamerHand;
import domain.Card;
import domain.Gamers;
import domain.Hand;
import java.util.List;
import java.util.stream.Collectors;

public enum CardName {
    ACE("A"),
    TWO("2"),
    THREE("3"),
    FOUR("4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K");

    private final String name;

    CardName(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getHandStatusAsString(final Hand hand) {
        List<Card> cardsInHand = hand.getCards();

        return cardsInHand.stream()
                .map(card -> CardName.valueOf(card.name()).getName() + card.getShape())
                .collect(Collectors.joining(", "));
    }

    public static List<GamerHand> getGamerHandStatus(final Gamers gamers) {
        return gamers.listOf().stream()
                .map(gamer -> new GamerHand(gamer.getName(), getHandStatusAsString(gamer.getHand())))
                .toList();
    }

    public static DealerHand getDealerHandWithHiddenCard(final Hand hand) {
        return new DealerHand(getDealerHandAfterStartGame(hand));
    }

    private static String getDealerHandAfterStartGame(final Hand hand) {
        List<Card> cardsInHand = hand.getCards();
        Card firstCard = cardsInHand.get(0);
        return firstCard.getScore() + firstCard.getShape();
    }
}
