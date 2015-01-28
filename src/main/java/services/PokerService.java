package services;

import cards.*;
import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@Singleton
public class PokerService
{
    //List<Card> hand;
    List<Card> deck;

    public List<Hand> handList;
    public String getName()
    {
        return "Andre";
    }

    public List<Card> createDeck()
    {
        handList = new ArrayList<>();
        deck = new ArrayList<>();
        for (Suit suit : Suit.values())
        {
            for (Rank rank : Rank.values())
            {
                deck.add(new Card(rank,suit));
            }
        }
        shuffle();
        return deck;
    }

    public void shuffle()
    {
        Collections.shuffle(deck);

            //System.out.println(deck.toString());

        //return deck;
    }

    List<Card> hand;

    public Hand dealHand()
    {
        if (deck == null || deck.size() < 5 )
        {
            deck = createDeck();
        }

        hand = new ArrayList<>();
        for (int i = 0; i < 5; i++)
        {
            hand.add(deck.get(i));
            deck.remove(i);
        }

        String a = hand.get(0).toString();
        String b = hand.get(1).toString();
        String c = hand.get(2).toString();
        String d = hand.get(3).toString();
        String e = hand.get(4).toString();

        Hand h1 = new Hand(a,b,c,d,e);
        return h1;
    }

    public String test()
    {
        //createDeck();
        Hand newHand = dealHand();
        handList.add(newHand);
        return newHand.toString();
    }

    public String getImage(Card card) {

        if (card.toString().compareTo("A♣") == 0) {
            return "ace_of_clubs";
        }
        if (card.toString().compareTo("2♣") == 0) {
            return "2_of_clubs";
        }
        if (card.toString().compareTo("3♣") == 0) {
            return "3_of_clubs";
        }
        if (card.toString().compareTo("4♣") == 0) {
            return "4_of_clubs";
        }
        if (card.toString().compareTo("5♣") == 0) {
            return "5_of_clubs";
        }
        if (card.toString().compareTo("6♣") == 0) {
            return "6_of_clubs";
        }
        if (card.toString().compareTo("7♣") == 0) {
            return "7_of_clubs";
        }
        if (card.toString().compareTo("8♣") == 0) {
            return "8_of_clubs";
        }
        if (card.toString().compareTo("9♣") == 0) {
            return "9_of_clubs";
        }
        if (card.toString().compareTo("10♣") == 0) {
            return "10_of_clubs";
        }
        if (card.toString().compareTo("J♣") == 0) {
            return "jack_of_clubs2";
        }
        if (card.toString().compareTo("Q♣") == 0) {
            return "queen_of_clubs2";
        }
        if (card.toString().compareTo("K♣") == 0) {
            return "king_of_clubs2";
        }


        if (card.toString().compareTo("A♦") == 0) {
            return "ace_of_diamonds";
        }
        if (card.toString().compareTo("2♦") == 0) {
            return "2_of_diamonds";
        }
        if (card.toString().compareTo("3♦") == 0) {
            return "3_of_diamonds";
        }
        if (card.toString().compareTo("4♦") == 0) {
            return "4_of_diamonds";
        }
        if (card.toString().compareTo("5♦") == 0) {
            return "5_of_diamonds";
        }
        if (card.toString().compareTo("6♦") == 0) {
            return "6_of_diamonds";
        }
        if (card.toString().compareTo("7♦") == 0) {
            return "7_of_diamonds";
        }
        if (card.toString().compareTo("8♦") == 0) {
            return "8_of_diamonds";
        }
        if (card.toString().compareTo("9♦") == 0) {
            return "9_of_diamonds";
        }
        if (card.toString().compareTo("10♦") == 0) {
            return "10_of_diamonds";
        }
        if (card.toString().compareTo("J♦") == 0) {
            return "jack_of_diamonds2";
        }
        if (card.toString().compareTo("Q♦") == 0) {
            return "queen_of_diamonds2";
        }
        if (card.toString().compareTo("K♦") == 0) {
            return "king_of_diamonds2";
        }

        if (card.toString().compareTo("A♥") == 0) {
            return "ace_of_hearts";
        }
        if (card.toString().compareTo("2♥") == 0) {
            return "2_of_hearts";
        }
        if (card.toString().compareTo("3♥") == 0) {
            return "3_of_hearts";
        }
        if (card.toString().compareTo("4♥") == 0) {
            return "4_of_hearts";
        }
        if (card.toString().compareTo("5♥") == 0) {
            return "5_of_hearts";
        }
        if (card.toString().compareTo("6♥") == 0) {
            return "6_of_hearts";
        }
        if (card.toString().compareTo("7♥") == 0) {
            return "7_of_hearts";
        }
        if (card.toString().compareTo("8♥") == 0) {
            return "8_of_hearts";
        }
        if (card.toString().compareTo("9♥") == 0) {
            return "9_of_hearts";
        }
        if (card.toString().compareTo("10♥") == 0) {
            return "10_of_hearts";
        }
        if (card.toString().compareTo("J♥") == 0) {
            return "jack_of_hearts2";
        }
        if (card.toString().compareTo("Q♥") == 0) {
            return "queen_of_hearts2";
        }
        if (card.toString().compareTo("K♥") == 0) {
            return "king_of_hearts2";
        }


        if (card.toString().compareTo("A♠") == 0) {
            return "ace_of_spades";
        }
        if (card.toString().compareTo("2♠") == 0) {
            return "2_of_spades";
        }
        if (card.toString().compareTo("3♠") == 0) {
            return "3_of_spades";
        }
        if (card.toString().compareTo("4♠") == 0) {
            return "4_of_spades";
        }
        if (card.toString().compareTo("5♠") == 0) {
            return "5_of_spades";
        }
        if (card.toString().compareTo("6♠") == 0) {
            return "6_of_spades";
        }
        if (card.toString().compareTo("7♠") == 0) {
            return "7_of_spades";
        }
        if (card.toString().compareTo("8♠") == 0) {
            return "8_of_spades";
        }
        if (card.toString().compareTo("9♠") == 0) {
            return "9_of_spades";
        }
        if (card.toString().compareTo("10♠") == 0) {
            return "10_of_spades";
        }
        if (card.toString().compareTo("J♠") == 0) {
            return "jack_of_spades2";
        }
        if (card.toString().compareTo("Q♠") == 0) {
            return "queen_of_spades2";
        }
        if (card.toString().compareTo("K♠") == 0) {
            return "king_of_spades2";
        }

        return "";
    }

    public List<Card> getHandList() {
        return hand;
    }

    public String evaluate(Hand hand)
    {
        if (HandEvaluator.isFlush(hand))
            return "Flush";
        else if (HandEvaluator.isFourOfAKind(hand))
            return "Four of a kind";
        else if (HandEvaluator.isFullHouse(hand))
            return "Full house";
        else if (HandEvaluator.isOnePair(hand))
            return "One Pair";
        else if (HandEvaluator.isTwoPair(hand))
            return "Two Pair";
        else if (HandEvaluator.isStraight(hand))
            return "Straight";
        else if (HandEvaluator.isThreeOfAKind(hand))
            return "Three of a kind";
        else if (HandEvaluator.isStraightFlush(hand))
            return "Straight flush";
        else return "High card";

        //return " ";
    }


    public Hand evaluateHands(List<Hand> handsList) {

        String[] ranks = {"Straight flush", "Four of a kind", "Full house", "Flush", "Straight", "Three of a kind", "Two Pair", "One Pair", "High card"};

        Hand highestHand = handsList.get(0);
        int indexHigh = 8;
        int index = 8;

        for (int i=0;i<ranks.length;i++) {
            if (evaluate(highestHand).compareTo(ranks[i]) == 0) {
                indexHigh = i;
            }
        }

        for (int i=1; i<handsList.size();i++) {
            for (int j=0;j<ranks.length;j++) {
                if (evaluate(handsList.get(i)).compareTo(ranks[j]) == 0) {
                    index = j;
                    if (index < indexHigh) {
                        indexHigh = index;
                        highestHand = handsList.get(i);
                    }
                }
            }

        }



        return highestHand;
    }
}
