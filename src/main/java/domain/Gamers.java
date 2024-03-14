package domain;

import java.util.Collections;
import java.util.List;

public class Gamers {
    private final List<Gamer> gamers;

    public Gamers(final List<Gamer> gamers) {
        this.gamers = gamers;
    }

    public List<Gamer> listOf() {
        return Collections.unmodifiableList(gamers);
    }

    public List<String> getNames() {
        return gamers.stream()
                .map(Gamer::getName)
                .toList();
    }

    public List<Hand> getHands() {
        return gamers.stream()
                .map(Gamer::getHand)
                .toList();
    }
}
