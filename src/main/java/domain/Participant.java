package domain;

import java.util.List;

public record Participant(Dealer dealer, List<Gamer> gamers) {
}
