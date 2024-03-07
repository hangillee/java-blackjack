package domain;

import controller.dto.CardStatus;
import controller.dto.CardsStatus;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int BLACKJACK_SCORE = 21;

    private final Participant participant;
    private final Cards cards;

    public Game(final Dealer dealer, final List<String> playerNames) {
        List<Player> players = playerNames.stream()
                .map(Player::new)
                .toList();
        this.participant = new Participant(dealer, players);
        cards = new Cards();
    }

    public Game(final Participant participant, final Cards cards) {
        this.participant = participant;
        this.cards = cards;
    }

    public CardsStatus initiateGameCondition() {
        List<CardStatus> status = new ArrayList<>();
        Dealer dealer = participant.dealer();
        status.add(new CardStatus(dealer.getName(), pickTwoCards(dealer)));
        for (Player player : participant.players()) {
            status.add(new CardStatus(player.getName(), pickTwoCards(player)));
        }
        return new CardsStatus(status);
    }

    public boolean isNotDealerBlackJack() {
        Dealer dealer = participant.dealer();
        return !dealer.isBlackJack(BLACKJACK_SCORE);
    }

    private List<Card> pickTwoCards(final Player player) {
        player.saveCard(cards.pick());
        player.saveCard(cards.pick());
        return player.getCards();
    }

    public CardStatus pickOneCard(final String name) {
        List<Player> players = participant.players();
        Player foundPlayer = players.stream()
                .filter(player -> player.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        foundPlayer.saveCard(cards.pick());
        return new CardStatus(foundPlayer.getName(), foundPlayer.getCards());
    }


    public List<String> getPlayerNames() {
        List<Player> players = participant.players();
        return players.stream()
                .map(Player::getName)
                .toList();
    }

    public CardStatus getCurrentCardStatus(final String name) {
        List<Player> players = participant.players();
        Player foundPlayer = players.stream()
                .filter(player -> player.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalStateException::new);
        return new CardStatus(foundPlayer.getName(), foundPlayer.getCards());
    }

    public int giveCardsToDealer() {
        Dealer dealer = participant.dealer();
        int threshold = 16;
        int currentScore = dealer.calculateScore(21);

        int count = 0;
        while (currentScore <= threshold) {
            dealer.saveCard(cards.pick());
            currentScore = dealer.calculateScore(21);
            count++;
        }
        return count;
    }

    public Participant getParticipant() {
        return participant;
    }

    public List<Boolean> judge() {
        if (isBusted(participant.dealer())) {
            return judgePlayersIfDealerBusted();
        }
        boolean isDealerBlackJack = isBlackJack(participant.dealer());
        List<Boolean> gameResult = new ArrayList<>();
        if (isDealerBlackJack) {
            for (Player player : participant.players()) {
                if (isBlackJack(player)) {
                    if (player.hasMoreCard(participant.dealer())) {
                        gameResult.add(false);
                    } else {
                        gameResult.add(true);
                    }
                } else {
                    gameResult.add(false);
                }
            }
        } else { // 딜러가 블랙잭이 아님
            for (Player player : participant.players()) {
                if (isBlackJack(player)) {
                    gameResult.add(true);
                } else {
                    if (player.hasMoreScore(participant.dealer())) {
                        gameResult.add(true);
                    } else if (player.hasLessScore(participant.dealer())) {
                        gameResult.add(false);
                    } else {
                        if (player.hasMoreCard(participant.dealer())) {
                            gameResult.add(false);
                        } else {
                            gameResult.add(true);
                        }
                    }
                }
            }
        }
        return gameResult;
    }

    private boolean isBlackJack(final Player player) {
        return player.calculateScore(BLACKJACK_SCORE) == BLACKJACK_SCORE;
    }

    public List<Boolean> compareScore() {
        Dealer dealer = participant.dealer();
        int dealerScore = dealer.calculateScore(BLACKJACK_SCORE);

        List<Boolean> isWinner = new ArrayList<>();
        List<Player> players = participant.players();

        return players.stream()
                .map(
                        player -> isWinner.add(doesPlayerWin(player.calculateScore(BLACKJACK_SCORE), dealerScore))
                ).toList();
    }

    private boolean doesPlayerWin(final int playerScore, final int dealerScore) {
        return playerScore >= dealerScore;
    }

    public List<Boolean> judgePlayersIfDealerBusted() {
        List<Boolean> gameResult = new ArrayList<>();
        for (Player player : participant.players()) {
            if (isBusted(player)) {
                gameResult.add(false);
            } else {
                gameResult.add(true);
            }
        }
        return gameResult;
    }

    private boolean isBusted(final Player player) {
        return player.calculateScore(BLACKJACK_SCORE) > BLACKJACK_SCORE;
    }
}
