package controller;

import static domain.constants.CardCommand.HIT;
import static domain.constants.CardCommand.STAND;

import controller.dto.GameResult;
import controller.dto.HandStatus;
import controller.dto.PlayerResult;
import domain.Dealer;
import domain.Game;
import domain.GameRule;
import domain.Participant;
import domain.Player;
import domain.constants.CardCommand;
import java.util.ArrayList;
import java.util.List;
import view.InputView;
import view.OutputView;

public class GameController {
    private final InputView inputView;
    private final OutputView outputView;

    public GameController(final InputView inputView, final OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Game game = start();
        startRound(game);
        giveCardToDealer(game);

        List<Integer> scores = new ArrayList<>();
        scores.add(game.getParticipant().dealer().calculateResultScore(21));
        for (Player player : game.getParticipant().players()) {
            scores.add(player.calculateResultScore(21));
        }
        outputView.printResult(getCurrentCardsStatus(game), scores);
        outputView.printGameResult(getResults(game));
    }

    private Game start() {
        List<String> names = inputView.enterPlayerNames();
        Dealer dealer = new Dealer("딜러");

        Game game = new Game(dealer, names);
        outputView.printAfterStartGame(game.initiateGameCondition());
        return game;
    }

    private List<HandStatus> getCurrentCardsStatus(final Game game) {
        List<HandStatus> handStatuses = new ArrayList<>();

        Participant participant = game.getParticipant();
        Dealer dealer = participant.dealer();
        List<Player> players = participant.players();

        handStatuses.add(new HandStatus(dealer.getName(), dealer.getHand()));
        for (Player player : players) {
            handStatuses.add(new HandStatus(player.getName(), player.getHand()));
        }

        return handStatuses;
    }

    // TODO: indent 줄이기
    private void startRound(final Game game) {
        List<String> names = game.getPlayerNames();
        for (String name : names) {
            CardCommand command = CardCommand.from(inputView.decideToGetMoreCard(name));
            HandStatus status = game.getCurrentCardStatus(name);
            while (HIT.equals(command)) {
                status = game.pickOneCard(name);
                outputView.printCardStatus(status);
                int currentSum = game.getPlayer(name).calculateScoreWhileDraw();
                if (currentSum >= 21) {
                    break;
                }
                command = CardCommand.from(inputView.decideToGetMoreCard(name));
            }
            if (STAND.equals(command)) {
                outputView.printCardStatus(status);
            }
        }
    }

    private void giveCardToDealer(final Game game) {
        int count = game.giveCardsToDealer();
        outputView.printDealerPickMessage(count);
    }

    public GameResult getResults(final Game game) {
        GameRule rule = new GameRule(game.getParticipant());
        List<Boolean> results = rule.judge();
        List<String> names = game.getPlayerNames();

        List<PlayerResult> playerResults = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            playerResults.add(new PlayerResult(names.get(i), results.get(i)));
        }
        return new GameResult(playerResults);
    }
}
