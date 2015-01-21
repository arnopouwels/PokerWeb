package cards;

import cards.Card;
import cards.Hand;
import cards.Rank;
import cards.Suit;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HandEvaluator {

    public static boolean isStraightFlush(Hand hand) {
        List<Rank> ranks = hand.getCards().stream().map(c -> c.getRank()).collect(Collectors.toList());
        return hand.getCards().stream().allMatch(c -> c.getSuit() == hand.getCards().get(0).getSuit())
                && (ranks.stream().mapToInt(r-> r.ordinal()).max().getAsInt() -
                ranks.stream().mapToInt(r -> r.ordinal()).min().getAsInt() == 4)
                && ranks.stream().distinct().count() == 5;
    }

    public static boolean isFourOfAKind(Hand hand) {
        List<Long> counted = hand.getCards().stream()
                .collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());
        if (counted.size() == 2 && counted.get(1) == 4)
        {
            return true;
        }
        return false;
    }

    public static boolean isFullHouse(Hand hand) {
        List<Long> counted = hand.getCards().stream()
                .collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());
        if (counted.size() == 2 && counted.get(0) == 2 && counted.get(1) == 3)
        {
            return true;
        }
        return false;
    }

    public static boolean isFlush(Hand hand) {
        if (isStraightFlush(hand)) return false;

        List<Rank> ranks = hand.getCards().stream().map(c -> c.getRank()).collect(Collectors.toList());
        return hand.getCards().stream().allMatch(c -> c.getSuit() == hand.getCards().get(0).getSuit())

                && ranks.stream().distinct().count() == 5;
    }

    public static boolean isStraight(Hand hand) {
        List<Rank> ranks = hand.getCards().stream().map(c -> c.getRank()).collect(Collectors.toList());
        return (ranks.stream().mapToInt(r-> r.ordinal()).max().getAsInt() -
                ranks.stream().mapToInt(r -> r.ordinal()).min().getAsInt() == 4)
                && ranks.stream().distinct().count() == 5;
    }

    public static boolean isThreeOfAKind(Hand hand) {
        List<Long> counted = hand.getCards().stream()
                .collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());
        if (counted.size() == 3 && counted.get(2) == 3)
        {
            return true;
        }
        return false;
    }

    public static boolean isOnePair(Hand hand) {
        List<Long> counted = hand.getCards().stream()
                .collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());
        if (counted.size() == 4 && counted.get(3) == 2)
        {
            return true;
        }
        return false;
    }

    public static boolean isTwoPair(Hand hand) {
        List<Long> counted = hand.getCards().stream()
                .collect(Collectors.groupingBy(o -> o.getRank().toString(), Collectors.counting()))
                .values().stream().sorted().collect(Collectors.toList());
        if (counted.size() == 3 && counted.get(2) == 2 && counted.get(1) == 2)
        {
            return true;
        }
        return false;
    }
}
