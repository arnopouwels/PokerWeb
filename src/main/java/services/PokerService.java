package services;

import cards.*;
import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Andre on 2015-01-12.
 */

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
        return newHand.toString() + " " + evaluate(newHand);
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


    public String evaluateHands() {

        String[] ranks = {"Straight flush", "Four of a kind", "Full house", "Flush", "Straight", "Three of a kind", "Two Pair", "One Pair", "High card"};

        Hand highestHand = handList.get(0);
        int indexHigh = 8;
        int index = 8;

        for (int i=0;i<ranks.length;i++) {
            if (evaluate(highestHand).compareTo(ranks[i]) == 0) {
                indexHigh = i;
            }
        }

        for (int i=1; i<handList.size();i++) {
            for (int j=0;j<ranks.length;j++) {
                if (evaluate(handList.get(i)).compareTo(ranks[j]) == 0) {
                    index = j;
                    if (index < indexHigh) {
                        indexHigh = index;
                        highestHand = handList.get(i);
                    }
                }
            }

        }



        return highestHand.toString();
    }
}
