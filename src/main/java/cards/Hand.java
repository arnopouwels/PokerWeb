package cards;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public final class Hand {

    private final List<Card> cards = new ArrayList<>(5);

    public Hand(String ... cardRepresentations) {
        if (cardRepresentations.length != 5) {
            throw new IllegalArgumentException("Exactly 5 cards are required.");
        }

        for (String cardRepresentation : cardRepresentations) {
            cards.add(new Card(cardRepresentation));
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(",", "(", ")");
        for (Card card : cards) {
            stringJoiner.add(card.toString());
        }
        return stringJoiner.toString();
    }
}
