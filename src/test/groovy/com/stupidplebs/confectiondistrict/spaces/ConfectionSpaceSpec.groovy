package com.stupidplebs.confectiondistrict.spaces

import spock.lang.Specification

import com.stupidplebs.confectiondistrict.Confection

class ConfectionSpaceSpec extends Specification {
    def "null confection parameter should throw NullPointerException"() {
        when:
        new ConfectionSpace(null)

        then:
        NullPointerException e = thrown()
        e.message == "confection cannot be null"

    }

    def "all return values"() {
        given:
        def space = new ConfectionSpace(Confection.BLINTZ)

        expect:
        space.confection == Confection.BLINTZ

    }

    def "instance should be unequal to null"() {
        given:
        def space = new ConfectionSpace(Confection.BLINTZ)

        and:
        def other = null

        expect:
        !space.equals(other)

    }

    def "instance should be equal to itself"() {
        given:
        def space = new ConfectionSpace(Confection.BLINTZ)

        and:
        def other = space

        expect:
        space.equals(other)

    }

    def "instance should be unequal to a non-Player object"() {
        given:
        def space = new ConfectionSpace(Confection.BLINTZ)

        and:
        def other = "this is not a ConfectionSpace object"

        expect:
        !space.equals(other)

    }

    def "instances differing on confection should be unequal and have unequal hashCodes"() {
        given:
        def space = new ConfectionSpace(Confection.BLINTZ)

        and:
        def other = new ConfectionSpace(Confection.LICORICE)

        expect:
        !space.equals(other)
        space.hashCode() != other.hashCode()

    }

    def "instances with equal confection should be equal and have equal hashCodes"() {
        given:
        def space = new ConfectionSpace(Confection.BLINTZ)

        and:
        def other = new ConfectionSpace(Confection.BLINTZ)

        expect:
        space.equals(other)
        space.hashCode() == other.hashCode()

    }

    def "toString should output color and numberOfMoves"() {
        given:
        def space = new ConfectionSpace(Confection.BLINTZ)

        expect:
        space.toString() == "ConfectionSpace{confection=BLINTZ}"

    }

}
