package com.stupidplebs.confectiondistrict.cards

import spock.lang.Specification

import com.stupidplebs.confectiondistrict.Color

class ColorCardSpec extends Specification {
    def "null color parameter to single-move card should throw NullPointerException"() {
        when:
        ColorCard.getSingleMove(null);

        then:
        NullPointerException e = thrown();
        e.message == "color cannot be null"

    }

    def "null color parameter to double-move card should throw NullPointerException"() {
        when:
        ColorCard.getDoubleMove(null);

        then:
        NullPointerException e = thrown();
        e.message == "color cannot be null"

    }

    def "getSingleMove should return color and 1 number of moves"() {
        given:
        def card = ColorCard.getSingleMove(Color.GREEN);

        expect:
        card instanceof ColorCard
        card.color == Color.GREEN
        card.numberOfMoves == 1

    }

    def "getDoubleMove should return color and 2 number of moves"() {
        given:
        def card = ColorCard.getDoubleMove(Color.BLUE);

        expect:
        card instanceof ColorCard
        card.color == Color.BLUE
        card.numberOfMoves == 2

    }

    def "instance should be unequal to null"() {
        given:
        def card = ColorCard.getDoubleMove(Color.BLUE)

        and:
        def other = null

        expect:
        !card.equals(other)

    }

    def "instance should be equal to itself"() {
        given:
        def card = ColorCard.getDoubleMove(Color.BLUE)

        and:
        def other = card

        expect:
        card.equals(other)

    }

    def "instance should be unequal to a non-Player object"() {
        given:
        def card = ColorCard.getDoubleMove(Color.BLUE)

        and:
        def other = "this is not a ColorCard object"

        expect:
        !card.equals(other)

    }

    def "instances differing on color should be unequal and have unequal hashCodes"() {
        given:
        def card = ColorCard.getDoubleMove(Color.BLUE)

        and:
        def other = ColorCard.getDoubleMove(Color.GREEN)

        expect:
        !card.equals(other)
        card.hashCode() != other.hashCode()

    }

    def "instances differing on number of moves should be unequal and have unequal hashCodes"() {
        given:
        def card = ColorCard.getDoubleMove(Color.BLUE)

        and:
        def other = ColorCard.getSingleMove(Color.BLUE)

        expect:
        !card.equals(other)
        card.hashCode() != other.hashCode()

    }

    def "instances with equal names should be equal and have equal hashCodes"() {
        given:
        def card = ColorCard.getDoubleMove(Color.BLUE)

        and:
        def other = ColorCard.getDoubleMove(Color.BLUE)

        expect:
        card.equals(other)
        card.hashCode() == other.hashCode()

    }

    def "toString should output color and numberOfMoves"() {
        given:
        def card = ColorCard.getDoubleMove(Color.BLUE)

        expect:
        card.toString() == "ColorCard{color=BLUE, numberOfMoves=2}"

    }

}
