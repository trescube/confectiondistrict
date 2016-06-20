package com.stupidplebs.confectiondistrict.board;

import com.stupidplebs.confectiondistrict.cards.Card;

public interface Board {
    Boolean isFinished(Integer currentSpace);

    Integer takeTurn(Integer currentSpace, Card card);
}
