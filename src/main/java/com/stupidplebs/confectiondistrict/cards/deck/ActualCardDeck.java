package com.stupidplebs.confectiondistrict.cards.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.stupidplebs.confectiondistrict.Confection;
import com.stupidplebs.confectiondistrict.Color;
import com.stupidplebs.confectiondistrict.cards.ConfectionCard;
import com.stupidplebs.confectiondistrict.cards.Card;
import com.stupidplebs.confectiondistrict.cards.ColorCard;

public class ActualCardDeck implements CardDeck {
    private final List<Card> entireDeck;

    private ActualCardDeck(final Map<Card, Integer> deck) {
        this.entireDeck = new ArrayList<>();

        for (final Map.Entry<Card, Integer> entry : deck.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                entireDeck.add(entry.getKey());
            }
        }

    }

    @Override
    public Queue<Card> shuffle() {
        final LinkedList<Card> shuffledDeck = new LinkedList<>(entireDeck);

        Collections.shuffle(shuffledDeck);

        return shuffledDeck;

    }

    public static class Builder {
        private final Map<Card, Integer> deck;

        public Builder() {
            deck = new HashMap<>();
        }

        public Builder singleMoveCard(final Color color, final Integer quantity) {
            if (null == color) {
                throw new NullPointerException("color cannot be null");
            }
            if (null == quantity) {
                throw new NullPointerException("quantity cannot be null");
            }

            deck.put(ColorCard.getSingleMove(color), quantity);
            return this;
        }

        public Builder doubleMoveCard(final Color color, final Integer quantity) {
            if (null == color) {
                throw new NullPointerException("color cannot be null");
            }
            if (null == quantity) {
                throw new NullPointerException("quantity cannot be null");
            }

            deck.put(ColorCard.getDoubleMove(color), quantity);
            return this;
        }

        public Builder confectionCard(final Confection confection) {
            if (null == confection) {
                throw new NullPointerException("confection cannot be null");
            }

            deck.put(new ConfectionCard(confection), 1);
            return this;
        }

        public ActualCardDeck build() {
            return new ActualCardDeck(deck);
        }

    }

}
