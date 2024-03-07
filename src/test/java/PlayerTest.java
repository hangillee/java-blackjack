import domain.Card;
import domain.Player;
import domain.constants.CardValue;
import domain.constants.Shape;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PlayerTest {

    @DisplayName("카드를 저장한다.")
    @Test
    void saveCard() {
        Player player = new Player("pobi");
        player.saveCard(new Card(CardValue.ACE, Shape.CLOVER));
        int totalSize = player.getTotalSize();
        Assertions.assertThat(totalSize).isEqualTo(1);
    }


    @DisplayName("카드의 총 점수를 계산한다.")
    @Nested
    class calculateScore {
        @DisplayName("에이스 카드가 없는 경우 단순 합산한다.")
        @Test
        void calculateScoreWithNoAce() {
            Player player = new Player("pobi");
            player.saveCard(new Card(CardValue.EIGHT, Shape.CLOVER));
            player.saveCard(new Card(CardValue.NINE, Shape.CLOVER));
            int totalScore = player.calculateScore(21);
            Assertions.assertThat(totalScore).isEqualTo(17);
        }

        @DisplayName("에이스 카드가 11로 계산되었을 때 21을 초과하면 1로 계산한다.")
        @Test
        void calculateScoreWithAceIfBusted() {
            Player player = new Player("pobi");
            player.saveCard(new Card(CardValue.EIGHT, Shape.CLOVER));
            player.saveCard(new Card(CardValue.THREE, Shape.CLOVER));
            player.saveCard(new Card(CardValue.ACE, Shape.CLOVER));

            int totalScore = player.calculateScore(21);
            Assertions.assertThat(totalScore).isEqualTo(12);
        }
    }

    @DisplayName("에이스 카드가 포함된 카드를 받았을 때 1로 계산한다.")
    @Test
    void drawAceCardAndCalculateScoreOne() {
        Player player = new Player("pobi");
        player.saveCard(new Card(CardValue.EIGHT, Shape.CLOVER));
        player.saveCard(new Card(CardValue.THREE, Shape.CLOVER));
        player.saveCard(new Card(CardValue.ACE, Shape.CLOVER));

        int totalScore = player.calculateScoreWhileDraw();
        Assertions.assertThat(totalScore).isEqualTo(12);
    }
}
