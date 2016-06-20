package com.stupidplebs.confectiondistrict.spaces

import spock.lang.Specification

import com.stupidplebs.confectiondistrict.Color

class ColorSpaceSpec extends Specification {
    def "null color parameter should throw NullPointerException"() {
        when:
        new ColorSpace(null, true, 17)

        then:
        NullPointerException e = thrown()
        e.message == "color cannot be null"

    }

    def "null sticky parameter should throw NullPointerException"() {
        when:
        new ColorSpace(Color.GREEN, null, 17)

        then:
        NullPointerException e = thrown()
        e.message == "sticky cannot be null"

    }
    def "null jumpAheadCount parameter should throw NullPointerException"() {
        when:
        new ColorSpace(Color.GREEN, true, null)

        then:
        NullPointerException e = thrown()
        e.message == "jumpAheadCount cannot be null"

    }

    def "negative jumpAheadCount parameter should throw IllegalArgumentException"() {
        when:
        new ColorSpace(Color.GREEN, -1)

        then:
        IllegalArgumentException e = thrown()
        e.message == "jumpAheadCount cannot be negative"

    }

    def "one parameter constructor should use false and 0"() {
        given:
        def space = new ColorSpace(Color.GREEN)

        expect:
        space.color == Color.GREEN
        !space.sticky
        space.jumpAheadCount == 0
        !space.trailhead

    }

    def "constructor without sticky parameter should use false"() {
        given:
        def space = new ColorSpace(Color.GREEN, 17)

        expect:
        space.color == Color.GREEN
        !space.sticky
        space.jumpAheadCount == 17
        space.trailhead

    }

    def "constructor without jumpAheadCount parameter should use 0"() {
        given:
        def nextSpace = new FinishSpace()

        def space = new ColorSpace(Color.GREEN, true)

        expect:
        space.color == Color.GREEN
        space.sticky
        space.jumpAheadCount == 0
        !space.trailhead

    }

    def "getters should return values supplied"() {
        given:
        def nextSpace = new FinishSpace()

        def space = new ColorSpace(Color.GREEN, true, 17)

        expect:
        space.color == Color.GREEN
        space.sticky
        space.jumpAheadCount == 17
        space.trailhead

    }

    def "instance should be unequal to null"() {
        given:
        def space = new ColorSpace(Color.GREEN, true, 17)

        and:
        def other = null

        expect:
        !space.equals(other)

    }

    def "instance should be equal to itself"() {
        given:
        def space = new ColorSpace(Color.GREEN, true, 17)

        and:
        def other = space

        expect:
        space.equals(other)

    }

    def "instance should be unequal to a non-ColorSpace object"() {
        given:
        def space = new ColorSpace(Color.GREEN, true, 17)

        and:
        def other = "this is not a ColorSpace object"

        expect:
        !space.equals(other)

    }

    def "instances differing only on color should be unequal and have unequal hashCodes"() {
        given:
        def space = new ColorSpace(Color.GREEN, true, 17)

        and:
        def other = new ColorSpace(Color.BLUE, true, 17)

        expect:
        !space.equals(other)
        space.hashCode() != other.hashCode()

    }

    def "instances differing only on sticky should be unequal and have unequal hashCodes"() {
        given:
        def space = new ColorSpace(Color.GREEN, true, 17)

        and:
        def other = new ColorSpace(Color.GREEN, false, 17)

        expect:
        !space.equals(other)
        space.hashCode() != other.hashCode()

    }

    def "instances differing only on jumpAheadCount should be unequal and have unequal hashCodes"() {
        given:
        def space = new ColorSpace(Color.GREEN, true, 17)

        and:
        def other = new ColorSpace(Color.GREEN, true, 18)

        expect:
        !space.equals(other)
        space.hashCode() != other.hashCode()

    }

    def "instances with equal color, sticky, and jumpAheadCount should be equal and have equal hashCodes"() {
        given:
        def space = new ColorSpace(Color.GREEN, true, 17)

        and:
        def other = new ColorSpace(Color.GREEN, true, 17)

        expect:
        space.equals(other)
        space.hashCode() == other.hashCode()

    }

    def "toString should output color, sticky, and jumpAheadCount"() {
        given:
        def space = new ColorSpace(Color.GREEN, true, 17)

        expect:
        space.toString() == "ColorSpace{color=GREEN, sticky=true, jumpAheadCount=17}"

    }

}
