package com.stupidplebs.confectiondistrict.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.stupidplebs.confectiondistrict.Confection;
import com.stupidplebs.confectiondistrict.Color;
import com.stupidplebs.confectiondistrict.cards.ConfectionCard;
import com.stupidplebs.confectiondistrict.cards.Card;
import com.stupidplebs.confectiondistrict.cards.ColorCard;
import com.stupidplebs.confectiondistrict.spaces.ConfectionSpace;
import com.stupidplebs.confectiondistrict.spaces.ColorSpace;
import com.stupidplebs.confectiondistrict.spaces.FinishSpace;
import com.stupidplebs.confectiondistrict.spaces.Space;
import com.stupidplebs.confectiondistrict.spaces.StartSpace;

public class ActualBoard implements Board {
    private final List<Space> spaces;
    private final Map<Confection, Integer> confectionSpaces;
    private final Integer finishSpace;

    private ActualBoard(final List<Space> spaces) {
        this.spaces = new ArrayList<>();
        this.spaces.add(new StartSpace());
        this.spaces.addAll(spaces);
        this.spaces.add(new FinishSpace());

        // find all the confection spaces for easy lookup
        this.confectionSpaces = findConfectionSpaces(this.spaces);

        // shortcut for easy reference
        this.finishSpace = this.spaces.size() - 1;

    }

    public List<Space> getAllSpaces() {
        return Collections.unmodifiableList(spaces);
    }

    private Map<Confection, Integer> findConfectionSpaces(final List<Space> spaces) {
        final Map<Confection, Integer> confectionSpaces = new HashMap<>();

        for (int i = 0; i < spaces.size(); i++) {
            if (isConfectionSpace(i)) {
                confectionSpaces.put(((ConfectionSpace) spaces.get(i)).getConfection(), i);
            }
        }

        return confectionSpaces;

    }

    private Boolean isConfectionSpace(final Integer space) {
        return spaces.get(space) instanceof ConfectionSpace;
    }

    private Boolean isColorSpace(final Integer space) {
        return spaces.get(space) instanceof ColorSpace;
    }

    @Override
    public Boolean isFinished(final Integer currentSpace) {
        if (null == currentSpace) {
            throw new NullPointerException("currentSpace cannot be null");
        }
        if (currentSpace < 0 || currentSpace > spaces.size()) {
            throw new IllegalArgumentException("currentSpace outside of range");
        }

        return currentSpace == finishSpace || currentSpace + 1 == finishSpace;

    }

    private Boolean isColorMatch(final ColorSpace colorSpace, final ColorCard colorCard) {
        return colorSpace.getColor() == colorCard.getColor();
    }

    private Boolean mustWait(final Integer spaceIdx, final Color color) {
        return isColorSpace(spaceIdx) && ((ColorSpace) spaces.get(spaceIdx)).isSticky()
                && ((ColorSpace) spaces.get(spaceIdx)).getColor() != color;
    }

    private Boolean isTrailhead(final Integer spaceIdx) {
        return isColorSpace(spaceIdx) && ((ColorSpace) spaces.get(spaceIdx)).isTrailhead();
    }

    @Override
    public Integer takeTurn(Integer currentSpace, final Card card) {
        if (null == currentSpace) {
            throw new NullPointerException("currentSpace cannot be null");
        }
        if (null == card) {
            throw new NullPointerException("card cannot be null");
        }
        if (currentSpace < 0 || currentSpace > spaces.size()) {
            throw new IllegalArgumentException("currentSpace outside of range");
        }
        if (isFinished(currentSpace)) {
            return currentSpace;
        }

        if (card instanceof ConfectionCard) {
            final ConfectionCard confectionCard = (ConfectionCard) card;

            if (!confectionSpaces.containsKey(confectionCard.getConfection())) {
                throw new IllegalArgumentException(confectionCard.getConfection() + " space not found on board");
            }

            // if confection card, move player to that space
            return confectionSpaces.get(confectionCard.getConfection());

        } else {
            final ColorCard colorCard = (ColorCard) card;

            // bail out of this turn if the player is waiting for a color
            if (mustWait(currentSpace, colorCard.getColor())) {
                return currentSpace;
            }

            Integer numberOfMovesLeft = colorCard.getNumberOfMoves();

            // take shortcut if the space is a trailhead
            if (isTrailhead(currentSpace)) {
                final ColorSpace colorSpace = (ColorSpace) spaces.get(currentSpace);

                currentSpace += colorSpace.getTrailheadSpaceCount();

                // if the shortcut landing space is the target color, don't go
                // further
                if (isColorMatch(colorSpace, colorCard)) {
                    numberOfMovesLeft--;

                    if (numberOfMovesLeft == 0) {
                        return currentSpace;
                    }

                }

            }

            while (numberOfMovesLeft > 0 && !isFinished(currentSpace)) {
                currentSpace = advanceSpace(currentSpace + 1, colorCard.getColor());
                numberOfMovesLeft--;
            }

            return currentSpace;

        }
    }

    private Integer advanceSpace(Integer currentSpace, final Color color) {
        while (true) {
            // if the current space matches the target color, they've matched
            // the goal
            if (isColorSpace(currentSpace) && ((ColorSpace) spaces.get(currentSpace)).getColor() == color) {
                return currentSpace;
            }

            // if next space is finish space, then that's the final space
            if (currentSpace + 1 == finishSpace) {
                return finishSpace;
            }

            currentSpace++;

        }

    }

    public static class Builder {
        private final List<Space> spaces;

        public Builder() {
            this.spaces = new ArrayList<>();
        }

        public Builder color(final Color color) {
            this.spaces.add(new ColorSpace(color));
            return this;
        }

        public Builder sticky(final Color color) {
            this.spaces.add(new ColorSpace(color, true));
            return this;
        }

        public Builder trailhead(final Color color, final Integer jumpAheadCount) {
            this.spaces.add(new ColorSpace(color, jumpAheadCount));
            return this;
        }

        public Builder confection(final Confection confection) {
            this.spaces.add(new ConfectionSpace(confection));
            return this;
        }

        public ActualBoard build() {
            return new ActualBoard(spaces);
        }
    }

}
