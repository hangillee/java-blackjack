package controller;

import static view.CardName.getDealerHandWithHiddenCard;
import static view.CardName.getGamerHandStatus;
import static view.CardName.getHandStatusAsString;

import controller.dto.dealer.DealerHand;
import controller.dto.gamer.GamerHand;
import domain.GameHost;
import domain.Gamer;
import domain.Gamers;
import java.util.List;
import view.GameCommand;
import view.InputView;
import view.OutputView;

public class GameHostController {
    private final GameHost gameHost = new GameHost(InputView.enterPlayerNames());

    public GameHost initGame() {
        gameHost.readyGame();
        return gameHost;
    }

    public void playGame() {
        printInitiateGameResult();
        startCardDraw();
        printDealerDrawMessage();
    }

    private void printInitiateGameResult() {
        List<String> gamerNames = gameHost.gamerNames();
        OutputView.printNoticeAfterStartGame(gamerNames);

        DealerHand dealerHandWithHiddenCard = getDealerHandWithHiddenCard(gameHost.dealerHand());
        OutputView.printDealerStatus(dealerHandWithHiddenCard);

        List<GamerHand> gamerHandStatus = getGamerHandStatus(gamerNames, gameHost.gamerHands());
        OutputView.printPlayerStatus(gamerNames, gamerHandStatus);
    }

    private void startCardDraw() {
        Gamers gamers = gameHost.findPlayingGamers();
        for (Gamer gamer : gamers.listOf()) {
            makeGamerToDrawCard(gamer);
        }
    }

    private void makeGamerToDrawCard(final Gamer gamer) {
        GamerHand currentHand = new GamerHand(gamer.getName(), getHandStatusAsString(gamer.getHand()));
        GameCommand command = inputCommand(gamer.getName());

        while (command.isHit()) {
            gameHost.drawOneCardToGamer(gamer);
            currentHand = getStatusAfterDraw(gamer);
            command = getCommandAfterDraw(gamer);
        }
        OutputView.printCardStatus(gamer.getName(), currentHand);
    }

    private GamerHand getStatusAfterDraw(final Gamer gamer) {
        return new GamerHand(gamer.getName(), getHandStatusAsString(gamer.getHand()));
    }

    private GameCommand getCommandAfterDraw(final Gamer gamer) {
        if (gamer.isNotAbleToDrawCard()) {
            return GameCommand.STAND;
        }
        OutputView.printCardStatus(gamer.getName(),
                new GamerHand(gamer.getName(), getHandStatusAsString(gamer.getHand())));
        return inputCommand(gamer.getName());
    }

    private GameCommand inputCommand(final String name) {
        return GameCommand.of(InputView.decideToGetMoreCard(name));
    }

    private void printDealerDrawMessage() {
        int count = gameHost.cardDrawCountOfDealer();
        OutputView.printDealerPickMessage(count);
    }
}
